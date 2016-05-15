package hillbillies.part3.programs.expression;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
//@SuppressWarnings("rawtypes")
public abstract class ReadVariable<T> extends MyExpression<T>{

	/**
	 * @param sourceLocation
	 */
	public ReadVariable(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		name = variableName;
	}
	
	//public abstract T evaluateExpression(Unit unit);
	
	public String getName(){
		return name;
	}
	
	private static String name;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "readVariable: name = "+ name +", variable = "+ this.evaluateExpression(unit);
	}
	
	@SuppressWarnings("unchecked")
	public T evaluateExpression(Unit unit) {
		if (this.getEvaluatedExpression() == null)
			this.setEvaluatedExpression((T) unit.getTask().getAssignment(this.getName()).getExpression().evaluateExpression(unit));
		System.out.print("evaluates to: ");
		System.out.println(this.getEvaluatedExpression());
		return this.getEvaluatedExpression();
	}
	
	private T evaluatedExpression = null;
	
	public T getEvaluatedExpression(){
		return this.evaluatedExpression;
	}

	public void setEvaluatedExpression(T expression){
		this.evaluatedExpression = expression;
	}
}
