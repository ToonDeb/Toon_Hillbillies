package hillbillies.part3.programs.expression;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public abstract class UnitPosition extends MyExpression {

	/**
	 * @param sourceLocation
	 */
	public UnitPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	public abstract int[] getPosition(Unit unit);

}
