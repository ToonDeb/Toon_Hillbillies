package hillbillies.part3.programs.statement.action;

import java.util.NoSuchElementException;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.statement.MyStatement;
import hillbillies.part3.programs.statement.StatementIterator;

/**
 * A class of Action statments
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public abstract class Action<T> extends MyStatement {

	/**
	 * @param sourceLocation
	 */
	public Action(MyExpression<T> expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.expression = expression;
	}
	
	public MyExpression<T> getExpression(){
		return this.expression;
	}
	
	private final MyExpression<T> expression;
	
	public abstract void execute(World world, Unit unit);
	
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
