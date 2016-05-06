package hillbillies.part2.facade;

import java.util.Set;

import javax.vecmath.Vector3d;

import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Unit;
//import hillbillies.model.UnitStatus;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;

/**
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class Facade implements IFacade{

	@Override
	public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException {
		int[][][] terrain = {{{0}}};
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		World world = new World(terrain, defaultListener);
		return new Unit(name, initialPosition, weight, agility, strength, toughness, world, null, enableDefaultBehavior);
	}

	@Override
	public double[] getPosition(Unit unit) throws ModelException {
		Vector3d vector = unit.getPosition();
		
		double[] position = {vector.x, vector.y, vector.z};
		return position;
	}

	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		return unit.getCubePosition();
	}

	@Override
	public String getName(Unit unit) throws ModelException {
		return unit.getName();
	}

	@Override
	public void setName(Unit unit, String newName) throws ModelException {
		try{
			unit.setName(newName);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public int getWeight(Unit unit) throws ModelException {
		return unit.getWeight();
	}

	@Override
	public void setWeight(Unit unit, int newValue) throws ModelException {
		unit.setWeight(newValue);
	}

	@Override
	public int getStrength(Unit unit) throws ModelException {
		return unit.getStrength();
	}

	@Override
	public void setStrength(Unit unit, int newValue) throws ModelException {
		unit.setWeight(newValue);
	}

	@Override
	public int getAgility(Unit unit) throws ModelException {
		return unit.getAgility();
	}

	@Override
	public void setAgility(Unit unit, int newValue) throws ModelException {
		unit.setAgility(newValue);
	}

	@Override
	public int getToughness(Unit unit) throws ModelException {
		return unit.getToughness();
	}

	@Override
	public void setToughness(Unit unit, int newValue) throws ModelException {
		unit.setToughness(newValue);
	}

	@Override
	public int getMaxHitPoints(Unit unit) throws ModelException {
		return unit.getMaxHP();
	}

	@Override
	public int getCurrentHitPoints(Unit unit) throws ModelException {
		return unit.getHP();
	}

	@Override
	public int getMaxStaminaPoints(Unit unit) throws ModelException {
		return unit.getMaxStamina();
	}

	@Override
	public int getCurrentStaminaPoints(Unit unit) throws ModelException {
		return unit.getStamina();
	}

	@Override
	public void advanceTime(Unit unit, double dt) throws ModelException {
		try{
		unit.advanceTime(dt);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		try{
			unit.newMoveToAdjacent(dx, dy, dz);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}

	
	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		return unit.getSpeed();
	}

	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		return unit.isMoving();
	}

	@Override
	public void startSprinting(Unit unit) throws ModelException {
		try{
			unit.startSprint();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}

	@Override
	public void stopSprinting(Unit unit) throws ModelException {
		try{
			unit.stopSprint();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public boolean isSprinting(Unit unit) throws ModelException {
		return unit.isSprinting();
	}

	@Override
	public double getOrientation(Unit unit) throws ModelException {
		return unit.getOrientation();
	}

	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		try{
			unit.moveTo(cube);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

//	@Override
//	@Deprecated
//	public void work(Unit unit) throws ModelException {
//		
//	}

	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		return unit.isWorking();
	}

	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		try{
			attacker.attack(defender);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}

	@Override
	public boolean isAttacking(Unit unit) throws ModelException {
		return unit.isAttacking();
	}

	@Override
	public void rest(Unit unit) throws ModelException {
		try {
			unit.rest();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public boolean isResting(Unit unit) throws ModelException {
		return unit.isResting();
	}

	@Override
	public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
		try{
			if(value)
				unit.startDefaultBehaviour();
			else
				unit.stopDefaultBehaviour();
		}
		catch (Exception e){
			throw new ModelException(e);
		}
	}

	
	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		return unit.getDefaultBoolean();
	}

	@Override
	public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException {
		try{
			World world = new World(terrainTypes, modelListener);
			return world;
		}
		catch (Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public int getNbCubesX(World world) throws ModelException {
		return world.getNbCubesX();
	}

	@Override
	public int getNbCubesY(World world) throws ModelException {
		return world.getNbCubesY();
	}

	@Override
	public int getNbCubesZ(World world) throws ModelException {
		return world.getNbCubesZ();
	}

	@Override
	public void advanceTime(World world, double dt) throws ModelException {
		try{
			world.advanceTime(dt);
		}
		catch (Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public int getCubeType(World world, int x, int y, int z) throws ModelException {
		try{
			return world.getCubeType(x, y, z).getNumber();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {
		try{
			world.setCubeType(x, y, z, value);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
		try{
			return world.getConnectedToBorder().isSolidConnectedToBorder(x, y, z);
		}
		catch (Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
		try{
			Unit unit = world.spawnUnit(enableDefaultBehavior);
			return unit;
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public void addUnit(Unit unit, World world) throws ModelException {
		try{
			unit.setWorld(world);
			world.addUnit(unit);
		}
		catch (Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public Set<Unit> getUnits(World world) throws ModelException {
		return world.getUnits();
	}

	@Override
	public boolean isCarryingLog(Unit unit) throws ModelException {
		return unit.isCarryingLog();
	}

	@Override
	public boolean isCarryingBoulder(Unit unit) throws ModelException {
		return unit.isCarryingBoulder();
	}

	@Override
	public boolean isAlive(Unit unit) throws ModelException {
		return !unit.isTerminated();
	}

	@Override
	public int getExperiencePoints(Unit unit) throws ModelException {
		return unit.getExperience();
	}

	@Override
	public void workAt(Unit unit, int x, int y, int z) throws ModelException {
		try{ 
			int[] target = {x, y, z};
			unit.workAt(target);
		}
		catch (Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public Faction getFaction(Unit unit) throws ModelException {
		return unit.getFaction();
	}

	@Override
	public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
		return faction.getUnitsOfFaction();
	}

	@Override
	public Set<Faction> getActiveFactions(World world) throws ModelException {
		try{
			return world.getActiveFactions();
		}
		catch (Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public double[] getPosition(Boulder boulder) throws ModelException {
		Vector3d vector = boulder.getPosition();
		double[] position = {vector.x, vector.y, vector.z};
		return position;
	}

	@Override
	public Set<Boulder> getBoulders(World world) throws ModelException {
		return world.getBoulders();
	}

	@Override
	public double[] getPosition(Log log) throws ModelException {
		Vector3d vector = log.getPosition();
		double[] position = {vector.x, vector.y, vector.z};
		return position;
	}

	@Override
	public Set<Log> getLogs(World world) throws ModelException {
		return world.getLogs();
	}

}
