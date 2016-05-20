package hillbillies.model;

import hillbillies.model.UnitStatus;
import hillbillies.model.pathfinding.AStarPathFinder;
import hillbillies.model.pathfinding.Path;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.expression.position.HerePosition;
import hillbillies.part3.programs.expression.position.NextToPosition;
import hillbillies.part3.programs.expression.unit.Enemy;
import hillbillies.part3.programs.statement.action.Action;
import hillbillies.part3.programs.statement.action.Follow;
import hillbillies.part3.programs.statement.action.Work;

import java.util.Arrays;

import java.util.Random;

import javax.vecmath.*;

import be.kuleuven.cs.som.annotate.*;
import ogp.framework.util.Util;

import static hillbillies.model.Constants.MAX_NB_UNITS_IN_FACTION;


/**
 * A class for Units
 * 
 * @author Toon Deburchgrave CWS-ELT
 *
 * @invar The weight of each Unit must be a valid weight for any Unit. 
 * 		  | isValidWeight(getWeight())
 * 
 * @invar The strength of each Unit must be a valid strength for any Unit. 
 * 		  | isValidUnitAttribute(getStrength())
 * 
 * @invar The agility of each Unit must be a valid agility for any Unit. 
 * 		  | isValidUnitAttribute(getAgility())
 * 
 * @invar The toughness of each Unit must be a valid toughness for any Unit. 
 * 		  | isValidUnitAttribute(getToughness())
 *
 * @invar The hitpoints of each Unit must be a valid hitpoints for this Unit. 
 * 		  | isValidHP(getHP())
 * 
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
 * @invar The orientation of each unit must be a valid orientation for any unit.
 *        | isValidOrientation(getOrientation())
 *        
 * @invar The adjacentDestination of each unit must be a valid
 *        adjacentDestination for any unit.
 *        | isValidAdjacentDestination(getAdjacentDestination())
 *        
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
 * @invar  The Task of each	 Unit must be a valid Task for any
 *         Unit.	
 *       | isValidTask(getTask())
 *
 *       
 * @version 0.1
 */
public class Unit extends GameObject {
	
	/**
	 * Initialize this new Unit with given position, strength, agility, weight
	 * and toughness.
	 *
	 *@effect 	The position and world of this new unit, are set by the constructor
	 * 			of the superClass of unit: gameobject
	 * 			| super(position, world)
	 * 
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
	 * @post	The status of this new Unit is IDLE.
	 * 			| new.getStatus() == UnitStatus.IDLE
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
	 * 		   	| this.setOrigin(position)
	 * @throws 	IllegalArgumentException
	 * 		   	The given position is not a valid position for this Unit
	 * 			| ! isValidOrigin(new.getCubePosition)
	 * 
	 * @post	The new unit is added to the given world
	 * 			| world.hasAsUnit(this) == true
	 * 
	 * @pararm	faction
	 * 			The faction of this new unit.
	 * @effect	The faction of this new unit is set to the given faction
	 * 			| this.setFaction(faction)
	 * @throws	IllegalArgumentException
	 * 			The faction is not a valid faction for this Unit
	 * 			| !faction.canHaveAsUnit(this)
	 * 
	 * @post	The new unit is added to the given faction
	 * 			| faction.hasAsUnit(this) == true
	 * 
	 * @param	defaultBehaviour
	 * 			The defaultbehaviour of this new unit
	 * @post	The defaultboolean of this unit is equal to defaultBehaviour
	 * 			| new.getDefaultBoolean == defaultBehaviour	
	 * 
	 */
	public Unit(String name, int[] position, int weight, int strength, int agility, 
			int toughness, boolean defaultBehaviour)
				throws IllegalArgumentException, NullPointerException {
		super(position);	
		
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
	
		this.setName(name);

		this.setStatus(UnitStatus.IDLE);
		this.setAdjacentDestination(position);
		this.setFinalDestination(position);

		this.setOrigin(position);

		if (defaultBehaviour)
			this.startDefaultBehaviour();
		
	}

	/**
	 * Sets the units status to walking, and the units adjacentDestination to
	 * adjacentDestination.
	 * 
	 * @param 	adjacentDestination
	 *          The neighbouring destination
	 * @effect 	The units status is set to WALKING, if the unit is not currently sprinting
	 * 			| if (!this.isSprinting())
	 * 			|	this.setStatus(UnitStatus.WALKING); 
	 * 
	 * @effect 	The units adjacentDestination is set to adjacentDestination
	 *         	| this.setAdjacentDestination(adjacentDestination);
	 *         
	 * @effect	If the Unit is currently at its final destination, set
	 * 			finalDestination to the given adjacentDestination
	 * 			| if(this.getPosition == this.getFinalDestination)
	 * 			| 		then this.setFinalDestination(adjacentDestination)
	 * 
	 * @effect	The origin of this Unit is set to its current position
	 * 			| this.setOrigin(this.getCubePosition())
	 * 
	 * @effect	The walkTimer is initiated
	 * 			| this.initiateWalktimer(adjacentDestination)
	 * 
	 * @throws 	IllegalArgumentException
	 *          The given adjacentDestination is not a valid
	 *          adjacentDestination
	 *          | ! isValidAdjacentDestination(adjacentDestination)
	 * @throws	IllegalStateException
	 * 			The unit is currently falling
	 * 			| this.isfalling()
	 */
	private void moveToAdjacent(int[] adjacentDestination) throws IllegalArgumentException {
		if(adjacentDestination == null){
			this.setFinalDestination(this.getCubePosition());
			return;
		}
		if (!isValidAdjacentDestination(adjacentDestination))
			throw new IllegalArgumentException("Invalid adjacentDestination!");
		if(this.isFalling())
			throw new IllegalStateException("can't move while falling!");
		
		if(!this.isSprinting() && !this.isFollowing())
			this.setStatus(UnitStatus.WALKING);
		
		if (Arrays.equals(this.getCubePosition(), this.getFinalDestination()))
			this.setFinalDestination(adjacentDestination);
		
		
		this.setAdjacentDestination(adjacentDestination);
		this.setOrigin(this.getCubePosition());
		
		this.initiateWalkTimer(adjacentDestination);
	}
	
