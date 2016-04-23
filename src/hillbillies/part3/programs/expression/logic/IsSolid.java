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
public class IsSolid extends MyExpression {

	/**
	 * @param sourceLocation
	 */
	public IsSolid(MyExpression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		position = (Position)expression;
		
	}

	public boolean get(World world, Unit unit) {
		// TODO Auto-generated method stub
		return world.isPassableTerrain(position.getPosition(world, unit));
	}
	
	private static Position position;

}
