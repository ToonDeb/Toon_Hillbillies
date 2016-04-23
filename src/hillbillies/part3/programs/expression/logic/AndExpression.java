package hillbillies.part3.programs.expression.logic;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;
import javafx.beans.binding.MapExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class AndExpression extends BooleanExpression {

	/**
	 * @param sourceLocation
	 */
	public AndExpression(MyExpression leftExpression, MyExpression rightExpression, SourceLocation sourceLocation) {
		super(sourceLocation);
		left = (BooleanExpression)leftExpression;
		right = (BooleanExpression)rightExpression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public boolean get(World world, Unit unit) {
		return left.get(world, unit) && right.get(world, unit);
	}
	
	private static BooleanExpression left;
	private static BooleanExpression right;

}
