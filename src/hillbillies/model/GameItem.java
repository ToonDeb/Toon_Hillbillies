package hillbillies.model;

import static hillbillies.model.Constants.MAX_OBJECT_WEIGHT;
import static hillbillies.model.Constants.MIN_OBJECT_WEIGHT;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of GameItems
 * 
 * @invar The weight of each GameItem must be a valid weight for any GameItem. 
 * 		  | isValidWeight(getWeight)
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public abstract class GameItem extends GameObject{
	
	private Random rand = new Random();
	
	/**
	 * initiate this gameitem, with given position and world, and a (valid) random weight
	 * 
	 * @param position
	 * 		  The position of this new Boulder
	 * @param world
	 * 		  The world of this new Boulder
	 *         
 	 * @post The Boulder has the given Position and World 
	 * 		 | new.getCubePosition() == position
	 * 		 | new.getWorld() == world
	 * @post The weight of this gameItem is a random integer
	 * 		 between MIN_OBJECT_WEIGHT and MAX_OBJECT_WEIGHT
	 * 		 | MIN_OBJECT_WEIGHT <= new.getWeight <= MAX_OBJECT_WEIGHT
	 */
	public GameItem(int[] position, World world){
		super(position, world);
		
		this.weight = MIN_OBJECT_WEIGHT + 
				rand.nextInt((MAX_OBJECT_WEIGHT-MIN_OBJECT_WEIGHT) + 1);
	}
	
	/**
	 * Return the weight of this GameItem.
	 */
	@Basic @Raw @Immutable
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Return whether the given weight is valid for this GameItem
	 * 
	 * @return true if the weight is within the limits
	 * 		   | result = (MIN_OBJECT_WEIGHT <= weight)&& (weight <= MAX_OBJECT_WEIGHT)
	 */
	public static boolean isValidWeight(int weight){
		return((MIN_OBJECT_WEIGHT <= weight)&& (weight <= MAX_OBJECT_WEIGHT));
	}

	/**
	 * Variable registering the weight of this GameItem.
	 */
	private final int weight;
	
	/**
	 * implements takeFallDamage, from GameObject. 
	 * GameItems don't take damage, so this does nothing.
	 */
	public void takeFallDamage(int fallDepth){
		return;
	}
	
	/**
	 * advance position if the gameitem is falling
	 * 
	 * @param deltaT
	 *        The amount of time to advance
	 *        
	 * @effect It the position below is passable, start falling
	 * 		   | if(this.getWorld.isPassableTerrain(this.getCubePositionBelow)
	 * 		   | then this.startFall()
	 * @effect If this GameItem is falling, update the fall
	 * 		   | if (this.isFalling)
	 * 		   | then this.updateFall(deltaT)
	 */
	public void advanceTime(double deltaT){
		
		//check if GameItem is on solid ground
		int[] belowPosition = this.getCubePositionBelow();
		if(this.getWorld().isPassableTerrain(belowPosition)){
			this.startFall();
		}
		
		//update position while falling
		if (this.isFalling()){
			this.updateFall(deltaT);
		}
	}
	
	/**
	 * Terminate this GameItem.
	 * 
	 * @post the GameItem is terminated
	 * 		 | new.isTerminated == true
	 * @post the world of this gameitem is null
	 * 		 | new.getWorld == null
	 * @post this GameItem is not in it's previous world
	 *       | world = this.getWorld 
	 * 		 | if this instanceof Log
	 * 		 | then !(new World).hasAsLog(new)
	 * 		 | else if this instanceof Boulder
	 * 		 | 		!(new World).hasAsBoulder(new)
	 */
	 public void terminate() {
		 this.isTerminated = true;
		 
		 if (this instanceof Log){
			 this.getWorld().removeLog((Log)this);
		 }
		 else if (this instanceof Boulder){
			 this.getWorld().removeBoulder((Boulder)this);
		 }
		 this.setWorld(null);
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this GameItem
	  * is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this person is terminated.
	  */
	 private boolean isTerminated = false;
	 
}
