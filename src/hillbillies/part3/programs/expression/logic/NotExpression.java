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
public class NotExpression extends MyExpression<Boolean> {

	/**
	 * @param sourceLocation
	 */
	public NotExpression(MyExpression<Boolean> expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		booleanExpression = expression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Boolean evaluateExpression(Unit unit) {
		return !booleanExpression.evaluateExpression(unit);
	}
	
	private static MyExpression<Boolean> booleanExpression;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "Not: !" + this.evaluateExpression(unit);
	}

}
