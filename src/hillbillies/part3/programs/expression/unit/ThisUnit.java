package hillbillies.part3.programs.expression.unit;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class ThisUnit extends MyExpression<Unit> {

	/**
	 * @param sourceLocation
	 */
	public ThisUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.unit.UnitExpression#getUnit(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Unit evaluateExpression(Unit unit) {
		return unit;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "ThisUnit: " + this.evaluateExpression(unit).getName();
	}

}
