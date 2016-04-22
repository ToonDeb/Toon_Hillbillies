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
public class IfThenElse extends MyStatement {
	
	public IfThenElse(MyExpression expression, MyStatement then, MyStatement other, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
		this.then = then;
		this.other = other;
	}
	
	public MyExpression getExpression(){
		return this.expression;
	}
	
	public MyStatement getThen(){
		return this.then;
	}
	
	public MyStatement getElse(){
		return this.other;
	}
	
	private final MyExpression expression;
	private final MyStatement then;
	private final MyStatement other;
	
}
