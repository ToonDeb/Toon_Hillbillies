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
}
