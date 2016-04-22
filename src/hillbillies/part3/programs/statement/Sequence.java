package hillbillies.part3.programs.statement;

import java.util.List;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 * 
 * @invar   Each Sequence must have proper MyStatements.
 *        | hasProperMyStatements()	
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class Sequence extends MyStatement {

	/**
	 * @param sourceLocation
	 * 
 	 * @post   This new Sequence has no MyStatements yet.
	 *       | new.getNbMyStatements() == 0
	 */
	public Sequence(List<MyStatement> statements, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.myStatements = statements;
	}


	/**
	 * Return the MyStatement associated with this Sequence at the
	 * given index.
	 * 
	 * @param  index
	 *         The index of the MyStatement to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of MyStatements for this Sequence.
	 *       | (index < 1) || (index > getNbMyStatements())
	 */
	@Basic
	@Raw
	public MyStatement getMyStatementAt(int index) throws IndexOutOfBoundsException {
		return myStatements.get(index - 1);
	}

	/**
	 * Return the number of MyStatements associated with this Sequence.
	 */
	@Basic
	@Raw
	public int getNbMyStatements() {
		return myStatements.size();
	}

	/**
	 * Check whether this Sequence has the given MyStatement as one of its
	 * MyStatements.
	 * 
	 * @param  myStatement
	 *         The MyStatement to check.
	 * @return The given MyStatement is registered at some position as
	 *         a MyStatement of this Sequence.
	 *       | for some I in 1..getNbMyStatements():
	 *       |   getMyStatementAt(I) == myStatement
	 */
	public boolean hasAsMyStatement(@Raw MyStatement myStatement) {
		return myStatements.contains(myStatement);
	}


	private List<MyStatement> myStatements;

	
}
