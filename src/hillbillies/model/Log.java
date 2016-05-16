package hillbillies.model;

//import be.kuleuven.cs.som.annotate.*;


/**
 * A class of Logs
 * 
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class Log extends GameItem{
	
	/** 
	 * initialize the Log.
	 * 
	 * @param position
	 * 		  The position of this new Log
	 * @param world
	 * 		  The world of this new Log
	 * 
	 * @post The Log has the given Position and World 
	 * 		 | new.getCubePosition() == position
	 * 		 | new.getWorld() == world
	 */
	public Log(int[] position, World world) throws IllegalArgumentException{
		super(position, world);
		
	}
	

	

}