	/**
	 * Move to a neighbouring cube.
	 * 
	 * @param dx
	 * 			The distance and direction(+ or -) in the x direction
	 * @param dy
	 * 			The distance and direction in the y direction
	 * @param dz
	 * 			The distance and direction in the z direction
	 * 
	 * @effect	The moveTo method is called, with the position of the targeted cube as argument.
	 * 			| destination = this.getcubePosition + {dx, dy, dz}
	 * 			| this.moveTo(destination)
	 * 
	 * @throws 	IllegalArgumentException
	 * 			The given arguments are more than 1 away.
	 * 			| (dx > 1 || dx < -1 || dy > 1 || dy < -1 || dz > 1 || dz < -1)
	 */
	public void facadeMoveToAdjacent(int dx, int dy, int dz) throws IllegalArgumentException{
		if(dx > 1 || dx < -1 || dy > 1 || dy < -1 || dz > 1 || dz < -1)
			throw new IllegalArgumentException("more than 1 away!");
		
		int[] destination = {
				this.getCubePosition()[0] + dx,
				this.getCubePosition()[1] + dy, 
				this.getCubePosition()[2] + dz};
		
		this.moveTo(destination);
	}

	/**
	 * Sets the units status to walking, and the units adjacentDestination to
	 * adjacentDestination.
	 * 
	 * @param 	finalDestination
	 *          The final destination for this unit.
	 * 
	 * @effect	If the current position is equal to the given destination
	 * 		    do nothing
	 * 			| if this.getCubePosition == finalDestination
	 * 			| 		return;
	 * 
	 * @effect 	The units finalDestination is set to finalDestination, 
	 *         	| this.setFinalDestination(finalDestination);      	
	 *         
	 * @effect	the unit will execute moveToAdjacent, with as argument the destination
	 *         	selected with findPath 
	 * 			| this.moveToAdjacent(this.findPath)
	 * 
	 * @throws 	IllegalArgumentException
	 *          The given finalDestination is not a valid finalDestination
	 *          | ! isValidFinalDestination(finalDestination)
	 *          
	 * @effect	The path of this unit is reset to null
	 * 			| this.resetPath
	 * 
	 * @throws illegalArgumentException
	 * 			The destination is not a valid position for this unit
	 * 			| !this.getWorld().isValidWorldPosition(finalDestination) || 
	 *			| !this.getWorld().isNeighbouringSolid(finalDestination)||
	 *			| !this.getWorld().isPassableTerrain(finalDestination))
	 *
	 * @throws illegalStateException
	 * 		   The unit is currently falling
	 * 			|this.isFalling
	 * 
	 */
	public void moveTo(int[] finalDestination) throws IllegalArgumentException {
		if (!this.getWorld().isValidWorldPosition(finalDestination) || 
				!this.getWorld().isNeighbouringSolid(finalDestination)||
				!this.getWorld().isPassableTerrain(finalDestination))
			throw new IllegalArgumentException("Invalid final destination! " + finalDestination[0] + "," + finalDestination[1] + "," + finalDestination[2]);
		if (this.isFalling())
			throw new IllegalStateException("can't move while falling");
		if (Arrays.equals(this.getCubePosition(), finalDestination))
			return;
		this.setFinalDestination(finalDestination);
		this.resetPath();
		int[] nextPosition = this.findPath();
		if(nextPosition == null){
			this.setStatus(UnitStatus.IDLE);
			this.setFinalDestination(this.getCubePosition());
		}
		else
			this.moveToAdjacent(nextPosition);
	}
	
	/**
	 * Start following the given Unit
	 * 
	 * @param 	otherUnit
	 * 			The Unit to follow
	 * 
	 * @post	The status of this unit is "following"
	 * 			| new.getStatus == UnitStatus.FOLLOWING
	 * 
	 * @effect	Move to the current position of the unit
	 * 		    | this.moveTo(otherUnit.getCubePosition)
	 * 
	 * @throws	NullPointerException
	 * 			if otherUnit is null
	 * 			| (otherUnit == null)
	 */
	public void startFollowing(Unit otherUnit) throws NullPointerException{
		if (otherUnit == null)
			throw new NullPointerException("no other unit given!");
		this.followUnit = otherUnit;
		this.setStatus(UnitStatus.FOLLOWING);
		try {
			this.moveTo(otherUnit.getCubePosition());
		} catch (Exception e) {
			this.followUnit = null;
			this.setStatus(UnitStatus.IDLE);
		}
	}
	
	/**
	 * Move to the current location of the unit this unit is following
	 * 
	 * @effect If this unit is not neighbouring the unit being followed,
	 * 		 moveTo the location of the followUnit
	 * 		 | if !this.isNeighbouringCube(followUnit.getCubePosition())
	 *  	 |		this.moveTo(followUnit.getCubePosition());
	 * @post If this unit is neighbouring the other unit, stop following
	 * 		 | else
	 * 		 | 		this.getStatus != FOLLOWING
	 * @effect If this unit is in defaultmode, and has arribed next to a unit
	 * 		   attack the other unit.
	 * 		 | if(this.getDefaultBoolean)
	 * 		 | 		this.attack(followUnit)
	 */
	private void follow(){
		if(!this.isNeighbouringCube(followUnit.getCubePosition())){
			this.moveTo(followUnit.getCubePosition());
		}
		else{
			this.setStatus(UnitStatus.IDLE);
			Unit follow = this.followUnit;
			this.followUnit = null;
			if(this.getDefaultBoolean())
				this.attack(follow);
		}
	}
	
	/**
	 * check if this unit is currently following another unit
	 * 
	 * @return 
	 * 		|(this.getStatus == FOLLOWING)
	 */
	public boolean isFollowing(){
		return this.getStatus() == UnitStatus.FOLLOWING;
	}
	
	/**
	 * The Unit this unit is currently following
	 */
	private Unit followUnit;
	
	/**
	 * Check if the Unit is moving
	 * 
	 * @return	true if the unit is walking or sprinting
	 * 			| result == (unit.getStatus() == UnitStatus.WALKING) 
	 * 			| 	&& (unit.getStatus() == UnitStatus.SPRINTING))
	 */
	public boolean isMoving(){
		 return ((this.getStatus() == UnitStatus.WALKING) 
				 || (this.getStatus() == UnitStatus.SPRINTING)
				 || (this.getStatus() == UnitStatus.FOLLOWING));
	}

