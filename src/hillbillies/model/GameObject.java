package hillbillies.model;

import static hillbillies.model.Constants.MAX_OBJECT_WEIGHT;
import static hillbillies.model.Constants.MIN_OBJECT_WEIGHT;

import java.util.Random;

import javax.vecmath.*;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of GameObjects
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public abstract class GameObject extends GameEntity{
	
	Random rand;
	
	public GameObject(Vector3d position, World world){
		super(position, world);
		
		this.weight = MIN_OBJECT_WEIGHT + 
				rand.nextInt((MAX_OBJECT_WEIGHT-MIN_OBJECT_WEIGHT) + 1);
	}
	
	/**
	 * Return the weight of this Log.
	 */
	@Basic @Raw @Immutable
	public int getWeight() {
		return this.weight;
	}

	/**
	 * Variable registering the weight of this GameObject.
	 */
	private final int weight;
}
