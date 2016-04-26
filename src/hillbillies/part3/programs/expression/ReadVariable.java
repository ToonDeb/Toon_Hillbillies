package hillbillies.part3.programs.expression;

import hillbillies.model.Task;
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
	
	public MyExpression getExpression(Task task){
		return task.getExpression(name);
	}
	
	private static String name;

}
