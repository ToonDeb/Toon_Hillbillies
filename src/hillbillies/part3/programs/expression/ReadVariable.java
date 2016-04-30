package hillbillies.part3.programs.expression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
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

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "readVariable: name = "+ name +", variable = "+ this.getExpression(unit.getTask());
	}

}
