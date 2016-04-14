package hillbillies.model.pathfinding;


import java.util.ArrayList;
import java.util.Collections;

//import javax.vecmath.Vector3d;

//import hillbillies.model.GameObject;
import hillbillies.model.Unit;
import hillbillies.model.World;


import static hillbillies.model.Constants.MAXSEARCHDEPTH;



/**
 * A path finder implementation that uses the AStar heuristic based algorithm
 * to determine a path. 
 * 
 * source: http://www.cokeandcode.com/main/tutorials/path-finding/
 * 
 * @author Kevin Glass
 * 
 * adapted for personal use by Toon Deburchgrave
 */
public class AStarPathFinder{
	/** The set of nodes that have been searched through */
	private ArrayList<Node> closed = new ArrayList<Node>();
	/** The set of nodes that we do not yet consider fully searched */
	private SortedList open = new SortedList();
	
	/** The map being searched */
	private World map;
	/** The maximum depth of search we're willing to accept before giving up */
	private int maxSearchDistance = MAXSEARCHDEPTH;
	
	/** The complete set of nodes across the map */
	private Node[][][] nodes;

	/** The heuristic we're applying to determine which nodes to search first */
	private Heuristic heuristic;
	
	/**
	 * Create a path finder with the default heuristic - closest to target.
	 * 
	 * @param map The map to be searched
	 * @param maxSearchDistance The maximum depth we'll search before giving up
	 * @param allowDiagMovement True if the search should try diaganol movement
	 */
	public AStarPathFinder(World map) {
		this(map, new Heuristic());
	}

	/**
	 * Create a path finder 
	 * 
	 * @param heuristic The heuristic used to determine the search order of the map
	 * @param map The map to be searched
	 * @param maxSearchDistance The maximum depth we'll search before giving up
	 * @param allowDiagMovement True if the search should try diaganol movement
	 */
	public AStarPathFinder(World map, Heuristic heuristic) {
		this.heuristic = heuristic;
		this.map = map;
		
		nodes = new Node[map.getNbCubesX()][map.getNbCubesY()][map.getNbCubesZ()];
		for (int x=0;x<map.getNbCubesX();x++) {
			for (int y=0;y<map.getNbCubesY();y++) {
				for (int z=0;z<map.getNbCubesZ();z++){
					nodes[x][y][z] = new Node(x,y,z);
				}
			}
		}
	}
	
	/**
	 * Find a path from the starting location provided (sx,sy) to the target
	 * location (tx,ty) avoiding blockages and attempting to honour costs 
	 * provided by the tile map.
	 * 
	 * @param mover 
	 * The entity that will be moving along the path. This provides
	 * a place to pass context information about the game entity doing the moving, e.g.
	 * can it fly? can it swim etc.
	 * 
	 * @param sx The x coordinate of the start location
	 * @param sy The y coordinate of the start location
	 * @param sz The z coordinate of the start location
	 * @param tx The x coordinate of the target location
	 * @param ty Teh y coordinate of the target location
	 * @param tz Teh z coordinate of the target location
	 * @return The path found from start to end, or null if no path can be found.
	 */
	public Path findPath(Unit mover, int sx, int sy, int sz, int tx, int ty, int tz) {
		// easy first check, if the destination is blocked, we can't get there

//		if (map.blocked(mover, tx, ty)) {
//			return null;
//		}
		
		// initial state for A*. The closed group is empty. Only the starting

		// tile is in the open list and it'e're already there
		nodes[sx][sy][sz].cost = 0;
		nodes[sx][sy][sz].depth = 0;
		closed.clear();
		open.clear();
		open.add(nodes[sx][sy][sz]);
		
		nodes[tx][ty][tz].parent = null;
		
		// while we haven'n't exceeded our max search depth
		int maxDepth = 0;
		while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
			// pull out the first node in our open list, this is determined to 
			// be the most likely to be the next step based on our heuristic

			Node current = getFirstInOpen();
			if (current == nodes[tx][ty][tz]) {
				break;
			}
			
			removeFromOpen(current);
			addToClosed(current);
			
			// search through all the neighbours of the current node evaluating
			// them as next steps

			for (int x=-1;x<2;x++) {
				for (int y=-1;y<2;y++) {
					for (int z=-1; z<2;z++){
						// not a neighbour, its the current tile

						if ((x == 0) && (y == 0) && (z == 0)) {
							continue;
						}
						
						// determine the location of the neighbour and evaluate it
	
						int xp = x + current.x;
						int yp = y + current.y;
						int zp = z + current.z;
						
						if (isValidLocation(mover,sx,sy,sz,xp,yp,zp)) {
							// the cost to get to this node is cost the current plus the movement
							// cost to reach this node. Note that the heursitic value is only used
							// in the sorted open list
	
							float nextStepCost = current.cost + getMovementCost(mover, current.x, current.y, current.z, xp, yp, zp);
							Node neighbour = nodes[xp][yp][zp];
							//map.pathFinderVisited(xp, yp, zp); TODO: check if nog werkt
							// if the new cost we've determined for this node is lower than 
							// it has been previously makes sure the node hasn'e've
							// determined that there might have been a better path to get to
							// this node so it needs to be re-evaluated
	
							if (nextStepCost < neighbour.cost) {
								if (inOpenList(neighbour)) {
									removeFromOpen(neighbour);
								}
								if (inClosedList(neighbour)) {
									removeFromClosed(neighbour);
								}
							}
							
							// if the node hasn't already been processed and discarded then
							// reset it's cost to our current cost and add it as a next possible
							// step (i.e. to the open list)
	
							if (!inOpenList(neighbour) && !(inClosedList(neighbour))) {
								neighbour.cost = nextStepCost;
								neighbour.heuristic = getHeuristicCost(mover, xp, yp, zp, tx, ty, tz);
								maxDepth = Math.max(maxDepth, neighbour.setParent(current));
								addToOpen(neighbour);
							}
						}
					}
				}
			}
		}

