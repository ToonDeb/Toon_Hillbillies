package hillbillies.model;

import hillbillies.model.UnitStatus;
import hillbillies.model.pathfinding.AStarPathFinder;
import hillbillies.model.pathfinding.Path;

import java.util.Arrays;
import java.util.Random;

import javax.vecmath.*;

import be.kuleuven.cs.som.annotate.*;
import ogp.framework.util.Util;

//import static hillbillies.model.Constants.MAX_X_POSITION;
//import static hillbillies.model.Constants.MAX_Y_POSITION;
//import static hillbillies.model.Constants.MAX_Z_POSITION;
import static hillbillies.model.Constants.MAX_NB_UNITS_IN_FACTION;

/**
 * 
 * 
 * @authors Toon Deburchgrave CWS-ELT
 *          
 * 
 *          A class for Units
 *
 * @invar The position of each Unit must be a valid position for any Unit. 
 * 		  | isValidPosition(getPosition())
 *
 * @invar The weight of each Unit must be a valid weight for any Unit. 
 * 		  | isValidWeight(getWeight())
 * @invar The strength of each Unit must be a valid strength for any Unit. 
 * 		  | isValidUnitAttribute(getStrength())
 * @invar The agility of each Unit must be a valid agility for any Unit. 
 * 		  | isValidUnitAttribute(getAgility())
 * @invar The toughness of each Unit must be a valid toughness for any Unit. 
 * 		  | isValidUnitAttribute(getToughness())
 *
 * @invar The hitpoints of each Unit must be a valid hitpoints for this Unit. 
 * 		  | isValidHP(getHP())
 * @invar The stamina of each Unit must be a valid stamina for any Unit. 
 * 		  | isValidStamina(getStamina())
 *
 * @invar The workTime of each Unit must be a valid workTime for any Unit. 
 * 		  | isValidWorkTime(getWorkTime())
 *
 * @invar The attackCountDown of each Unit must be a valid attackCountDown for
 *        any Unit. 
 *        | isValidAttackCountDown(getAttackCountDown())
 *
 * @invar The status of each Unit must be a valid status for any Unit. 
 * 		  | isValidStatus(getStatus())
 *
 * @invar The restTime of each Unit must be a valid restTime for any Unit. 
 *     	  | isValidRestTime(getRestTime())
 * 
 * @invar The name of each unit must be a valid name for any unit. 
 * 		  | isValidName(getName())
 *
 * @invar The defaultBoolean of each Unit must be a valid defaultBoolean for any
 *        Unit. 
 *        | isValidDefaultBoolean(getDefaultBoolean())
 * @invar The orientation of each unit must be a valid orientation for any unit.
 *        | isValidOrientation(getOrientation())
 * @invar The adjacentDestination of each unit must be a valid
 *        adjacentDestination for any unit.
 *        | isValidAdjacentDestination(getAdjacentDestination())
 * @invar The finalDestination of each unit must be a valid finalDestination for
 *        any unit. 
 *        | isValidFinalDestination(getFinalDestination())
 * 
 * @invar The walkTimer of each Unit must be a valid walkTimer for any Unit. 
 *        | isValidWalkTimer(getWalkTimer())
 *
 * @invar The origin of each Unit must be a valid origin for any Unit.
 *        | isValidOrigin(getOrigin())
 *
 * @invar  The Faction of each Unit must be a valid Faction for any
 *         Unit.
 *       | isValidFaction(getFaction())
 *       
 * @invar  The Experience of each Unit must be a valid Experience for any
 *         Unit.
 *       | isValidExperience(getExperience())
 *       
 * @invar  The gameItem of each Unit must be a valid gameItem for any
 *         Unit.
 *       | isValidGameItem(getGameItem())
 *       
 * @invar  The workTarget of each Unit must be a valid workTarget for any
 *         Unit.
 *       | isValidWorkTarget(getWorkTarget())
 *       
 * @invar  The pathIndex of each Unit must be a valid pathIndex for any
 *         Unit.
 *       | isValidPathIndex(getPathIndex())
 *       
 * @version 0.1
 */
public class Unit extends GameObject {
	
	//private static final int MAX_X_POSITION = 50;
	//private static final int MAX_Y_POSITION = 50;
	//private static final int MAX_Z_POSITION = 50;
	
	/**
	 * Initialize this new Unit with given position, strength, agility, weight
	 * and toughness.
	 *
	 *
	 * @param 	position
	 *        	The position for this new Unit.
	 * @effect 	The position of this new Unit is set to the given position.
	 *         	| this.setPosition(position)
	 * @throws 	IllegalArgumentException
	 * 		   	The given position is not a valid position for this Unit
	 * 
	 * @param 	orientation
	 *        	The orientation for this new unit.
	 * @post 	If the given orientation is a valid orientation for any unit, the
	 *       	orientation of this new unit is equal to the given orientation.
	 *       	Otherwise, the orientation of this new unit is equal to PI/2. 
	 *       	| if (isValidOrientation(orientation)) 
	 *       	| 		then new.getOrientation() == orientation 
	 *       	| else new.getOrientation() == PI/2
	 * 
	 * @param 	strength
	 *        	The strength for this new Unit.
	 * @post 	If the given strength is a valid strength for any starting Unit,
	 *       	the strength of this new Unit is equal to the given strength.
	 *       	Otherwise, the strength of this new Unit is equal to 25. 
	 *      	| if (isValidStartAttribute(strength)) 
	 *       	| 		then new.getStrength() == strength 
	 *       	| else new.getStrength() == 25
	 *
	 * @param 	agility
	 *        	The agility for this new Unit.
	 * @post 	If the given agility is a valid agility for any starting Unit, the
	 *       	agility of this new Unit is equal to the given agility. Otherwise,
	 *       	the agility of this new Unit is equal to 25. 
	 *       	| if (isValidStartAttribute(agility)) 
	 *       	| 		then new.getAgility() == agility
	 *      	| else new.getAgility() == 25
	 *
	 * @param 	weight
	 *        	The weight for this new Unit.
	 * @post 	If the given weight is a valid weight for any starting Unit, the
	 *       	weight of this new Unit is equal to the given weight. Otherwise,
	 *       	the weight of this new Unit is equal to 25. 
	 *       	| if (isValidStartWeight(weight)) 
	 *       	| 		then new.getWeight() == weight 
	 *       	| else new.getWeight() == 25
	 *
	 * @param 	toughness
	 *        	The toughness for this new Unit.
	 * @post 	If the given toughness is a valid toughness for any starting Unit,
	 *       	the toughness of this new Unit is equal to the given toughness.
	 *       	Otherwise, the toughness of this new Unit is equal to 25. 
	 *       	| if (isValidStartAttribute(toughness)) 
	 *       	| 		then new.getToughness() == toughness 
	 *       	| else new.getToughness() == 25
	 * 
	 * @post 	the orientation of this new Unit is PI/2 
	 * 		 	| new.getOrientation == PI/2
	 * 
	 * @post 	The stamina of this new Unit is equal to the calculated stamina, 
	 * 		 	depending on weight and toughness. 
	 * 			| new.getStamina() == 200*(weight()/100)*(toughness()/100)
	 *
	 * @post 	The hitpoints of this new Unit is equal to the calculated hitpoints,
	 * 		 	depending on weight and toughness. 
	 *       	| new.getHP() == 200*(weight/100)*(toughness/100)
	 *       
	 * @post	The experience of this new Unit is equal to 0.
	 * 			| new.getExperience() == 0
	 *
	 * @param 	name
	 *        	The name for this new unit.
	 * @effect 	The name of this new unit is set to the given name.
	 *         	| this.setName(name)
	 * @throws 	IllegalArgumentException
	 * 		   	The given name is not a valid name for a Unit
	 * 			| ! isValidName(name)
	 * 
	 * @effect 	The adjacentDestination of this new unit is set to the given
	 *         	position 
	 *         	| this.setAdjacentDestination(position)
	 * @throws 	IllegalArgumentException
	 * 		   	The given position is not a valid position for this Unit
	 * 			| ! isValidAdjacentDestination(position)
	 * 
	 * @effect 	The finalDestination of this new Unit is set to the given
	 *         	position 
	 *         	| this.setFinalDestination(position)
	 * @throws 	IllegalArgumentException
	 * 		   	The given position is not a valid position for this Unit
	 * 			| ! isValidFinalDestination(position)
	 * 	
	 * @effect 	The origin of this new Unit is set to the cubePosition of 
	 * 		   	the given position
	 * 		   	| this.setOrigin(new.getCubePosition)
	 * @throws 	IllegalArgumentException
	 * 		   	The given position is not a valid position for this Unit
	 * 			| ! isValidOrigin(new.getCubePosition)
	 * 
	 * 
	 */
	public Unit(String name, int[] position, int weight, int strength, int agility, 
			int toughness, World world, Faction faction, boolean defaultBehaviour)
				throws IllegalArgumentException {
		// null is given as the default world
		super(position, world);
		
		if(!faction.canHaveAsUnit(this))
			throw new IllegalArgumentException("not a valid faction for this unit!");
	
		if(!world.canHaveAsUnit(this))
			throw new IllegalArgumentException("not a valid world for this unit!");
		
		
		if (!isValidStartAttribute(strength))
			strength = 25;
		setStrength(strength);
		if (!isValidStartAttribute(agility))
			agility = 25;
		setAgility(agility);
		if (!isValidStartWeight(weight))
			weight = (this.getStrength() + this.getAgility()) / 2;
		setWeight(weight);
		if (!isValidStartAttribute(toughness))
			toughness = 25;
		setToughness(toughness);
		this.setHP(this.getMaxHP());
		this.setStamina(this.getMaxStamina());
		
		//this.setPosition(position);
		this.setName(name);

		this.setStatus(UnitStatus.IDLE);
		
		this.setAdjacentDestination(position);
		this.setFinalDestination(position);

		this.setOrigin(this.getCubePosition());
		
		world.addUnit(this);
		this.setFaction(faction);
		faction.addUnit(this);
	
		if (defaultBehaviour)
			this.startDefaultBehaviour();
		
	}
	
//	public Unit(String name, int[] position, int weight, int strength, int agility, int toughness, World world, Faction faction)
//			throws IllegalArgumentException {
//		this(name, position, weight, strength, agility, toughness);
//		if(!world.isValidWorldPosition(position)||!world.isPassableTerrain(position)||!world.isNeighbouringSolid(position)){
//			throw new IllegalArgumentException("not a valid position for a new unit!");
//		}
//		if(!faction.canHaveAsUnit(this)){
//			throw new IllegalArgumentException("not a valid faction for this unit!");
//		}
//		if(!world.canHaveAsUnit(this))
//			throw new IllegalArgumentException("not a valid world for this unit!");
//		
//		this.setWorld(world);
//		world.addUnit(this);
//		this.setFaction(faction);
//		faction.addUnit(this);
//	
//	}

//	/**
//	 * Return the position of this Unit.
//	 */
//	@Basic
//	@Raw
//	public Vector3d getPosition() {
//		return this.position;
//	}

//	/**
//	 * Return the position of the cube occupied by this Unit.
//	 */
//	public int[] getCubePosition() {
//		int cubeX = (int) Math.floor(this.getPosition().x);
//		int cubeY = (int) Math.floor(this.getPosition().y);
//		int cubeZ = (int) Math.floor(this.getPosition().z);
//		int[] cubePosition = { cubeX, cubeY, cubeZ };
//		return cubePosition;
//	}

//	/**
//	 * Check whether the given position is a valid position for any Unit.
//	 *
//	 * @param 	position
//	 *          The position to check.
//	 * @return 	False if the given position is not effective. 
//	 * 		   	| if (position == null) 
//	 * 		   	| 		then result == false 
//	 * 		   	Otherwise, true if all three coordinates are within the boundaries of the map 
//	 * 		   	| else if 
//	 * 		   	| (position.x >= 0) && (position.x < 50) && 
//	 * 		   	| (position.y >= 0) && (position.y < 50) && 
//	 *         	| (position.z >= 0) && (position.z < 50) 
//	 *         	| 		then result == true
//	 */
//	public static boolean isValidPosition(Vector3d position) {
//		return position != null && (position.x >= 0) && (position.x < MAX_X_POSITION) 
//				&& (position.y >= 0) && (position.y < MAX_Y_POSITION)
//				&& (position.z >= 0) && (position.z < MAX_Z_POSITION);
//	}

//	/**
//	 * Set the position of this Unit to the given position.
//	 *
//	 * @param 	position
//	 *          The new position for this Unit.
//	 * @post 	The position of this Unit is equal to the given position.
//	 *       	| new.getPosition() == position
//	 * @throws 	IllegalArgumentException
//	 *         	The given position is not a valid position for any Unit.
//	 *         	| ! isValidPosition(this.getPosition())
//	 */
//	@Raw
//	private void setPosition(Vector3d position) throws IllegalArgumentException {
//		if (!isValidPosition(position))
//			throw new IllegalArgumentException("the given position is not a valid position");
//		this.position = position;
//	}

//	/**
//	 * Variable registering the position of this Unit.
//	 */
//	private Vector3d position;

