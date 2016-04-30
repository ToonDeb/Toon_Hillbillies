package hillbillies.part3.programs.statement;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.expression.position.PositionExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class MoveTo extends Action {

	/**
	 * @param expression
	 * @param sourceLocation
	 */
	public MoveTo(MyExpression expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.Action#execute(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public void execute(World world, Unit unit) throws IllegalArgumentException{
		if(!(this.getExpression() instanceof PositionExpression))
			throw new IllegalArgumentException("Not a positionexpression!");
		unit.moveTo(((PositionExpression)this.getExpression()).getPosition(world, unit));
	}


}
