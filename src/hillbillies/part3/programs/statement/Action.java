package hillbillies.part3.programs.statement;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public abstract class Action extends MyStatement {

	/**
	 * @param sourceLocation
	 */
	public Action(MyExpression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.expression = expression;
	}
	
	public MyExpression getExpression(){
		return this.expression;
	}
	
	private final MyExpression expression;
}