	/**
	 * Sets the units status to walking, and the units adjacentDestination to
	 * adjacentDestination.
	 * 
	 * @param 	adjacentDestination
	 *          The neighbouring destination
	 * @effect 	The units status is set to WALKING, the units adjacentDestination
	 *         	is set to adjacentDestination
	 *         	| this.setStatus(UnitStatus.WALKING); 
	 *         	| this.setAdjacentDestination(adjacentDestination);
	 * @effect	If the Unit is currently at its final destination, set
	 * 			finalDestination to the given adjacentDestination
	 * 			| if(this.getPosition == this.getFinalDestination)
	 * 			| 		then this.setFinalDestination(adjacentDestination)
	 * @throws 	IllegalArgumentException
	 *          The given adjacentDestination is not a valid
	 *          adjacentDestination
	 *          | ! isValidAdjacentDestination(adjacentDestination)
	 */
	public void moveToAdjacent(int[] adjacentDestination) throws IllegalArgumentException {
		if (!isValidAdjacentDestination(adjacentDestination))
			throw new IllegalArgumentException("Invalid adjacentDestination!");
		if(this.isFalling())
			throw new IllegalStateException("can't move while falling!");
		
		if(!this.isSprinting())
			this.setStatus(UnitStatus.WALKING);

		if (Arrays.equals(this.getCubePosition(), this.getFinalDestination())) {
			this.setAdjacentDestination(adjacentDestination);
			this.setFinalDestination(adjacentDestination);
			
		} 
		else
			this.setAdjacentDestination(adjacentDestination);
		this.setOrigin(this.getCubePosition());
		
		this.initiateWalkTimer(adjacentDestination);
	}
	
	public void newMoveToAdjacent(int dx, int dy, int dz){
		if(dx > 1 || dx < -1 || dy > 1 || dy < -1 || dz > 1 || dz < -1)
			throw new IllegalArgumentException("more then 1 away!");
		int[] vector = {this.getAdjacentDestination()[0] + dx,
				this.getAdjacentDestination()[1] + dy, this.getAdjacentDestination()[2] + dz};
		this.moveTo(vector);
	}

	/** TODO: update documentatie unit
	 * Sets the units status to walking, and the units adjacentDestination to
	 * adjacentDestination.
	 * 
	 * @param 	finalDestination
	 *          The final destination for this unit.
	 * @effect 	The units finalDestination is set to finalDestination, the unit
	 *         	will execute moveToAdjacent, with as argument the destination
	 *         	selected with findPath 
	 *         	| this.setFinalDestination(finalDestination);
	 *         	| this.moveToAdjacent(this.findPath)
	 * @throws 	IllegalArgumentException
	 *          The given finalDestination is not a valid finalDestination
	 *          | ! isValidFinalDestination(finalDestination)
	 */
	public void moveTo(int[] finalDestination) throws IllegalArgumentException {
		if (!this.getWorld().isValidWorldPosition(finalDestination) || 
				!this.getWorld().isNeighbouringSolid(finalDestination)||
				!this.getWorld().isPassableTerrain(finalDestination))
			throw new IllegalArgumentException("Invalid final destination!");
		if (this.isFalling())
			throw new IllegalStateException("can't move while falling");
		this.setFinalDestination(finalDestination);
		this.resetPath();
		this.moveToAdjacent(this.findPath());
	}

//	/**
//	 * Return the next step (the adjacent destination) of this unit based on its
//	 * final destination.
//	 * 
//	 * @return 	A Vector3d
//	 * 			
//	 */
//	private Vector3d findPath() {
//		double xFinalDes = this.getFinalDestination().getX();
//		double yFinalDes = this.getFinalDestination().getY();
//		double zFinalDes = this.getFinalDestination().getZ();
//		
//		double xThis = this.getPosition().getX();
//		double yThis = this.getPosition().getY();
//		double zThis = this.getPosition().getZ();
//		
//		double xAdjDes;
//		double yAdjDes;
//		double zAdjDes;
//
//		if (Util.fuzzyEquals(xThis, xFinalDes))
//			xAdjDes = this.getCubePosition()[0] + 0.5;
//		else if (xThis < xFinalDes)
//			xAdjDes = this.getCubePosition()[0] + 1.5;
//		else
//			xAdjDes = this.getCubePosition()[0] - 0.5;
//
//		if (Util.fuzzyEquals(yThis, yFinalDes))
//			yAdjDes = this.getCubePosition()[1] + 0.5;
//		else if (yThis < yFinalDes)
//			yAdjDes = this.getCubePosition()[1] + 1.5;
//		else
//			yAdjDes = this.getCubePosition()[1] - 0.5;
//
//		if (Util.fuzzyEquals(zThis, zFinalDes))
//			zAdjDes = this.getCubePosition()[2] + 0.5;
//		else if (zThis < zFinalDes)
//			zAdjDes = this.getCubePosition()[2] + 1.5;
//		else
//			zAdjDes = this.getCubePosition()[2] - 0.5;
//		return new Vector3d(xAdjDes, yAdjDes, zAdjDes);
//	}
	
	/**
	 * Check if the Unit is moving
	 * 
	 * @return	true if the unit is walking or sprinting
	 * 			| result == (unit.getStatus() == UnitStatus.WALKING) 
	 * 			| 	&& (unit.getStatus() == UnitStatus.SPRINTING))
	 */
	public boolean isMoving(){
		 return ((this.getStatus() == UnitStatus.WALKING) 
				 || (this.getStatus() == UnitStatus.SPRINTING));
	}

	/** TODO: updateposition documentatie
	 * Updates the position of the unit.
	 * 
	 * @param 	time
	 *          The time elapsed.
	 * @post 	The new position of this unit is where the unit would be if it went
	 *       	at its speed, towards the given adjacentDestination, during the
	 *       	given time, if that position doesn't equal or surpass the
	 *       	adjacentDestination.
	 * @throws 	IllegalArgumentException
	 *          The given time is not a valid time 
	 *          | ! isValidTime(time)
	 */
	private void updatePosition(double time) throws IllegalArgumentException, IllegalStateException {
		if (!isValidTime(time))
			throw new IllegalArgumentException("Invalid time!");
		
		this.updateOrientation();
		Vector3d nextPosition = this.getVelocity();
		nextPosition.scaleAdd(time, this.getPosition());
		
		double newWalkTimer = this.getWalkTimer() - time;
		
		if (newWalkTimer < 0) {
			this.setWalkTimer(0);
			this.increaseExperience(1);
			this.setAtPosition(this.getAdjacentDestination());
			
			if (Arrays.equals(this.getCubePosition(), this.getFinalDestination()))
				this.setStatus(UnitStatus.IDLE);
			else
				this.moveToAdjacent(this.findPath());
		} 
		else{
			this.setPosition(nextPosition);
			this.setWalkTimer(newWalkTimer);
		}
	}
	
	/**
	 * TODO: findPath documentation
	 * @return
	 */
	private int[] findPath(){
		
		this.setPathIndex(this.getPathIndex()+1);
		if(this.getPath()==null){
			AStarPathFinder pathFinder = new AStarPathFinder(this.getWorld());
			
			int sx = this.getCubePosition()[0];
			int sy = this.getCubePosition()[1];
			int sz = this.getCubePosition()[2];
			
			int tx = this.getFinalDestination()[0];
			int ty = this.getFinalDestination()[1];
			int tz = this.getFinalDestination()[2];
			this.setPath(pathFinder.findPath(this, sx, sy, sz, tx, ty, tz));
			this.setPathIndex(1);
		}
		
		return this.getPath().getStepInt(this.getPathIndex());
	}
	
	private void resetPath(){
		this.setPath(null);
		this.setPathIndex(0);
	}
	
	private Path getPath(){
		return this.path;
	}
	
	private void setPath(Path path){
		this.path = path;
	}
	
	/**
	 * Variable registering the path of this Unit.
	 */
	private Path path;
	
	/**
	 * Return the pathIndex of this Unit.
	 */
	@Basic @Raw
	private int getPathIndex() {
		return this.pathIndex;
	}

