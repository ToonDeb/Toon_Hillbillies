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
public class WhileLoop extends MyStatement {
	
	public WhileLoop(MyExpression<Boolean> expression, MyStatement statement, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
		this.statement = statement;
	}
	
	public MyExpression<Boolean> getExpression(){
		return this.expression;
	}
	
	public MyStatement getStatement(){
		return this.statement;
	}
	private final MyExpression<Boolean> expression;
	private final MyStatement statement;
	
	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.MyStatement#iterator(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public StatementIterator iterator(World world, Unit unit) {
		return new StatementIterator(){

			@Override
			public boolean hasNext() {
				if(this.inLoop){
					return true;
				}
				if((WhileLoop.this.getExpression()).evaluateExpression(unit)){
					return true;
				}
				else{
					return false;
				}
			}

			@Override
			public MyStatement next() throws NoSuchElementException {
				if(!this.hasNext())
					throw new NoSuchElementException("the while loop is false");
				if(iterator.isTerminal())
					return getStatement();
				else{
					MyStatement statement = iterator.next();
					if(this.inLoop && !iterator.hasNext()){
						this.inLoop = false;
						iterator = getStatement().iterator(world, unit);
					}
					else if(!this.inLoop){
						this.inLoop = true;
					}
					return statement;
				}
			}
			
			public boolean isTerminal(){
				return false;
			}
			private boolean inLoop = false;
			private StatementIterator iterator = getStatement().iterator(world, unit);
		};
	}
}
