package hillbillies.part3.programs.expression.logic;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;


/**
 * A class of isPassable expressions
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class IsPassable extends MyExpression<Boolean> {

	/**
	 * @param sourceLocation
	 */
	public IsPassable(MyExpression<int[]> expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		position = expression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Boolean evaluateExpression(Unit unit) {
		return unit.getWorld().isPassableTerrain(position.evaluateExpression(unit));
	}
	
	private static MyExpression<int[]> position;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "IsPassable: " + this.evaluateExpression(unit);
	}
}
