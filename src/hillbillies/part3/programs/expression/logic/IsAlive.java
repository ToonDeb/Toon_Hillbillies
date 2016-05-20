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
public class IsAlive extends MyExpression<Boolean> {
	
	/**
	 * 
	 * @param expression
	 * @param sourceLocation
	 */
	public IsAlive(MyExpression<Unit> expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		unitExpression = expression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Boolean evaluateExpression(Unit unit) {
		System.out.println(unit);
		return !unitExpression.evaluateExpression(unit).isTerminated();
	}

	private static MyExpression<Unit> unitExpression;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString()
	 */
	@Override
	public String toString(Unit unit) {
		return "IsAlive: " + this.evaluateExpression(unit);
	}
}
