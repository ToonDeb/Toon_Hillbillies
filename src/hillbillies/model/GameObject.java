package hillbillies.model;

//import static hillbillies.model.Constants.MAX_X_POSITION;
//import static hillbillies.model.Constants.MAX_Y_POSITION;
//import static hillbillies.model.Constants.MAX_Z_POSITION;
import static hillbillies.model.Constants.MAX_NB_UNITS_IN_WORLD;
import static hillbillies.model.Constants.AIR;
import static hillbillies.model.Constants.TREE;
import static hillbillies.model.Constants.ROCK;
import static hillbillies.model.Constants.WORKSHOP;
import static hillbillies.model.Constants.DIRECTLYNEIGHBOURINGLIST;
import static hillbillies.model.Constants.NEIGHBOURINGLIST;

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
 * @invar  The fallDepth of each GameObject must be a valid fallDepth for any
 *         GameObject.
 *       | isValidFallDepth(getFallDepth())
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
		if(world != null)
			if(!world.isValidWorldPosition(position))
				throw new IllegalArgumentException("not a valid position in this world");
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
		if(position == null){
			throw new NullPointerException();
		}
		
		if(world == null){
			return true;
		}
		
		if(world.isValidWorldPosition(toCubePosition(position)))
				return true;
		else
			return false;
	}
	
	/**
	 * transform position from vector3d to int[]
	 */
	public static int[] toCubePosition(Vector3d position){
		int[] cubePosition = {(int)position.x,(int)position.y,(int)position.z};
		return cubePosition;
	}
	
	/**
	 * transform position from int[] to vector3d, in the middle of the cube
	 * @param position
	 * @return
	 */
	public static Vector3d toVectorPosition(int[]position){
		Vector3d vector = new Vector3d(position[0] + 0.5, position[1] + 0.5, position[2] + 0.5);
		return vector;
	}
	
	/**
	 * Set the position of this GameObject to the center of the cube, specified
	 * by the given cubePosition
	 * 
	 * @param cubePosition
	 * 		  the cube of the new location
	 * @post  the cubeposition of the GameObject is equal to the given cubeposition
	 * 		  | new.getCubePosition == cubePosition
	 */
	public void setAtPosition(int[] cubePosition){
		Vector3d position = new Vector3d(
				cubePosition[0]+0.5, cubePosition[1]+0.5, cubePosition[2]+0.5);
		this.setPosition(position);
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
		return toCubePosition(this.getPosition());
	}
	
	/**
	 * Update the position of the falling gameobject
	 */
	public void updateFall(double dt){
		if(!Unit.isValidTime(dt))
			throw new IllegalArgumentException("invalid time!");
		
		double nextTime = this.getFallTimer() - dt;
		
		if (Util.fuzzyLessThanOrEqualTo(nextTime, 0)){
			this.setAtPosition(this.getFallDestination());
			this.takeFallDamage(this.fallDepth);
			this.isFalling = false;
		}
		else{
			Vector3d nextPosition = new Vector3d(0, 0, -3);
			nextPosition.scaleAdd(dt, this.getPosition());
			this.setPosition(nextPosition);
			this.setFallTimer(nextTime);
		}
		
	}

	/**
	 * Return the fallDepth of this GameObject.
	 */
	@Basic @Raw
	public int getFallDepth() {
		return this.fallDepth;
	}

	/**
	 * Check whether the given fallDepth is a valid fallDepth for
	 * any GameObject.
	 *  
	 * @param  fallDepth
	 *         The fallDepth to check.
	 * @return 
	 *       | result == (fallDepth > 0)
	*/
	public static boolean isValidFallDepth(int fallDepth) {
		return (fallDepth > 0);
	}

	/**
	 * Set the fallDepth of this GameObject to the given fallDepth.
	 * 
	 * @param  fallDepth
	 *         The new fallDepth for this GameObject.
	 * @post   The fallDepth of this new GameObject is equal to
	 *         the given fallDepth.
	 *       | new.getFallDepth() == fallDepth
	 * @throws IllegalArgumentException
	 *         The given fallDepth is not a valid fallDepth for any
	 *         GameObject.
	 *       | ! isValidFallDepth(getFallDepth())
	 */
	@Raw
	public void setFallDepth(int fallDepth) 
			throws IllegalArgumentException {
		if (! isValidFallDepth(fallDepth))
			throw new IllegalArgumentException();
		this.fallDepth = fallDepth;
	}

	/**
	 * Variable registering the fallDepth of this GameObject.
	 */
	private int fallDepth;
	
	
	/**
	 * an abstract method that gets called when stopped falling.
	 * @param 	fallDepth
	 * 			the amount of cubes this gameobject has fallen
	 */
	public abstract void takeFallDamage(int fallDepth);
	
	/**
	 * Set fallDestination to the cube above a solid cube.
	 */
	public void startFall(){
		this.isFalling = true;
		
		int[] belowPosition = this.getCubePosition();
		int depth = 0;
		
		while(!this.getWorld().hasSolidBelow(belowPosition)){
			belowPosition[2] -= 1;
			depth += 1;
		}
		
		this.setFallDepth(depth);
		System.out.println(belowPosition[0] + " " + belowPosition[1] + " " + belowPosition[2]);
		this.setFallDestination(belowPosition);
		this.setFallTimer(depth/3.0);
	}
	
	public boolean isFalling(){
		return this.isFalling;
	}
	
	private boolean isFalling;
	
	/**
	 * Returns the coordinates of the cube beneath this gameobject
	 * 
	 * @return 	the cube coordinates of the cube below
	 * 			| belowPosition = this.getCubePosition
	 * 			| belowPosition[2] = belowPosition[2]-1
	 * 			| return belowPosition
	 */
	public int[] getCubePositionBelow(){
		int[] cubePosition = this.getCubePosition();
		cubePosition[2] = cubePosition[2] - 1;
		return cubePosition;
	}
	
	/**
	 * return true if the given position is less then 2 cubes away 
	 * 		from the current position
	 * 
	 * @param 	target
	 * 			the position to test
	 * @return	| if (|this.position[0] - target[0]| > 1) OR
	 * 			| 		(|this.position[1] - target[1]| > 1) OR
	 * 			| 		(|this.position[2] - target[2]| > 1)
	 * 			| 	then return false
	 * 			| else 
	 * 			|	return true
	 */
	public boolean isNeighboringCube(int[] target){
		int[] position = this.getCubePosition();
		if ((Math.abs(position[0] - target[0]) > 1) ||
				(Math.abs(position[1] - target[1]) > 1) ||
				(Math.abs(position[2] - target[2]) > 1))
			return false;
		else
			return true;
	}


	/**
	 * Return the fallTimer of this GameObject.
	 */
	@Basic @Raw
	public double getFallTimer() {
		return this.fallTimer;
	}

	/**
	 * Check whether the given fallTimer is a valid fallTimer for
	 * any GameObject.
	 *  
	 * @param  fallTimer
	 *         The fallTimer to check.
	 * @return 
	 *       | result == (fallTimer >= 0)
	*/
	public static boolean isValidFallTimer(double fallTimer) {
		return (fallTimer >= 0);
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
	public int[] getFallDestination() {
		return this.fallDestination;
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
	public void setFallDestination(int[] fallDestination) 
			throws IllegalArgumentException {
		if (! this.getWorld().isValidWorldPosition(fallDestination))
			throw new IllegalArgumentException("fallDestination is not in world");
		this.fallDestination = fallDestination;
	}

	/**
	 * Variable registering the fallDestination of this GameObject.
	 */
	private int[] fallDestination;
	
	
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
