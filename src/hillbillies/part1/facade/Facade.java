/**
 * 
 */
package hillbillies.part1.facade;

import javax.vecmath.Vector3d;

import hillbillies.model.Unit;
import hillbillies.model.UnitStatus;
import ogp.framework.util.ModelException;

/**
 * @author Nathan, Toon
 *
 */
public class Facade implements IFacade {

	@Override
	public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException {
		try{
		// change initialPosition from int to double
		
	
		//Unit unit = new Unit(name, initialPosition, weight, agility, strength, toughness);
		//if (enableDefaultBehavior)
			//unit.startDefaultBehaviour();
		return null;
		} catch (IllegalArgumentException e){
			throw new ModelException(e);
		} catch (IllegalStateException e) {
			throw new ModelException();
			
		}
	
	}

	
	@Override
	public double[] getPosition(Unit unit) throws ModelException {
		try{
			Vector3d vector = unit.getPosition();
		
		double[] position = {vector.x, vector.y, vector.z};
		return position;
		} catch (IllegalArgumentException e){
			throw new ModelException();
		} catch (IllegalStateException e) {
			throw new ModelException();
		}
	}

	
	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		try{
		return unit.getCubePosition();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public String getName(Unit unit) throws ModelException {
		try{
		return unit.getName();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public void setName(Unit unit, String newName) throws ModelException {
		try{
		unit.setName(newName);
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public int getWeight(Unit unit) throws ModelException {
	try{
		return unit.getWeight();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public void setWeight(Unit unit, int newValue) throws ModelException {
		try{
			unit.setWeight(newValue);
		
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public int getStrength(Unit unit) throws ModelException {
		try{
	
		return unit.getStrength();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public void setStrength(Unit unit, int newValue) throws ModelException {
		try{
		unit.setStrength(newValue);
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public int getAgility(Unit unit) throws ModelException {
		try{
		return unit.getAgility();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public void setAgility(Unit unit, int newValue) throws ModelException {
		try{
		unit.setAgility(newValue);
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	@Override
	public int getToughness(Unit unit) throws ModelException {
		try{
		return unit.getToughness();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public void setToughness(Unit unit, int newValue) throws ModelException {
		try{
		unit.setToughness(newValue);
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public int getMaxHitPoints(Unit unit) throws ModelException {
		try{
		return unit.getMaxHP();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public int getCurrentHitPoints(Unit unit) throws ModelException {
		try{
		return unit.getHP();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public int getMaxStaminaPoints(Unit unit) throws ModelException {
		try{
		return unit.getMaxStamina();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public int getCurrentStaminaPoints(Unit unit) throws ModelException {
		try{
		return unit.getStamina();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public void advanceTime(Unit unit, double dt) throws ModelException {
		try{
		unit.advanceTime(dt);
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		try{
		
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		try{
		if (! unit.isMoving())
			return 0;
		else
			return unit.getSpeed();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		try{
		return unit.isMoving();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	
	@Override
	public void startSprinting(Unit unit) throws ModelException {
		try{
		unit.startSprint();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public void stopSprinting(Unit unit) throws ModelException {
		try{
		unit.stopSprint();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public boolean isSprinting(Unit unit) throws ModelException {
		try{
		return (unit.getStatus() == UnitStatus.SPRINTING);
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public double getOrientation(Unit unit) throws ModelException {
		try{
		return unit.getOrientation();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		try{
		Vector3d destination = new Vector3d();
		destination.set(cube[0]+0.5,cube[1]+0.5, cube[2]+0.5);
		//unit.moveTo(destination);
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

	@Override
	public void work(Unit unit) throws ModelException {
		try{
		//unit.work();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		try{
		UnitStatus status = unit.getStatus();
		return (status == UnitStatus.WORKING);
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		try{
			attacker.attack(defender);
		} catch (IllegalArgumentException e){
			throw new ModelException();
		} catch (IllegalStateException e) {
			throw new ModelException();
		}
	}


	@Override
	public boolean isAttacking(Unit unit) throws ModelException {
		try{
		UnitStatus status = unit.getStatus();
		return (status == UnitStatus.ATTACKING);
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public void rest(Unit unit) throws ModelException {
		try{
		unit.rest();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public boolean isResting(Unit unit) throws ModelException {
		try{
		UnitStatus status = unit.getStatus();
		return (status == UnitStatus.REST) || (status == UnitStatus.RESTING);
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
		try{
		if (value)
			unit.startDefaultBehaviour();
		else
			unit.stopDefaultBehaviour();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}


	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		try{
		return unit.getDefaultBoolean();
	} catch (IllegalArgumentException e){
		throw new ModelException();
	} catch (IllegalStateException e) {
		throw new ModelException();
	}
	}

}
