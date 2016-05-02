package hillbillies.model;

import java.util.HashMap;
import java.util.Iterator;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.internal.generated.HillbilliesTaskLangParser.AssignmentStatementContext;
import hillbillies.part3.programs.statement.Assignment;
import hillbillies.part3.programs.statement.MyStatement;
import hillbillies.part3.programs.statement.NullStatement;
import hillbillies.part3.programs.statement.Print;
import hillbillies.part3.programs.statement.StatementIterator;
import hillbillies.part3.programs.statement.action.Action;

/**
 * A class of Tasks
 *
 * @invar  Each Task can have its name as name.
 *       | canHaveAsName(this.getName())
 * 
 * @invar  The priority of each Task must be a valid priority for any
 *         Task.
 *       | isValidPriority(getPriority())
 *       
 * @invar  The Statement of each Task must be a valid Statement for any
 *         Task.
 *       | isValidStatement(getStatement())
 *       
 * @invar  The AssignedVariables of each Task must be a valid AssignedVariables for any
 *         Task.
 *       | isValidAssignedVariables(getAssignedVariables())
 *       
 * @invar  The Unit of each Task must be a valid Unit for any
 *         Task.
 *       | isValidUnit(getUnit())
 *       
 * @invar  The statementIterator of each Task must be a valid statementIterator for any
 *         Task.
 *       | isValidStatementIterator(getStatementIterator())
 *       
 * @author  ...
 * @version 1.0
 */
public class Task {
	
	/**
	 * Initialize this new Task with given name and priority.
	 * 
	 * @param  name
	 *         The name for this new Task.
	 * @post   The name of this new Task is equal to the given
	 *         name.
	 *       | new.getName() == name
	 * @throws IllegalArgumentException
	 *         This new Task cannot have the given name as its name.
	 *       | ! canHaveAsName(this.getName())
	 *       
	 * @param  priority
	 *         The priority for this new Task.
 	 * @effect The priority of this new Task is set to
 	 *         the given priority.
 	 *       | this.setPriority(priority)
 	 *       
 	 * @param  statement
 	 *         The Statement for this new Task.
 	 * @effect The Statement of this new Task is set to
 	 *         the given Statement.
 	 *       | this.setStatement(statement)
	 */
	public Task(String name, int priority, MyStatement statement) throws IllegalArgumentException {
		if (! canHaveAsName(name))
			throw new IllegalArgumentException();
		this.name = name;
		
		this.setPriority(priority);
		
		this.setStatement(statement); //mss immutable?
		
		this.setAssignedVariables(new HashMap<String, Object>());
	}
	
	/**
	 * Return the name of this Task.
	 */
	@Basic @Raw @Immutable
	public String getName() {
		return this.name;
	}
	
	/**
	 * Check whether this Task can have the given name as its name.
	 *  
	 * @param  name
	 *         The name to check.
	 * @return 
	 *       | result == (name != null)
	*/
	@Raw
	public boolean canHaveAsName(String name) {
		return (name != null);
	}
	
	/**
	 * Variable registering the name of this Task.
	 */
	private final String name;
	

	/**
	 * Return the priority of this Task.
	 */
	@Basic @Raw
	public int getPriority() {
		return this.priority;
	}
	
	/**
	 * Check whether the given priority is a valid priority for
	 * any Task.
	 *  
	 * @param  priority
	 *         The priority to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidPriority(int priority) {
		return true;
	}
	
	/**
	 * Set the priority of this Task to the given priority.
	 * 
	 * @param  priority
	 *         The new priority for this Task.
	 * @post   The priority of this new Task is equal to
	 *         the given priority.
	 *       | new.getPriority() == priority
	 * @throws IllegalArgumentException
	 *         The given priority is not a valid priority for any
	 *         Task.
	 *       | ! isValidPriority(getPriority())
	 */
	@Raw
	public void setPriority(int priority) 
			throws IllegalArgumentException {
		if (! isValidPriority(priority))
			throw new IllegalArgumentException();
		this.priority = priority;
	}
	
	/**
	 * Variable registering the priority of this Task.
	 */
	private int priority;

