package hillbillies.part3.programs.statement;

import java.util.NoSuchElementException;

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

public class Print extends MyStatement {

	/**
	 * @param sourceLocation
	 */
	public Print(MyExpression<?> expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.expression = expression;
		
	}
	
	public MyExpression<?> getExpression(){
		return this.expression;
	}
	
	public void execute(Unit unit){
		System.out.println(this.getExpression().toString(unit));
	}
	
	private final MyExpression<?> expression;

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
			public MyStatement next()throws NoSuchElementException {
				throw new NoSuchElementException("has no next elements");
			}
			
			public boolean isTerminal(){
				return true;
			}
		};
	}

}
