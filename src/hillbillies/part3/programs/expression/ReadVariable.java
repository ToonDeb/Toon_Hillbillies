package hillbillies.part3.programs.expression;

import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class ReadVariable extends MyExpression {

	/**
	 * @param sourceLocation
	 */
	public ReadVariable(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		name = variableName;
	}
	
	public MyExpression getExpression(){
		return ;
	}
	
	private static String name;

}
