package hillbillies.model;

import static hillbillies.model.Constants.MAX_OBJECT_WEIGHT;
import static hillbillies.model.Constants.MIN_OBJECT_WEIGHT;

import java.util.Random;

import javax.vecmath.*;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of GameItems
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public abstract class GameItem extends GameObject{
	
	private Random rand = new Random();
	
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
	
	/** TODO: terminate gameitem
	 * Terminate this GameItem.
	 *
	 * @post   This GameItem  is terminated.
	 *       | new.isTerminated()
	 * @post   ...
	 *       | ...
	 */
	 public void terminate() {
		 this.isTerminated = true;
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
