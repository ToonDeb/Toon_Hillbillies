package hillbillies.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

//import javax.vecmath.Vector3d;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import hillbillies.model.CubeType;


import static hillbillies.model.Constants.DIRECTLYNEIGHBOURINGLIST;
import static hillbillies.model.Constants.MAX_NB_UNITS_IN_WORLD;
import static hillbillies.model.Constants.MAX_NB_UNITS_IN_FACTION;
import static hillbillies.model.Constants.MAX_NB_ACTIVE_FACTIONS;


/**
 * A class of Worlds
 * @invar  The TerrainChangeListener of each World must be a valid TerrainChangeListener for any
 *         World.
 *       | isValidTerrainChangeListener(getTerrainChangeListener())
 *       
 * @invar  The terrainTypes of each World must be a valid terrainTypes for any
 *         World.
 *       | isValidTerrainType(getTerrainType())
 *       
 * @invar   Each World must have proper logs.
 *        | hasProperLogs()
 *        
 * @invar   Each World must have proper Boulders.
 *        | hasProperBoulders()
 *        
 * @invar   Each World must have proper Units.
 *        | hasProperUnits()
 *
 * @invar   Each World must have proper Factions.
 *        | hasProperFactions()
 *        
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
	 *         
	 * @pre    The given TerrainChangeListener must be a valid TerrainChangeListener for any World.
	 *       | isValidTerrainChangeListener(TerrainChangeListener)
	 *       
	 * @post   The TerrainChangeListener of this new World is equal to the given
	 *         TerrainChangeListener.
	 *       | new.getTerrainChangeListener() == modelListener
	 *       
 	 * @post   This new World has no logs yet.
	 *       | new.getNbLogs() == 0
	 *       
	 * @post   This new World has no Boulders yet.
 	 *       | new.getNbBoulders() == 0
	 *       
 	 * @post   This new World has no Units yet.
	 *       | new.getNbUnits() == 0
	 *       
 	 * @post   This new World has no Factions yet.
	 *       | new.getNbFactions() == 0
	 *       
	 * @post 	The locations of the workshops in this world are stored
	 * 			in workschopLocations
	 * 		 | this.getCubeType(new.workshopLocations[x]) == WORKSHOP
	 * 		 | for every x, x < new.workshopLocations.length
	 *       
	 */
	public World(int[][][] terrainType, TerrainChangeListener modelListener)
			throws IllegalArgumentException, NullPointerException {
		if((terrainType == null)||(modelListener == null))
			throw new NullPointerException();
		int[][][] newTerrainType = Arrays.copyOf(terrainType, terrainType.length);
		this.setTerrainType(newTerrainType);
		this.setTerrainChangeListener(modelListener);
		
		this.setConnectedToBorder(
				new ConnectedToBorder(
						this.getNbCubesX(), this.getNbCubesY(), this.getNbCubesZ()));
		
		ArrayList<int[]> workshopLocations = new ArrayList<int[]>();
		for(int x=0; x<this.getNbCubesX(); x++){
			for(int y=0; y<this.getNbCubesY(); y++){
				for(int z=0; z<this.getNbCubesZ();z++){
					if (this.getCubeType(x, y, z).isPassable()){
						this.getConnectedToBorder().changeSolidToPassable(x, y, z);
					}
					if(this.getCubeType(x, y, z) == CubeType.WORKSHOP){
						int[] position = {x,y,z};
						workshopLocations.add(position);
					}
				}
			}
		}
		int list[][] = new int[workshopLocations.size()][3];
		this.workshopLocations = workshopLocations.toArray(list);
	}
	
	/**
	 * return the connectedToBorder of this world
	 */
	public ConnectedToBorder getConnectedToBorder(){
		return this.connectedToBorder;
	}
	
	/**
	 * set the connectedToBorder of this world
	 * @param connectedToBorder
	 */
	private void setConnectedToBorder(ConnectedToBorder connectedToBorder){
		this.connectedToBorder = connectedToBorder;
	}
	
	private ConnectedToBorder connectedToBorder;

	/**
	 * Return the TerrainChangeListener of this World.
	 */
	@Basic @Raw
	private TerrainChangeListener getTerrainChangeListener() {
		return this.modelListener;
	}

	/**
	 * Check whether the given TerrainChangeListener is a valid TerrainChangeListener for
	 * any World.
	 *  
	 * @param  terrainChangeListener
	 *         The TerrainChangeListener to check.
	 * @return terrainchangelistener is not null
	 *       | result = terrainChangeListener != null
	*/
	private static boolean isValidTerrainChangeListener(TerrainChangeListener terrainChangeListener) {
		return (terrainChangeListener != null);
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
	private int[][][] getTerrainType() {
		return this.terrainType;
	}
	
	/** 
	 * Check whether the given terrainTypes is a valid terrainTypes for
	 * any World.
	 * 
	 * @return true if terraintType is not null
	 * 		   | result = (terrainType != null)
	*/
	private static boolean isValidTerrainType(int[][][] terrainType) {
		return (terrainType != null);
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
	private void setTerrainType(int[][][] terrainType) throws IllegalArgumentException {
		if (! isValidTerrainType(terrainType))
			throw new IllegalArgumentException();
		this.terrainType = terrainType;
	}
	
	/**
	 * returns true if the terrain at position is passable
	 * 
	 * @param position
	 * @return false if it is not a valid position in this world
	 * 			| if !this.isValidWorldPosition(position)
	 * 			|	then result = false
	 * 			Else, check if the cube is passable
	 * 			| this.getCubeType(position).isPassable()
	 */
	public boolean isPassableTerrain(int[] position) {
		if (! this.isValidWorldPosition(position))
			return false;
		
		return this.getCubeType(position[0], position[1], position[2]).isPassable();
				
	}
	
	/**
	 * Check if the given position is within this world
	 * @param 	position
	 * 			the position to check
	 * @return	return true if position falls within the borders of this world
	 * 
	 */
	public boolean isValidWorldPosition(int[] position){
		if((this.getNbCubesX()-1 >= position[0])&&(0 <= position[0])&&
				(this.getNbCubesY()-1 >= position[1])&&(0 <= position[1]) &&
				(this.getNbCubesZ()-1 >= position[2])&&(0 <= position[2]))
			return true;
		else
			return false;
	}
	
	/**
	 * Returns true if the cube below the given position is solid.
	 * 
	 * @return true if the given y-position is zero
	 * 			| if position[2] == 0
	 * 			|		result = true
	 * 			else, true if belowposition is not passable
	 * 			| result = (!this.isPassableTerrain(position + {0,0,-1}))
	 * @throws IllegalArgumentException
	 * 			the given posiiton is not valid in this world
	 * 			| !this.isValidWorldPosition(position)
	 */
	public boolean hasSolidBelow(int[] position) throws IllegalArgumentException{
		if(!this.isValidWorldPosition(position))
			throw new IllegalArgumentException("not a valid position");
		if (position[2] == 0){
			return true;
		}
		
		int[] belowPosition = new int[3];
		belowPosition[0] = position[0];
		belowPosition[1] = position[1];
		belowPosition[2] = position[2]-1;
		return(!this.isPassableTerrain(belowPosition));
	}
	
	/**
	 * Returns true if the given position is neighbouring a solid cube
	 * @param 	position
	 * 			the position to check.
	 * @returns true if a solid is underneath
	 *     		| if this.hasSolidBelow(position)
	 *     		| then result = true
	 *     		else, check in every direction
	 *     		| for direction in DIRECTLYNEIGHBOURING
	 *       	| 	if !getCubeType(position + direction).isPassable
	 *       	|		result = true
	 *       	| result = false
	 *
	 * @throws IllegalArgumentException
	 * 			if the position is not a valid position in this world
	 * 			| !this.isValidWorldPosition(position)
	 */
	public boolean isNeighbouringSolid(int[] position) throws IllegalArgumentException{
		if(!this.isValidWorldPosition(position)){
			throw new IllegalArgumentException("not a valid position");
		}
		if(this.hasSolidBelow(position)){
			return true;
		}
		for(int[] test: DIRECTLYNEIGHBOURINGLIST){
			int[] neighbouringPosition = new int[3];
			neighbouringPosition[0] = position[0] + test[0];
			neighbouringPosition[1] = position[1] + test[1];
			neighbouringPosition[2] = position[2] + test[2];
			if(this.isValidWorldPosition(neighbouringPosition))
				if(!this.isPassableTerrain(neighbouringPosition))
					return true;
		}
		return false;
	}
	
	
	/**
	 * Variable registering the terrainTypes of this World.
	 */
	private int[][][] terrainType;
	
	/**
	 * return the number of cubes in the x dimension of this world
	 */
	public int getNbCubesX(){
		return this.getTerrainType().length;
	}
	
	/**
	 * return the number of cubes in the y dimension of this world
	 */
	public int getNbCubesY(){
		return this.getTerrainType()[0].length;
	}
	
	/**
	 * return the number of cubes in the z dimension of this world
	 */
	public int getNbCubesZ(){
		return this.getTerrainType()[0][0].length;
	}
	
	/**
	 * return the cubeType at the position
	 * @param x
	 * 			| the x-coordinate of the cubetype
	 * @param y
	 * 			| the y-coordinate of the cubetype
	 * @param z
	 * 			| the z-coordinate of the cubetype
	 * @return the cubetype at that coordinate
	 * 			| result = cubetype
	 * 			| where cubetype.getNumber == this.getTerrainType()[x][y][z]
	 * @throws IllegalArgumentException
	 * 			the given posiiton is not valid in this world
	 * 			| !this.isValidWorldPosition({x,y,z})
	 */
	public CubeType getCubeType(int x, int y, int z) throws IllegalArgumentException{
		if(!this.isValidWorldPosition(new int[] {x,y,z}))
			throw new IllegalArgumentException("not a valid position");
		int typeInt = this.getTerrainType()[x][y][z];
		return CubeType.getCubeType(typeInt);
	}
	
	/**
	 * Set the cubeType at the given position to the given value.
	 * If other terrain becomes disconnected, their value will be set to air,
	 * and will drop a boulder or a log.
	 * 
	 * @param x
	 * 		  | x-coordinate of the cube
	 * @param y
	 * 		  | y-coordinate of the cube
	 * @param z
	 * 		  | z-coordinate of the cube
	 * @param value
	 * 		  | the new cubetype
	 * 
	 * @post the cubetype at the given position is the given cubetype.
	 * 		  | this.getCubeType(x,y,z).getNumber() == value
	 * @post there are no solid cubes that are not connected to a border
	 * 		  | this.getConnectedToBorder.isSolidConnectedToBorder(x,y,z)
	 * 		  |  for every x, y and z < this.getNbCubesX/Y/Z
	 * 		  |  	&& getCubeType(x,y,z).isSollid
	 * @effect the path of every unit is reset
	 * 		  | for unit in this.getUnits
	 * 		  | 	do	unit.resetPath()
	 * 
	 * @throws IllegalArgumentException
	 * 	  		if the position is not a valid position in this world, or
	 * 			the given value is not a valid value for a cubetype
	 * 			| !this.isValidWorldPosition({x,y,z}) || CubeType.getCubeType(value) == null
	 * 
	 */
	//@Raw 
	public void setCubeType(int x, int y, int z, int value) throws IllegalArgumentException{
		CubeType cubeType = CubeType.getCubeType(value);
		
		if(cubeType == null)
			throw new IllegalArgumentException("wrong value");
		
		int[] position = {x, y, z};
		
		if(!this.isValidWorldPosition(position))
			throw new IllegalArgumentException("invalid position");
		
		this.getTerrainType()[x][y][z] = value;
		this.getTerrainChangeListener().notifyTerrainChanged(x, y, z);
		
		if(cubeType.isPassable()){
			List<int[]> changedTerrain = this.getConnectedToBorder().changeSolidToPassable(x, y, z);
			for(int[] changedPosition: changedTerrain){
				int otherX = changedPosition[0];
				int otherY = changedPosition[1];
				int otherZ = changedPosition[2];
				CubeType type = this.getCubeType(otherX, otherY, otherZ);
				this.getTerrainType()[otherX][otherY][otherZ] = 0;
				this.getTerrainChangeListener().notifyTerrainChanged(otherX, otherY, otherZ);
				
				if((type == CubeType.ROCK)&&(new Random().nextDouble()<=0.25)){
					Boulder boulder = new Boulder(changedPosition,this);
					this.addBoulder(boulder);
				}
				
				if((type == CubeType.TREE)&&(new Random().nextDouble()<= 0.25)){
					Log log = new Log(changedPosition, this);
					this.addLog(log);
				}
				
			}
		}
		
		for(Unit unit: this.getUnits()){
			unit.resetPath();
		}
	}

	/**
	 * Check whether this World has the given log as one of its
	 * logs.
	 * 
	 * @param  log
	 *         The log to check.
	 */
	@Basic
	@Raw
	public boolean hasAsLog(@Raw Log log) {
		return logs.contains(log);
	}

	/**
	 * Check whether this World can have the given log
	 * as one of its logs.
	 * 
	 * @param  log
	 *         The log to check.
	 * @return True if and only if the given log is effective
	 *         and that log is a valid log for this World.
	 *       | result ==
	 *       |   (log != null) &&
	 *       |   (Log.isValidWorld(this))
	 */
	@Raw
	public boolean canHaveAsLog(Log log) {
		return (log != null) && (log.isValidWorld(this));
	}

	/**
	 * Check whether this World has proper logs attached to it.
	 * 
	 * @return True if and only if this World can have each of the
	 *         logs attached to it as one of its logs,
	 *         and if each of these logs references this World as
	 *         the World to which they are attached.
	 *       | for each log in Log:
	 *       |   if (hasAsLog(log))
	 *       |     then canHaveAsLog(log) &&
	 *       |          (log.getWorld() == this)
	 */
	public boolean hasProperLogs() {
		for (Log log : logs) {
			if (!canHaveAsLog(log))
				return false;
			if (log.getWorld() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of logs associated with this World.
	 *
	 * @return  The total number of logs collected in this World.
	 *        | result ==
	 *        |   card({log:Log | hasAsLog({log)})
	 */
	public int getNbLogs() {
		return logs.size();
	}

	/**
	 * Add the given log to the set of logs of this World.
	 * 
	 * @param  log
	 *         The log to be added.
	 * @pre    The given log is effective and already references
	 *         this World.
	 *       | (log != null) && (log.getWorld() == this)
	 * @post   This World has the given log as one of its logs.
	 *       | new.hasAsLog(log)
	 */
	public void addLog(@Raw Log log) 
			throws IllegalArgumentException, NullPointerException{
		if (log == null)
			throw new NullPointerException("log can't be null");
		if ((log.getWorld() != this)||(!this.canHaveAsLog(log)))
			throw new IllegalArgumentException("invalid log");
		logs.add(log);
	}

	/**
	 * Remove the given log from the set of logs of this World.
	 * 
	 * @param  log
	 *         The log to be removed.
	 * @pre    This World has the given log as one of
	 *         its logs, and the given log does not
	 *         reference any World.
	 *       | this.hasAsLog(log) &&
	 *       | (log.getWorld() == null)
	 * @post   This World no longer has the given log as
	 *         one of its logs.
	 *       | ! new.hasAsLog(log)
	 */
	@Raw
	public void removeLog(Log log) throws IllegalArgumentException{
		if( !this.hasAsLog(log))
			throw new IllegalArgumentException("can't remove log!");
		logs.remove(log);
	}
	
	/**
	 * Return the set of all logs in this world
	 * 
	 * @return the logs in this world
	 * 		| result = Set<Log> logs
	 * 		| where for log in logs
	 *  	|		this.hasAsLog(log)
	 */
	public Set<Log> getLogs(){
		Set<Log> allLogs = new HashSet<Log>();
		for(Log log: this.logs){
			allLogs.add(log);
		}
		return allLogs;
	}

	/**
	 * Variable referencing a set collecting all the logs
	 * of this World.
	 * 
	 * @invar  The referenced set is effective.
	 *       | logs != null
	 * @invar  Each log registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each log in logs:
	 *       |   ( (log != null) &&
	 *       |     (! log.isTerminated()) )
	 */
	private final Set<Log> logs = new HashSet<Log>();
	
	
	/**
	 * Check whether this World has the given Boulder as one of its
	 * Boulders.
	 * 
	 * @param  boulder
	 *         The Boulder to check.
	 */
	@Basic
	@Raw
	public boolean hasAsBoulder(@Raw Boulder boulder) {
		return boulders.contains(boulder);
	}

	/**
	 * Check whether this World can have the given Boulder
	 * as one of its Boulders.
	 * 
	 * @param  boulder
	 *         The Boulder to check.
	 * @return True if and only if the given Boulder is effective
	 *         and that Boulder is a valid Boulder for this World.
	 *       | result ==
	 *       |   (boulder != null) &&
	 *       |   Boulder.isValidWorld(this)
	 */
	@Raw
	public boolean canHaveAsBoulder(Boulder boulder) {
		return (boulder != null) && (boulder.isValidWorld(this));
	}

	/**
	 * Check whether this World has proper Boulders attached to it.
	 * 
	 * @return True if and only if this World can have each of the
	 *         Boulders attached to it as one of its Boulders,
	 *         and if each of these Boulders references this World as
	 *         the World to which they are attached.
	 *       | for each boulder in Boulder:
	 *       |   if (hasAsBoulder(boulder))
	 *       |     then canHaveAsBoulder(boulder) &&
	 *       |          (boulder.getWorld() == this)
	 */
	public boolean hasProperBoulders() {
		for (Boulder boulder : boulders) {
			if (!canHaveAsBoulder(boulder))
				return false;
			if (boulder.getWorld() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of Boulders associated with this World.
	 *
	 * @return  The total number of Boulders collected in this World.
	 *        | result ==
	 *        |   card({boulder:Boulder | hasAsBoulder({boulder)})
	 */
	public int getNbBoulders() {
		return boulders.size();
	}

	/**
	 * Add the given Boulder to the set of Boulders of this World.
	 * 
	 * @param  boulder
	 *         The Boulder to be added.
	 * @throws IllegalArgumentException  
	 * 		   The given Boulder is effective, already references
	 *         this World and this World can have it as one of its boulders
	 *         . Else, throw exception
	 *       | ((boulder == null) || (boulder.getWorld() != this) || 
	 *       |   !this.canHaveAsBoulder(boulder))
	 * @post   This World has the given Boulder as one of its Boulders.
	 *       | new.hasAsBoulder(boulder)
	 */
	public void addBoulder(@Raw Boulder boulder) throws IllegalArgumentException, NullPointerException {
		if ((boulder == null) || (boulder.getWorld() != this)||(!this.canHaveAsBoulder(boulder)))
			throw new IllegalArgumentException("invalid log");
		boulders.add(boulder);
	}

	/**
	 * Remove the given Boulder from the set of Boulders of this World.
	 * 
	 * @param  boulder
	 *         The Boulder to be removed.
	 * @pre    This World has the given Boulder as one of
	 *         its Boulders, and the given Boulder does not
	 *         reference any World.
	 *       | this.hasAsBoulder(boulder) &&
	 * @post   This World no longer has the given Boulder as
	 *         one of its Boulders.
	 *       | ! new.hasAsBoulder(boulder)
	 */
	@Raw
	public void removeBoulder(Boulder boulder) throws IllegalArgumentException{
		if( !this.hasAsBoulder(boulder))
			throw new IllegalArgumentException("can't remove boulder!");
		boulders.remove(boulder);
	}
	
	/**
	 * Return the set of all boulders in this world
	 * 
	 * @return the boulders in this world
	 * 		| result = Set<Boulder> boulders
	 * 		| where for boulder in boulders
	 *  	|		this.hasAsBoulder(boulder)
	 */
	public Set<Boulder> getBoulders(){
		Set<Boulder> allBoulders = new HashSet<Boulder>();
		for(Boulder boulder: this.boulders){
			allBoulders.add(boulder);
		}
		return allBoulders;
	}

	/**
	 * Variable referencing a set collecting all the Boulders
	 * of this World.
	 * 
	 * @invar  The referenced set is effective.
	 *       | boulders != null
	 * @invar  Each Boulder registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each boulder in boulders:
	 *       |   ( (boulder != null) &&
	 *       |     (! boulder.isTerminated()) )
	 */
	private final Set<Boulder> boulders = new HashSet<Boulder>();
	

	/**
	 * Check whether this World has the given Unit as one of its
	 * Units.
	 * 
	 * @param  unit
	 *         The Unit to check.
	 */
	@Basic
	@Raw
	public boolean hasAsUnit(@Raw Unit unit) {
		return units.contains(unit);
	}

	/**
	 * Check whether this World can have the given Unit
	 * as one of its Units.
	 * 
	 * @param  unit
	 *         The Unit to check.
	 * @return True if and only if the given Unit is effective
	 *         and that Unit is a valid Unit for a World.
	 *       | result ==
	 *       |   (unit != null) &&
	 *       |   Unit.isValidWorld(this)
	 */
	@Raw
	public boolean canHaveAsUnit(Unit unit) {
		return (unit != null) && (unit.isValidWorld(this));
	}

	/**
	 * Check whether this World has proper Units attached to it.
	 * 
	 * @return True if and only if this World can have each of the
	 *         Units attached to it as one of its Units,
	 *         and if each of these Units references this World as
	 *         the World to which they are attached.
	 *       | for each unit in Unit:
	 *       |   if (hasAsUnit(unit))
	 *       |     then canHaveAsUnit(unit) &&
	 *       |          (unit.getWorld() == this)
	 */
	public boolean hasProperUnits() {
		for (Unit unit : units) {
			if (!canHaveAsUnit(unit))
				return false;
			if (unit.getWorld() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of Units associated with this World.
	 *
	 * @return  The total number of Units collected in this World.
	 *        | result ==
	 *        |   card({unit:Unit | hasAsUnit({unit)})
	 */
	public int getNbUnits() {
		return units.size();
	}

	/** 
	 * Add the given Unit to the set of Units of this World.
	 * 
	 * @param  unit
	 *         The Unit to be added.
	 * @throws IllegalArgumentException
	 * 		   The given Unit is effective and already references
	 *         this World. else, throw exception
	 *       | (unit == null)
	 * @post   This World has the given Unit as one of its Units.
	 *       | new.hasAsUnit(unit)
	 * @effect The unit is assigned a valid faction
	 * 		 | this.assignFactionTo(unit)
	 */
	public void addUnit(@Raw Unit unit) throws IllegalArgumentException{
		if((unit == null))
			throw new IllegalArgumentException("unit can't be null");
		if(!this.isValidWorldPosition(unit.getCubePosition()) || 
				!this.isPassableTerrain(unit.getCubePosition()))
			throw new IllegalArgumentException("unit is not in a valid location");
		this.assignFactionTo(unit);
		unit.setWorld(this);
		units.add(unit);
	}

	/**
	 * Remove the given Unit from the set of Units of this World.
	 * 
	 * @param  unit
	 *         The Unit to be removed.
	 * @throws IllegalArgumentException
	 * 	       This World has the given Unit as one of
	 *         its Units, and the given Unit does not
	 *         reference any World. Else, throw exception
	 *       | !this.hasAsUnit(unit) ||
	 *       | (unit.getWorld() != null)
	 * @post   This World no longer has the given Unit as
	 *         one of its Units.
	 *       | ! new.hasAsUnit(unit)
	 */
	@Raw
	public void removeUnit(Unit unit) throws IllegalArgumentException {
		if(!this.hasAsUnit(unit) || (unit.getWorld() != null))
			throw new IllegalArgumentException("can't remove unit");
		units.remove(unit);
	}
	
	/**
	 * Return the set of all Units in this world
	 */
	public Set<Unit> getUnits(){
		Set<Unit> units = new HashSet<Unit>();
		units.addAll(this.units);
		return units;
	}

	/**
	 * Variable referencing a set collecting all the Units
	 * of this World.
	 * 
	 * @invar  The referenced set is effective.
	 *       | units != null
	 * @invar  Each Unit registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each unit in units:
	 *       |   ( (unit != null) &&
	 *       |     (! unit.isTerminated()) )
	 */
	private final Set<Unit> units = new HashSet<Unit>();


	/**
	 * Check whether this World has the given Faction as one of its
	 * Factions.
	 * 
	 * @param  faction
	 *         The Faction to check.
	 */
	@Basic
	@Raw
	public boolean hasAsFaction(@Raw Faction faction) {
		return factions.contains(faction);
	}

	/**
	 * Check whether this World can have the given Faction
	 * as one of its Factions.
	 * 
	 * @param  faction
	 *         The Faction to check.
	 * @return True if and only if the given Faction is effective
	 *         and that Faction is a valid Faction for a World.
	 *       | result ==
	 *       |   (faction != null) &&
	 *       |   Faction.isValidWorld(this)
	 */
	@Raw
	public boolean canHaveAsFaction(Faction faction) {
		return (faction != null) && (faction.isValidWorld(this));
	}

	/**
	 * Check whether this World has proper Factions attached to it.
	 * 
	 * @return True if and only if this World can have each of the
	 *         Factions attached to it as one of its Factions,
	 *         and if each of these Factions references this World as
	 *         the World to which they are attached.
	 *       | for each faction in Faction:
	 *       |   if (hasAsFaction(faction))
	 *       |     then canHaveAsFaction(faction) &&
	 *       |          (faction.getWorld() == this)
	 */
	public boolean hasProperFactions() {
		for (Faction faction : factions) {
			if (!canHaveAsFaction(faction))
				return false;
			if (faction.getWorld() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of Factions associated with this World.
	 *
	 * @return  The total number of Factions collected in this World.
	 *        | result =
	 *        |   card({faction:Faction | hasAsFaction({faction)})
	 */
	public int getNbFactions() {
		return factions.size();
	}
	
	/**
	 * Return the number of Factions with one or more unit.
	 * 
	 * @return the amount of active factions
	 * 			| result = this.getActiveFactions.size()
	 */
	public int getNbActiveFactions(){
		return this.getActiveFactions().size();
	}
	
	
	/**
	 * returns all factions with 1 or more units
	 * 
	 * @return a set of all active factions 
	 * 			| Set<Faction> factions
	 * 			| where for every faction in factions
	 * 			|	faction.getNbUnits > 0 && faction.getWorld == this
	 */
	public Set<Faction> getActiveFactions(){
		Set<Faction> activeFactions = new HashSet<Faction>();
		for(Faction faction: this.factions){
			if (faction.getNbUnits() > 0){
				activeFactions.add(faction);
			}
		}
		return activeFactions;
	}
	
	/** 
	 * Returns the smallest active faction
	 * 
	 * @return the smallest faction with 1 or more units
	 * 	       | result = faction
	 * 		   | where faction.getNbUnits > 0 &&
	 * 		   | 	faction.getNbUnits >= otherFaction.getNbUnits
	 * 		   | 	with otherFaction any Faction with otherFaction.getWorld == this
	 */
	private Faction getSmallestFaction(){
		Faction smallestSoFar = null;
		int NbUnitsSmallest = Integer.MAX_VALUE;
		for(Faction faction: factions){
			if(faction.getNbUnits() < NbUnitsSmallest){
				smallestSoFar = faction;
				NbUnitsSmallest = faction.getNbUnits();
			}
		}
		return smallestSoFar;
	}
	
	
	/**
	 * Add the given Faction to the set of Factions of this World.
	 * 
	 * @param  faction
	 *         The Faction to be added.
	 * @throws IllegalArgumentException
	 * 			The given Faction is effective and already references
	 *         this World. Else, throw exception
	 *       | (faction == null) || (faction.getWorld() != this)
	 * @post   This World has the given Faction as one of its Factions.
	 *       | new.hasAsFaction(faction)
	 */
	public void addFaction(@Raw Faction faction) {
		if((faction == null) || (faction.getWorld() != this))
			throw new IllegalArgumentException("not a valid faction for this world");
		factions.add(faction);
	}

	/**
	 * Remove the given Faction from the set of Factions of this World.
	 * 
	 * @param  faction
	 *         The Faction to be removed.
	 * @throws IllegalArgumentException
	 * 		   This World has the given Faction as one of
	 *         its Factions, and the given Faction does not
	 *         reference any World. Else, throw exception
	 *       | !this.hasAsFaction(faction) ||
	 *       | (faction.getWorld() != null)
	 * @post   This World no longer has the given Faction as
	 *         one of its Factions.
	 *       | ! new.hasAsFaction(faction)
	 */
	@Raw
	private void removeFaction(Faction faction) throws IllegalArgumentException{
		if (!this.hasAsFaction(faction) || (faction.getWorld() != null))
			throw new IllegalArgumentException("not a valid faction to removes");
		factions.remove(faction);
	}

	/**
	 * Variable referencing a set collecting all the Factions
	 * of this World.
	 * 
	 * @invar  The referenced set is effective.
	 *       | factions != null
	 * @invar  Each Faction registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each faction in factions:
	 *       |   ( (faction != null) &&
	 *       |     (! faction.isTerminated()) )
	 */
	private final Set<Faction> factions = new HashSet<Faction>();
	
	
	/**
	 * add a new unit to this world, with random (valid) attributes and position.
	 * the defaultbehaviour of this new unit is set to enabledefaultbehaviour
	 * 
	 * @param 	enableDefaultBehavior
	 * 		  	Whether or not the defaultbehaviour of the new unit 
	 * 		  	should be enabled
	 * @post	A new Unit is created, with valid, semi-random properties.
	 * 			The unit is then added to this world.
	 * @return 	The spawned unit, who is in this world and has a valid faction.
	 * 			If it is not possible to add a unit to this world, return null.
	 */
	public Unit spawnUnit(boolean enableDefaultBehavior){
		if(this.getNbUnits()== MAX_NB_UNITS_IN_WORLD){
			throw new IllegalStateException("max units reached");
		}
		
		int strength = new Random().nextInt(76) + 25;
		int agility = new Random().nextInt(76) + 25;
		int toughness = new Random().nextInt(76) + 25;
		int minimumWeight = (strength + agility)/2;
		int weight = new Random().nextInt(100-minimumWeight) + minimumWeight;
		
		int[] position = new int[3];
		position[0] = new Random().nextInt(this.getNbCubesX()+1);
		position[1] = new Random().nextInt(this.getNbCubesY()+1);
		position[2] = new Random().nextInt(this.getNbCubesZ()+1);
		
		while(!(this.isPassableTerrain(position) && this.hasSolidBelow(position))){
			position[0] = new Random().nextInt(this.getNbCubesX());
			position[1] = new Random().nextInt(this.getNbCubesY());
			position[2] = new Random().nextInt(this.getNbCubesZ());
		}
		Unit unit = new Unit("Hillbilly", position, weight, strength, agility, toughness, enableDefaultBehavior);
		
		try{
			this.addUnit(unit);
		}
		catch(Exception e){
			return null;
		}
		
		return unit;
		
	}
	
	/**
	 * Assign a faction to a unit. If no viable factions are available,
	 * and no new factions can be made, throw exception
	 * 
	 * @param unit
	 * 		  The unit to assign a faction to
	 * @post  The faction of the unit, was the faction with the smallest
	 * 		  amount of units, or a new faction if the maximum amount
	 * 		  of factions wasn't reached yet
	 * @throws IllegalSateException
	 * 		   If no valid factions are available, and no new factions can
	 * 		   be created
	 */
	public void assignFactionTo(Unit unit) throws IllegalStateException{
		Faction faction = null;
		if(this.getNbActiveFactions() < MAX_NB_ACTIVE_FACTIONS){
			faction = new Faction(this);
			this.addFaction(faction);
		}
		else{
			faction = this.getSmallestFaction();
		}
		
		if(faction.getNbUnits() > MAX_NB_UNITS_IN_FACTION){
			throw new IllegalStateException("No faction available!");
		}
		
		unit.setFaction(faction);
		faction.addUnit(unit);
	}
	
	/** TODO stream
	 * Returns the log at the given position, if there is one. returns null otherwise.
	 * 
	 * @param 	position
	 * 			| the position at which the log should be
	 * @return	the log at the given position, otherwise null
	 * 			| result = log 
	 * 			| where log.getCubePosition == position
	 */
	public Log logAtPosition(int[] position){
		for(Log log: this.logs){
			if(Arrays.equals(log.getCubePosition(), position)){
				return log;
			}
		}
		return null;
	}
	
	/**
	 * Returns the boulder at the given position, if there is one. returns null otherwise.
	 * 
	 * @param 	position
	 * 			| the position at which the boulder should be
	 * @return	the boulder at the given position, otherwise null
	 * 			| result = boulder 
	 * 			| where boulder.getCubePosition == position
	 */
	public Boulder boulderAtPosition(int[] position){
		for(Boulder boulder: this.boulders){
			if(Arrays.equals(boulder.getCubePosition(), position)){
				return boulder;
			}
		}
		return null;
	}
	
	/**
	 * advance the time for all the gameobjects currently in this world
	 * 
	 * @param dt
	 * 		  The amount of time advanced
	 * 
	 * @effect advance the time for every boulder by dt
	 * 			| for(Boudler boulder in this.getBoulders())
	 * 			|	do boulder.advanceTime(dt)
	 * @effect advance the time for every log by dt
	 * 			| for(Log log in this.getLogs())
	 * 			|	do log.advanceTime(dt)
	 * @effect advance the time for every Unit by dt
	 * 			| for(Unit unit in this.getUnits())
	 * 			|	do unit.advanceTime(dt)
	 * 
	 * @post the set toRemove is empty
	 * 		| new.toRemove.size == 0
	 */
	public void advanceTime(double dt){
		for(Boulder boulder: this.getBoulders()){
			boulder.advanceTime(dt);
		}
		for(Log log: this.getLogs()){
			log.advanceTime(dt);
		}
		for(Unit unit: this.getUnits()){
			unit.advanceTime(dt);
		}
		
		for(Unit unit: toRemove){
			unit.setWorld(null);
			this.removeUnit(unit);
			
		}
		this.toRemove = new HashSet<Unit>();
	}
	
	/**
	 * return a random workshop in this world
	 * 
	 * @return The location of a workshop in this world
	 * 			| result = position
	 * 			| where this.getCubeType(position) == CubeType.WORKSHOP
	 */
	public int[] getWorkshopLocation(){
		int random = new Random().nextInt(this.workshopLocations.length);
		return this.workshopLocations[random];
	}
	
	/**
	 * A variable containing the location of every workshop in this world.
	 */
	private int[][] workshopLocations;
	
	
	/**
	 * Schedule a unit to be removed
	 * 
	 * @param unit
	 * 		  The unit to be removed at the end of advancetime
	 * 
	 * @post unit is part of the toRemove set
	 * 		 | new.toRemove.contains(unit) == true
	 * 
	 * @throws NullPointerException
	 * 		   Unit can't be null
	 * 			| unit == null
	 * @throws IllegalStateException
	 * 		   Unit must be terminated to remove
	 * 			| !unit.isTerminated
	 */
	public void scheduleToRemove(Unit unit) throws NullPointerException, IllegalStateException{
		if(unit == null)
			throw new NullPointerException("can't remove null");
		if(!unit.isTerminated()){
			throw new IllegalStateException("unit must be terminated to remove from world");
		}
		toRemove.add(unit);
	}
	
	/**
	 * A set containing all units, terminated during advanceTime.
	 */
	private Set<Unit> toRemove = new HashSet<Unit>();
}	
