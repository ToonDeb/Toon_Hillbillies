package hillbillies.part3.programs.statement.action;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of moveTo statements
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class MoveTo extends Action<int[]> {

	/**
	 * @param expression
	 * @param sourceLocation
	 */
	public MoveTo(MyExpression<int[]> expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.Action#execute(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public void execute(World world, Unit unit) throws IllegalArgumentException{
		unit.moveTo(this.getExpression().evaluateExpression(unit));
	}


}