	/**
	 * Check whether the given pathIndex is a valid pathIndex for
	 * any Unit.
	 *  
	 * @param  pathIndex
	 *         The pathIndex to check.
	 * @return 
	 *       | result == (pathIndex >= 0)
	*/
	private static boolean isValidPathIndex(int pathIndex) {
		return (pathIndex >= 0);
	}

	/**
	 * Set the pathIndex of this Unit to the given pathIndex.
	 * 
	 * @param  pathIndex
	 *         The new pathIndex for this Unit.
	 * @post   The pathIndex of this new Unit is equal to
	 *         the given pathIndex.
	 *       | new.getPathIndex() == pathIndex
	 * @throws IllegalArgumentException
	 *         The given pathIndex is not a valid pathIndex for any
	 *         Unit.
	 *       | ! isValidPathIndex(getPathIndex())
	 */
	@Raw
	private void setPathIndex(int pathIndex) 
			throws IllegalArgumentException {
		if (! isValidPathIndex(pathIndex))
			throw new IllegalArgumentException();
		this.pathIndex = pathIndex;
	}

	/**
	 * Variable registering the pathIndex of this Unit.
	 */
	private int pathIndex;
	
	/**
	 * Reduce the hp of this unit, and  set its status to idle
	 * implementation of the takeFallDamage method in GameObject
	 * 
	 * @param 	fallDepth
	 * 		  	The amount of z-levels the unit has fallen.
	 * @effect 	the Units HP is reduced by 10*falldepth
	 * 			| this.takeDamage(10*fallDepth)
	 * @post	the status of the unit will be IDLE
	 * 			| new.getStatus == IDLE
	 */
	public void takeFallDamage(int fallDepth){
		this.setStatus(UnitStatus.IDLE);
		this.takeDamage(fallDepth * 10);		
	}
	
	/**
	 * Return the walkTimer of this Unit.
	 */
	@Basic
	@Raw
	private double getWalkTimer() {
		return this.walkTimer;
	}

	/**
	 * Check whether the given walkTimer is a valid walkTimer for any Unit.
	 * Every walkTimer is valid
	 * 
	 * @param	walkTimer
	 *        	The walkTimer to check.
	 * @return 	| result == (walkTimer >= 0)
	 */
	private static boolean isValidWalkTimer(double walkTimer) {
		return (walkTimer >= 0);
	}

	/**
	 * Set the walkTimer of this Unit to the given walkTimer.
	 * 
	 * @param 	walkTimer
	 *         	The new walkTimer for this Unit.
	 * @post 	The walkTimer of this new Unit is equal to the given walkTimer. 
	 *       	| new.getWalkTimer() == walkTimer
	 * @throws 	IllegalArgumentException
	 *          The given walkTimer is not a valid walkTimer for any Unit. 
	 *          | ! isValidWalkTimer(getWalkTimer())
	 */
	@Raw
	private void setWalkTimer(double walkTimer) throws IllegalArgumentException {
		if (!isValidWalkTimer(walkTimer))
			throw new IllegalArgumentException("not a valid walktimer");
		this.walkTimer = walkTimer;
	}

	/**
	 * Variable registering the walkTimer of this Unit.
	 */
	private double walkTimer = 0;

	/**
	 * Return the origin of this Unit.
	 */
	@Basic
	@Raw
	private int[] getOrigin() {
		return this.origin;
	}

	/**
	 * Check whether the given origin is a valid origin for this Unit.
	 * 
	 * @param 	origin
	 *          The origin to check.
	 * @return 	| result == (world.isValidWorldPosition(origin))
	 */
	private boolean isValidOrigin(int[] origin) {
		if (this.getWorld() == null)
				return true;
		return this.getWorld().isValidWorldPosition(origin);
	}

	/**
	 * Set the origin of this Unit to the given origin.
	 * 
	 * @param 	origin
	 *          The new origin for this Unit.
	 * @post 	The origin of this new Unit is equal to the given origin.
	 *       	| new.getOrigin() == origin
	 * @throws 	IllegalArgumentException
	 *          The given origin is not a valid origin for any Unit. 
	 *          | ! isValidOrigin(getOrigin())
	 */
	@Raw
	private void setOrigin(int[] origin) throws IllegalArgumentException {
		if (this.getWorld() != null)
			if (!isValidOrigin(origin))
				throw new IllegalArgumentException("not a valid origin");
		this.origin = origin;
	}

	/**
	 * Variable registering the origin of this Unit.
	 */
	private int[] origin = { 0, 0, 0 };

/*	*//**
	 * Check if destination is reached of surpassed
	 * 
	 * @param 	newPosition
	 * @return 	true if the destination lies between the old and the new position
	 *
	 
	private boolean destinationIsReached(Vector3d newPosition, Vector3d destination) {
		/*
		if ((Util.fuzzyLessThanOrEqualTo(this.getPosition().x, destination.x))
				&& (Util.fuzzyLessThanOrEqualTo(destination.x, newPosition.x))
				&& (Util.fuzzyLessThanOrEqualTo(this.getPosition().y, destination.y))
				&& (Util.fuzzyLessThanOrEqualTo(destination.y, newPosition.y))
				&& (Util.fuzzyLessThanOrEqualTo(this.getPosition().z, destination.z))
				&& (Util.fuzzyLessThanOrEqualTo(destination.z, newPosition.z)))
			return true;
		else if ((Util.fuzzyGreaterThanOrEqualTo(this.getPosition().x, destination.x))
				&& (Util.fuzzyGreaterThanOrEqualTo(destination.x, newPosition.x))
				&& (Util.fuzzyGreaterThanOrEqualTo(this.getPosition().y, destination.y))
				&& (Util.fuzzyGreaterThanOrEqualTo(destination.y, newPosition.y))
				&& (Util.fuzzyGreaterThanOrEqualTo(this.getPosition().z, destination.z))
				&& (Util.fuzzyGreaterThanOrEqualTo(destination.z, newPosition.z)))
			return true;
		*//*
		if (newPosition.epsilonEquals(destination, 1E-2))
			return true;
		return false;
	}*/


	/**
	 * Return the velocity of the unit as a vector.
	 */
	private Vector3d getVelocity() throws IllegalArgumentException, IllegalStateException {

		int[] adjacentDestination = this.getAdjacentDestination();
		

		double xDistance = adjacentDestination[0] + 0.5 - this.getPosition().x;
		double yDistance = adjacentDestination[1] + 0.5 - this.getPosition().y;
		double zDistance = adjacentDestination[2] + 0.5 - this.getPosition().z;

		Vector3d velocity = new Vector3d(xDistance, yDistance, zDistance);
		velocity.normalize();
		double speed = this.getSpeed();
		velocity.scale(speed);
		return velocity;
	}
	
	/**
	 * Return the base speed of this unit
	 */
	private double getBaseSpeed() {
		return 1.5 * (this.getStrength() + this.getAgility()) / (200 * weight / 100);
	}

	/**
	 * Return the speed of this unit.
	 */
	public double getSpeed() {
		UnitStatus status = this.getStatus();
		if(!this.isMoving())
			return 0;

		double vbase = this.getBaseSpeed();
		double v;
		
		if (this.getOrigin()[2] - this.getAdjacentDestination()[2] < 0)
			v = 0.5 * vbase;
		else if (this.getOrigin()[2] - this.getAdjacentDestination()[2] > 0)
			v = 1.2 * vbase;
		else
			v = vbase;

		if (status == UnitStatus.SPRINTING)
			return 2 * v;
		return v;
	}

	/**
	 * Updates the orientation of this unit, so it faces its destination.
	 * 
	 * @post 	The new orientation of this unit is towards the direction of its
	 *       	velocity, projected in the xy-plane. 
	 *       	| let 
	 *       	| 	velocity = this.getVelocity() 
	 *       	| 	vy = velocity.y, vx = velocity.x, 
	 *       	| 	newOrientation = Math.atan2(vy, vx) 
	 *       	| in
	 *       	| 	new.getOrientation == newOrientation
	 */
	private void updateOrientation() {

		Vector3d velocity = this.getVelocity();
		double vy = velocity.y;
		double vx = velocity.x;

		double newOrientation = Math.atan2(vy, vx);
		this.setOrientation(newOrientation);
	}

	/**
	 * Checks wether the current unit can sprint.
	 * 
	 * @return 	False if the unit is not walking 
	 * 			| if (!(this.getStatus() == UnitStatus.WALKING)) 
	 * 			| 		then result == false 
	 * 			Otherwise, true if the unit has stamina left 
	 * 			| else 
	 * 			| 		result == (this.getStamina > 0)
	 */
	private boolean canSprint() {
		return (this.getStatus() == UnitStatus.WALKING) && (this.getStamina() > 0);
	}

	/**
	 * Set the status of the unit to SPRINTING
	 * 
	 * @pre 	The unit can sprint. 
	 * 			| this.canSprint()
	 * @effect 	The status of this unit is set to SPRINTING
	 *         	| this.setStatus(UnitStatus.SPRINTING)
	 * 
	 */
	public void startSprint() {
		if(! this.canSprint())
			throw new IllegalStateException("unit can't sprint at this moment");
		this.setStatus(UnitStatus.SPRINTING);
	}

	/**
	 * Set the status of the unit from SPRINTING to WALKING
	 * 
	 * @effect 	The status of this unit is set to WALKING
	 *         	| this.setStatus(UnitStatus.WALKING)
	 * 
	 */
	public void stopSprint() {
		if (!this.isSprinting())
			throw new IllegalStateException("unit must be sprinting to stop sprinting!");
		this.setStatus(UnitStatus.WALKING);
	}
	
	/**
	 * Return true if this unit is sprinting
	 * @return 	The sprint-status of this unit
	 * 			| result == (this.getStatus() == UnitStatus.SPRINTING)
	 */
	public boolean isSprinting(){
		return (this.getStatus() == UnitStatus.SPRINTING);
	}

	/**
	 * Return the name of this unit.
	 */
	@Basic
	@Raw
	public String getName() {
		return this.name;
	}

