package hillbillies.model;

import java.util.Random;

import javax.vecmath.Vector3d;

//import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Logs
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class Log extends GameObject{
	
	Random rand;	
	
	/* 
	 * initialize the Log.
	 * The Log has the given Position
	 */
	public Log(Vector3d position){
		super(position);
		
	}

}
