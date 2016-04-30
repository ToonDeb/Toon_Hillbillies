package hillbillies.part3.programs.expression.logic;

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
public class OrExpression extends BooleanExpression {

	/**
	 * 
	 * @param leftExpression
	 * @param rightExpression
	 * @param sourceLocation
	 */
	public OrExpression(MyExpression leftExpression, MyExpression rightExpression, SourceLocation sourceLocation) {
		super(sourceLocation);
		left = (BooleanExpression)leftExpression;
		right = (BooleanExpression)rightExpression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public boolean get(World world, Unit unit) {
		return left.get(world, unit) || right.get(world, unit);
	}
	
	private static BooleanExpression left;
	private static BooleanExpression right;
	
	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return left.toString(unit) + " Or " + right.toString(unit);
	}
}
