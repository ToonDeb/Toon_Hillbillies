package hillbillies.part3.programs.expression;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of MyExpressions
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public abstract class MyExpression<T> {

	public MyExpression(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation(){
		return this.sourceLocation;
	}
	
	public abstract T evaluateExpression(Unit unit);
	
	public abstract String toString(Unit unit);
	
	private final SourceLocation sourceLocation;
}
