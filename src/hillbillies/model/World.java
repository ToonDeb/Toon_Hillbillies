package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.part2.listener.TerrainChangeListener;

/**
 * A class of Worlds
 * @invar  The TerrainChangeListener of each World must be a valid TerrainChangeListener for any
 *         World.
 *       | isValidTerrainChangeListener(getTerrainChangeListener())
 * @invar  The terrainTypes of each World must be a valid terrainTypes for any
 *         World.
 *       | isValidTerrainType(getTerrainType())
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class World {

	/**
	 * Initialize this new World with given terrainType and modelListener
	 *
	 * @param  terrainType
	 *         The terrainTypes for this new World.
	 * @effect The terrainTypes of this new World is set to
	 *         the given terrainTypes.
	 *       | this.setTerrainType(terrainType)
	 * @param  modelListener
	 *         The TerrainChangeListener for this new World.
	 * @pre    The given TerrainChangeListener must be a valid TerrainChangeListener for any World.
	 *       | isValidTerrainChangeListener(TerrainChangeListener)
	 * @post   The TerrainChangeListener of this new World is equal to the given
	 *         TerrainChangeListener.
	 *       | new.getTerrainChangeListener() == modelListener
	 */
	public World(int[][][] terrainType, TerrainChangeListener modelListener)
			throws IllegalArgumentException {
		this.setTerrainType(terrainType);
		this.setTerrainChangeListener(modelListener);
	}
	

	/**
	 * Return the TerrainChangeListener of this World.
	 */
	@Basic @Raw
	public TerrainChangeListener getTerrainChangeListener() {
		return this.modelListener;
	}

	/**
	 * Check whether the given TerrainChangeListener is a valid TerrainChangeListener for
	 * any World.
	 *  
	 * @param  TerrainChangeListener
	 *         The TerrainChangeListener to check.
	 * @return 
	 *       | result == true 
	*/
	public static boolean isValidTerrainChangeListener(TerrainChangeListener modelListener) {
		return true;
	}

	/**
	 * Set the TerrainChangeListener of this World to the given TerrainChangeListener.
	 * 
	 * @param  modelListener
	 *         The new TerrainChangeListener for this World.
	 * @pre    The given TerrainChangeListener must be a valid TerrainChangeListener for any
	 *         World.
	 *       | isValidTerrainChangeListener(modelListener)
	 * @post   The TerrainChangeListener of this World is equal to the given
	 *         TerrainChangeListener.
	 *       | new.getTerrainChangeListener() == modelListener
	 */
	@Raw
	private void setTerrainChangeListener(TerrainChangeListener modelListener) {
		assert isValidTerrainChangeListener(modelListener);
		this.modelListener = modelListener;
	}

	/**
	 * Variable registering the TerrainChangeListener of this World.
	 */
	private TerrainChangeListener modelListener;
	
	/**
	 * Return the terrainTypes of this World.
	 */
	@Basic @Raw
	public int[][][] getTerrainType() {
		return this.terrainType;
	}
	
	/** TODO: isvalidterrainType
	 * Check whether the given terrainTypes is a valid terrainTypes for
	 * any World.
	*/
	public static boolean isValidTerrainType(int[][][] terrainType) {
		return false;
	}
	
	/**
	 * Set the terrainTypes of this World to the given terrainTypes.
	 * 
	 * @param  terrainType
	 *         The new terrainTypes for this World.
	 * @post   The terrainTypes of this new World is equal to
	 *         the given terrainTypes.
	 *       | new.getTerrainType() == terrainType
	 * @throws IllegalArgumentException
	 *         The given terrainTypes is not a valid terrainTypes for any
	 *         World.
	 *       | ! isValidTerrainType(getTerrainType())
	 */
	@Raw
	public void setTerrainType(int[][][] terrainType) 
			throws IllegalArgumentException {
		if (! isValidTerrainType(terrainType))
			throw new IllegalArgumentException();
		this.terrainType = terrainType;
	}
	
	/**
	 * Variable registering the terrainTypes of this World.
	 */
	private int[][][] terrainType;
	
	public int getNbCubesX(){
		return this.getTerrainType()[0][0].length;
	}
	
	public int getNbCubesY(){
		return this.getTerrainType()[0].length;
	}
	
	public int getNbCubesZ(){
		return this.getTerrainType().length;
	}
	
	public int getCubeType(int x, int y, int z){
		return this.getTerrainType()[x][y][z];
	}
	
	@Raw
	public void setCubeType(int x, int y, int z, int value){
		terrainType[x][y][z] = value;
		this.getTerrainChangeListener().notifyTerrainChanged(x, y, z);
	}
	
	
}
