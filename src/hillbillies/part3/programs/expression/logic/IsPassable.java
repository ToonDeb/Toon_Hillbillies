package hillbillies.part3.programs.expression.logic;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.expression.position.Position;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class IsPassable extends BooleanExpression {

	/**
	 * @param sourceLocation
	 */
	public IsPassable(MyExpression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		position = (Position)expression;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public boolean get(World world, Unit unit) {
		// TODO Auto-generated method stub
		return world.isPassableTerrain(position.getPosition(world, unit));
	}
	
	private static Position position;
}
