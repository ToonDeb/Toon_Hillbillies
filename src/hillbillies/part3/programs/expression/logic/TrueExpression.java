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
public class TrueExpression extends BooleanExpression {

	/**
	 * @param sourceLocation
	 */
	public TrueExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}
	
	public boolean get(World world, Unit unit){
		return true;
	}

}