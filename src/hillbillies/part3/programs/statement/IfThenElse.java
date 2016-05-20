package hillbillies.part3.programs.statement;

import java.util.NoSuchElementException;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of If then Else statements
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class IfThenElse extends MyStatement {
	
	public IfThenElse(MyExpression<Boolean> expression, MyStatement then, MyStatement other, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
		this.then = then;
		if(other == null)
			this.other = new NullStatement(sourceLocation);
		else
			this.other = other;
	}
	
	public MyExpression<Boolean> getExpression(){
		return this.expression;
	}
	
	public MyStatement getThen(){
		return this.then;
	}
	
	public MyStatement getElse(){
		return this.other;
	}
	
	public MyStatement evaluate(World world, Unit unit){
		MyExpression<Boolean> expression = this.getExpression();
		
		if(expression.evaluateExpression(unit)){
			return this.getThen();
		}
		else{
			return this.getElse();
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
	
	private final MyExpression<Boolean> expression;
	private final MyStatement then;
	private final MyStatement other;
	
}
