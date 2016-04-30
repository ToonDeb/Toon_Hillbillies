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
	 * @pre    The given TerrainChangeListener must be a valid TerrainChangeListener for any World.
	 *       | isValidTerrainChangeListener(TerrainChangeListener)
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
		
		ArrayList<ArrayList<Integer>> workshopLocations = new ArrayList<ArrayList<Integer>>();
		for(int x=0; x<this.getNbCubesX(); x++){
			for(int y=0; y<this.getNbCubesY(); y++){
				for(int z=0; z<this.getNbCubesZ();z++){
					if (this.getCubeType(x, y, z).isPassable()){
						this.getConnectedToBorder().changeSolidToPassable(x, y, z);
					}
					if(this.getCubeType(x, y, z) == CubeType.WORKSHOP){
						ArrayList<Integer> position = new ArrayList<Integer>();
						position.add(x);
						position.add(y);
						position.add(z);
						workshopLocations.add(position);
					}
				}
			}
		}
		this.workshopLocations = workshopLocations.stream().map(u -> u.toArray()).toArray(int[][]::new);
		
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
	 * @param  TerrainChangeListener
	 *         The TerrainChangeListener to check.
	 * @return 
	 *       | result == true 
	*/
	private static boolean isValidTerrainChangeListener(TerrainChangeListener modelListener) {
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
	private int[][][] getTerrainType() {
		return this.terrainType;
	}
	
	/** TODO: isvalidterrainType
	 * Check whether the given terrainTypes is a valid terrainTypes for
	 * any World.
	*/
	private static boolean isValidTerrainType(int[][][] terrainType) {
		return true;
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
	private void setTerrainType(int[][][] terrainType) 
			throws IllegalArgumentException {
		if (! isValidTerrainType(terrainType))
			throw new IllegalArgumentException();
		this.terrainType = terrainType;
	}
	
	/**
	 * returns true if the terrain at position is AIR or WORKSHOP
	 * 
	 * @param position
	 * @return
	 * @throws IllegalArgumentException
	 */
	public boolean isPassableTerrain(int[] position)throws IllegalArgumentException{
		if (! this.isValidWorldPosition(position))
			return false;
		
		if(this.getCubeType(position[0], position[1], position[2]).isPassable()){
			return true;
		}
		return false;	
	}
	
	/**
	 * Check if the given position is within this world
	 * @param 	position
	 * 			the position to check
	 * @return	return true if position falls within the borders of this world
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
	 */
	public boolean hasSolidBelow(int[] position){
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
	 */
	public boolean isNeighbouringSolid(int[] position){
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
	 * @param y
	 * @param z
	 * @return
	 */
	public CubeType getCubeType(int x, int y, int z){
		int typeInt = this.getTerrainType()[x][y][z];
		return CubeType.getCubeType(typeInt);
	}
	
	/**
	 * Set the cubeType at the given position to the given value
	 * @param x
	 * @param y
	 * @param z
	 * @param value
	 */
	@Raw 
	public void setCubeType(int x, int y, int z, int value) 
			throws IllegalArgumentException{
		CubeType cubeType = CubeType.getCubeType(value);
		if(cubeType == null)
			throw new IllegalArgumentException("wrong value");
		int[] position = {x, y, z};
		if(!this.isValidWorldPosition(position))
			throw new IllegalArgumentException("invalid position");
		this.getTerrainType()[x][y][z] = value;
		this.getTerrainChangeListener().notifyTerrainChanged(x, y, z);
		
		if(cubeType == CubeType.AIR){
			List<int[]> changedTerrain = this.getConnectedToBorder().changeSolidToPassable(x, y, z);
			for(int[] changedPosition: changedTerrain){
				int otherX = changedPosition[0];
				int otherY = changedPosition[1];
				int otherZ = changedPosition[2];
				CubeType type = this.getCubeType(otherX, otherY, otherZ);
				this.getTerrainType()[otherX][otherY][otherZ] = 0;
				this.getTerrainChangeListener().notifyTerrainChanged(otherX, otherY, otherZ);
				
				if((type == CubeType.ROCK)&&(random.nextDouble()<=0.25)){
					Boulder boulder = new Boulder(changedPosition,this);
					this.addBoulder(boulder);
				}
				
				if((type == CubeType.TREE)&&(random.nextDouble()<= 0.25)){
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
	private boolean hasProperLogs() {
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
	 * @pre    The given Boulder is effective and already references
	 *         this World.
	 *       | (boulder != null) && (boulder.getWorld() == this)
	 * @post   This World has the given Boulder as one of its Boulders.
	 *       | new.hasAsBoulder(boulder)
	 */
	public void addBoulder(@Raw Boulder boulder) throws IllegalArgumentException, NullPointerException {
		if (boulder == null)
			throw new NullPointerException("log can't be null");
		if ((boulder.getWorld() != this)||(!this.canHaveAsBoulder(boulder)))
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
	 * @pre    The given Unit is effective and already references
	 *         this World.
	 *       | (unit != null) && (unit.getWorld() == this)
	 * @post   This World has the given Unit as one of its Units.
	 *       | new.hasAsUnit(unit)
	 */
	public void addUnit(@Raw Unit unit) {
		if(unit == null)
			throw new NullPointerException("can't add null as unit");
		if(unit.getWorld()!= this)
			throw new IllegalArgumentException("unit has other world");
		units.add(unit);
	}

	/**
	 * Remove the given Unit from the set of Units of this World.
	 * 
	 * @param  unit
	 *         The Unit to be removed.
	 * @pre    This World has the given Unit as one of
	 *         its Units, and the given Unit does not
	 *         reference any World.
	 *       | this.hasAsUnit(unit) &&
	 *       | (unit.getWorld() == null)
	 * @post   This World no longer has the given Unit as
	 *         one of its Units.
	 *       | ! new.hasAsUnit(unit)
	 */
	@Raw
	public void removeUnit(Unit unit) {
		assert this.hasAsUnit(unit) && (unit.getWorld() == null);
		units.remove(unit);
	}
	
	/**
	 * Return the set of all Units in this world
	 */
	public Set<Unit> getUnits(){
		return this.units;
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
	 *        | result ==
	 *        |   card({faction:Faction | hasAsFaction({faction)})
	 */
	public int getNbFactions() {
		return factions.size();
	}
	
	/**
	 * Return the number of Factions with one or more unit.
	 */
	public int getNbActiveFactions(){
		int i = 0;
		
		for(Faction faction: factions){
			if (faction.getNbUnits() > 0){
				i += 1;
			}
		}
		
		return i;
	}
	
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
	 * @return
	 */
	public Faction getSmallestFaction(){
		Faction smallestSoFar = null;
		int NbUnitsSmallest = 9999;
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
	 * @pre    The given Faction is effective and already references
	 *         this World.
	 *       | (faction != null) && (faction.getWorld() == this)
	 * @post   This World has the given Faction as one of its Factions.
	 *       | new.hasAsFaction(faction)
	 */
	public void addFaction(@Raw Faction faction) {
		assert (faction != null) && (faction.getWorld() == this);
		factions.add(faction);
	}

	/**
	 * Remove the given Faction from the set of Factions of this World.
	 * 
	 * @param  faction
	 *         The Faction to be removed.
	 * @pre    This World has the given Faction as one of
	 *         its Factions, and the given Faction does not
	 *         reference any World.
	 *       | this.hasAsFaction(faction) &&
	 *       | (faction.getWorld() == null)
	 * @post   This World no longer has the given Faction as
	 *         one of its Factions.
	 *       | ! new.hasAsFaction(faction)
	 */
	@Raw
	public void removeFaction(Faction faction) {
		assert this.hasAsFaction(faction) && (faction.getWorld() == null);
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
	
	Random random = new Random();
	
	/**
	 * add a new unit to this world, with random (valid) attributes and position.
	 * the defaultbehaviour of this new unit is set to enabledefaultbehaviour
	 * @param enableDefaultBehavior
	 */
	public Unit spawnUnit(boolean enableDefaultBehavior){
		if(this.getNbUnits()== MAX_NB_UNITS_IN_WORLD){
			throw new IllegalStateException("max units reached");
		}
		
		int strength = random.nextInt(76) + 25;
		int agility = random.nextInt(76) + 25;
		int toughness = random.nextInt(76) + 25;
		int minimumWeight = (strength + agility)/2;
		int weight = random.nextInt(100-minimumWeight) + minimumWeight;
		
		int[] position = new int[3];
		position[0] = random.nextInt(this.getNbCubesX()+1);
		position[1] = random.nextInt(this.getNbCubesY()+1);
		position[2] = random.nextInt(this.getNbCubesZ()+1);
		
		while(!(this.isPassableTerrain(position) && this.hasSolidBelow(position))){
			position[0] = random.nextInt(this.getNbCubesX());
			position[1] = random.nextInt(this.getNbCubesY());
			position[2] = random.nextInt(this.getNbCubesZ());
		}

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
		Unit unit = new Unit("Hillbilly", position, weight, strength, agility, toughness, this, faction, enableDefaultBehavior);
		return unit;
		
	}
	
	/**
	 * Returns the log at the given position, if there is one. returns null otherwise.
	 * 
	 * @param 	position
	 * 			| the position at which the log should be
	 * @return	the log at the given position, otherwise null
	 * 			| result = log : log.getCubePosition == position
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
	 * 			| result = boulder : boulder.getCubePosition == position
	 */
	public Boulder boulderAtPosition(int[] position){
		for(Boulder boulder: this.boulders){
			if(Arrays.equals(boulder.getCubePosition(), position)){
				return boulder;
			}
		}
		return null;
	}
	
//	/**
//	 * Returns the GameItem at the given position, if there is one. returns null otherwise.
//	 * 
//	 * @param 	position
//	 * 			| the position at which the GameItem should be
//	 * @return	the gameItem at the given position, otherwise null
//	 * 			| result = GameItem : GameItem.getCubePosition == position
//	 */
//	public GameItem gameItemAtPosition(int[] position){
//		for(Boulder boulder: this.boulders){
//			if(Arrays.equals(boulder.getCubePosition(), position)){
//				return boulder;
//			}
//		}
//		
//		for(Log log: this.logs){
//			if(Arrays.equals(log.getCubePosition(), position)){
//				return log;
//			}
//		}
//		
//		return null;
//	}
	
	/**
	 * advance the time for all the gameobjects currently in this world
	 * @param dt
	 */
	public void advanceTime(double dt){
		for(Boulder boulder: boulders){
			boulder.advanceTime(dt);
		}
		for(Log log: logs){
			log.advanceTime(dt);
		}
		for(Unit unit: units){
			unit.advanceTime(dt);
		}
	}
	
	/**
	 * return a random workshop in this world
	 * 
	 * @return The location of a workshop in this world
	 */
	public int[] getWorkshopLocation(){
		int random = new Random().nextInt(this.workshopLocations.length);
		return this.workshopLocations[random];
	}
	
//	/**
//	 * 
//	 */
//	public void searchWorkshopLocations(){
//		List<int[]> workshopLocations = new ArrayList<int[]>();
//		for(int x = 0; x < this.getNbCubesX(); x++){
//			for (int y = 0; y < this.getNbCubesY(); y++){
//				for (int z = 0; z < this.getNbCubesZ(); z++){
//					if(this.getCubeType(x, y, z) == CubeType.WORKSHOP){
//						int[] position = {x, y, z};
//						workshopLocations.add(position);
//					}
//				}
//			}
//		}
//		
//	}
	
	/**
	 * A variable containing the location of every workshop in this world.
	 */
	public int[][] workshopLocations;
}	