		// since we'e've run out of search 
		// there was no path. Just return null

		if (nodes[tx][ty][tz].parent == null) {
			return null;
		}
		
		// At this point we've definitely found a path so we can uses the parent
		// references of the nodes to find out way from the target location back
		// to the start recording the nodes on the way.

		Path path = new Path();
		Node target = nodes[tx][ty][tz];
		while (target != nodes[sx][sy][sz]) {
			path.prependStep(target.x, target.y, target.z);
			target = target.parent;
		}
		path.prependStep(sx,sy,sz);
		
		// thats it, we have our path 

		return path;
	}

	/**
	 * Get the first element from the open list. This is the next
	 * one to be searched.
	 * 
	 * @return The first element in the open list
	 */
	protected Node getFirstInOpen() {
		return (Node) open.first();
	}
	
	/**
	 * Add a node to the open list
	 * 
	 * @param node The node to be added to the open list
	 */
	protected void addToOpen(Node node) {
		open.add(node);
	}
	
	/**
	 * Check if a node is in the open list
	 * 
	 * @param node The node to check for
	 * @return True if the node given is in the open list
	 */
	protected boolean inOpenList(Node node) {
		return open.contains(node);
	}
	
	/**
	 * Remove a node from the open list
	 * 
	 * @param node The node to remove from the open list
	 */
	protected void removeFromOpen(Node node) {
		open.remove(node);
	}
	
	/**
	 * Add a node to the closed list
	 * 
	 * @param node The node to add to the closed list
	 */
	protected void addToClosed(Node node) {
		closed.add(node);
	}
	
	/**
	 * Check if the node supplied is in the closed list
	 * 
	 * @param node The node to search for
	 * @return True if the node specified is in the closed list
	 */
	protected boolean inClosedList(Node node) {
		return closed.contains(node);
	}
	
	/**
	 * Remove a node from the closed list
	 * 
	 * @param node The node to remove from the closed list
	 */
	protected void removeFromClosed(Node node) {
		closed.remove(node);
	}
	
	/**
	 * Check if a given location is valid for the supplied mover
	 * 
	 * @param mover The mover that would hold a given location
	 * @param sx The starting x coordinate
	 * @param sy The starting y coordinate
	 * @param sz The starting z coordinate
	 * @param x The x coordinate of the location to check
	 * @param y The y coordinate of the location to check
	 * @param z The z coordinate of the location to check
	 * @return True if the location is valid for the given mover
	 */
	protected boolean isValidLocation(Unit mover, int sx, int sy, int sz, int x, int y, int z) {
		int[] position = {x,y,z};
		if(mover.getWorld().isValidWorldPosition(position))
			if(map.isPassableTerrain(position))
				if(map.isNeighbouringSolid(position))
					return true;
		
		return false;
	}
	
	/**
	 * Get the cost to move through a given location
	 * 
	 * @param mover The entity that is being moved
	 * @param sx The x coordinate of the tile whose cost is being determined
	 * @param sy The y coordiante of the tile whose cost is being determined
	 * @param sz The z coordiante of the tile whose cost is being determined
	 * @param tx The x coordinate of the target location
	 * @param ty The y coordinate of the target location
	 * @param tz The z coordinate of the target location
	 * @return The cost of movement through the given tile
	 */
	public float getMovementCost(Unit mover, int sx, int sy, int sz, int tx, int ty, int tz) {
		int dx = Math.abs(sx - tx);
		int dy = Math.abs(sy - ty);
		int dz = tz - sz;
		
		float cost = 0;
		if((dx + dy == 1)||(dx + dy == 0))
			cost = 10;
		else if(dx + dy == 2)
			cost = 14;
		
		// going up
		if(dz > 0)
			cost = cost * 2;
		//going down
		else if(dz < 0)
			cost = cost * 0.833f;
		
		return cost;
	}

	/**
	 * Get the heuristic cost for the given location. This determines in which 
	 * order the locations are processed.
	 * 
	 * @param mover The entity that is being moved
	 * @param x The x coordinate of the tile whose cost is being determined
	 * @param y The y coordiante of the tile whose cost is being determined
	 * @param z The z coordiante of the tile whose cost is being determined
	 * @param tx The x coordinate of the target location
	 * @param ty The y coordinate of the target location
	 * @param tz The z coordinate of the target location
	 * @return The heuristic cost assigned to the tile
	 */
	public float getHeuristicCost(Unit mover, int x, int y, int z, int tx, int ty, int tz) {
		return heuristic.getCost(map, mover, x, y, z, tx, ty, tz);
	}
	
	/**
	 * A simple sorted list
	 *
	 * @author kevin
	 */
	private class SortedList {
		/** The list of elements */
		private ArrayList<Node> list = new ArrayList<Node>();
		
		/**
		 * Retrieve the first element from the list
		 *  
		 * @return The first element from the list
		 */
		public Object first() {
			return list.get(0);
		}
		
		/**
		 * Empty the list
		 */
		public void clear() {
			list.clear();
		}
		
		/**
		 * Add an element to the list - causes sorting
		 * 
		 * @param o The element to add
		 */
		@SuppressWarnings("unchecked")
		public void add(Node o) {
			list.add(o);
			Collections.sort(list);
		}
		
		/**
		 * Remove an element from the list
		 * 
		 * @param o The element to remove
		 */
		public void remove(Node o) {
			list.remove(o);
		}
	
		/**
		 * Get the number of elements in the list
		 * 
		 * @return The number of element in the list
 		 */
		public int size() {
			return list.size();
		}
		
		/**
		 * Check if an element is in the list
		 * 
		 * @param o The element to search for
		 * @return True if the element is in the list
		 */
		public boolean contains(Node o) {
			return list.contains(o);
		}
	}
	
	/**
	 * A single node in the search graph
	 */
	private class Node implements Comparable {
		/** The x coordinate of the node */
		private int x;
		/** The y coordinate of the node */
		private int y;
		/** The z coordinate of the node */
		private int z;
		/** The path cost for this node */
		private float cost;
		/** The parent of this node, how we reached it in the search */
		private Node parent;
		/** The heuristic cost of this node */
		private float heuristic;
		/** The search depth of this node */
		private int depth;
		
		/**
		 * Create a new node
		 * 
		 * @param x The x coordinate of the node
		 * @param y The y coordinate of the node
		 * @param z The z coordinate of the node
		 */
		public Node(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		/**
		 * Set the parent of this node
		 * 
		 * @param parent The parent node which lead us to this node
		 * @return The depth we have no reached in searching
		 */
		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;
			
			return depth;
		}
		
		/**
		 * @see Comparable#compareTo(Object)
		 */
		public int compareTo(Object other) {
			Node o = (Node) other;
			
			float f = heuristic + cost;
			float of = o.heuristic + o.cost;
			
			if (f < of) {
				return -1;
			} else if (f > of) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}