package hillbillies.part3.programs.expression.logic;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class IsSolid extends MyExpression<Boolean> {

	/**
	 * @param sourceLocation
	 */
	public IsSolid(MyExpression<int[]> expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		position = expression;
		
	}

	public Boolean evaluateExpression(Unit unit) {
		return !unit.getWorld().isPassableTerrain(position.evaluateExpression(unit));
	}
	
	private static MyExpression<int[]> position;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "IsSolid: " + this.evaluateExpression(unit);
	}

}
