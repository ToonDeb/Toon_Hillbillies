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
public class FalseExpression extends MyExpression<Boolean> {

	/**
	 * @param sourceLocation
	 */
	public FalseExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	public Boolean evaluateExpression(Unit unit){
		return false;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString()
	 */
	@Override
	public String toString(Unit unit) {
		return "False";
	}

}
