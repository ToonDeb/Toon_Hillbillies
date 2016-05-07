package hillbillies.part3.programs.expression.logic;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;


/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class TrueExpression extends MyExpression<Boolean> {

	/**
	 * @param sourceLocation
	 */
	public TrueExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	public Boolean evaluateExpression(Unit unit){
		return true;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "True";
	}

}
