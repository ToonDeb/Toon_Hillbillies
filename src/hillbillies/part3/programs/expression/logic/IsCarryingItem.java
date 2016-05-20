package hillbillies.part3.programs.expression.logic;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of IsCarryingItem expressions
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class IsCarryingItem extends MyExpression<Boolean> {

	/**
	 * 
	 * @param expression
	 * @param sourceLocation
	 */
	public IsCarryingItem(MyExpression<Unit> expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		unitExpression = expression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Boolean evaluateExpression(Unit unit) {
		return unitExpression.evaluateExpression(unit).isCarryingItem();
	}
	
	private static MyExpression<Unit> unitExpression;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString()
	 */
	@Override
	public String toString(Unit unit) {
		return "isCarryingItem: " + this.evaluateExpression(unit);
	}

}
