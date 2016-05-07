package hillbillies.part3.programs.expression.position;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class PositionOf extends MyExpression<int[]> {

	/**
	 * @param sourceLocation
	 */
	public PositionOf(MyExpression<Unit> unitExpression, SourceLocation sourceLocation) {
		super(sourceLocation);
		unit = unitExpression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.UnitPosition#getPosition(hillbillies.model.Unit)
	 */
	public int[] evaluateExpression(Unit thisUnit) {
		return unit.evaluateExpression(thisUnit).getCubePosition();
	}
	
	private static MyExpression<Unit> unit;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.evaluateExpression(unit);
		return "PositionOf: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}

}