	/**
	 * Check whether the given name is a valid name for any unit.
	 *
	 * @param 	name
	 *         	The name to check.
	 * @return 	False if the given name is not effective. 
	 * 			| if (name == null) 
	 * 			| 		then result == false 
	 * 			Otherwise, false if the name is shorter than two characters 
	 *         	| else if (name.length < 2) 
	 *         	| 		then result == false
	 *         	Otherwise, false if the name doesn't begin with an uppercase letter 
	 *         	| else if (!name.substring(0,1).matches("A-Z")) 
	 *         	| 		then result == false
	 *         	Otherwise, true if the name after the first character only consists 
	 *         	of letters (upper case and lower case),
	 *         	quotes (singles and doubles) and spaces 
	 *         	| else if (name[1:].matches("[A-Za-z\'\" ]+")) 
	 *         	| 		then result == true
	 *
	 */
	private static boolean isValidName(String name) {
		if (name == null)
			return false;
		if (name.length() < 2)
			return false;
		if (!Character.isUpperCase(name.charAt(0)))
			return false;
		for (int i = 0; i < name.length(); i++){
			char c = name.charAt(i);
			if (! (	(Character.isUpperCase(c)) ||
					(Character.isLowerCase(c)) ||
					(c == '"') ||
					(c == ' ') ||
					(c == '\''))){
				return false;
			}
		}
		return true;
	}

	/**
	 * Set the name of this unit to the given name.
	 *
	 * @param 	name
	 *          The new name for this unit.
	 * @post 	The name of this new unit is equal to the given name. 
	 *       	| new.getName() == name
	 * @throws 	IllegalArgumentException
	 *          The given name is not a valid name for any unit.
	 *          | ! isValidName(getName())
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException {
		if (! isValidName(name))
			throw new IllegalArgumentException("wrong name!");
		this.name = name;
	}

	/**
	 * Variable registering the name of this unit.
	 */
	private String name;
	

	/**
	 * Return the orientation of this unit.
	 */
	@Basic
	@Raw
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * Check whether the given orientation is a valid orientation for any unit.
	 * 
	 * @param 	orientation
	 *          The orientation to check.
	 * @return 	The orientation of the unit is between 0 and 2*Math.PI 
	 *  		|  orientation >= 0 && orientation < 2*Math.PI
	 * 
	 */
	private static boolean isValidOrientation(double orientation) {
		return (orientation >= 0) && (orientation < 2 * Math.PI);
	}

	/**
	 * Set the orientation of this unit to the given orientation.
	 * 
	 * @param orientation
	 *            The new orientation for this unit.
	 * 
	 * @post The orientation is set to the physically corresponding orientation
	 *       between -2*Math.PI and 2*Math.PI. | if (Math.abs(orientation) >
	 *       2*Math.PI) | new.getOrientation = orientation % 2*Math.PI
	 */
	//@Raw
	private void setOrientation(double orientation) {
		while (orientation > 2 * Math.PI) {
			orientation = orientation - 2 * Math.PI;
		}
		while (orientation < -2 * Math.PI) {
			orientation = orientation + 2 * Math.PI;
		}
		assert(isValidOrientation(orientation));
		this.orientation = (orientation);
	}

	/**
	 * Variable registering the orientation of this unit.
	 */
	private double orientation = Math.PI / 2;

	/**
	 * Set the orientation of THIS Unit to face the other Unit
	 *
	 * @param 	other
	 *          the other unit, to which we will face
	 * @post 	The orientation of this Unit is towards the other Unit. 
	 *       	| new.getOrientation() == Math.atan2(y_other - y_this, x_other - x_this)
	 * @throws 	IllegalArgumentException
	 *          the given other unit is not in a valid position
	 *          | ! this.canAttack(other)
	 */
	private void face(int[] position) throws IllegalArgumentException {
		double x_this = this.getCubePosition()[0];
		double y_this = this.getCubePosition()[1];
		double x_other = position[0];
		double y_other = position[1];
		
		if(Arrays.equals(position, this.getCubePosition()))
			this.setOrientation(Math.PI/2);
		else{
			double this_orientation = Math.atan2(y_other - y_this, x_other - x_this);
			this.setOrientation(this_orientation);
		}
	}

	/**TODO: dodge valid terrain
	 * 
	 * The Unit moves instantaniously to a random position bordering its current
	 * position This new position is a valid position in the gameworld
	 *
	 * 
	 * @post 	The unit is in a position bordering its current position.
	 * @throws 	IllegalStateException
	 *          The unit is not being attacked.
	 *          | ! this.getStatus == DEFENDING
	 */
	private void dodge() throws IllegalStateException {

		if (!(this.getStatus() == UnitStatus.DEFENDING))
			throw new IllegalStateException("Unit is not being attacked!");

		Vector3d newPosition = new Vector3d(-1, -1, -1);
		int counter = 0;

		double thisX = this.getPosition().x;
		double thisY = this.getPosition().y;
		double thisZ = this.getPosition().z;
		
		while (counter < 10000) {
			// Returns a double between -1 and +1
			double xJump = 2 * random.nextDouble() - 1;
			double yJump = 2 * random.nextDouble() - 1;

			newPosition.set(thisX + xJump, thisY + yJump, thisZ);
			counter++;
			// in this way, because ispassableterrain requires a valid position
			if(isValidPosition(newPosition, this.getWorld()))
					if(this.getWorld().isPassableTerrain(toCubePosition(newPosition)))
						break;
		}
		
		if (isValidPosition(newPosition, this.getWorld()))
			this.setPosition(newPosition);
		
		this.increaseExperience(20);
	}

	/**
	 * Check whether this Unit can attack the other Unit
	 *
	 * @param 	other
	 *          the unit being attacked
	 * @return 	True if and only if the other unit occupies a cube that, in
	 *         	regards to the cube this unit occupies has the same z-coordinate,
	 *         	an x-coordinate differing no more than 1 and a y-coordinate
	 *         	differing no more than 1. 
	 *         	| return (this.getCubePosition.z == other.getCubePosition.z) && 
	 *         	| (abs(this.getCubePosition.x - other.getCubePosition.x) < 2) && 
	 *         	| (abs(this.getCubePosition.y - other.getCubePosition.y) < 2)
	 * @throws 	IllegalArgumentException
	 *          The other unit is not effective 
	 *          | other == null
	 */
	private boolean canAttack(Unit other) throws IllegalArgumentException {
		if (other == null)
			throw new NullPointerException("can't attack null");
		if (other.isFalling())
			return false;
		return (this.getCubePosition()[2] == other.getCubePosition()[2])
				&& (Math.abs(this.getCubePosition()[0] - other.getCubePosition()[0]) < 2)
				&& (Math.abs(this.getCubePosition()[1] - other.getCubePosition()[1]) < 2);
	}

	/**
	 * set a random destination in the gameworld
	 * 
	 * @effect Call the moveTo method, and give 3 random integers between 0 and 50 
	 *         | this.moveTo({randomBetween0And51,randomBetween0And51,randomBetween0And51})
	 * 
	 */
	private void moveToRandom(){
		int counter = 0;
		int[] position = {-1,-1,-1};
		while (counter < 10000) {
			// Returns a double between -1 and +1
			position[0] = random.nextInt(this.getWorld().getNbCubesX());
			position[1] = random.nextInt(this.getWorld().getNbCubesY());
			position[2] = random.nextInt(this.getWorld().getNbCubesZ());

			counter++;
			// in this way, because ispassableterrain requires a valid position
			if(this.getWorld().isValidWorldPosition(position));
					if(this.getWorld().isPassableTerrain(position))
						break;
		}
		this.moveTo(position);
	}
	

	/**
	 * Return the adjacentDestination of this unit.
	 */
	@Basic
	@Raw
	private int[] getAdjacentDestination() {
		return this.adjacentDestination;
	}

	/** 
	 * Check whether the given adjacentDestination is a valid
	 * adjacentDestination for this unit.
	 * 
	 * @param 	adjacentDestination
	 *          The adjacentDestination to check.
	 * @return 	True if the adjacentDestination is the centre of a valid
	 *         	neighbouring position. 
	 *         	TODO:doc isValidAdjacentDestination
	 * 
	 */
	private boolean isValidAdjacentDestination(int[] adjacentDestination) {
		if(this.getWorld()==null)
			return true;
		
		int[] thisPos = this.getCubePosition();
		for (int i = 0; i<3;i++){
			if (Math.abs(thisPos[i]-adjacentDestination[i]) > 1)
				return false;
		}
		if(!this.getWorld().isNeighbouringSolid(adjacentDestination) ||
				(!this.getWorld().isPassableTerrain(adjacentDestination)))
			return false;
		return true;
		/*return isValidPosition(adjacentDestination) &&
		((!(Math.abs(this.getPosition().x - adjacentDestination.x)>=1))&&
		(!(Math.abs(this.getPosition().y - adjacentDestination.y)>=1))&&
		(!(Math.abs(this.getPosition().z - adjacentDestination.z)>=1)) || (
		Util.fuzzyEquals((adjacentDestination.x % 1), 0.5)&&
		Util.fuzzyEquals((adjacentDestination.x % 1), 0.5)&&
		Util.fuzzyEquals((adjacentDestination.x % 1), 0.5)));
	*/
	}

	/**
	 * Set the adjacentDestination of this unit to the given
	 * adjacentDestination.
	 * 
	 * @param 	adjacentDestination
	 *          The new adjacentDestination for this unit.
	 * @post 	The adjacentDestination of this new unit is equal to the given
	 *       	adjacentDestination. 
	 *       	| new.getAdjacentDestination() == adjacentDestination
	 * @post	The walktimer of this unit will be the time needed to arrive
	 * 			at the adjacentDestination
	 * 			| new.getWalkTimer == length/this.getSpeed()
	 * @post	The origin of this unit will be its current cubepostion
	 * 			| new.getOrigin == this.getCubePosition
	 * @throws 	IllegalArgumentException
	 *          The given adjacentDestination is not a valid
	 *          adjacentDestination for any unit.
	 *          | ! isValidAdjacentDestination(getAdjacentDestination())
	 */
	@Raw
	private void setAdjacentDestination(int[] adjacentDestination) throws IllegalArgumentException {
		if (!isValidAdjacentDestination(adjacentDestination))
			throw new IllegalArgumentException();
		this.adjacentDestination = adjacentDestination;
	}

	/**
	 * @param adjacentDestination
	 */
	private void initiateWalkTimer(int[] adjacentDestination) {
		if (!isValidAdjacentDestination(adjacentDestination))
			throw new IllegalArgumentException("invalid adjacent destination");
		Vector3d newVector = new Vector3d(this.getPosition());
		Vector3d adjacentVector = toVectorPosition(adjacentDestination);
		newVector.sub(adjacentVector);
		double length = newVector.length();
		this.setWalkTimer(length / this.getSpeed());

		
	}

	/**
	 * Variable registering the adjacentDestination of this unit.
	 */
	private int[] adjacentDestination = null;

	/**
	 * Return the finalDestination of this unit.
	 */
	@Basic
	@Raw
	private int[] getFinalDestination() {
		return this.finalDestination;
	}


