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
public class NotExpression extends BooleanExpression {

	/**
	 * @param sourceLocation
	 */
	public NotExpression(MyExpression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		booleanExpression = (BooleanExpression)expression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public boolean get(World world, Unit unit) {
		return !booleanExpression.get(world, unit);
	}
	
	private static BooleanExpression booleanExpression;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "Not: !" + this.get(unit.getWorld(), unit);
	}

}
