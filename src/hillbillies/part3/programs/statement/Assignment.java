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
public class Assignment extends MyStatement {
	
	public Assignment(String variableName, MyExpression expression, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
		this.variableName = variableName;
	}
	
	public MyExpression getExpression(){
		return this.expression;
	}
	
	public String getVariableName(){
		return this.variableName;
	}

	private final MyExpression expression;
	private final String variableName;
}
