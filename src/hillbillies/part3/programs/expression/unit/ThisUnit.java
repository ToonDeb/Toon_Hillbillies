package hillbillies.part3.programs.expression.unit;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class ThisUnit extends UNIT {

	/**
	 * @param sourceLocation
	 */
	public ThisUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.unit.UNIT#getUnit(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Unit getUnit(World world, Unit unit) {
		return unit;
	}

}