	/**
	 * Set the finalDestination of this unit to the given finalDestination.
	 * 
	 * @param 	finalDestination
	 *          The new finalDestination for this unit.
	 * @post 	The finalDestination of this unit is equal to the given
	 *       	finalDestination. 
	 *       	| new.getFinalDestination() == finalDestination
	 * @throws 	IllegalArgumentException
	 *          The given finalDestination is not a valid position
	 *          for any unit. 
	 *          | ! isValidPosition(finalDestination())
	 */
	@Raw
	private void setFinalDestination(int[] finalDestination) throws IllegalArgumentException {
//		if (this.getWorld() != null)
//			if (this.getWorld().isValidWorldPosition(finalDestination))
//				throw new IllegalArgumentException("illegal final destination");
		this.finalDestination = finalDestination;
	}

	/**
	 * Variable registering the finalDestination of this unit.
	 */
	private int[] finalDestination = {0,0,0};

	
	/**
	 * Return the weight of this Unit.
	 */
	@Basic
	@Raw
	public int getWeight() {
		int weight = this.weight;
		if(this.getGameItem() != null)
			weight += this.getGameItem().getWeight();
		return weight;
	}

	/**
	 * Check whether the given weight is a valid weight for this Unit.
	 *
	 * @param 	weight
	 *          The weight to check.
	 * @return 	| result == ((1 < weight) && (weight < 200)) && (weight >=
	 *         		(this.getStrength() + this.getAgility())/2))
	 */
	private boolean isValidWeight(int weight) {
		return ((1 < weight) && (weight < 200));
	}

	/**
	 * Check whether the given weight is a valid weight for the initialization
	 * of this Unit
	 *
	 * @param 	weight
	 *          The weight to check
	 * @return 	| result == ((25 <= weight) && (weight <= 100) && (weight >=
	 *         	(this.getStrength() + this.getAgility())/2))
	 */
	private boolean isValidStartWeight(int weight) {
		return ((25 <= weight) && (weight <= 100) && (weight >= (this.getStrength() + this.getAgility()) / 2));
	}

	/**
	 * Set the weight of this Unit to the given weight.
	 *
	 * @param 	weight
	 *          The new weight for this Unit.
	 * @post 	If the given weight is a valid weight for any Unit, the weight of
	 *       	this new Unit is equal to the given weight. 
	 *       	| if (isValidWeight(weight)) 
	 *       	| 		then new.getWeight() == weight 
	 *       	| else 
	 *    		| 		this.weight = (this.getStrength() + this.getAgility())/2
	 */
	@Raw
	public void setWeight(int weight) {
		if (isValidWeight(weight))
			this.weight = weight;
		else
			this.weight = (this.getStrength() + this.getAgility()) / 2;
	}

	/**
	 * Variable registering the weight of this Unit.
	 */
	private int weight;

	/**
	 * Check whether the given attribute is a valid attribute for any Unit.
	 *
	 * @param 	attribute
	 *          The attribute to check.
	 * @return 	| result == ((1 <= attribute) && (attribute < 200))
	 */
	public static boolean isValidUnitAttribute(int attribute) {
		return ((1 <= attribute) && (attribute <= 200));
	}

	/**
	 * Check whether the given attribute is a valid attribute for the
	 * initialization of any Unit
	 *
	 * @param 	toughness
	 *          The attribute to check
	 * @return 	| result == ((25 <= attribute) && (attribute <= 100)
	 */
	public static boolean isValidStartAttribute(int attribute) {
		return ((25 <= attribute) && (attribute <= 100));
	}

	/**
	 * Return the strength of this Unit.
	 */
	@Basic
	@Raw
	public int getStrength() {
		return this.strength;
	}

	/**
	 * Set the strength of this Unit to the given strength.
	 *
	 * @param 	strength
	 *          The new strength for this Unit.
	 * @post	If the given strength is a valid strength for any Unit, the
	 *       	strength of this new Unit is equal to the given strength. 
	 *       	| if (isValidUnitAttribute(strength)) 
	 *       	| 		then new.getStrength() == strength 
	 *       	| else 
	 *       	|		new.getStrength() == 25
	 */
	@Raw
	public void setStrength(int strength) {
		if (isValidUnitAttribute(strength))
			this.strength = strength;
		else
			this.strength = 25;
	}

	/**
	 * Variable registering the strength of this Unit.
	 */
	private int strength;

	/**
	 * Return the agility of this Unit.
	 */
	@Basic
	@Raw
	public int getAgility() {
		return this.agility;
	}

	/**
	 * Set the agility of this Unit to the given agility.
	 *
	 * @param 	agility
	 *          The new agility for this Unit.
	 * @post 	If the given agility is a valid agility for any Unit, the agility
	 *       	of this new Unit is equal to the given agility. 
	 *       	| if (isValidUnitAttribute(agility)) 
	 *       	| 		then new.getAgility() == agility
	 *       	| else 
	 *       	| 		new.getAgility() == 25
	 */
	@Raw
	public void setAgility(int agility) {
		if (isValidUnitAttribute(agility))
			this.agility = agility;
		else
			this.agility = 25;
	}

	/**
	 * Variable registering the agility of this Unit.
	 */
	private int agility;

	/**
	 * Return the toughness of this Unit.
	 */
	@Basic
	@Raw
	public int getToughness() {
		return this.toughness;
	}

	/**
	 * Set the toughness of this Unit to the given toughness.
	 *
	 * @param 	toughness
	 *          The new toughness for this Unit.
	 * @post 	If the given toughness is a valid toughness for any Unit, the
	 *       	toughness of this new Unit is equal to the given toughness. 
	 *       	| if (isValidUnitAttribute(toughness)) 
	 *       	| 		then new.getToughness() == toughness 
	 *       	| else 
	 *       	|	 	new.getToughness() == 25
	 */
	@Raw
	public void setToughness(int toughness) {
		if (isValidUnitAttribute(toughness))
			this.toughness = toughness;
		else
			this.toughness = 25;
	}

	/**
	 * Variable registering the toughness of this Unit.
	 */
	private int toughness;

	/**
	 * Return the hitpoints of this Unit.
	 */
	@Basic
	@Raw
	public int getHP() {
		return this.hp;
	}

	/**
	 * Return the maximum hitpoints of this Unit.
	 */
	public int getMaxHP() {
		return (int) Math.ceil(200 * (this.getWeight() / 100.0) * (this.getToughness() / 100.0));
	}

	/**
	 * Check whether the given hitpoints is a valid hitpoints for this Unit.
	 *
	 * @param 	hitpoints
	 *          The hitpoints to check.
	 * @return 	| result == ((0 <= hp) && (hp <= this.getMaxHP))
	 */
	private boolean isValidHP(int hp) {
		return (0 <= hp) && (hp <= this.getMaxHP());
	}

	/**
	 * Set the hitpoints of this Unit to the given hitpoints.
	 *
	 * @param 	hp
	 *          The new hitpoints for this Unit.
	 * @pre 	The given hitpoints must be a valid hitpoints for this Unit.
	 *      	| isValidHP(hp)
	 * @post 	The hitpoints of this Unit is equal to the given hitpoints.
	 *       	| new.getHP() == hp
	 */
	@Raw
	private void setHP(int hp) {
		assert isValidHP(hp);
		this.hp = hp;
	}
	
	/** TODO: documentatie
	 * Reduce the hp of this unit with the given amount. 
	 * If the hp reaches zero, terminate the unit.
	 * 
	 * @param 	amount
	 * 			the amount by which the hp needs to be reduced
	 * @post	
	 * 			| if newHP > 0
	 * 			|	then new.getHP = this.getHP - amount
	 * @effect	terminate the unit if hp reaches zero
	 * 			| if newHP <= 0
	 * 			|	then this.terminate
	 */
	public void takeDamage(int amount){
		int newHP = this.getHP() - amount;
		if (newHP <= 0){
			this.terminate();
		}
		else{
			this.setHP(newHP);
		}
	}

	/**
	 * Variable registering the hitpoints of this Unit.
	 */
	private int hp;

	/**
	 * Return the stamina of this Unit.
	 */
	@Basic
	@Raw
	public int getStamina() {
		return this.stamina;
	}

	/**
	 * Return the maximum stamina of this Unit
	 */
	public int getMaxStamina() {
		return this.getMaxHP();
	}

	/**
	 * Check whether the given stamina is a valid stamina for this Unit.
	 *
	 * @param 	stamina
	 *         	The stamina to check.
	 * @return 	| result == ((0 <= stamina) && (stamina <= this.getMaxStamina))
	 */
	private boolean isValidStamina(int stamina) {
		return ((0 <= stamina) && (stamina <= this.getMaxStamina()));
	}

	/**
	 * Set the stamina of this Unit to the given stamina.
	 *
	 * @param 	stamina
	 *          The new stamina for this Unit.
	 * @pre 	The given stamina must be a valid stamina for any Unit.
	 *      	| isValidStamina(stamina)
	 * @post 	The stamina of this Unit is equal to the given stamina.
	 *       	| new.getStamina() == stamina
	 */
	@Raw
	private void setStamina(int stamina) {
		assert isValidStamina(stamina);
		this.stamina = stamina;
	}

	/**
	 * Variable registering the stamina of this Unit.
	 */
	private int stamina;
	
	
	
	/**
	 * Start the work-condition
	 * 
	 * @throws 	IllegalArgumentException
	 *          The given workTime is not a valid workTime for any Unit.
	 *          | ! isValidWorkTime(getWorkTime())
	 * @param	x, y and z
	 * 			The cube-coordinates of the targetted cube.
	 * @post	the workTarget is set to the given coordinates
	 * 			| new.getWorkTarget == (x, y, z)
	 * @post	the workTime is set
	 * 			| new.getWorkTime == 500/this.getStrength
	 * @post	the status is set to Working
	 * 			| new.getStatus == WORKING
	 */
	public void workAt(int[] workTarget) throws IllegalArgumentException {
		if (! this.isNeighboringCube(workTarget))
			throw new IllegalArgumentException("not a neighbouring cube");
		if (this.isFalling())
			throw new IllegalStateException("can't work while falling");
		this.setWorkTime(500.0d / this.getStrength());
		this.setStatus(UnitStatus.WORKING);
		this.setWorkTarget(workTarget);
		
		this.face(workTarget);
	}
	