	/** 
	 * Updates the position of the unit.
	 * 
	 * @param 	time
	 *          The time elapsed.
	 * @effect 	The new position of this unit is where the unit would be if it went
	 *       	at its speed, towards the given adjacentDestination, during the
	 *       	given time, if the walktimer is not 0 or smaller. Else, set the position to
	 *       	the adjacentDestination of this unit
	 *       	| if (this.getWalkTimer - timer > 0)
	 *       	| 	then this.setPosition(this.getVelocity.scaleAdd(time,this.getPosition()))
	 *       	|		this.setWalkTimer(this.getWalkTimer - timer)
	 *       	else, the set the position of this unit to the adjacentdestination, set the walktimer to
	 *       	0 and increase the experience by one. 
	 *       	| else this.setWalkTimer(0)
	 *       	|		this.increaseExperience(1)
	 *       	|		this.setAtPosition(this.getAdjacentDestination
	 *       	if the finaldestinatino has been reached, set status to idle
	 *       	|		if (this.getCubePosition == this.getFinalDestination)
	 *       	|			this.setStatus(IDLE)
	 *       	else, move to adjacent, determined by the path
	 *       	|		else this.moveToAdjacent(this.findPath)
	 * @throws 	IllegalArgumentException
	 *          The given time is not a valid time 
	 *          | ! isValidTime(time)
	 *          
	 * @effect	the orientation is update
	 * 			| this.updateOrientation()
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
			
			if (Arrays.equals(this.getCubePosition(), this.getFinalDestination())){
				if(this.isFollowing())
					this.follow();
				else
					this.setStatus(UnitStatus.IDLE);
			}
			else
				this.moveToAdjacent(this.findPath());
		} 
		else{
			this.setPosition(nextPosition);
			this.setWalkTimer(newWalkTimer);
		}
	}
	
	/**
	 * Return the next position from the path. 
	 * If the path doesn't exist, create a new path, and give the first position
	 * of that path
	 * 
	 * @effect	The pathIndex is increased by one, if path is not null
	 * 			| if(this.getPath != null)
	 * 			|	then this.setPathIndex(this.getPathIndex + 1)
	 * 			Else, pathIndex is set to one
	 * 			| else this.setPathIndex(1)
	 * @effect	If the path of this unit is null, find a new path
	 * 			| if(this.getPath == null)
	 * 			| 	then pathfinder = new AStarPathFinder(this.getWorld)
	 * 			|		this.setPath(pathFinder.findpath(this, currentPosition, finalDestination))
	 * @return 	the position on the path, on index PathIndex
	 * 			| return this.getPath.getStepInt(this.getPathIndex)
	 */
	private int[] findPath(){
		
		this.setPathIndex(this.getPathIndex() + 1);
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
		if(this.getPath() == null)
			return null;
		
		return this.getPath().getStepInt(this.getPathIndex());
	}
	
	/**
	 * Set path to null
	 * 
	 * @post	the path of this unit is null
	 * 			| new.getPath() == null	
	 */
	public void resetPath(){
		this.setPath(null);
	}
	
	/**
	 * Return the path of this unit
	 */
	public Path getPath(){
		return this.path;
	}
	
	/**
	 * Set the path of this unit
	 * @param path
	 * 		  The path to be set
	 * @post  the path of this unit is the given path
	 * 		  | new.getPath() == path
	 */
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
	 * @throws 	IllegalStateException
	 * 			The unit is not currently falling
	 * 			| !this.isFalling
	 * @throws	IllegalArgumentException
	 * 			fallDepth can't be negative
	 * 			| fallDepth <= 0
	 */
	public void takeFallDamage(int fallDepth)
			throws IllegalArgumentException, IllegalStateException{
		if(!this.isFalling())
			throw new IllegalStateException("Unit is not Falling");
		if(fallDepth <= 0)
			throw new IllegalArgumentException("can't fall negative distance!");
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
	private int[] origin;

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
		return (this.getStatus() == UnitStatus.WALKING) && 
				(this.getStamina() > 0);
	}

	/**
	 * Set the status of the unit to SPRINTING
	 * 
	 * @pre 	The unit can sprint. 
	 * 			| this.canSprint()
	 * @effect 	The status of this unit is set to SPRINTING
	 *         	| this.setStatus(UnitStatus.SPRINTING)
	 * @throws	IllegalStateException
	 * 			The unit cannot sprint at this moment
	 * 			| !this.canSprint
	 */
	public void startSprint() {
		if(! this.canSprint())
			throw new IllegalStateException("unit can't sprint at this moment");
		this.setStatus(UnitStatus.SPRINTING);
		this.initiateWalkTimer(this.getAdjacentDestination());
	}

	/**
	 * Set the status of the unit from SPRINTING to WALKING
	 * 
	 * @effect 	The status of this unit is set to WALKING
	 *         	| this.setStatus(UnitStatus.WALKING)
	 * @throws	IllegalStateException
	 * 			the Unit is not sprinting at the moment
	 * 			| !this.isSprinting
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

	/**
	 * The Unit moves instantaniously to a random position bordering its current
	 * position This new position is a valid position in the gameworld
	 *
	 * 
	 * @post 	The unit is in a position bordering its current position.
	 * @throws 	IllegalStateException
	 *          The unit is not being attacked.
	 *          | ! this.getStatus == DEFENDING
	 * 
	 * @effect	the experience of this unit is increased by 10
	 * 			| this.increaseExperience(10)
	 * @post	the Status of this unit is set to IDLE
	 * 			| new.getStatus == IDLE
	 */
	private void dodge() throws IllegalStateException {

		if (!(this.getStatus() == UnitStatus.DODGING))
			throw new IllegalStateException("Unit is not being attacked!");

		Vector3d newPosition = new Vector3d(-1, -1, -1);
		int counter = 0;

		double thisX = this.getPosition().x;
		double thisY = this.getPosition().y;
		double thisZ = this.getPosition().z;
		
		while (counter < 10000) {
			// Returns a double between -1 and +1
			double xJump = 2 * new Random().nextDouble() - 1;
			double yJump = 2 * new Random().nextDouble() - 1;

			newPosition.set(thisX + xJump, thisY + yJump, thisZ);
			counter++;
			// in this way, because ispassableterrain requires a valid position
			if(isValidPosition(newPosition, this.getWorld()))
					if(this.getWorld().isPassableTerrain(toCubePosition(newPosition)))
						break;
		}
		
		if (isValidPosition(newPosition, this.getWorld()))
			this.setPosition(newPosition);
		this.setStatus(UnitStatus.IDLE);
		this.increaseExperience(20);
	}

