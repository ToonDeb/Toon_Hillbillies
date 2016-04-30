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
public class PositionOf extends PositionExpression {

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

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.getPosition(unit.getWorld(), unit);
		return "PositionOf: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}

}
