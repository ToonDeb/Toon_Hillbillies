package hillbillies.part3.programs.statement.action;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class Work extends Action<int[]> {

	/**
	 * @param expression
	 * @param sourceLocation
	 */
	public Work(MyExpression<int[]> expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.Action#execute(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public void execute(World world, Unit unit) throws IllegalArgumentException{
		unit.workAt(this.getExpression().evaluateExpression(unit));
	}

}