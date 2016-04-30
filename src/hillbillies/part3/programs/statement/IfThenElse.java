package hillbillies.part3.programs.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.expression.logic.BooleanExpression;

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
		if(other == null)
			this.other = new NullStatement(sourceLocation);
		else
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
	
	public MyStatement evaluate(World world, Unit unit){
		MyExpression expression = this.getExpression();
		if(expression instanceof BooleanExpression){
			if (((BooleanExpression)expression).get(world, unit)){
				return this.getThen();
			}
			else{
				return this.getElse();
			}
		}
		else{
			throw new IllegalArgumentException("the expression is not a booleanexpression!");
		}
	}
	
	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.MyStatement#iterator()
	 */
	@Override
	public StatementIterator iterator(World world, Unit unit) {
		return new StatementIterator() {

			@Override
			public boolean hasNext() {
				if(this.hasReturned)
					return false;
				// split up, because can change if anything changed
				if (iterator == null){
					MyStatement statement = evaluate(world, unit);
					if(statement.iterator(world, unit).isTerminal()){
						return true;
					}
					else{
						return statement.iterator(world, unit).hasNext();
					}
				}
				else{
					if(iterator.isTerminal()){
						return true;
					}
					else{
						return iterator.hasNext();
					}
				}
			}

			@Override
			public MyStatement next()throws NoSuchElementException {
				if(!this.hasNext())
					throw new NoSuchElementException();
				
				if (iterator == null){
					MyStatement statement = evaluate(world, unit);
					iterator = statement.iterator(world, unit);
				}
				
				if(iterator.isTerminal()){
					this.hasReturned = true;
					return evaluate(world, unit);
				}
				return iterator.next();
			}
			
			public boolean isTerminal(){
				return false;
			}
			
			private StatementIterator iterator;
			
			private boolean hasReturned = false;
		};
	}
	
	private final MyExpression expression;
	private final MyStatement then;
	private final MyStatement other;
	
}