	/**
	 * TODO: finishWork documentatie
	 */
	private void finishWork(){
		
		this.increaseExperience(10);
		// drop boulder/log if carrying one
		if(this.getGameItem() != null){
			this.dropItem();
			return;
		}
		
		// target cube is workshop, and one Boulder and one Log are available
		World world = this.getWorld();
		Boulder boulder = world.boulderAtPosition(this.getWorkTarget());
		Log log = world.logAtPosition(this.getWorkTarget());
		
		int workX = this.getWorkTarget()[0];
		int workY = this.getWorkTarget()[1];
		int workZ = this.getWorkTarget()[2];
		
		if((world.getCubeType(workX, workY, workZ)== 3) &&
				(boulder != null) &&
				(log != null)){
			boulder.terminate();
			log.terminate();
			
			int newWeight = this.getWeight() + 1;
			if(this.isValidWeight(newWeight))
				this.setWeight(newWeight);
			int newToughness = this.getToughness() + 1;
			if(isValidUnitAttribute(newToughness))
				this.setToughness(newToughness);
			
			return;
		}
		
		// if boulder is present, pick up boulder
		if(boulder != null){
			this.setGameItem(boulder);
			boulder.setWorld(null);
			world.removeBoulder(boulder);
			
			return;
		}
		
		// if log is present, pick up log
		if(log != null){
			this.setGameItem(log);
			log.setWorld(null);
			world.removeLog(log);
			
			return;
		}
		
		// if the target cube is a tree, destroy the tree and drop a log
		if(world.getCubeType(workX, workY, workZ)==2){
			world.setCubeType(workX, workY, workZ, 0);
			Log newLog = new Log(this.getWorkTarget(), world);
			world.addLog(newLog);
			
			return;
		}
		
		//if the target cube is a rock, destroy the rock and drop a boulder
		if(world.getCubeType(workX, workY, workZ)==1){
			world.setCubeType(workX, workY, workZ, 0);
			Boulder newBoulder = new Boulder(this.getWorkTarget(), world);
			world.addBoulder(newBoulder);
			
			return;
		}
	}

	/**TODO dropitem documentatie
	 * 
	 */
	private void dropItem() {
		if(this.isCarryingLog()){
			Log log = (Log)this.getGameItem();
			if(this.isWorking())
				log.setAtPosition(this.getWorkTarget());
			else
				log.setAtPosition(this.getCubePosition());
			log.setWorld(this.getWorld());
			this.getWorld().addLog(log);
			this.setGameItem(null);
		}
		else if(this.isCarryingBoulder()){
			Boulder boulder = (Boulder)this.getGameItem();
			if(this.isWorking())
				boulder.setAtPosition(this.getWorkTarget());
			else
				boulder.setAtPosition(this.getCubePosition());
			boulder.setWorld(this.getWorld());
			this.getWorld().addBoulder(boulder);
			this.setGameItem(null);
		}
	}
	
	/**
	 * Return true if this unit is working
	 * @return
	 * 			| result == (this.getStatus()==UnitStatus.WORKING)
	 */
	public boolean isWorking(){
		return (this.getStatus()==UnitStatus.WORKING);
	}


	/**
	 * Return the workTarget of this Unit.
	 */
	@Basic @Raw
	private int[] getWorkTarget() {
		return this.workTarget;
	}

	/** TODO: isValidWorkTarget
	 * Check whether the given workTarget is a valid workTarget for
	 * any Unit.
	 *  
	 * @param  workTarget
	 *         The workTarget to check.
	 * @return 
	 *       | result == 
	*/
	private static boolean isValidWorkTarget(int[] workTarget) {
		return true;
	}

	/**
	 * Set the workTarget of this Unit to the given workTarget.
	 * 
	 * @param  workTarget
	 *         The new workTarget for this Unit.
	 * @post   The workTarget of this new Unit is equal to
	 *         the given workTarget.
	 *       | new.getWorkTarget() == workTarget
	 * @throws IllegalArgumentException
	 *         The given workTarget is not a valid workTarget for any
	 *         Unit.
	 *       | ! isValidWorkTarget(getWorkTarget())
	 */
	@Raw
	private void setWorkTarget(int[] workTarget) 
			throws IllegalArgumentException {
		if (! isValidWorkTarget(workTarget))
			throw new IllegalArgumentException();
		this.workTarget = workTarget;
	}

	/**
	 * Variable registering the workTarget of this Unit.
	 */
	private int[] workTarget;
	
	
	
	/**
	 * Return the workTime of this Unit.
	 */
	@Basic
	@Raw
	private double getWorkTime() {
		return this.worktime;
	}

	/**
	 * Check whether the given workTime is a valid workTime for any Unit.
	 *
	 * @param 	workTime
	 *          The workTime to check.
	 * @return 	| result == (worktime >= 0)
	 */
	private static boolean isValidWorkTime(double worktime) {
		return Util.fuzzyGreaterThanOrEqualTo(worktime, 0);
	}

	/**
	 * Set the workTime of this Unit to the given workTime.
	 *
	 * @param 	worktime
	 *          The new workTime for this Unit.
	 * @post 	The workTime of this new Unit is equal to the given workTime.
	 *       	| new.getWorkTime() == worktime
	 * @throws 	IllegalArgumentException
	 *          The given workTime is not a valid workTime for any Unit.
	 *          | ! isValidWorkTime(worktime)
	 */
	@Raw
	private void setWorkTime(double worktime) throws IllegalArgumentException {
		if (!isValidWorkTime(worktime))
			throw new IllegalArgumentException("the worktime is invalid");
		this.worktime = worktime;
	}

	/**
	 * Reduces the worktime with time
	 *
	 * @param 	time
	 *          The time to be subtracted from worktime
	 * @throws 	IllegalArgumentException
	 *          The given time is not a valid time for any Unit.
	 * @effect	If the workTime is smaller then or equal to 0, do finishWork
	 * 			| if (this.worktime - time <= 0)
	 * 			| 	then this.finishWork();
	 */
	private void advanceWorkTime(double time) throws IllegalArgumentException {
		if (!isValidTime(time))
			throw new IllegalArgumentException("The given time is not a valid time");

		double newWorkTime = this.getWorkTime() - time;
		if (Util.fuzzyLessThanOrEqualTo(newWorkTime, 0)) {
			this.setWorkTime(0);
			this.setStatus(UnitStatus.IDLE);
			this.finishWork();
		} else
			this.setWorkTime(newWorkTime);
	}

	/**
	 * Variable registering the workTime of this Unit. Default value 0.
	 */
	private double worktime = 0;

	/**
	 * Return the gameItem of this Unit.
	 */
	@Basic @Raw
	private GameItem getGameItem() {
		return this.gameItem;
	}

	/** TODO: isvalidgameItem unit
	 * Check whether the given gameItem is a valid gameItem for
	 * any Unit.
	 *  
	 * @param  gameItem
	 *         The gameItem to check.
	 * @return 
	 *       | result == 
	*/
	private static boolean isValidGameItem(GameItem gameItem) {
		return true;
	}

	/**
	 * Set the gameItem of this Unit to the given gameItem.
	 * 
	 * @param  gameItem
	 *         The new gameItem for this Unit.
	 * @post   The gameItem of this new Unit is equal to
	 *         the given gameItem.
	 *       | new.getGameItem() == gameItem
	 * @throws IllegalArgumentException
	 *         The given gameItem is not a valid gameItem for any
	 *         Unit.
	 *       | ! isValidGameItem(getGameItem())
	 */
	@Raw
	private void setGameItem(GameItem gameItem) 
			throws IllegalArgumentException {
		if (! isValidGameItem(gameItem))
			throw new IllegalArgumentException();
		this.gameItem = gameItem;
	}

	/**
	 * Variable registering the gameItem of this Unit.
	 */
	private GameItem gameItem = null;
	
	/**
	 * Return whether this unit is carrying a log
	 * 
	 * @return true if gameItem is a log
	 * 			| (this.getGameItem == Log)
	 */
	public boolean isCarryingLog(){
		if(this.getGameItem() == null)
			return false;
		return (this.getGameItem().getClass() == Log.class);
	}
	
	/**
	 * Return whether this unit is carrying a boulder
	 * 
	 * @return true if gameItem is a boulder
	 * 			| (this.getGameItem == boulder)
	 */
	public boolean isCarryingBoulder(){
		if(this.getGameItem() == null)
			return false;
		return (this.getGameItem().getClass() == Boulder.class);
	}
	
	
	/**
	 * This Unit attacks the Other Unit
	 *
	 * @param 	other
	 *          the Unit being attacked
	 * @throws 	IllegalArgumentException
	 *          The Unit being attacked is not a valid Unit
	 *          | ! canAttack(other)
	 */
	public void attack(Unit other) throws IllegalArgumentException {
		if (other.isTerminated())
			return;
		if (!this.canAttack(other))
			throw new IllegalArgumentException("The other Unit cannot be attacked");
		if (this.isFalling())
			throw new IllegalStateException("can't attack while falling!");
		this.setAttackCountDown(1d);
		this.face(other.getCubePosition());
		this.setStatus(UnitStatus.ATTACKING);
		other.defend(this);
	}
	
	public boolean isAttacking(){
		return (this.getStatus()==UnitStatus.ATTACKING);
	}

	/**
	 * Return the attackCountDown of this Unit.
	 */
	@Basic
	@Raw
	private double getAttackCountDown() {
		return this.attackCountDown;
	}

	/**
	 * Check whether the given attackCountDown is a valid attackCountDown for
	 * any Unit.
	 *
	 * @param 	attackCountDown
	 *         	The attackCountDown to check.
	 * @return 	| result == (0 <= attackCountDown <=1 )
	 */
	private static boolean isValidAttackCountDown(double attackCountDown) {
		return ((Util.fuzzyGreaterThanOrEqualTo(attackCountDown, 0))
				&& (Util.fuzzyGreaterThanOrEqualTo(1, attackCountDown)));
	}

	/**
	 * Set the attackCountDown of this Unit to the given attackCountDown.
	 *
	 * @param 	attackCountDown
	 *          The new attackCountDown for this Unit.
	 * @post 	The attackCountDown of this Unit is equal to the given
	 *       	attackCountDown. 
	 *       	| new.getAttackCountDown() == attackCountDown
	 * @throws 	IllegalArgumentException
	 *          The given attackCountDown is not a valid attackCountDown for
	 *          any Unit. 
	 *          | ! isValidAttackCountDown(getAttackCountDown())
	 */
	@Raw
	private void setAttackCountDown(double attackCountDown) throws IllegalArgumentException {
		if (!isValidAttackCountDown(attackCountDown))
			throw new IllegalArgumentException("the given countdown is not valid");
		this.attackCountDown = attackCountDown;
	}

