package hillbillies.part3.programs.statement;


import java.util.NoSuchElementException;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value @SuppressWarnings("rawtypes")
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
	
	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.MyStatement#iterator()
	 */
	@Override
	public StatementIterator iterator(World world, Unit unit) {
		return new StatementIterator() {

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public MyStatement next() {
				throw new NoSuchElementException("has no next elements");
			}
			
			public boolean isTerminal(){
				return true;
			}
		};
	}
}
