package hillbillies.part3.programs.expression.unit;

import java.util.Random;

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
public class Friend extends UnitExpression {

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
//		int random = new Random().nextInt(friendlyFaction.getNbUnits());
//		for (Unit friendlyUnit: friendlyFaction.getUnitsOfFaction()){
//			if(friendlyUnit != unit)
//				return friendlyUnit;
//		}
		
		int random = new Random().nextInt(friendlyFaction.getNbUnits());
		int i = -1;
		for (Unit friendlyUnit: friendlyFaction.getUnitsOfFaction()){
			i += 1;
			if (i == random){
				return friendlyUnit;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "FriendUnit: " + this.getUnit(unit.getWorld(), unit).getName();
	}



}
