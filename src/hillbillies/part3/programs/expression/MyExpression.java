package hillbillies.part3.programs.expression;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
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
