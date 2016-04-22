package hillbillies.part3.programs.expression;

import java.util.Set;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class Friend extends UNIT {

	/**
	 * @param unit
	 * @param sourceLocation
	 */
	public Friend(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.UNIT#getUnit(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Unit getUnit(World world, Unit unit) {
		Faction friendlyFaction = unit.getFaction();
		for (Unit friendlyUnit: friendlyFaction.getUnitsOfFaction()){
			if(friendlyUnit != unit)
				return friendlyUnit;
		}
		return null;
	}



}