	/**
	 * Reduces the worktime with time
	 *
	 * @param 	time
	 *          The time to be subtracted from attackcountdown
	 * @post 	The attackCountDown of this Unit is reduced by the amount time
	 *       	| new.getAttackCountDown() == this.getAttackCountDown() - time
	 * @post 	If the new attackcountdown is zero, the new status is idle
	 *       	| if (new.getAttackCountDown <= 0) 
	 *       	| new.getStatus() == UnitSTatus.Idle
	 * @throws 	IllegalArgumentException
	 *          The given time is not a valid time for any Unit. | !
	 *          | ! isValidTime(time)
	 */
	private void advanceAttackTime(double time) throws IllegalArgumentException {
		if (!isValidTime(time))
			throw new IllegalArgumentException("The given time is not a valid time");

		double newAttackTime = this.getAttackCountDown() - time;
		if (Util.fuzzyLessThanOrEqualTo(newAttackTime, 0)) {
			this.increaseExperience(20);
			this.setAttackCountDown(0);
			this.setStatus(UnitStatus.IDLE);
		} else
			this.setAttackCountDown(newAttackTime);
	}

	/**
	 * Variable registering the attackCountDown of this Unit.
	 */
	private double attackCountDown = 0;

	Random random = new Random();

	/**
	 * This Unit is attacked by the other Unit, and can take damage because of
	 * this
	 *
	 * @param 	other
	 *          The Unit attacking this Unit.
	 * @effect 	If dodging succeeds, move to a random tile around the unit
	 *         	| if (this.dodgeChance(other)) TODO either this, this or this, effect 
	 *         	| 	then this.dodge
	 * @effect 	This Unit will face the attacking unit 
	 * 			| this.face(other)
	 * @post 	if dodging fails and blocking succeeds, nothing hapens and the
	 *       	method is stopped 
	 *       	| if (! this.dodgeChance(other)) && (! this.blockChance) 
	 *       	| new.getHP == this.getHP - other.getStrength/10
	 * @post 	the status of this unit is DEFENDING 
	 * 			| new.getStatus == UnitStatus.DEFENDING
	 * @throws 	IllegalArgumentException
	 *          This Unit cannot be attacked by the other Unit
	 *          | ! other.canAttack(this)
	 */
	public void defend(Unit other) throws IllegalArgumentException {
		if (!other.canAttack(this))
			throw new IllegalArgumentException("This Unit can not be attacked by the other unit");

		this.setStatus(UnitStatus.DEFENDING);
		this.face(other.getCubePosition());

		if (this.dodgeChance(other)) {
			this.setStatus(UnitStatus.DODGING);
			
			// this.setStatus(UnitStatus.IDLE); //TODO:hier, of in advanceTime?
			return;
		}

		if (this.blockChance(other)) {
			this.increaseExperience(20);
			// this.setStatus(UnitStatus.IDLE);
			return;
		}
		int newHP = this.getHP() - other.getStrength() / 10;
		if (newHP > 0)
			this.setHP(newHP);
		else
			this.terminate(); //TODO:death
	}

	/**
	 * Will return true if this unit succesfully dodges the attack by the other
	 * unit
	 * 
	 * @param 	other
	 *          The Unit attacking this Unit
	 * @return 	| result == (RandomNumberBetween0And1 <= (0.2d*this.getAgility())/other.getAgility())
	 */
	private boolean dodgeChance(Unit other) {
		double dodgeChance = (0.2d * this.getAgility()) / other.getAgility();
		return (Util.fuzzyLessThanOrEqualTo(random.nextDouble(), dodgeChance));
	}

	/**
	 * Will return true if this unit succesfully blocks the attack by the other
	 * unit
	 * 
	 * @param 	other
	 *          The Unit attacking this Unit
	 * @return 	| result == (RandomNumberBetween0And1 <= 
	 * 			| (0.25d* (this.getStrength() + this.getAgility()))
	 *         	| /(other.getStrength()+other.getAgility())
	 */
	private boolean blockChance(Unit other) {
		double blockChance = (0.25d * (this.getStrength() + this.getAgility()))
				/ (other.getStrength() + other.getAgility());
		return (Util.fuzzyLessThanOrEqualTo(random.nextDouble(), blockChance));

	}

	/** TODO: assert kan niet, want dan moet private zijn
	 * Initiate the rest status for this unit.
	 *
	 * @pre 	The Unit is not attacking 
	 * 			| this.status != UnitStatus.ATTACKING
	 * @pre 	The Unit is not being attacked 
	 * 			| this.status != UnitStatus.DEFENDING
	 * @post 	The status of this Unit is 'resting' 
	 * 			| new.getStatus() == Unitstatus.RESTING
	 * @post 	The restTime of this Unit is 0 
	 * 			| new.getRestTime() == 0
	 */
	public void rest() { 
		assert (this.getStatus() != UnitStatus.ATTACKING);
		assert (this.getStatus() != UnitStatus.DEFENDING);
		assert (!this.isFalling());
		this.setRestTime(0);
		this.setStatus(UnitStatus.REST);
	}
	
	/**
	 * Return true if this unit is working
	 * @return
	 * 			| result == (this.getStatus()==UnitStatus.REST/RESTING)
	 */
	public boolean isResting(){
		return (this.getStatus()==UnitStatus.REST)||(this.getStatus()==UnitStatus.RESTING);
	}
	
	/**
	 * Update the hp and stamina of this resting Unit.
	 *
	 * @param 	time
	 *           The amount of time passed
	 * @pre 	time is a valid time for any unit 
	 * 			| isValidTime(time)
	 * @pre 	This Unit is in RESTING or REST status 
	 * 			| (this.getStatus() == UnitStatus.REST) || (this.getStatus() == UnitStatus.RESTING)
	 * @post 	If this Unit has status REST, and the time needed to heal 1 hp has
	 *       	passed, set the status to resting 
	 *       	| if (newTime > 0) && (this.getStatus == UnitStatus.REST) 
	 *       	| new.getStatus == UnitStatus.RESTING
	 * @post 	if this unit does not have maximum hp yet, and the time needed to
	 *       	heal 1 hp has passed, one hp will be added, and 
	 *       	| if (this.getMaxHP != this.getHP) && (newTime >= 0) 
	 *       	| 		then new.getHP == this.getHP + 1 
	 *     		|		new.getRestTime == newTime
	 * @post 	if this unit has maximum hp, does not have maximum stamina, and the TODO: use setRest
	 *       	time needed to heal 1 stamina has passed 
	 *       	| if (this.getMaxHP == this.getHP) && (this.getMaxStamina != this.getStamina) 
	 *       	| 	&& (newTime1>= 0) 
	 *       	| 		then new.getStamina == this.getStamina + 1 
	 *       	| 		new.getRestTime == newTime1
	 * @post 	if this unit has maximum hp and maximum stamina, set status to IDLE
	 *       	| if (this.getMaxHP == this.getHP) && (this.getMaxStamina ==this.getStamina) 
	 *       	|		then new.getStatus == UnitStatus.IDLE
	 */
	private void advanceRest(double time) {
		assert isValidTime(time);
		assert (this.getStatus() == UnitStatus.REST) || (this.getStatus() == UnitStatus.RESTING);

		this.setRestTime(this.getRestTime() + time);
		double oneHPTime = this.getToughness() / (200d * 0.2d);
		double newTime = this.getRestTime() - oneHPTime;
		// check for rest-status
		if (Util.fuzzyGreaterThanOrEqualTo(newTime, 0) && (this.getStatus() == UnitStatus.REST)) {
			this.setStatus(UnitStatus.RESTING);
		}
		// check for hp
		if (this.getMaxHP() != this.getHP()) {
			if (Util.fuzzyGreaterThanOrEqualTo(newTime, 0)) {
				this.setRestTime(newTime);
				this.setHP(this.getHP() + 1);
			}
			return;
		}
		// check for stamina
		else if (this.getStamina() != this.getMaxStamina()) {
			double oneStaminaTime = this.getToughness() / (100d * 0.2d);
			double newTime1 = this.getRestTime() - oneStaminaTime;
			if (Util.fuzzyGreaterThanOrEqualTo(newTime1, 0)) {
				this.setRestTime(newTime1);
				this.setStamina(this.getStamina() + 1);
			}
			return;
		}
		// resting has ended
		else {
			this.setStatus(UnitStatus.IDLE);
		}
	}

	/**
	 * Return the restTime of this Unit.
	 */
	@Basic
	@Raw
	private double getRestTime() {
		return this.restTime;
	}

	/**
	 * Check whether the given restTime is a valid restTime for any Unit.
	 *
	 * @param 	restTime
	 *          The restTime to check.
	 * @return 	| result == (restTime >= 0))
	 */
	private static boolean isValidRestTime(double restTime) {
		return (Util.fuzzyGreaterThanOrEqualTo(restTime, 0));
	}

	/**
	 * Set the restTime of this Unit to the given restTime.
	 *
	 * @param 	restTime
	 *          The new restTime for this Unit.
	 * @pre 	The given restTime must be a valid restTime for any Unit.
	 *      	| isValidRestTime(restTime)
	 * @post 	The restTime of this Unit is equal to the given restTime.
	 *       	| new.getRestTime() == restTime
	 */
	@Raw
	private void setRestTime(double restTime) {
		assert isValidRestTime(restTime);
		this.restTime = restTime;
	}

	/**
	 * Variable registering the restTime of this Unit.
	 */
	private double restTime = 0;

	/**
	 * Return the status of this Unit.
	 */
	@Basic
	@Raw
	public UnitStatus getStatus() {
		return this.status;
	}

	/**
	 * Check whether the given status is a valid status for any Unit.
	 *
	 * @param 	status
	 *          The status to check.
	 * @return 	| result == (status != null)
	 */
	private static boolean isValidStatus(UnitStatus status) {
		return (status != null);
	}

	/**
	 * Set the status of this Unit to the given status.
	 *
	 * @param 	status
	 *          The new status for this Unit.
	 * @post 	The status of this new Unit is equal to the given status.
	 *       	| new.getStatus() == status
	 * @throws 	IllegalArgumentException
	 *          The given status is not a valid status for any Unit.
	 *          | ! isValidStatus(getStatus())
	 */
	@Raw
	private void setStatus(UnitStatus status) throws IllegalArgumentException {
		if (!isValidStatus(status))
			throw new IllegalArgumentException();
		this.status = status;
	}

	/**
	 * Variable registering the status of this Unit.
	 */
	private UnitStatus status;

