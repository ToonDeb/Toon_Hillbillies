package hillbillies.model;

import static hillbillies.model.Constants.MAX_NB_UNITS_IN_WORLD;
import javax.vecmath.*;
import be.kuleuven.cs.som.annotate.*;
import ogp.framework.util.Util;

/**
 * A class of GameObjects
 * 
 * @invar  The position of each GameObject must be a valid position for any
 *         GameObject.
 *       | isValidPosition(getPosition())
 *       
 * @invar The world of each GameObject must be a valid World for any GameObject
 * 		 | isValidWorld(getWorld())
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public abstract class GameObject {


	/**
	 * Initialize this new GameObject with given position.
	 *
	 * @param  position
	 *         The position for this new GameObject.
	 *         
	 * @post The position of this new GameObject is set to
	 *         the given position
	 *       | new.getCubePosition == position
	 *       
	 * @throws	IllegalArgumentException
	 * 			The given position is not a valid position for any unit, in this world.
	 * 			| !world.isValidWorldPosition(position) ||
	 * 			|	world.isPassableTerrain(position)
	 * 
	 * @param 	world
	 * 			The world of this new GameObject.
	 * 
	 * @post 	The world of this new GameObject is set to the given World
	 * 			| new.getWorld == world
	 * @throws	NullPointerException
	 * 			The given world may not be null
	 * 			| world == null
	 */
	public GameObject(int[] position, World world) throws IllegalArgumentException {
		if(world == null)
			throw new NullPointerException("World can't be null");
	
		if(!world.isValidWorldPosition(position)||!world.isPassableTerrain(position))
			throw new IllegalArgumentException("not a valid position in this world");
		this.setWorld(world);
		this.setAtPosition(position);
	}

	public GameObject(int[] position){
		this.setAtPosition(position);
	}
	
	/**
	 * Return the position of this GameObject.
	 */
	@Basic @Raw
	public Vector3d getPosition() {
		return this.position;
	}

	/**
	 * Check whether the given position is a valid position for
	 * any GameObject.
	 * 
	 * @return (position != null) && (world.isValidWorldPosition(toCubePosition(position)))
	*/
	public static boolean isValidPosition(Vector3d position, World world) {
		if(position == null)
			return false;
		return world.isValidWorldPosition(toCubePosition(position));
	}
	
	/**
	 * transform position from vector3d to int[],
	 * rounding to the lowest whole number.
	 * 
	 * @return the int[] of the number
	 * 		   | result = {(int)position.x,(int)position.y,(int)position.z}
	 */
	public static int[] toCubePosition(Vector3d position){
		return new int[] {(int)position.x,(int)position.y,(int)position.z};
	}
	
	/**
	 * transform position from int[] to vector3d, in the middle of the cube
	 * 
	 * @param position
	 *        the position to transform
	 * @return the position in Vector3d
	 * 		  | result = 
	 *        |  Vector3d(position[0] + 0.5, position[1] + 0.5, position[2] + 0.5)
	 */
	public static Vector3d toVectorPosition(int[]position){
		return new Vector3d(position[0] + 0.5, position[1] + 0.5, position[2] + 0.5);
	}
	
	/**
	 * Set the position of this GameObject to the center of the cube, specified
	 * by the given cubePosition
	 * 
	 * @param cubePosition
	 * 		  the cube of the new location
	 * @post  the cubeposition of the GameObject is equal to the given cubeposition
	 * 		  | new.getCubePosition == cubePosition
	 * @throws IllegalArgumentException
	 * 		  the position is not a valid position for this gameObject
	 * 		  | (cubePosition == null) || (!this.getWorld.isValidWorldPosition(cubePosition))
	 */
	public void setAtPosition(int[] cubePosition) throws IllegalArgumentException{
		if(this.getWorld() != null){
			if((cubePosition == null) || (!this.getWorld().isValidWorldPosition(cubePosition)))
				throw new IllegalArgumentException("not a valid position for this world");
		}
		this.setPosition(toVectorPosition(cubePosition));
	}

	/**
	 * Set the position of this GameObject to the given position.
	 * 
	 * @param  position
	 *         The new position for this GameObject.
	 * @post   The position of this new GameObject is equal to
	 *         the given position.
	 *       | new.getPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any
	 *         GameObject.
	 *       | ! isValidPosition(getPosition())
	 */
	@Raw
	public void setPosition(Vector3d position) throws IllegalArgumentException {
		if(this.getWorld() != null)
			if (! isValidPosition(position, this.getWorld()))
				throw new IllegalArgumentException();
		this.position = position;
	}

	/**
	 * Variable registering the position of this GameObject.
	 */
	private Vector3d position;
	
	/**
	 * Return the position of the cube occupied by this GameObject.
	 */
	public int[] getCubePosition() {
		return toCubePosition(this.getPosition());
	}
	
	/** 
	 * Update the position of the falling gameobject
	 * 
	 * @param dt
	 * 		  The amount of time to advance
	 * @post  If the FallTimer reaches 0, set the GameObject
	 * 		  at the FallDestination and set IsFalling to false
	 * 		  | if getFallTimer - dt <= 0
	 * 		  |	 	then new.isFalling == false
	 * 		  | 	new.getPosition = this.getFallDestination
	 * @effect If the FallTimer reaches 0, take FallDamage
	 * 		  | if getFallTimer - dt <= 0
	 * 		  | 	do this.takeFallDamage(this.fallDepth)
	 * @post  In all other cases, update position and falltimer
	 * 		  | else
	 * 		  |  	new.getPosition = this.getPosition + dt*(0,0,-3)
	 * 		  |     new.getFallTimer = this.getFallTimer - dt
	 * @throws IllegalArgumentException
	 * 		   if the given time is not a valid time
	 * 		   | !Unit.isValidTime(dt)
	 */
	public void updateFall(double dt) throws IllegalArgumentException{
		if(!Unit.isValidTime(dt))
			throw new IllegalArgumentException("invalid time!");
		
		double nextTime = this.getFallTimer() - dt;
		
		if (Util.fuzzyLessThanOrEqualTo(nextTime, 0)){
			this.setAtPosition(this.getFallDestination());
			this.takeFallDamage(this.getFallDepth());
			this.isFalling = false;
		}
		else{
			Vector3d nextPosition = new Vector3d(0, 0, -3);
			nextPosition.scaleAdd(dt, this.getPosition());
			this.setPosition(nextPosition);
			this.setFallTimer(nextTime);
		}
		
	}

	/**
	 * Return the fallDepth of this GameObject.
	 */
	@Basic @Raw
	private int getFallDepth() {
		return this.fallDepth;
	}

	/**
	 * Check whether the given fallDepth is a valid fallDepth for
	 * any GameObject.
	 *  
	 * @param  fallDepth
	 *         The fallDepth to check.
	 * @return 
	 *       | result == (fallDepth > 0)
	*/
	private static boolean isValidFallDepth(int fallDepth) {
		return (fallDepth > 0);
	}

	/**
	 * Set the fallDepth of this GameObject to the given fallDepth.
	 * 
	 * @param  fallDepth
	 *         The new fallDepth for this GameObject.
	 * @post   The fallDepth of this new GameObject is equal to
	 *         the given fallDepth.
	 *       | new.getFallDepth() == fallDepth
	 * @throws IllegalArgumentException
	 *         The given fallDepth is not a valid fallDepth for any
	 *         GameObject.
	 *       | ! isValidFallDepth(getFallDepth())
	 */
	@Raw
	private void setFallDepth(int fallDepth) throws IllegalArgumentException {
		if (! isValidFallDepth(fallDepth))
			throw new IllegalArgumentException();
		this.fallDepth = fallDepth;
	}

	/**
	 * Variable registering the fallDepth of this GameObject.
	 */
	private int fallDepth;
	
	
	/**
	 * an abstract method that gets called when stopped falling.
	 * 
	 * @param 	fallDepth
	 * 			the amount of cubes this gameobject has fallen
	 * @throws	IllegalArgumentException
	 * 			fallDepth can't be negative
	 * 			| fallDepth <= 0
	 */
	public abstract void takeFallDamage(int fallDepth);
	
	/**
	 * Start the fall of this GameObject
	 * 
	 * @post this GameObject is falling
	 * 		 | new.isFalling == true
	 * @post the FallDestination of this GameObject is set
	 * 		 the first cube above a solid cube, below its current
	 * 		 position
	 * 		 | this.getWorld.hasSolidBelow(new.getFallDestination)
	 * 		 | for position between new.getFallDestination and this.getCubePosition
	 * 		 | 		where this.getWorld.getCubeType(position).isPassable()
	 * @post The FallDepth of this GameObject is the length between it's current position
	 * 		 And the falldestination.
	 * 		 | new.getFallDepth == new.getCubePosition[2] - new.getFallDestination[2]
	 * @throws IllegalStateException
	 * 			The current position has a solid cube below it
	 * 			| this.getWorld.hasSolidBelow(this.getCubePosition)
	 */
	public void startFall()throws IllegalStateException{
		this.isFalling = true;
		if(this.getWorld().hasSolidBelow(this.getCubePosition()))
			throw new IllegalStateException("can't fall now");
		
		int[] belowPosition = this.getCubePosition();
		int depth = 0;
		
		while(!this.getWorld().hasSolidBelow(belowPosition)){
			belowPosition[2] -= 1;
			depth += 1;
		}
		
		this.setFallDepth(depth);
		this.setFallDestination(belowPosition);
		this.setFallTimer(depth/3.0);
	}
	
	/**
	 * return true if the gameobject is currently falling
	 */
	public boolean isFalling(){
		return this.isFalling;
	}
	
	/**
	 * Variable registering whether a GameObject is currently falling
	 */
	private boolean isFalling;
	
	/**
	 * Returns the coordinates of the cube beneath this gameobject
	 * 
	 * @return 	the cube coordinates of the cube below
	 * 			| belowPosition = this.getCubePosition
	 * 			| belowPosition[2] = belowPosition[2]-1
	 * 			| return belowPosition
	 */
	public int[] getCubePositionBelow(){
		int[] cubePosition = this.getCubePosition();
		cubePosition[2] = cubePosition[2] - 1;
		return cubePosition;
	}
	
	/**
	 * return true if the given position is less then 2 cubes away 
	 * 		from the current position
	 * 
	 * @param 	target
	 * 			the position to test
	 * @return	| if (|this.position[0] - target[0]| > 1) OR
	 * 			| 		(|this.position[1] - target[1]| > 1) OR
	 * 			| 		(|this.position[2] - target[2]| > 1)
	 * 			| 	then return false
	 * 			| else 
	 * 			|	return true
	 */
	public boolean isNeighbouringCube(int[] target){
		if(!this.getWorld().isValidWorldPosition(target))
			return false;
		int[] position = this.getCubePosition();
		if ((Math.abs(position[0] - target[0]) > 1) ||
				(Math.abs(position[1] - target[1]) > 1) ||
				(Math.abs(position[2] - target[2]) > 1))
			return false;
		else
			return true;
	}


	/**
	 * Return the fallTimer of this GameObject.
	 */
	@Basic @Raw
	private double getFallTimer() {
		return this.fallTimer;
	}

	/**
	 * Check whether the given fallTimer is a valid fallTimer for
	 * any GameObject.
	 *  
	 * @param  fallTimer
	 *         The fallTimer to check.
	 * @return 
	 *       | result == (fallTimer >= 0)
	*/
	private static boolean isValidFallTimer(double fallTimer) {
		return (fallTimer >= 0);
	}

	/**
	 * Set the fallTimer of this GameObject to the given fallTimer.
	 * 
	 * @param  fallTimer
	 *         The new fallTimer for this GameObject.
	 * @post   The fallTimer of this new GameObject is equal to
	 *         the given fallTimer.
	 *       | new.getFallTimer() == fallTimer
	 * @throws IllegalArgumentException
	 *         The given fallTimer is not a valid fallTimer for any
	 *         GameObject.
	 *       | ! isValidFallTimer(getFallTimer())
	 */
	@Raw
	private void setFallTimer(double fallTimer) throws IllegalArgumentException {
		if (! isValidFallTimer(fallTimer))
			throw new IllegalArgumentException();
		this.fallTimer = fallTimer;
	}

	/**
	 * Variable registering the fallTimer of this GameObject.
	 */
	private double fallTimer = 0;

	/**
	 * Return the fallDestination of this GameObject.
	 */
	@Basic @Raw
	private int[] getFallDestination() {
		return this.fallDestination;
	}

	/**
	 * Set the fallDestination of this GameObject to the given fallDestination.
	 * 
	 * @param  fallDestination
	 *         The new fallDestination for this GameObject.
	 * @post   The fallDestination of this new GameObject is equal to
	 *         the given fallDestination.
	 *       | new.getFallDestination() == fallDestination
	 * @throws IllegalArgumentException
	 *         The given fallDestination is not a valid fallDestination for any
	 *         GameObject.
	 *       | ! isValidFallDestination(getFallDestination())
	 */
	@Raw
	private void setFallDestination(int[] fallDestination) throws IllegalArgumentException {
		if (! this.getWorld().isValidWorldPosition(fallDestination))
			throw new IllegalArgumentException("fallDestination is not in world");
		this.fallDestination = fallDestination;
	}

	/**
	 * Variable registering the fallDestination of this GameObject.
	 */
	private int[] fallDestination;
	
	
	/**
	 * Return the World of this GameObject.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}

	/** 
	 * Check whether the given World is a valid World for
	 * this GameObject.
	 * 
	 * @return True if world == null
	 * 		   | if (world == null)
	 * 		   |  	result = true
	 * 		   True if this is a unit, and the amount of units in
	 * 		   the world is below the max number of units
	 * 		   | if this instanceof Unit
	 * 		   | 	result = (world.getNbUnits() < MAX_NB_UNITS_IN_WORLD
	 * 		   else return true
	 * 		   | else 
	 * 		   | 	result = true
	 * 
	 */
	public boolean isValidWorld(World world) {
		if (world == null){
			return true;
		}
		if (this instanceof Unit){
			return (world.getNbUnits() < MAX_NB_UNITS_IN_WORLD);
		}
		return true;
	}

	/**
	 * Set the World of this Log to the given World.
	 * 
	 * @param  world
	 *         The new World for this GameObject.
	 * @post   The World of this new GameObject is equal to
	 *         the given World.
	 *       | new.getWorld() == world
	 * @throws IllegalArgumentException
	 *         The given World is not a valid World for any
	 *         GameObject.
	 *       | ! isValidWorld(getWorld())
	 */
	@Raw
	public void setWorld(World world) throws IllegalArgumentException {
		if (! isValidWorld(world))
			throw new IllegalArgumentException();
		this.world = world;
	}

	/**
	 * Variable registering the World of this Log.
	 */
	private World world;
}
