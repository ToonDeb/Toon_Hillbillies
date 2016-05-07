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
public class AndExpression extends MyExpression<Boolean> {

	/**
	 * @param sourceLocation
	 */
	public AndExpression(MyExpression<Boolean> leftExpression, MyExpression<Boolean> rightExpression, SourceLocation sourceLocation) {
		super(sourceLocation);
		left = leftExpression;
		right = rightExpression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Boolean evaluateExpression(Unit unit) {
		return left.evaluateExpression(unit) && right.evaluateExpression(unit);
	}
	
	
	
	private static MyExpression<Boolean> left;
	private static MyExpression<Boolean> right;
	
	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString()
	 */
	@Override
	public String toString(Unit unit) {
		return left.toString(unit) + " and " + right.toString(unit);
	}

}
