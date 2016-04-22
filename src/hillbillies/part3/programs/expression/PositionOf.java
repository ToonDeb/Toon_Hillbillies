package hillbillies.part3.programs.expression;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class PositionOf extends MyExpression {

	/**
	 * @param sourceLocation
	 */
	public PositionOf(UNIT unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.unit = unit;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.UnitPosition#getPosition(hillbillies.model.Unit)
	 */
	public int[] getPosition(World world, Unit thisUnit) {
		// TODO Auto-generated method stub
		return unit.getUnit(world, thisUnit).getCubePosition();
	}
	
	private static UNIT unit;

}
