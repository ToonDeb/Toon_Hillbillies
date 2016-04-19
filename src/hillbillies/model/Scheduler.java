package hillbillies.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Schedulers
 * 
 * @invar  The faction of each Scheduler must be a valid faction for any
 *         Scheduler.
 *       | isValidFaction(getFaction())
 *
 *
 * @author  ...
 * @version 1.0
 */
public class Scheduler {

	/**
	 * Initialize this new Scheduler with given faction.
	 *
	 * @param  faction
	 *         The faction for this new Scheduler.
	 * @effect The faction of this new Scheduler is set to
	 *         the given faction.
	 *       | this.setFaction(faction)
	 */
	public Scheduler(Faction faction)
			throws IllegalArgumentException {
		this.setFaction(faction);
	}


	/**
	 * Return the faction of this Scheduler.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}

	/**
	 * Check whether the given faction is a valid faction for
	 * any Scheduler.
	 *  
	 * @param  faction
	 *         The faction to check.
	 * @return 
	 *       | result == (faction != null)
	*/
	public static boolean isValidFaction(Faction faction) {
		return (faction != null);
	}
	
	/**
	 * Set the faction of this Scheduler to the given faction.
	 * 
	 * @param  faction
	 *         The new faction for this Scheduler.
	 * @post   The faction of this new Scheduler is equal to
	 *         the given faction.
	 *       | new.getFaction() == faction
	 * @throws IllegalArgumentException
	 *         The given faction is not a valid faction for any
	 *         Scheduler.
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
	 * Variable registering the faction of this Scheduler.
	 */
	private Faction faction;
	

}