	/**
	 * Return the Statement of this Task.
	 */
	@Basic @Raw
	public MyStatement getStatement() {
		return this.statement;
	}

	/**
	 * Check whether the given Statement is a valid Statement for
	 * any Task.
	 *  
	 * @param  Statement
	 *         The Statement to check.
	 * @return 
	 *       | result == (statement != null)
	*/
	public static boolean isValidStatement(MyStatement statement) {
		return (statement != null);
	}

	/**
	 * Set the Statement of this Task to the given Statement.
	 * 
	 * @param  statement
	 *         The new Statement for this Task.
	 * @post   The Statement of this new Task is equal to
	 *         the given Statement.
	 *       | new.getStatement() == statement
	 * @throws IllegalArgumentException
	 *         The given Statement is not a valid Statement for any
	 *         Task.
	 *       | ! isValidStatement(getStatement())
	 */
	@Raw
	public void setStatement(MyStatement statement) 
			throws IllegalArgumentException {
		if (! isValidStatement(statement))
			throw new IllegalArgumentException();
		this.statement = statement;
	}

	/**
	 * Variable registering the Statement of this Task.
	 */
	private MyStatement statement;
	
	/**
	 * Returns the expression assigned to the given variableName
	 * 
	 * @param variableName
	 * 		  The name of the expression to search for
	 * @return 
	 * 			| return this.getAssignedVariables().get(variableName)
	 */
	public Object getExpression(String variableName){
		return this.getAssignedVariables().get(variableName);
	}
	


	///**
	// * Initialize this new Task with given AssignedVariables.
	// *
	// * @param  assignedVariable
	// *         The AssignedVariables for this new Task.
	// * @effect The AssignedVariables of this new Task is set to
	// *         the given AssignedVariables.
	// *       | this.setAssignedVariables(assignedVariable)
	// */
	//public Task(HashMap<String, MyExpression> assignedVariable)
	//		throws IllegalArgumentException {
	//	this.setAssignedVariables(assignedVariable);
	//}


	/**
	 * Return the AssignedVariables of this Task.
	 */
	@Basic @Raw
	public HashMap<String, Object> getAssignedVariables() {
		return this.assignedVariable;
	}

	/** TODO: isValidAssignedVariable
	 * Check whether the given AssignedVariables is a valid AssignedVariables for
	 * any Task.
	 *  
	 * @param  AssignedVariables
	 *         The AssignedVariables to check.
	 * @return 
	 *       | result == 
	*/
	public static boolean isValidAssignedVariables(HashMap<String, Object> assignedVariable) {
		return true;
	}

	/** TODO: assignedVariable zetten op de uitkomst van de expression, niet op de expression zelf
	 * Set the AssignedVariables of this Task to the given AssignedVariables.
	 * 
	 * @param  assignedVariable
	 *         The new AssignedVariables for this Task.
	 * @post   The AssignedVariables of this new Task is equal to
	 *         the given AssignedVariables.
	 *       | new.getAssignedVariables() == assignedVariable
	 * @throws IllegalArgumentException
	 *         The given AssignedVariables is not a valid AssignedVariables for any
	 *         Task.
	 *       | ! isValidAssignedVariables(getAssignedVariables())
	 */
	@Raw
	public void setAssignedVariables(HashMap<String, Object> assignedVariable) 
			throws IllegalArgumentException {
		if (! isValidAssignedVariables(assignedVariable))
			throw new IllegalArgumentException();
		this.assignedVariable = assignedVariable;
	}

	/**
	 * Variable registering the AssignedVariables of this Task.
	 */
	private HashMap<String, Object> assignedVariable;
	
	/**
	 * TODO: task iterator documententatie
	 * @return
	 */
	public Iterator<MyStatement> iterator(){
		if(this.getUnit()==null)
			throw new IllegalStateException("task is not assigned to a unit!");
		return this.getStatement().iterator(this.unit.getWorld(), this.unit);
	}

	/**
	 * Return the Unit of this Task.
	 */
	@Basic @Raw
	public Unit getUnit() {
		return this.unit;
	}

