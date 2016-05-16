package hillbillies.model;

/**
 * A class of Boulders
 * 
 * @invar  The World of each Boulder must be a valid World for any
 *         Boulder.
 *       | isValidWorld(getWorld())
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class Boulder extends GameItem {
	
	/** 
	 * initialize the Boulder. 
	 * 
	 * @param position
	 * 		  The position of this new Boulder
	 * @param world
	 * 		  The world of this new Boulder
	 *         
 	 *  @post The Boulder has the given Position and World 
	 * 		 | new.getCubePosition() == position
	 * 		 | new.getWorld() == world
	 */
	public Boulder(int[] position, World world) throws IllegalArgumentException{
		super(position, world);
	}

}
