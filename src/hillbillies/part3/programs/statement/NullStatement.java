package hillbillies.part3.programs.statement;


import java.util.NoSuchElementException;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of nullStatements
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class NullStatement extends MyStatement {

	/**
	 * @param sourceLocation
	 */
	public NullStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.MyStatement#iterator(hillbillies.model.World, hillbillies.model.Unit)
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
				throw new NoSuchElementException();
			}
			
			@Override
			public boolean isTerminal() {
				return true;
			}
		};
	}

}
