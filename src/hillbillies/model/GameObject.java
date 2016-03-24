package hillbillies.model;

//import static hillbillies.model.Constants.MAX_X_POSITION;
//import static hillbillies.model.Constants.MAX_Y_POSITION;
//import static hillbillies.model.Constants.MAX_Z_POSITION;
import static hillbillies.model.Constants.MAX_NB_UNITS_IN_WORLD;

import javax.vecmath.*;

import be.kuleuven.cs.som.annotate.*;
import ogp.framework.util.Util;

/**
 * A class of GameObjects
 * @invar  The position of each GameObject must be a valid position for any
 *         GameObject.
 *       | isValidPosition(getPosition())
 * @invar  The fallDestination of each GameObject must be a valid fallDestination for any
 *         GameObject.
 *       | isValidFallDestination(getFallDestination())
 * @invar  The fallTimer of each GameObject must be a valid fallTimer for any
 *         GameObject.
 *       | isValidFallTimer(getFallTimer())
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
	 * @effect The position of this new GameObject is set to
	 *         the given position.
	 *       | this.setPosition(position)
	 */
	public GameObject(int[] position, World world)
			throws IllegalArgumentException {
		Vector3d vectorPosition = new Vector3d(position[0]+0.5, position[1]+0.5, position[2]+0.5);
		this.setPosition(vectorPosition);
		
		this.setWorld(world);
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
	*/
	public static boolean isValidPosition(Vector3d position, World world) {
		if(world == null){
			return true;
		}
		return position != null && (position.x >= 0) && (position.x < world.getNbCubesX()) 
			&& (position.y >= 0) && (position.y < world.getNbCubesY())
				&& (position.z >= 0) && (position.z < world.getNbCubesZ());
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
	public void setPosition(Vector3d position) 
			throws IllegalArgumentException {
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
		int cubeX = (int) Math.floor(this.getPosition().x);
		int cubeY = (int) Math.floor(this.getPosition().y);
		int cubeZ = (int) Math.floor(this.getPosition().z);
		int[] cubePosition = { cubeX, cubeY, cubeZ };
		return cubePosition;
	}
	
	/**
	 * Update the position of the falling gameobject
	 * 
	 */
	public void updateFall(double dt){
		if(!Unit.isValidTime(dt))
			throw new IllegalArgumentException("invalid time!");
		
		double nexTime = this.getFallTimer() - dt;
		
		if (Util.fuzzyLessThanOrEqualTo(nexTime, 0)){
			this.setPosition(this.getFallDestination());
			this.takeFallDamage(this.fallDepth);
		}
		else{
			Vector3d nextPosition = new Vector3d(0, 0, -3);
			nextPosition.scaleAdd(dt, this.getPosition());
			this.setPosition(nextPosition);
		}
		
	}
	
	private int fallDepth;
	
	public abstract void takeFallDamage(int fallDepth);
	
	/**
	 * Set fallDestination to the cube above a solid cube.
	 */
	public void startFall(){
		this.isFalling = true;
		
		int[] belowPosition = this.getCubePositionBelow();
		
		while(this.getWorld().isPassableTerrain(belowPosition)){
			belowPosition[2] = belowPosition[2] - 1;
		}
		
		this.setFallDestination(new Vector3d(
				belowPosition[0]+0.5, belowPosition[1]+0.5, belowPosition[2]+1.5));
	}
	
	public boolean isFalling(){
		return this.isFalling;
	}
	
	private boolean isFalling;
	
	public int[] getCubePositionBelow(){
		int[] cubePosition = this.getCubePosition();
		int[] belowPosition = cubePosition;
		belowPosition[2] = belowPosition[2] - 1;
		return belowPosition;
	}


	/**
	 * Return the fallTimer of this GameObject.
	 */
	@Basic @Raw
	public double getFallTimer() {
		return this.fallTimer;
	}

	/** TODO: is valid falltimer
	 * Check whether the given fallTimer is a valid fallTimer for
	 * any GameObject.
	 *  
	 * @param  fallTimer
	 *         The fallTimer to check.
	 * @return 
	 *       | result == 
	*/
	public static boolean isValidFallTimer(double fallTimer) {
		return true;
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
	public void setFallTimer(double fallTimer) 
			throws IllegalArgumentException {
		if (! isValidFallTimer(fallTimer))
			throw new IllegalArgumentException();
		this.fallTimer = fallTimer;
	}

	/**
	 * Variable registering the fallTimer of this GameObject.
	 */
	private double fallTimer = 0;
	


///**
// * Initialize this new GameObject with given fallDestination.
// *
// * @param  fallDestination
// *         The fallDestination for this new GameObject.
// * @effect The fallDestination of this new GameObject is set to
// *         the given fallDestination.
// *       | this.setFallDestination(fallDestination)
// */
//public GameObject(Vector3d fallDestination)
//		throws IllegalArgumentException {
//	this.setFallDestination(fallDestination);
//}


	/**
	 * Return the fallDestination of this GameObject.
	 */
	@Basic @Raw
	public Vector3d getFallDestination() {
		return this.fallDestination;
	}

	/** TODO: isValidFallDestination
	 * Check whether the given fallDestination is a valid fallDestination for
	 * any GameObject.
	 *  
	 * @param  fallDestination
	 *         The fallDestination to check.
	 * @return 
	 *       | result == 
	*/
	public boolean isValidFallDestination(Vector3d fallDestination) {
		return isValidPosition(fallDestination, this.getWorld());
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
	public void setFallDestination(Vector3d fallDestination) 
			throws IllegalArgumentException {
		if (! isValidFallDestination(fallDestination))
			throw new IllegalArgumentException();
		this.fallDestination = fallDestination;
	}

	/**
	 * Variable registering the fallDestination of this GameObject.
	 */
	private Vector3d fallDestination;
	
	
	/**
	 * Return the World of this GameObject.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}

	/** TODO: isValidWorld
	 * Check whether the given World is a valid World for
	 * this GameObject.
	 */
	public boolean isValidWorld(World world) {
		if (world == null){
			return true;
		}
		if (this instanceof Unit){
			if (world.getNbUnits() < MAX_NB_UNITS_IN_WORLD){
				return true;
			}
			else{
				return false;
			}
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
	public void setWorld(World world) 
			throws IllegalArgumentException {
		if (! isValidWorld(world))
			throw new IllegalArgumentException();
		this.world = world;
	}

	/**
	 * Variable registering the World of this Log.
	 */
	private World world;
}
