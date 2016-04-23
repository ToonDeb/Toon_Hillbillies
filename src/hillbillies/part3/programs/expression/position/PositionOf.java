package hillbillies.part3.programs.expression.position;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.UNIT;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class PositionOf extends Position {

	/**
	 * @param sourceLocation
	 */
	public PositionOf(UNIT unitExpression, SourceLocation sourceLocation) {
		super(sourceLocation);
		unit = unitExpression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.UnitPosition#getPosition(hillbillies.model.Unit)
	 */
	public int[] getPosition(World world, Unit thisUnit) {
		return unit.getUnit(world, thisUnit).getCubePosition();
	}
	
	private static UNIT unit;

}
