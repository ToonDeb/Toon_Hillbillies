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
public class Attack extends Action<Unit> {

	/**
	 * @param expression
	 * @param sourceLocation
	 */
	public Attack(MyExpression<Unit> expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.Action#execute(hillbillies.model.Unit)
	 */
	@Override
	public void execute(World world, Unit unit) {
		unit.attack(this.getExpression().evaluateExpression(unit));
		
	}
	

}
