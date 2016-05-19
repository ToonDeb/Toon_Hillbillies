package hillbillies.part3.facade;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.vecmath.Vector3d;

import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.TaskFactory;
import ogp.framework.util.ModelException;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class Facade implements IFacade {

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#createWorld(int[][][], hillbillies.part2.listener.TerrainChangeListener)
	 */
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

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getNbCubesX(hillbillies.model.World)
	 */
	@Override
	public int getNbCubesX(World world) throws ModelException {
		return world.getNbCubesX();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getNbCubesY(hillbillies.model.World)
	 */
	@Override
	public int getNbCubesY(World world) throws ModelException {
		return world.getNbCubesY();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getNbCubesZ(hillbillies.model.World)
	 */
	@Override
	public int getNbCubesZ(World world) throws ModelException {
		return world.getNbCubesZ();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#advanceTime(hillbillies.model.World, double)
	 */
	@Override
	public void advanceTime(World world, double dt) throws ModelException {
		try{
			world.advanceTime(dt);
		}
		catch (Exception e){
			throw new ModelException(e);
		}

	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getCubeType(hillbillies.model.World, int, int, int)
	 */
	@Override
	public int getCubeType(World world, int x, int y, int z) throws ModelException {
		try{
			return world.getCubeType(x, y, z).getNumber();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#setCubeType(hillbillies.model.World, int, int, int, int)
	 */
	@Override
	public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {
		try{
			world.setCubeType(x, y, z, value);
		}
		catch(Exception e){
			throw new ModelException(e);
		}

	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#isSolidConnectedToBorder(hillbillies.model.World, int, int, int)
	 */
	@Override
	public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
		try{
			return world.getConnectedToBorder().isSolidConnectedToBorder(x, y, z);
		}
		catch (Exception e){
			throw new ModelException(e);
		}
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#spawnUnit(hillbillies.model.World, boolean)
	 */
	@Override
	public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
		try{
			Unit unit = world.spawnUnit(enableDefaultBehavior);
			return unit;
		}
		catch(Exception e){
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#addUnit(hillbillies.model.Unit, hillbillies.model.World)
	 */
	@Override
	public void addUnit(Unit unit, World world) throws ModelException {
		try{
			System.out.println("unit added");
			unit.setWorld(world);
			world.addUnit(unit);
		}
		catch (Exception e){
			throw new ModelException(e);
		}

	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getUnits(hillbillies.model.World)
	 */
	@Override
	public Set<Unit> getUnits(World world) throws ModelException {
		return world.getUnits();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#isCarryingLog(hillbillies.model.Unit)
	 */
	@Override
	public boolean isCarryingLog(Unit unit) throws ModelException {
		return unit.isCarryingLog();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#isCarryingBoulder(hillbillies.model.Unit)
	 */
	@Override
	public boolean isCarryingBoulder(Unit unit) throws ModelException {
		return unit.isCarryingBoulder();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#isAlive(hillbillies.model.Unit)
	 */
	@Override
	public boolean isAlive(Unit unit) throws ModelException {
		return !unit.isTerminated();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getExperiencePoints(hillbillies.model.Unit)
	 */
	@Override
	public int getExperiencePoints(Unit unit) throws ModelException {
		return unit.getExperience();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#workAt(hillbillies.model.Unit, int, int, int)
	 */
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

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getFaction(hillbillies.model.Unit)
	 */
	@Override
	public Faction getFaction(Unit unit) throws ModelException {
		return unit.getFaction();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getUnitsOfFaction(hillbillies.model.Faction)
	 */
	@Override
	public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
		return faction.getUnitsOfFaction();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getActiveFactions(hillbillies.model.World)
	 */
	@Override
	public Set<Faction> getActiveFactions(World world) throws ModelException {
		try{
			return world.getActiveFactions();
		}
		catch (Exception e){
			throw new ModelException(e);
		}
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getPosition(hillbillies.model.Boulder)
	 */
	@Override
	public double[] getPosition(Boulder boulder) throws ModelException {
		Vector3d vector = boulder.getPosition();
		double[] position = {vector.x, vector.y, vector.z};
		return position;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getBoulders(hillbillies.model.World)
	 */
	@Override
	public Set<Boulder> getBoulders(World world) throws ModelException {
		return world.getBoulders();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getPosition(hillbillies.model.Log)
	 */
	@Override
	public double[] getPosition(Log log) throws ModelException {
		Vector3d vector = log.getPosition();
		double[] position = {vector.x, vector.y, vector.z};
		return position;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part2.facade.IFacade#getLogs(hillbillies.model.World)
	 */
	@Override
	public Set<Log> getLogs(World world) throws ModelException {
		return world.getLogs();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#createUnit(java.lang.String, int[], int, int, int, int, boolean)
	 */
	@Override
	public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException {
		return new Unit(name, initialPosition, weight, agility, strength, toughness, enableDefaultBehavior);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getPosition(hillbillies.model.Unit)
	 */
	@Override
	public double[] getPosition(Unit unit) throws ModelException {
		Vector3d vector = unit.getPosition();
		double[] position = {vector.x, vector.y, vector.z};
		return position;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getCubeCoordinate(hillbillies.model.Unit)
	 */
	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		return unit.getCubePosition();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getName(hillbillies.model.Unit)
	 */
	@Override
	public String getName(Unit unit) throws ModelException {
		return unit.getName();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#setName(hillbillies.model.Unit, java.lang.String)
	 */
	@Override
	public void setName(Unit unit, String newName) throws ModelException {
		try{
			unit.setName(newName);
		}
		catch(Exception e){
			throw new ModelException(e);
		}

	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getWeight(hillbillies.model.Unit)
	 */
	@Override
	public int getWeight(Unit unit) throws ModelException {
		return unit.getWeight();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#setWeight(hillbillies.model.Unit, int)
	 */
	@Override
	public void setWeight(Unit unit, int newValue) throws ModelException {
		unit.setWeight(newValue);

	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getStrength(hillbillies.model.Unit)
	 */
	@Override
	public int getStrength(Unit unit) throws ModelException {
		return unit.getStrength();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#setStrength(hillbillies.model.Unit, int)
	 */
	@Override
	public void setStrength(Unit unit, int newValue) throws ModelException {
		unit.setWeight(newValue);

	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getAgility(hillbillies.model.Unit)
	 */
	@Override
	public int getAgility(Unit unit) throws ModelException {
		return unit.getAgility();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#setAgility(hillbillies.model.Unit, int)
	 */
	@Override
	public void setAgility(Unit unit, int newValue) throws ModelException {
		unit.setAgility(newValue);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getToughness(hillbillies.model.Unit)
	 */
	@Override
	public int getToughness(Unit unit) throws ModelException {
		return unit.getToughness();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#setToughness(hillbillies.model.Unit, int)
	 */
	@Override
	public void setToughness(Unit unit, int newValue) throws ModelException {
		unit.setToughness(newValue);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getMaxHitPoints(hillbillies.model.Unit)
	 */
	@Override
	public int getMaxHitPoints(Unit unit) throws ModelException {
		return unit.getMaxHP();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getCurrentHitPoints(hillbillies.model.Unit)
	 */
	@Override
	public int getCurrentHitPoints(Unit unit) throws ModelException {
		return unit.getHP();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getMaxStaminaPoints(hillbillies.model.Unit)
	 */
	@Override
	public int getMaxStaminaPoints(Unit unit) throws ModelException {
		return unit.getMaxStamina();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getCurrentStaminaPoints(hillbillies.model.Unit)
	 */
	@Override
	public int getCurrentStaminaPoints(Unit unit) throws ModelException {
		return unit.getStamina();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#advanceTime(hillbillies.model.Unit, double)
	 */
	@Override
	public void advanceTime(Unit unit, double dt) throws ModelException {
		try{
			unit.advanceTime(dt);
		}
		catch(Exception e){
			throw new ModelException(e);
		}

	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#moveToAdjacent(hillbillies.model.Unit, int, int, int)
	 */
	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		try{
			unit.facadeMoveToAdjacent(dx, dy, dz);
		}
		catch(Exception e){
			throw new ModelException(e);
		}

	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getCurrentSpeed(hillbillies.model.Unit)
	 */
	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		return unit.getSpeed();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#isMoving(hillbillies.model.Unit)
	 */
	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		return unit.isMoving();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#startSprinting(hillbillies.model.Unit)
	 */
	@Override
	public void startSprinting(Unit unit) throws ModelException {
		try{
			unit.startSprint();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#stopSprinting(hillbillies.model.Unit)
	 */
	@Override
	public void stopSprinting(Unit unit) throws ModelException {
		try{
			unit.stopSprint();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#isSprinting(hillbillies.model.Unit)
	 */
	@Override
	public boolean isSprinting(Unit unit) throws ModelException {
		return unit.isSprinting();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#getOrientation(hillbillies.model.Unit)
	 */
	@Override
	public double getOrientation(Unit unit) throws ModelException {
		return unit.getOrientation();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#moveTo(hillbillies.model.Unit, int[])
	 */
	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		try{
			unit.moveTo(cube);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#work(hillbillies.model.Unit)
	 */
	@Override
	public void work(Unit unit) throws ModelException {
		// not used

	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#isWorking(hillbillies.model.Unit)
	 */
	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		return unit.isWorking();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#fight(hillbillies.model.Unit, hillbillies.model.Unit)
	 */
	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		try{
			attacker.attack(defender);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#isAttacking(hillbillies.model.Unit)
	 */
	@Override
	public boolean isAttacking(Unit unit) throws ModelException {
		return unit.isAttacking();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#rest(hillbillies.model.Unit)
	 */
	@Override
	public void rest(Unit unit) throws ModelException {
		try {
			unit.rest();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#isResting(hillbillies.model.Unit)
	 */
	@Override
	public boolean isResting(Unit unit) throws ModelException {
		return unit.isResting();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#setDefaultBehaviorEnabled(hillbillies.model.Unit, boolean)
	 */
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

	/* (non-Javadoc)
	 * @see hillbillies.part1.facade.IFacade#isDefaultBehaviorEnabled(hillbillies.model.Unit)
	 */
	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		return unit.getDefaultBoolean();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#createTaskFactory()
	 */
	@Override
	public ITaskFactory<?, ?, Task> createTaskFactory() {
		return new TaskFactory();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#isWellFormed(hillbillies.model.Task)
	 */
	@Override
	public boolean isWellFormed(Task task) throws ModelException {
		// not required
		return false;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#getScheduler(hillbillies.model.Faction)
	 */
	@Override
	public Scheduler getScheduler(Faction faction) throws ModelException {
		return faction.getScheduler();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#schedule(hillbillies.model.Scheduler, hillbillies.model.Task)
	 */
	@Override
	public void schedule(Scheduler scheduler, Task task) throws ModelException {
		scheduler.scheduleTask(task);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#replace(hillbillies.model.Scheduler, hillbillies.model.Task, hillbillies.model.Task)
	 */
	@Override
	public void replace(Scheduler scheduler, Task original, Task replacement) throws ModelException {
		try{
			scheduler.replaceTask(original, replacement);
		}
		catch (Exception e){
			throw new ModelException(e);
		}

	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#areTasksPartOf(hillbillies.model.Scheduler, java.util.Collection)
	 */
	@Override
	public boolean areTasksPartOf(Scheduler scheduler, Collection<Task> tasks) throws ModelException {
		try {
			return scheduler.arePartOf(tasks);
		} 
		catch (Exception e) {
			throw new ModelException(e);
		}
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#getAllTasksIterator(hillbillies.model.Scheduler)
	 */
	@Override
	public Iterator<Task> getAllTasksIterator(Scheduler scheduler) throws ModelException {
		return scheduler.iterator();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#getSchedulersForTask(hillbillies.model.Task)
	 */
	@Override
	public Set<Scheduler> getSchedulersForTask(Task task) throws ModelException {
		return task.getSchedulers();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#getAssignedUnit(hillbillies.model.Task)
	 */
	@Override
	public Unit getAssignedUnit(Task task) throws ModelException {
		return task.getUnit();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#getAssignedTask(hillbillies.model.Unit)
	 */
	@Override
	public Task getAssignedTask(Unit unit) throws ModelException {
		return unit.getTask();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#getName(hillbillies.model.Task)
	 */
	@Override
	public String getName(Task task) throws ModelException {
		return task.getName();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.facade.IFacade#getPriority(hillbillies.model.Task)
	 */
	@Override
	public int getPriority(Task task) throws ModelException {
		return task.getPriority();
	}

}
