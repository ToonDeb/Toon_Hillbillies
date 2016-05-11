package hillbillies.part3.programs.expression;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.statement.Assignment;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
//@SuppressWarnings("rawtypes")
public class ReadVariable<T> extends MyExpression<T>{

	/**
	 * @param sourceLocation
	 */
	public ReadVariable(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		name = variableName;
	}
	
	public T evaluateExpression(Unit unit){
		return (T) unit.getTask().getAssignment(name).getEvaluatedExpression(unit);
	}
	
	private static String name;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "readVariable: name = "+ name +", variable = "+ this.evaluateExpression(unit);
	}

}
