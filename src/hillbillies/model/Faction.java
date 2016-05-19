package hillbillies.model;

import java.util.*;
import be.kuleuven.cs.som.annotate.*;
import static hillbillies.model.Constants.MAX_NB_ACTIVE_FACTIONS;

/**
 * A class of Factions
 * 
 * @invar  The World of each Faction must be a valid World for any
 *         Faction.
 *       | isValidWorld(getWorld())
 *       
 * @invar  The Units of each Faction must be valid Units.
 * 		 | this.hasProperUnits()
 * 
 * @invar  A Faction always has a Scheduler
 * 		 | this.getScheduler != null
 * 		   
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class Faction {

	/**
	 * Initialize this new Faction with given World.
	 *
	 * @param  world
	 *         The World for this new Faction.
	 * @effect The World of this new Faction is set to
	 *         the given World.
	 *       | this.setWorld(world)
	 * @throws IllegalArgumentException
	 * 		   The given world is not a valid world
	 * 		 | !this.isValidWorld(world)
	 */
	public Faction(World world) throws IllegalArgumentException {
		if(!this.isValidWorld(world))
			throw new IllegalArgumentException("not a valid world for this faction");
		this.setWorld(world);
	}


	/**
	 * Return the World of this Faction.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}

	/**
	 * Check whether the given World is a valid World for
	 * any Faction.
	 */
	public boolean isValidWorld(World world) {
		if (this.getNbUnits() == 0){
			return true;
		}
		else{
			return (world.getNbActiveFactions() < MAX_NB_ACTIVE_FACTIONS);
		}
		
	}
	
	/**
	 * Set the World of this Faction to the given World.
	 * 
	 * @param  world
	 *         The new World for this Faction.
	 * @post   The World of this new Faction is equal to
	 *         the given World.
	 *       | new.getWorld() == world
	 * @throws IllegalArgumentException
	 *         The given World is not a valid World for any
	 *         Faction.
	 *       | ! isValidWorld(getWorld())
	 */
	@Raw
	public void setWorld(World world) throws IllegalArgumentException {
		if (! isValidWorld(world))
			throw new IllegalArgumentException();
		this.world = world;
	}

	/**
	 * Variable registering the World of this Faction.
	 */
	private World world;
	

	/**
	 * Check whether this Faction has the given Unit as one of its
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
	 * Check whether this Faction can have the given Unit
	 * as one of its Units.
	 * 
	 * @param  unit
	 *         The Unit to check.
	 * @return True if and only if the given Unit is effective
	 *         and that Unit is a valid Unit for a Faction.
	 *       | result ==
	 *       |   (unit != null) &&
	 *       |   Unit.isValidFaction(this)
	 */
	@Raw
	public boolean canHaveAsUnit(Unit unit) {
		return (unit != null) && (Unit.isValidFaction(this));
	}

	/**
	 * Check whether this Faction has proper Units attached to it.
	 * 
	 * @return True if and only if this Faction can have each of the
	 *         Units attached to it as one of its Units,
	 *         and if each of these Units references this Faction as
	 *         the Faction to which they are attached.
	 *       | for each unit in Unit:
	 *       |   if (hasAsUnit(unit))
	 *       |     then canHaveAsUnit(unit) &&
	 *       |          (unit.getFaction() == this)
	 */
	public boolean hasProperUnits() {
		for (Unit unit : units) {
			if (!canHaveAsUnit(unit))
				return false;
			if (unit.getFaction() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of Units associated with this Faction.
	 *
	 * @return  The total number of Units collected in this Faction.
	 *        | result ==
	 *        |   card({unit:Unit | hasAsUnit({unit)})
	 */
	public int getNbUnits() {
		return units.size();
	}

	/**
	 * Add the given Unit to the set of Units of this Faction.
	 * 
	 * @param  unit
	 *         The Unit to be added.
	 * @pre    The given Unit is effective and already references
	 *         this Faction.
	 *       | (unit != null) && (unit.getFaction() == this)
	 * @post   This Faction has the given Unit as one of its Units.
	 *       | new.hasAsUnit(unit)
	 */
	public void addUnit(@Raw Unit unit) {
		assert (unit != null) && (unit.getFaction() == this);
		units.add(unit);
	}

	/**
	 * Remove the given Unit from the set of Units of this Faction.
	 * 
	 * @param  unit
	 *         The Unit to be removed.
	 * @throws IllegalArgumentException
	 * 	       This Faction has the given Unit as one of
	 *         its Units, and the given Unit does not
	 *         reference any Faction. Else, throw exception.
	 *       | !this.hasAsUnit(unit)
	 * @post   This Faction no longer has the given Unit as
	 *         one of its Units.
	 *       | ! new.hasAsUnit(unit)
	 */
	@Raw
	public void removeUnit(Unit unit) {
		if(!this.hasAsUnit(unit) )
			throw new IllegalArgumentException("unit can't be removed");
		units.remove(unit);
	}
	
	/**
	 * return the set of all units belonging to this faction
	 */
	public Set<Unit> getUnitsOfFaction(){
		Set<Unit> allUnits = new HashSet<Unit>();
		allUnits.addAll(this.units);
		return allUnits;
	}
	
	/**
	 * Variable referencing a set collecting all the Units
	 * of this Faction.
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
	 * Return the Scheduler of this Faction.
	 */
	@Basic @Raw
	public Scheduler getScheduler() {
		return this.scheduler;
	}

	/**
	 * Variable registering the Scheduler of this Faction.
	 */
	private Scheduler scheduler = new Scheduler(this);

}
