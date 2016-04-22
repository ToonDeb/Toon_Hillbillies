package hillbillies.part3.programs.expression;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public abstract class UNIT extends MyExpression {
	
	public UNIT(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public abstract Unit getUnit(World world, Unit unit);
}
