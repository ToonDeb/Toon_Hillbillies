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
@Value
public class Assignment<T> extends MyStatement {
	
	
	public Assignment(String variableName, MyExpression<T> expression, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
		this.variableName = variableName;
	}
	
	public MyExpression<T> getExpression(){
		return this.expression;
	}
	
//	public void setEvaluatedExpression(T evaluatedExpression){
//		this.evaluatedExpression = evaluatedExpression;
//	}
	
	public T getEvaluatedExpression(Unit unit){
		if (this.evaluatedExpression == null)
			this.evaluatedExpression = this.getExpression().evaluateExpression(unit);
		
		return this.evaluatedExpression;
		
	}
	
	public String getVariableName(){
		return this.variableName;
	}
	
	private T evaluatedExpression;
	private final MyExpression<T> expression;
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