	/**
	 * Check whether this Unit can attack the other Unit
	 *
	 * @param 	other
	 *          the unit being attacked
	 * @return 	If the other unit is falling, return false
	 * 			| if (other.isFalling)
	 * 			|	then return false
	 * 			If the other unit and this unit are of the same faction, return false
	 * 			| if (other.getFaction == this.getFaction)
	 * 			|	then return false
	 * 			True if and only if the other unit occupies a cube that, in
	 *         	regards to the cube this unit occupies has the same z-coordinate,
	 *         	an x-coordinate differing no more than 1 and a y-coordinate
	 *         	differing no more than 1. 
	 *         	| return (this.getCubePosition.z == other.getCubePosition.z) && 
	 *         	| 	(abs(this.getCubePosition.x - other.getCubePosition.x) < 2) && 
	 *         	| 	(abs(this.getCubePosition.y - other.getCubePosition.y) < 2)
	 * @throws 	NullPointerException
	 *          The other unit is not effective 
	 *          | other == null
	 */
	private boolean canAttack(Unit other) throws NullPointerException {
		if (other == null)
			throw new NullPointerException("can't attack null");
		if (other.isFalling()){
			return false;
		}
			
		if (other.getFaction()==this.getFaction()){
			return false;
		}
		return (this.getCubePosition()[2] == other.getCubePosition()[2])
				&& (Math.abs(this.getCubePosition()[0] - other.getCubePosition()[0]) < 2)
				&& (Math.abs(this.getCubePosition()[1] - other.getCubePosition()[1]) < 2);
	}

	/**
	 * set a random destination in the gameworld
	 * 
	 * @effect Call the moveTo method, and give a random, valid position in the gameworld
	 * 			with passable terrain, and neighbouring a solid cube
	 *         | this.moveTo(randomPosition)
	 *         | where	(this.getWorld().isValidWorldPosition(randomPosition)
	 *		   |			&&(this.getWorld().isPassableTerrain(randomPosition))
	 *		   |			&& (this.getWorld().isNeighbouringSolid(randomPosition))
	 * 
	 */
	private void moveToRandom(){
		this.resetPath();
		int counter = 0;
		int[] position = {-1,-1,-1};
		while (counter < 100) {
			
			position[0] = new Random().nextInt(this.getWorld().getNbCubesX());
			position[1] = new Random().nextInt(this.getWorld().getNbCubesY());
			position[2] = new Random().nextInt(this.getWorld().getNbCubesZ());

			counter++;
			// in this way, because ispassableterrain requires a valid position
			if(this.getWorld().isValidWorldPosition(position))
				if(this.getWorld().isPassableTerrain(position))
					if(this.getWorld().isNeighbouringSolid(position)){
						this.setFinalDestination(position);
						int[] nextpos = this.findPath();
						if(nextpos != null)
							break;
					}
		}
		this.setFinalDestination(this.getCubePosition());
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
	 * @return 	false if the adjacentDestination is more then 1 cube away, in any direction
	 *         | for (i from 0 to 3)
	 *         | 	if(|this.getCubePosition[i]-adjacentDestination[i]| > 1)
	 *         |		then return false
	 *         	false if it is not neighbouring a solid, or is not passable terrain
	 *         | if (!this.getWorld.isNeighbouringSolid(adjacentDestination))||
	 *         |		(!this.getWorld.isPassableTerrain(adjacentDestination))
	 *         |	then return false
	 *         	else it's a valid adjacentdestination true
	 *         | else return true
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
	 * the walktimer is set to the time remaining to arrive at the adjacentdestinatino
	 * from this units current position
	 * 
	 * @param adjacentDestination
	 * 		  the destination for which to set the walktimer
	 * @post  Walktimer is equal to the length between current position and destination, divided by
	 * 		  the speed of the unit
	 * 		 | new.getWalkTimer == (this.getPosition - toVectorPosition(adjacentDestination))/this.getSpeed
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
		if (this.getWorld() != null)
			if (!this.getWorld().isValidWorldPosition(finalDestination))
				throw new IllegalArgumentException("illegal final destination");
		this.finalDestination = finalDestination;
	}

