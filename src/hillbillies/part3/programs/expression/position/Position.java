package hillbillies.part3.programs.expression.position;

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
public abstract class Position extends MyExpression {

	/**
	 * @param sourceLocation
	 */
	public Position(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	public abstract int[] getPosition(World world, Unit unit);

}