	public void advanceTime(double deltaT) {
		
		// Check if unit stands on solid ground
		int[] belowPosition = this.getCubePositionBelow();
		
		
		// check all status
		if (this.isFalling()){
			this.updateFall(deltaT);
		}
		else if(this.getWorld().isPassableTerrain(belowPosition)&&
				!this.getWorld().isNeighbouringSolid(this.getCubePosition())){
			this.startFall();
			this.setStatus(UnitStatus.FALLING);
		}
		else if (this.getStatus() == UnitStatus.DODGING) {
			this.dodge();
		}
		else if (this.getStatus() == UnitStatus.DEFENDING) {
			this.setStatus(UnitStatus.IDLE);
		}
		else if (this.isAttacking()) {
			this.advanceAttackTime(deltaT);
		}
		else if ((this.isResting())) {
			this.advanceRest(deltaT);
		}
		else if (this.isWorking()) {
			this.advanceWorkTime(deltaT);
		}
		else if (this.isMoving()) {
			this.updatePosition(deltaT);
		}

		if (Util.fuzzyGreaterThanOrEqualTo(this.rest3MinTime, 3 * 60) 
				&& (this.getStatus() != UnitStatus.DEFENDING) 
				&& (!this.isFalling())) {
			this.rest3MinTime = 0;
			this.rest();
		} else {
			this.rest3MinTime = this.rest3MinTime + deltaT;
		}

		if ((this.getStatus() == UnitStatus.IDLE) && (this.getDefaultBoolean() == true)) {
			this.defaultBehaviour();
		}

	}

	/**
	 * Check if the unit can still sprint
	 * 
	 * @param 	deltaT
	 * 			The time 
	 */
	private void updateSprint(double deltaT) {
		stamina = this.getStamina();
		if (stamina > 0) {
			this.sprintTime = this.sprintTime + deltaT;
			if (Util.fuzzyGreaterThanOrEqualTo(this.sprintTime, 0.1)) {
				while (this.sprintTime > 0.1) {
					this.sprintTime = this.sprintTime - 0.1;
					this.setStamina(this.getStamina() - 1);
				}
			}
			this.updatePosition(deltaT);
		} else {
			this.setStatus(UnitStatus.WALKING);
			this.updatePosition(deltaT);
		}
	}

	private double rest3MinTime;

	private double sprintTime;

	/**
	 * Check whether the given time is valid for any Unit
	 *
	 * @param 	time
	 *          The time to check.
	 * @return 	| result == ((time > 0) && (time < 0.2))
	 */
	public static boolean isValidTime(double time) {
		return true;
//		return Util.fuzzyGreaterThanOrEqualTo(time, 0) &&
//				Util.fuzzyLessThanOrEqualTo(time, 0.2)
//				&& (! Util.fuzzyEquals(time, 0)) && (! Util.fuzzyEquals(time, 0.2));
	}


	/** TODO: terminate Unit
	 *  TODO: drop item
	 * Terminate this Unit.
	 *
	 * @post 	This Unit is terminated. 
	 * 			| new.isTerminated()
	 */
	public void terminate() {
		this.isTerminated = true;
		this.getWorld().removeUnit(this);
		this.dropItem();
		this.setStatus(UnitStatus.IDLE);
		this.setWorld(null);
		this.getFaction().removeUnit(this);
		this.setFaction(null);
	}

	/**
	 * Return a boolean indicating whether or not this Unit is terminated.
	 */
	@Basic
	@Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Variable registering whether this person is terminated.
	 */
	private boolean isTerminated = false;

	/* END Terminate */

	/* DefaultBehaviour */

	/**
	 * The Unit is in default behaviour mode
	 * 
	 * @post 	the defaultBoolean is true 
	 * 			| new.getDefaultBoolean == true
	 * @effect 	start the defaultBehaviour method 
	 * 			| this.defaultBehaviour
	 */
	public void startDefaultBehaviour() {
		this.setDefaultBoolean(true);
		this.defaultBehaviour();
	}

	/**
	 * The Unit is no longer in default behavior mode
	 * 
	 * @post 	the defaultBoolean is false 
	 * 			| new.getDefaultBoolean == false
	 */
	public void stopDefaultBehaviour() {
		this.setDefaultBoolean(false);
		this.setStatus(UnitStatus.IDLE);
	}

	/**
	 * Determine the next behaviour
	 * 
	 * @throws 	IllegalStateException
	 *          The unit is not in defaultbehaviour 
	 *          | ! this.getDefaultBoolean()
	 * @effect 	1/3 of the times this method is called, the unit will work 
	 * 			| if (RandomNumberBetween0And1 <= 1/3) 
	 * 			|		then this.workAt(this.getCubePosition)
	 * @effect 	1/3 of the time this method is called, the unit will rest 
	 * 			| if (1/3 < RandomNumberBetween0And1 <= 2/3) 
	 * 			| 		then this.rest()
	 * @effect 	1/3 of the time this method is called, the unit will walk to a
	 *         	random location 
	 *         	| if (2/3 < RandomNumberBetween0And1) 
	 *         	|		then this.moveToRandom()
	 */
	private void defaultBehaviour() throws IllegalStateException {
		if (!this.getDefaultBoolean())
			throw new IllegalStateException();

		double chance = random.nextDouble();
		
//		
//		if (Util.fuzzyLessThanOrEqualTo(chance, 0.333333d)) {
//			this.workAt(this.getCubePosition());
//			return;
//		} else if (Util.fuzzyLessThanOrEqualTo(chance, 0.666666d)) {
//			this.rest();
//			return;
//		} else {
//			this.moveToRandom();
//		}

	}

	/**
	 * Return the defaultBoolean of this Unit.
	 */
	@Basic
	@Raw
	public boolean getDefaultBoolean() {
		return this.defaultBoolean;
	}

	/**
	 * Check whether the given defaultBoolean is a valid defaultBoolean for any
	 * Unit.
	 * 
	 * @param 	defaultBoolean
	 *          The defaultBoolean to check.
	 * @return 	| result == (defaultBoolean == true) || (defaultBoolean == false)
	 */
	private static boolean isValidDefaultBoolean(boolean defaultBoolean) {
		return (defaultBoolean == true) || (defaultBoolean == false);
	}

	/**
	 * Set the defaultBoolean of this Unit to the given defaultBoolean.
	 * 
	 * @param 	defaultBoolean
	 *          The new defaultBoolean for this Unit.
	 * @post 	The defaultBoolean of this new Unit is equal to the given
	 *       	defaultBoolean. 
	 *       	| new.getDefaultBoolean() == defaultBoolean
	 * @throws	IllegalArgumentException
	 *          The given defaultBoolean is not a valid defaultBoolean for any Unit. 
	 *          | ! isValidDefaultBoolean(getDefaultBoolean())
	 */
	@Raw
	private void setDefaultBoolean(boolean defaultBoolean) throws IllegalArgumentException {
		if (!isValidDefaultBoolean(defaultBoolean))
			throw new IllegalArgumentException();
		this.defaultBoolean = defaultBoolean;
	}

	/**
	 * Variable registering the defaultBoolean of this Unit.
	 */

	private boolean defaultBoolean = false;
	
	
	/** TO BE ADDED TO CLASS HEADING
	 * @invar  The Faction of each Unit must be a valid Faction for any
	 *         Unit.
	 *       | isValidFaction(getFaction())
	 */

	/**
	 * Return the Faction of this Unit.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}

	/**
	 * Check whether the given Faction is a valid Faction for
	 * any Unit. TODO: isvalidFaction unit
	 *  
	 * @param  Faction
	 *         The Faction to check.
	 * @return 
	 *       | result == (faction.getNbUnits <= max_NB_Units_in_Faction)
	*/
	public static boolean isValidFaction(Faction faction) {
		return (faction.getNbUnits() <= MAX_NB_UNITS_IN_FACTION);
	}

	/**
	 * Set the Faction of this Unit to the given Faction.
	 * 
	 * @param  faction
	 *         The new Faction for this Unit.
	 * @post   The Faction of this new Unit is equal to
	 *         the given Faction.
	 *       | new.getFaction() == faction
	 * @throws IllegalArgumentException
	 *         The given Faction is not a valid Faction for any
	 *         Unit.
	 *       | ! isValidFaction(getFaction())
	 */
	@Raw
	public void setFaction(Faction faction) 
			throws IllegalArgumentException {
		if (! isValidFaction(faction))
			throw new IllegalArgumentException();
		this.faction = faction;
	}

	/**
	 * Variable registering the Faction of this Unit.
	 */
	private Faction faction = null;
	

	/**
	 * Return the Experience of this Unit.
	 */
	@Basic @Raw
	public int getExperience() {
		return this.experience;
	}

	/** TODO: isValidExperience
	 * Check whether the given Experience is a valid Experience for
	 * any Unit.
	 *  
	 * @param  Experience
	 *         The Experience to check.
	 * @return 
	 *       | result == 
	*/
	public static boolean isValidExperience(int experience) {
		return true;
	}

	/**
	 * Set the Experience of this Unit to the given Experience.
	 * 
	 * @param  experience
	 *         The new Experience for this Unit.
	 * @post   The Experience of this new Unit is equal to
	 *         the given Experience.
	 *       | new.getExperience() == experience
	 * @throws IllegalArgumentException
	 *         The given Experience is not a valid Experience for any
	 *         Unit.
	 *       | ! isValidExperience(getExperience())
	 */
	@Raw
	public void setExperience(int experience) throws IllegalArgumentException {
		if (! isValidExperience(experience))
			throw new IllegalArgumentException();
		this.experience = experience;
	}
	
	/**
	 * TODO: increaseExperience documentation
	 * @param experience
	 * @throws IllegalArgumentException
	 */
	public void increaseExperience(int amount) throws IllegalArgumentException{
		// if all atributes have the maximum value, do nothing
		if ((this.getAgility() == 200)&&(this.getStrength()==200)&&(this.getToughness()==200))
			return;
		
		int newExperience = this.getExperience() + amount;
		while(newExperience >= 10){
			newExperience = newExperience - 10;
			int randomInt = random.nextInt(3);
			if ((randomInt == 0) && isValidUnitAttribute(this.getAgility()+1)){
				this.setAgility(this.getAgility()+1);
			}
			else if((randomInt == 1)&& isValidUnitAttribute(this.getStrength()+1)){
				this.setStrength(this.getStrength()+1);
			}
			else if((randomInt == 2)&& isValidUnitAttribute(this.getToughness()+1)){
				this.setToughness(this.getToughness()+1);
			}
			else{
				newExperience = newExperience + 10;
			}
		}
		this.setExperience(newExperience);
	}

	/**
	 * Variable registering the Experience of this Unit.
	 */
	private int experience = 0;

}
