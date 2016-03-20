package hillbillies.model;

import static hillbillies.model.Constants.MAX_X_POSITION;
import static hillbillies.model.Constants.MAX_Y_POSITION;
import static hillbillies.model.Constants.MAX_Z_POSITION;

import javax.vecmath.*;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of GameObjects
 * @invar  The position of each GameObject must be a valid position for any
 *         GameObject.
 *       | isValidPosition(getPosition())
 *
 * @author  ...
 * @version 1.0
 */
public abstract class GameEntity {


	/**
	 * Initialize this new GameObject with given position.
	 *
	 * @param  position
	 *         The position for this new GameObject.
	 * @effect The position of this new GameObject is set to
	 *         the given position.
	 *       | this.setPosition(position)
	 */
	public GameEntity(Vector3d position, World world)
			throws IllegalArgumentException {
		this.setPosition(position);
		
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
	public static boolean isValidPosition(Vector3d position) {
		return position != null && (position.x >= 0) && (position.x < MAX_X_POSITION) 
				&& (position.y >= 0) && (position.y < MAX_Y_POSITION)
				&& (position.z >= 0) && (position.z < MAX_Z_POSITION);
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
		if (! isValidPosition(position))
			throw new IllegalArgumentException();
		this.position = position;
	}

	/**
	 * Variable registering the position of this GameObject.
	 */
	private Vector3d position;
	
	/**
	 * Return the position of the cube occupied by this GameEntity.
	 */
	public int[] getCubePosition() {
		int cubeX = (int) Math.floor(this.getPosition().x);
		int cubeY = (int) Math.floor(this.getPosition().y);
		int cubeZ = (int) Math.floor(this.getPosition().z);
		int[] cubePosition = { cubeX, cubeY, cubeZ };
		return cubePosition;
	}
	
	
	/**
	 * Return the World of this GameEntity.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}

	/** TODO: isValidWorld
	 * Check whether the given World is a valid World for
	 * any GameEntity.
	 *  
	 * @param  World
	 *         The World to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidWorld(World world) {
		return true;
	}

	/**
	 * Set the World of this Log to the given World.
	 * 
	 * @param  world
	 *         The new World for this GameEntity.
	 * @post   The World of this new GameEntity is equal to
	 *         the given World.
	 *       | new.getWorld() == world
	 * @throws IllegalArgumentException
	 *         The given World is not a valid World for any
	 *         GameEntity.
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
