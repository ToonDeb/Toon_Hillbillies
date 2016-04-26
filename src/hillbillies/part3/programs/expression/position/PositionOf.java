package hillbillies.part3.programs.expression.position;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.expression.unit.UnitExpression;

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
	public PositionOf(MyExpression unitExpression, SourceLocation sourceLocation) {
		super(sourceLocation);
		unit = (UnitExpression)unitExpression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.UnitPosition#getPosition(hillbillies.model.Unit)
	 */
	public int[] getPosition(World world, Unit thisUnit) {
		return unit.getUnit(world, thisUnit).getCubePosition();
	}
	
	private static UnitExpression unit;

}
