package hillbillies.part3.programs.statement;


import java.util.List;
import java.util.NoSuchElementException;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.Unit;
import hillbillies.model.World;
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


	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.MyStatement#iterator(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public StatementIterator iterator(World world, Unit unit) {
		return new StatementIterator(){

			@Override
			public boolean hasNext() {
				if(this.endReached)
					return false;
				if(this.currentIterator.isTerminal())
					return true;
				if(this.currentIterator.hasNext()){
					return true;
				}
				
				this.hasNext_index = this.index + 1;
				this.hasNext_iterator = getMyStatementAt(hasNext_index).iterator(world, unit);
				
				while(this.hasNext_index <= getNbMyStatements()){
					if (this.hasNext_iterator.isTerminal() || this.hasNext_iterator.hasNext()){
						return true;
					}
					hasNext_index += 1;
					this.hasNext_iterator = getMyStatementAt(hasNext_index).iterator(world, unit);
				}
				return false;
			}

			@Override
			public MyStatement next() throws NoSuchElementException{
				
				if (this.currentIterator.isTerminal()){
					index += 1;
					if(index <= getNbMyStatements()){
						this.currentIterator = getMyStatementAt(index).iterator(world, unit);
					}
					else{
						this.endReached = true;
					}
					return getMyStatementAt(index-1);
				}
				else{
					if(this.currentIterator.hasNext()){
						return this.currentIterator.next();
					}
					else{
						
						this.index += 1;
						this.currentIterator = getMyStatementAt(index).iterator(world, unit);
						while(this.index <= getNbMyStatements()){
							if (this.currentIterator.isTerminal() || this.currentIterator.hasNext()){
								index += 1;
								this.currentIterator = getMyStatementAt(index).iterator(world, unit);
								if(index == getNbMyStatements())
									this.endReached = true;
								return getMyStatementAt(index);
							}
							index += 1;
							this.currentIterator = getMyStatementAt(index).iterator(world, unit);
						}
						throw new IllegalStateException("you shouldn't be here :)");
					}
				}
			}
			
			public boolean isTerminal(){
				return false;
			}
			
			private boolean endReached = false;
			
			private StatementIterator currentIterator = getMyStatementAt(1).iterator(world, unit);
			
			private int index = 1;
			
			private int hasNext_index;
			
			private StatementIterator hasNext_iterator;
		};
	}

	
}
