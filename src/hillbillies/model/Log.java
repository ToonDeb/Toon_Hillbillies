package hillbillies.model;

import javax.vecmath.Vector3d;

//import be.kuleuven.cs.som.annotate.*;


/**
 * A class of Logs
 * 
 * @invar  The World of each Log must be a valid World for any
 *         Log.
 *       | isValidWorld(getWorld())
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class Log extends GameItem{
	
	/** 
	 * initialize the Log.
	 * The Log has the given Position and World
	 */
	public Log(int[] position, World world) throws IllegalArgumentException{
		super(position, world);
		
	}
	

	

}
