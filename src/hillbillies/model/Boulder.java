package hillbillies.model;

import java.util.Random;

import javax.vecmath.Vector3d;

//import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Boulders
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class Boulder extends GameObject {
	
	Random rand;	
	
	/* 
	 * initialize the Boulder. 
	 * The Boulder has a given position, 
	 */
	public Boulder(Vector3d position){
		super(position);
	}
}