	/**
	 * Check whether the given Unit is a valid Unit for
	 * any Task.
	 *  
	 * @param  Unit
	 *         The Unit to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidUnit(Unit unit) {
		return true;
	}

	/**
	 * Set the Unit of this Task to the given Unit.
	 * 
	 * @param  unit
	 *         The new Unit for this Task.
	 * @post   The Unit of this new Task is equal to
	 *         the given Unit.
	 *       | new.getUnit() == unit
	 * @throws IllegalArgumentException
	 *         The given Unit is not a valid Unit for any
	 *         Task.
	 *       | ! isValidUnit(getUnit())
	 */
	@Raw
	public void setUnit(Unit unit) throws IllegalArgumentException {
		if (! isValidUnit(unit))
			throw new IllegalArgumentException();
		this.unit = unit;
		this.setStatementIterator();
	}

	/**
	 * Variable registering the Unit of this Task.
	 */
	private Unit unit;
	
	public void removeFromSchedulers(){
		
	}


///**
// * Initialize this new Task with given statementIterator.
// *
// * @param  iterator
// *         The statementIterator for this new Task.
// * @effect The statementIterator of this new Task is set to
// *         the given statementIterator.
// *       | this.setStatementIterator(iterator)
// */
//public Task(StatementIterator iterator)
//		throws IllegalArgumentException {
//	this.setStatementIterator(iterator);
//}


	/**
	 * Return the statementIterator of this Task.
	 */
	@Basic @Raw
	public StatementIterator getStatementIterator() {
		return this.iterator;
	}

	/**
	 * Check whether the given statementIterator is a valid statementIterator for
	 * any Task.
	 *  
	 * @param  statementIterator
	 *         The statementIterator to check.
	 * @return 
	 *       | result == 
	*/
	public static boolean isValidStatementIterator(StatementIterator iterator) {
		return true;
	}

	/**
	 * Set the statementIterator of this Task to the given statementIterator.
	 * 
	 * @post   The statementIterator of this new Task is equal to the 
	 * 			statemeIterator of the statement.
	 *       | new.getStatementIterator() == this.getStatement().iterator(this.getUnit().getWorld(), this.getUnit())
	 * @throws IllegalStateException
	 *         The Task is not assigned to a unit.
	 *       | ! isValidStatementIterator(getStatementIterator())
	 */
	@Raw
	public void setStatementIterator() throws IllegalArgumentException {
		if (this.getUnit() == null)
			throw new IllegalStateException("no assigned unit");
		this.getStatement().iterator(this.getUnit().getWorld(), this.getUnit());
	}

	/**
	 * Variable registering the statementIterator of this Task.
	 */
	private StatementIterator iterator;
	
	/**
	 * Terminate this Task.
	 *
	 * @post   This Task  is terminated.
	 *       | new.isTerminated()
	 * @post   ...
	 *       | ...
	 */
	 public void terminate() {
		 this.isTerminated = true;
		 this.getUnit().setTask(null);
		 this.setUnit(null);
		 //delete from unit
		 //delete from scheduler
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this Task
	  * is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this person is terminated.
	  */
	 private boolean isTerminated = false;
	 
	
	public void advanceTime(double deltaT){
		double taskTime = deltaT;
		
		while (taskTime > 0){
			taskTime -= 0.001;
			if (this.getStatementIterator().hasNext()){
				MyStatement statement = this.getStatementIterator().next();
				if (statement instanceof NullStatement){
					
				}
				else if(statement instanceof Assignment){
					Assignment assignStatement = (Assignment)statement;
					this.getAssignedVariables().put(assignStatement.getVariableName(), assignStatement.getExpression());
				}
				else if(statement instanceof Action){
					Action actionStatement = (Action)statement;
					actionStatement.execute(this.getUnit().getWorld(), this.getUnit());
					this.getUnit().startAction();
					return;
				}
				else if(statement instanceof Print){
					Print printStatement = (Print)statement;
					printStatement.execute();
				}
				else{
					throw new IllegalStateException("not a valid/known instance of statment");
				}
			}
			else{
				this.terminate();
				return;
			}
		}
	}
	
	public void interruptAction(){
		this.setPriority(this.getPriority()-1);
		this.getUnit().setTask(null);
		this.setUnit(null);
		this.iterator = null;
	}
	
}
