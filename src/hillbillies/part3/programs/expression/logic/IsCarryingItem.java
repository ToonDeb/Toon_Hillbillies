package hillbillies.part3.programs.expression.logic;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.expression.unit.UnitExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class IsCarryingItem extends BooleanExpression {

	/**
	 * 
	 * @param expression
	 * @param sourceLocation
	 */
	public IsCarryingItem(MyExpression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		unitExpression = (UnitExpression)expression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public boolean get(World world, Unit unit) {
		return unitExpression.getUnit(world, unit).isCarryingItem();
	}
	
	private static UnitExpression unitExpression;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString()
	 */
	@Override
	public String toString(Unit unit) {
		return "isCarryingItem: " + this.get(unit.getWorld(), unit);
	}

}
