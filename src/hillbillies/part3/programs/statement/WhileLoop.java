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
public class WhileLoop extends MyStatement {
	
	public WhileLoop(MyExpression expression, MyStatement statement, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
		this.statement = statement;
	}
	
	public MyExpression getExpression(){
		return this.expression;
	}
	
	public MyStatement getStatement(){
		return this.statement;
	}
	private final MyExpression expression;
	private final MyStatement statement;
}
