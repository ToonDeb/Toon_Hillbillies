package hillbillies.part3.programs.expression;

import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class TrueExpression extends MyExpression {

	/**
	 * @param sourceLocation
	 */
	public TrueExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}
	
	public boolean get(){
		return true;
	}

}