	/**
	 * Variable registering the finalDestination of this unit.
	 */
	private int[] finalDestination = {0,0,0};

	
	/**
	 * Return the weight of this Unit. If the unit is carrying an item,
	 * add the weight of the item to its own weight.
	 * 
	 * @return the weight of the unit plus item
	 * 			| if (this.getGameItem != null)
	 * 			| 	then return this.weight + this.getGameItem.getWeight
	 * 			| else return this.weight
	 */
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
		return ((1 < weight) && (weight < 200) && 
				(weight >= (this.getStrength() + this.getAgility()) / 2));
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
		return ((25 <= weight) && (weight <= 100) && 
				(weight >= (this.getStrength() + this.getAgility()) / 2));
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
	private static boolean isValidUnitAttribute(int attribute) {
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
	private static boolean isValidStartAttribute(int attribute) {
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
	
	/**
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
	private void takeDamage(int amount){
		assert (amount>0);
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
	 *          The given target is not neigbhouring a cube
	 *          | !this.isNeighbouringCube(worktarget)
	 * @throws	IllegalStateException
	 * 			The unit is currently falling
	 * 			| this.isFalling         
	 * 
	 * @param	x, y and z
	 * 			The cube-coordinates of the targetted cube.
	 * @post	the workTarget is set to the given coordinates
	 * 			| new.getWorkTarget == workTarget
	 * @post	the workTime is set
	 * 			| new.getWorkTime == 500/this.getStrength
	 * @post	the status is set to Working
	 * 			| new.getStatus == WORKING
	 * @effect	the orientation of this unit, is towards the worktarget
	 * 			| this.face(worktarget)
	 */
	public void workAt(int[] workTarget) throws IllegalArgumentException {
		if (! this.isNeighbouringCube(workTarget))
			throw new IllegalArgumentException("not a neighbouring cube");
		if (this.isFalling())
			throw new IllegalStateException("can't work while falling");
		this.setWorkTime(500.0d / this.getStrength());
		this.setStatus(UnitStatus.WORKING);
		this.setWorkTarget(workTarget);
		
		this.face(workTarget);
	}
	
	/**
	 * Do the work task
	 * 
	 * @effect	increase the experience of this unit by 10
	 * 			| this.increaseExperience(10)
	 * @effect	if this unit is carrying an item, drop the item and exit the method
	 * 			| if(this.getGameItem != null)
	 * 			|	then this.dropItem
	 * 			|		return
	 * @effect	if this unit is not carrying an item, and the unit is currently standing on a workshop,
	 * 			and a boulder and a log are present, increase weight and toughness of this unit.
	 * 			| if(world.getCubeType(this.getWorkTarget) == WORKSHOP) && 
	 * 			|	(world.BoulderAtPosition(this.getWorkTarget)!=null) &&
	 * 			|	(world.LogAtPosition(this.getWorktTarget) != null)
	 * 			|	then this.SetWeight(this.getWeight + 1)
	 * 			| 		 this.setToughness(this.getToughness + 1)
	 * 			|		 boulder.terminate
	 * 			|		 log.terminate
	 * 			|		 return
	 * @effect	If this unit is not carrying an item, and there is no workshop present, and there
	 * 			is a boulder present at the worktarget, pick up the boulder and exit the method
	 * 			| if (world.BoulderAtPosition(this.getWorkTarget)!=null)
	 * 			| 		then this.setGameItem(boulder)
	 * 			|			 boulder.setWorld(null)
	 * 			|			 this.getWorld.removeBoulder(boulder)
	 * 			|			return
	 * @effect	If this unit is not carrying an item, and there is no workshop present, and there
	 * 			is a Log present at the worktarget, pick up the log and exit the method
	 * 			| if (world.logAtPosition(this.getWorkTarget)!=null)
	 * 			| 		then this.setGameItem(log)
	 * 			|				log.setWorld(null)
	 * 			|				this.getWorld.removeLog(log)
	 * 			|				return
	 * @effect	if the targeted cube is a tree, destroy that cube, and drop a log
	 * 			| world = this.getWorld
	 * 			| if (world.getCubeType(workTarget) == TREE)
	 * 			|		then world.setCubeType(workTarget, AIR)
	 * 			|			Log log = new Log(workTarget, world)
	 * 			|			world.addLog(log)
	 * 			|			return
	 * @effect	if the targeted cube is a rock, destroy that cube, and drop a boulder
	 * 			| world = this.getWorld
	 * 			| if (world.getCubeType(workTarget) == ROCK)
	 * 			|		then world.setCubeType(workTarget, AIR)
	 * 			|			Boulder boulder = new Boulder(workTarget, world)
	 * 			|			world.addBoulder(boulder)
	 * 			|			return
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
		
		if((world.getCubeType(workX, workY, workZ)== CubeType.WORKSHOP) &&
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
		if(world.getCubeType(workX, workY, workZ)==CubeType.TREE){
			world.setCubeType(workX, workY, workZ, CubeType.AIR.getNumber());
			Log newLog = new Log(this.getWorkTarget(), world);
			world.addLog(newLog);
			
			return;
		}
		
		//if the target cube is a rock, destroy the rock and drop a boulder
		if(world.getCubeType(workX, workY, workZ)==CubeType.ROCK){
			world.setCubeType(workX, workY, workZ, CubeType.AIR.getNumber());
			Boulder newBoulder = new Boulder(this.getWorkTarget(), world);
			world.addBoulder(newBoulder);
			
			return;
		}
	}

	/**
	 * Put the item currently being carried by this unit, back into the world
	 * 
	 * @effect	if this unit is carrying a log, 
	 * 			the world of the log is the world of this unit, and that world contains the log,
	 * 			the position of the log is the worktarget position, 
	 * 			| if(this.isCarryingLog()){
	 *			| 	Log log = (Log)this.getGameItem();
	 *			| 	log.setWorld(this.getWorld());
	 *			|	this.getWorld().addLog(log);
	 *			|	if(this.isWorking())
	 * 			|		log.setAtPosition(this.getWorkTarget());
	 *			|	else
	 *			|		log.setAtPosition(this.getCubePosition());
	 *			|	this.setGameItem(null);
	 * @effect 	if this unit is carrying a boulder
	 * 			set the world of the boulder to the world of the unit, add the boulder to the world
	 * 			and set the position of the boulder to the worktarget
	 * 			| if(this.isCarryingBoulder()){
	 *			|	Boulder boulder = (Boulder)this.getGameItem();
	 *			|	boulder.setWorld(this.getWorld());
	 * 			| 	this.getWorld().addBoulder(boulder);
	 *			|   if(this.isWorking())
	 *			|		boulder.setAtPosition(this.getWorkTarget());
	 * 			|	else
	 *			|		boulder.setAtPosition(this.getCubePosition());
	 *			|	this.setGameItem(null);
	 *	}
	 *	
	 */
	private void dropItem() {
		if(this.isCarryingLog()){
			Log log = (Log)this.getGameItem();
			log.setWorld(this.getWorld());
			this.getWorld().addLog(log);
			if(this.isWorking())
				log.setAtPosition(this.getWorkTarget());
			else
				log.setAtPosition(this.getCubePosition());
			this.setGameItem(null);
		}
		else if(this.isCarryingBoulder()){
			Boulder boulder = (Boulder)this.getGameItem();
			boulder.setWorld(this.getWorld());
			this.getWorld().addBoulder(boulder);
			if(this.isWorking())
				boulder.setAtPosition(this.getWorkTarget());
			else
				boulder.setAtPosition(this.getCubePosition());
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

	/** 
	 * Check whether the given workTarget is a valid workTarget for
	 * any Unit.
	 *  
	 * @param  workTarget
	 *         The workTarget to check.
	 * @return 
	 *       | result == (this.getWorld().isValidWorldPosition(workTarget) && 
	 *       | 				this.isNeighbouringCube(workTarget))
	*/
	private boolean isValidWorkTarget(int[] workTarget) {
		return (this.getWorld().isValidWorldPosition(workTarget) && 
				this.isNeighbouringCube(workTarget));
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
	 * @effect	If the workTime is smaller then or equal to 0, do finishWork, and set status to idle
	 * 			| if (this.worktime - time <= 0)
	 * 			| 	then this.finishWork();
	 * 			|		this.setStatus(IDLE)
	 * @effect	Else, set worktime to the reduce worktime
	 * 			| else this.setWorkTime(this.getWorkTime - time)
	 */
	private void advanceWorkTime(double time) throws IllegalArgumentException {
		if (!isValidTime(time))
			throw new IllegalArgumentException("The given time is not a valid time");

		double newWorkTime = this.getWorkTime() - time;
		if (Util.fuzzyLessThanOrEqualTo(newWorkTime, 0)) {
			this.finishWork();
			this.setStatus(UnitStatus.IDLE);
			
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

	/**
	 * Check whether the given gameItem is a valid gameItem for
	 * any Unit. Any gameItem is a valid item for any unit
	 *  
	 * @param  gameItem
	 *         The gameItem to check.
	 * @return 
	 *       | result == true
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
	private void setGameItem(GameItem gameItem) throws IllegalArgumentException {
		if (! isValidGameItem(gameItem))
			throw new IllegalArgumentException();
		this.gameItem = gameItem;
	}

	/**
	 * Variable registering the gameItem of this Unit.
	 */
	private GameItem gameItem = null;
	
	/**
	 * Return whether this unit is carrying an item
	 * 
	 * @return true if gameItem is not null
	 * 			| result == (this.getGameItem() != null)
	 */
	public boolean isCarryingItem(){
		if(this.getGameItem() == null)
			return false;
		else
			return true;
	}
	
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
	 * @throws	illegalStateException
	 * 			the unit is currently falling
	 * 			| this.isFalling
	 * @effect	Set the attackcountdown to 1 second
	 * 			| this.setAttackCountDown(1)
	 * @effect	set orientation to face the other unit
	 * 			| this.face(other.getCubePosition)
	 * @effect	set status to attacking
	 * 			| this.setStatus(ATTACKING)
	 * @effect	let the other unit defend
	 * 			| other.defend(this)
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
	
	/**
	 * Return true if this units status is ATTACKING
	 */
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
	 * @effect 	If the new attackcountdown is zero, the new status is idle,
	 * 			experience is increased by 20
	 *       	| if (new.getAttackCountDown <= 0) 
	 *       	| 	then this.setStatus(IDLE)
	 *       	|		this.increaseExperience(20)
	 * 
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
			this.setStatus(UnitStatus.IDLE);
		} else
			this.setAttackCountDown(newAttackTime);
	}

	/**
	 * Variable registering the attackCountDown of this Unit.
	 */
	private double attackCountDown = 0;

	/**
	 * This Unit is attacked by the other Unit, and can take damage because of
	 * this
	 *
	 * @param 	other
	 *          The Unit attacking this Unit.
	 * @effect	Either the unit dodges, or the unit blocks the attack, or the unit takes damage
	 * 			when this unit blocks an attack, the experience will increase by 20
	 *         	| Either this.setStatus(DODGING)
	 *         	| Or 	this.increaseExperience(20)
	 *          | Or	this.takeDamage(other.getStrength/10)
	 * @effect 	This Unit will face the attacking unit 
	 * 			| this.face(other)
	 * @post 	the status of this unit is DEFENDING 
	 * 			| new.getStatus == UnitStatus.DEFENDING
	 * @throws 	IllegalArgumentException
	 *          This Unit cannot be attacked by the other Unit
	 *          | ! other.canAttack(this)
	 */
	private void defend(Unit other) throws IllegalArgumentException {
		if (!other.canAttack(this))
			throw new IllegalArgumentException("This Unit can not be attacked by the other unit");
		
		this.interruptTask();
		
		this.setStatus(UnitStatus.DEFENDING);
		this.face(other.getCubePosition());

		if (this.dodgeSuccesfull(other)) {
			this.setStatus(UnitStatus.DODGING);
			
			// this.setStatus(UnitStatus.IDLE); 
			return;
		}

		if (this.blockChance(other)) {
			this.increaseExperience(20);
			// this.setStatus(UnitStatus.IDLE);
			return;
		}
		int damage = other.getStrength() / 10;
		this.takeDamage(damage);
	}

	/**
	 * Will return true if this unit succesfully dodges the attack by the other
	 * unit
	 * 
	 * @param 	other
	 *          The Unit attacking this Unit
	 * @return 	| result == (RandomNumberBetween0And1 <= (0.2d*this.getAgility())/other.getAgility())
	 */
	private boolean dodgeSuccesfull(Unit other) {
		double dodgeChance = (0.2d * this.getAgility()) / other.getAgility();
		return (Util.fuzzyLessThanOrEqualTo(new Random().nextDouble(), dodgeChance));
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
		return (Util.fuzzyLessThanOrEqualTo(new Random().nextDouble(), blockChance));

	}

	/** 
	 * Initiate the rest status for this unit.
	 *
	 * @post 	The status of this Unit is 'resting' 
	 * 			| new.getStatus() == Unitstatus.RESTING
	 * @post 	The restTime of this Unit is 0 
	 * 			| new.getRestTime() == 0
	 * @throws 	IllegalStateException
	 * 			this unit is currently attacking,  defending or falling
	 * 			| this.isAttacking || this.getStatus == DEFENDING || this.isFalling
	 * 			
	 */
	public void rest() {
		if ((this.isAttacking())||
				(this.getStatus() == UnitStatus.DEFENDING)||
				(this.getStatus() == UnitStatus.DODGING) ||
				(this.isFalling()))
			throw new IllegalStateException("Can't rest now");
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
	 * @post 	if this unit has maximum hp, does not have maximum stamina, and the
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
		if (this.getMaxHP() >= this.getHP()) {
			if (Util.fuzzyGreaterThanOrEqualTo(newTime, 0)) {
				this.setRestTime(newTime);
				this.setHP(this.getHP() + 1);
			}
			return;
		}
		// check for stamina
		else if (this.getStamina() <= this.getMaxStamina()) {
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

	/**
	 * update all timers and properties of this unit
	 */
	public void advanceTime(double deltaT) {
		if(!isValidTime(deltaT))
			throw new IllegalArgumentException("not a valid time");
		if(!this.isFollowing())
			this.followUnit = null;
		
		if(this.getTask() != null && this.hasFinishedAction() && !this.isTerminated){
			this.getTask().advanceTime(deltaT);
		}
		
		// Check if unit stands on solid ground
		int[] belowPosition = this.getCubePositionBelow();
		
		
		// check all status
		if (this.isFalling()){
			this.updateFall(deltaT);
			if(this.isTerminated){
				return;
			}
		}
		else if(this.getWorld().isPassableTerrain(belowPosition)&&
				!this.getWorld().isNeighbouringSolid(this.getCubePosition())){
			this.startFall();
			this.interruptTask();
			this.setStatus(UnitStatus.FALLING);
		}
		else if (this.getStatus() == UnitStatus.DODGING) {
			this.dodge();
			if(this.isTerminated){
				return;
			}
		}
		else if (this.getStatus() == UnitStatus.DEFENDING) {
			this.setStatus(UnitStatus.IDLE);
			if(this.isTerminated){
				return;
			}
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
			if(this.isSprinting())
				this.updateSprint(deltaT);
			else if(this.isFollowing()){
				try{
					this.follow();
				}
				catch (Exception e){
					this.setStatus(UnitStatus.IDLE);
					this.followUnit = null;
				}
			}
			this.updatePosition(deltaT);
		}
		if(this.isTerminated){
			return;
		}

		if (Util.fuzzyGreaterThanOrEqualTo(this.rest3MinTime, 3 * 60) 
				&& (this.getStatus() != UnitStatus.DEFENDING) 
				&& (!this.isFalling())) {
			this.rest3MinTime = 0;
			this.rest();
			this.interruptTask();
		} 
		else {
			this.rest3MinTime = this.rest3MinTime + deltaT;
		}

		if(this.getStatus() == UnitStatus.IDLE && !this.hasFinishedAction()){
			this.hasFinishedAction = true;
			return;
		}
		
		if(this.getStatus() == UnitStatus.IDLE){
			Scheduler scheduler = this.getFaction().getScheduler();
			scheduler.assignNextTaskTo(this);
		}
		
		if ((this.getStatus() == UnitStatus.IDLE) && (this.getDefaultBoolean() == true)) {
			this.defaultBehaviour();
		}
		
	}

	/**
	 * Check if the unit can still sprint
	 * 
	 * @param 	deltaT
	 * 			The time to advance
	 * @throws 	IllegalArgumentException
	 * 			The given time is not a valid time
	 * 			| !isValidTime(deltaT)
	 * @post	The stamina of this Unit is reduced
	 * 			by 1 for every 0.1 seconds this unit
	 * 			is sprinting.
	 * 			| while ((this.getStamina() > 0) && (this.sprintTime > 0.1))
	 *			|	this.sprintTime -= 0.1;
	 *			|	this.setStamina(this.getStamina()-1);
	 * @effect	If the new Stamina is 0, this unit starts
	 * 			walking, and the moveTo method is called again
	 * 			| if new.getStamina == 0 then
	 * 			| this.setStatus(WALKING)
	 * 			| this.moveTo(this.getFinalDestination)
	 * 			| this.sprintTime = 0
	 * @effect	The position is updated
	 * 			| this.updatePosition(deltaT)
	 */
	private void updateSprint(double deltaT) {
		if(!isValidTime(deltaT))
			throw new IllegalArgumentException();
		
		this.sprintTime += deltaT;
		while ((this.getStamina() > 0) && (this.sprintTime > 0.1)){
			this.sprintTime -= 0.1;
			this.setStamina(this.getStamina()-1);
		}
		
		if (this.getStamina() == 0){
			this.setStatus(UnitStatus.WALKING);
			this.moveTo(this.getFinalDestination());
			
			this.sprintTime = 0;
		}
		
		this.updatePosition(deltaT);

	}
	
	/**
	 * Variable registering the amount of time since the last
	 * rest
	 */
	private double rest3MinTime;
	
	/**
	 * Variable registering the amount of time left in this sprint
	 */
	private double sprintTime;

	/**
	 * Check whether the given time is valid for any Unit
	 *
	 * @param 	time
	 *          The time to check.
	 * @return 	| result == ((time > 0) && (time < 0.2))
	 */
	public static boolean isValidTime(double time) {
		return Util.fuzzyGreaterThanOrEqualTo(time, 0) &&
				Util.fuzzyLessThanOrEqualTo(time, 0.2);
	}


	/** 
	 * Terminate this Unit.
	 *
	 * @post 	This Unit is terminated. 
	 * 			| new.isTerminated()
	 * @effect	This unit is scheduled for removal
	 * 			| this.getWorld.scheduleToRemove(this)
	 * @post	This unit status is idle
	 * 			| new.getStatus == IDLE
	 * @post	this unit has no faction
	 * 			| new.getFaction == null
	 * @effect 	this unit is not present in faction
	 * 			| this.getFaction.removeUnit(this)
	 * 
	 */
	public void terminate() {
		this.isTerminated = true;
		this.getWorld().scheduleToRemove(this);
		this.dropItem();
		this.setStatus(UnitStatus.IDLE);
		
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
	}

	/**
	 * The Unit is no longer in default behavior mode
	 * 
	 * @post 	the defaultBoolean is false 
	 * 			| new.getDefaultBoolean == false
	 */
	public void stopDefaultBehaviour() {
		this.setDefaultBoolean(false);
	}

	/**
	 * Determine the next behaviour
	 * 
	 * @throws 	IllegalStateException
	 *          The unit is not in defaultbehaviour 
	 *          | ! this.getDefaultBoolean()
	 * @effect	There is a 1/4 chance the Unit will work on a 
	 * 			(neighbouring) cube.
	 * 			| this.workAt(neighbouringCube)
	 * 			| where this.isNeighbouringCube(neighbouringCube)
	 * @effect 	There is a 1/4 chance the Unit will rest
	 * 			| this.rest
	 * @effect	There is a 1/4 chance the Unit will follow an enemy 
	 * 			Unit and attack him once.
	 * 			| this.follow(enemy)
	 * 			| where enemy.getFaction != this.getFaction
	 * @effect	There is a 1/4 chance the Unit will move to a random 
	 * 			(valid) position in this world
	 * 			| this.moveToRandom()
	 * 			If this occurs, there is a 1/2 chance the unit will
	 * 			start sprinting
	 * 			| this.startSprint()
	 */
	private void defaultBehaviour() throws IllegalStateException {
		if (!this.getDefaultBoolean())
			throw new IllegalStateException();

		double chance = new Random().nextDouble();
		SourceLocation loc = new SourceLocation(0, 0);
		
		if (Util.fuzzyLessThanOrEqualTo(chance, 0.25d)) {
			MyExpression<int[]> here = new HerePosition(loc);
			MyExpression<int[]> expre = new NextToPosition(here, loc);
			Action<int[]> statement = new Work(expre, loc);
			statement.execute(this.getWorld(), this);
		} else if (Util.fuzzyLessThanOrEqualTo(chance, 0.50d)) {
			this.rest();
		} else if (Util.fuzzyLessThanOrEqualTo(chance, 0.75d)) {
			MyExpression<Unit> expr = new Enemy(loc);
			Action<Unit> follow = new Follow(expr, loc);
			follow.execute(this.getWorld(), this);
		} else {
			this.moveToRandom();
			if(Util.fuzzyLessThanOrEqualTo(chance, 0.875d) && (this.getStamina()>1))
				this.startSprint();
		}
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
	 * Set the defaultBoolean of this Unit to the given defaultBoolean.
	 * 
	 * @param 	defaultBoolean
	 *          The new defaultBoolean for this Unit.
	 * @post 	The defaultBoolean of this new Unit is equal to the given
	 *       	defaultBoolean. 
	 *       	| new.getDefaultBoolean() == defaultBoolean
	 */
	@Raw
	private void setDefaultBoolean(boolean defaultBoolean) throws IllegalArgumentException {
		this.defaultBoolean = defaultBoolean;
	}

	/**
	 * Variable registering the defaultBoolean of this Unit.
	 */

	private boolean defaultBoolean = false;

	/**
	 * Return the Faction of this Unit.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}

	/**
	 * Check whether the given Faction is a valid Faction for
	 * any Unit.
	 *  
	 * @param  Faction
	 *         The Faction to check.
	 * @return 
	 *       | result == (faction.getNbUnits <= max_NB_Units_in_Faction)
	 *       |			|| (faction == null)
	*/
	public static boolean isValidFaction(Faction faction) {
		if(faction == null)
			return true;
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
	public void setFaction(Faction faction) throws IllegalArgumentException {
		if (! isValidFaction(faction))
			throw new IllegalArgumentException("not a valid faction");
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

	/** 
	 * Check whether the given Experience is a valid Experience for
	 * any Unit.
	 *  
	 * @param  Experience
	 *         The Experience to check.
	 * @return 
	 *       | result == (experience >= 0)
	*/
	private static boolean isValidExperience(int experience) {
		return experience >= 0;
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
	private void setExperience(int experience) throws IllegalArgumentException {
		if (! isValidExperience(experience))
			throw new IllegalArgumentException();
		this.experience = experience;
	}
	
	/**
	 * Increase the experince by the given amount. If the 
	 * total experience is more then 10, increase a random attribute
	 * for every 10 experience points this unit has.
	 * 
	 * @param amount
	 * 		  The amount of experience this unit gains.
	 * @effect If all attributes have their maximum value,
	 * 		   do nothing and return
	 * 		   | if ((this.getAgility() == 200)&&
	 * 		   |		(this.getStrength()==200)&&(this.getToughness()==200))
	 *		   |	return;
	 *		   Else, the following things apply: 
	 * @post   For every 10 experience left, increase a random attribute by one,
	 * 		   but never more then the maximum value.
	 * 		   | this.getAgility + this.getStrength + 
	 * 		   | 	this.getToughness + (amount + this.getExperience)/10
	 * 		   | ==
	 * 		   | new.getAgility + new.getStrength + new.getToughness
	 * @post   The new Experience of this unit, is the total experience modulo 10
	 * 		   | new.getExperience = (this.getExperience + amount) % 10
	 * @post   The weight is a valid weigth, if it became to small due to the 
	 * 		   increase of the strength and agility
	 * 	 	   | isValidWeight(new.getWeight)
	 * @throws IllegalArgumentException
	 * 		   The amount can not be negative
	 * 		   | amount < 0	
	 */
	private void increaseExperience(int amount) throws IllegalArgumentException{
		if(amount < 0)
			throw new IllegalArgumentException("can't give negative experience!");
		// if all atributes have the maximum value, do nothing
		if ((this.getAgility() == 200)&&(this.getStrength()==200)&&(this.getToughness()==200))
			return;
		int counter = 0;
		int newExperience = this.getExperience() + amount;
		while((newExperience >= 10) && (counter < 50)){
			newExperience = newExperience - 10;
			int randomInt = new Random().nextInt(3);
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
				counter += 1;
				newExperience = newExperience + 10;
			}
		}
		
		if (!this.isValidWeight(this.getWeight()))
			this.setWeight(-1);
		this.setExperience(newExperience % 10);
	}

	/**
	 * Variable registering the Experience of this Unit.
	 */
	private int experience = 0;
	
	/**
	 * Interrupt the task currently executed by this unit
	 * 
	 * @effect Interrupt if currently executing a Task
	 * 		   | if this.getTask != null
	 * 		   | 	do this.getTask.interrupt
	 */
	public void interruptTask(){
		if(this.getTask() != null)
			this.getTask().interrupt();
	}

	/**
	 * Return the Task of this Unit.
	 */
	@Basic @Raw
	public Task getTask() {
		return this.task;
	}

	/**
	 * Check whether the given Task is a valid Task for
	 * any Unit.
	 *  
	 * @param  Task
	 *         The Task to check.
	 * @return 
	 *       | result == true if (task == null) || (task.getUnit() == null) 
	*/
	private static boolean isValidTask(Task task) {
		if(task == null)
			return true;
		else
			return (task.getUnit() == null);
	}

	/**
	 * Set the Task of this Unit to the given Task.
	 * 
	 * @param  task
	 *         The new Task for this Unit.
	 * @post   The Task of this new Unit is equal to
	 *         the given Task.
	 *       | new.getTask() == task
	 * @throws IllegalArgumentException
	 *         The given Task is not a valid Task for any
	 *         Unit.
	 *       | ! isValidTask(getTask())
	 */
	@Raw
	public void setTask(Task task) throws IllegalArgumentException {
		if (! isValidTask(task))
			throw new IllegalArgumentException();
		this.task = task;
	}

	/**
	 * Variable registering the Task of this Unit.
	 */
	private Task task;
	
	/**
	 * Set the hasFinishedAction variable to false
	 * 
	 * @post this unit has not finished the action
	 * 		 | this.hasFinishedAction() == false;
	 */
	public void startAction(){
		this.hasFinishedAction = false;
	}
	
	/**
	 * Return true if this unit has finished an action,
	 */
	public boolean hasFinishedAction(){
		return this.hasFinishedAction;
	}
	/**
	 * variable registering whether the action of the task has finished.
	 */
	private boolean hasFinishedAction = true;
	
	
}
