package hillbillies.part3.programs.expression;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public abstract class MyExpression {
	
	public MyExpression(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
}
