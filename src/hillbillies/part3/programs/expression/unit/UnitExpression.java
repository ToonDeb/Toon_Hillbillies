package hillbillies.part3.programs.expression.unit;

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
public abstract class UnitExpression extends MyExpression {
	
	public UnitExpression(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public abstract Unit getUnit(World world, Unit unit);
}
