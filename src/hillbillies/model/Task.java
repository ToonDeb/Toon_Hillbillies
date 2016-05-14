package hillbillies.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
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
 * @invar   Each Task must have proper Schedulers.
 *        | hasProperSchedulers()
 *       
 * @author  ...
 * @version 1.0
 */
public class Task implements Comparable<Task>{
	
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
 	 *       
 	 * @post   This new Task has no Schedulers yet.
	 *       | new.getNbSchedulers() == 0
	 */
	public Task(String name, int priority, MyStatement statement) throws IllegalArgumentException {
		if (! canHaveAsName(name))
			throw new IllegalArgumentException();
		this.name = name;
		
		this.setPriority(priority);
		this.setStatement(statement); //mss immutable?
		
		this.setAssignedVariables(new HashMap<String, Assignment>());
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
		System.out.println("setpriority");
		for(Scheduler scheduler: this.getSchedulers()){
			scheduler.sortTasks();
		}
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
	public HashMap<String, Assignment> getAssignedVariables() {
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
	public static boolean isValidAssignedVariables(HashMap<String, Assignment> assignedVariable) {
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
	public void setAssignedVariables(HashMap<String, Assignment> assignedVariable) 
			throws IllegalArgumentException {
		if (! isValidAssignedVariables(assignedVariable))
			throw new IllegalArgumentException();
		this.assignedVariable = assignedVariable;
	}
	
	/**
	 * Returns the expression assigned to the given variableName
	 * 
	 * @param variableName
	 * 		  The name of the expression to search for
	 * @return 
	 * 			| return this.getAssignedVariables().get(variableName)
	 */
	public Assignment getAssignment(String variableName){
		return this.getAssignedVariables().get(variableName);
	}

	/**
	 * Variable registering the AssignedVariables of this Task.
	 */
	private HashMap<String, Assignment> assignedVariable;
	
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
		
		if(unit != null){
			this.setStatementIterator();
		}
		
	}
	
	public boolean isAssigned(){
		return(this.getUnit() != null);
	}
	
	public void assignTo(Unit unit){
		unit.setTask(this);
		this.setUnit(unit);
	}

	/**
	 * Variable registering the Unit of this Task.
	 */
	private Unit unit;
	
//	public void removeFromSchedulers(){
//		
//	}


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
		this.iterator = this.getStatement().iterator(this.getUnit().getWorld(), this.getUnit());
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
		 
		 for(Scheduler scheduler: this.schedulers){
			 //scheduler.removeTask(this);
			 this.removeScheduler(scheduler);
		 }
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
			if (this.getStatementIterator().isTerminal()){
				MyStatement statement = this.getStatement();
				if(statement instanceof Action){
					Action<?> actionStatement = (Action<?>)statement;
					try{
						actionStatement.execute(this.getUnit().getWorld(), this.getUnit());
					}
					catch(Exception e){
						this.interrupt();
					}
					this.getUnit().startAction();
					this.terminate();
					//return;
				}
				else if (statement instanceof Print){
					Print printStatement = (Print)statement;
					printStatement.execute(this.getUnit());
					this.terminate();
					//return;
				}
				else{
					this.terminate();
				}
				//all other cases, no influence
			}
			if (this.getStatementIterator().hasNext()){
				MyStatement statement = this.getStatementIterator().next();
				System.out.println(statement);
				//this.lastStatement = statement;
			
				if (statement instanceof NullStatement){
					taskTime += 0.001;
				}
				else if(statement instanceof Assignment){
					Assignment assignStatement = (Assignment)statement;
					//Assignment previousAssignment = this.getAssignedVariables().get(assignStatement.getVariableName());
					//if((previousAssignment != null) && 
					//		(previousAssignment.getExpression().getClass().isInstance(assignStatement.getExpression()))){
						this.getAssignedVariables().put(assignStatement.getVariableName(), assignStatement);
					//}
					//else{
					//	throw new IllegalArgumentException("wrong type of assigned variable");
					//}
					
					
				}
				else if(statement instanceof Action){
					Action<?> actionStatement = (Action<?>)statement;
					try{
						actionStatement.execute(this.getUnit().getWorld(), this.getUnit());
					}
					catch(Exception e){
						this.interrupt();
						return;
					}
					this.getUnit().startAction();
					return;
				}
				else if(statement instanceof Print){
					Print printStatement = (Print)statement;
					printStatement.execute(this.getUnit());
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
	
	/**
	 * 
	 */
	public void interrupt(){
		if(this.getUnit() == null)
			throw new IllegalStateException("not being executed!");
		this.getUnit().setTask(null);
		this.setUnit(null);
		this.setPriority(this.getPriority()-1);
	}
	
//	public void redoLastStatement(){
//		executeStatement(this.lastStatement);
//	}
//	
//	private MyStatement lastStatement;

	/**
	 * Check whether this Task has the given Scheduler as one of its
	 * Schedulers.
	 * 
	 * @param  scheduler
	 *         The Scheduler to check.
	 */
	@Basic
	@Raw
	public boolean hasAsScheduler(@Raw Scheduler scheduler) {
		return schedulers.contains(scheduler);
	}

	/**
	 * Check whether this Task can have the given Scheduler
	 * as one of its Schedulers.
	 * 
	 * @param  scheduler
	 *         The Scheduler to check.
	 * @return True if and only if the given Scheduler is effective
	 *         and that Scheduler is a valid Scheduler for a Task.
	 *       | result ==
	 *       |   (scheduler != null) &&
	 *       |   scheduler.canHaveAsTask(this)
	 */
	@Raw
	public boolean canHaveAsScheduler(Scheduler scheduler) {
		return (scheduler != null) && (scheduler.canHaveAsTask(this));
	}

	/**
	 * Check whether this Task has proper Schedulers attached to it.
	 * 
	 * @return True if and only if this Task can have each of the
	 *         Schedulers attached to it as one of its Schedulers,
	 *         and if each of these Schedulers references this Task as
	 *         the Task to which they are attached.
	 *       | for each scheduler in Scheduler:
	 *       |   if (hasAsScheduler(scheduler))
	 *       |     then canHaveAsScheduler(scheduler) &&
	 *       |          (scheduler.hasAsTask(this))
	 */
	public boolean hasProperSchedulers() {
		for (Scheduler scheduler : schedulers) {
			if (!canHaveAsScheduler(scheduler))
				return false;
			if (!scheduler.hasAsTask(this))
				return false;
		}
		return true;
	}

	/**
	 * Return the number of Schedulers associated with this Task.
	 *
	 * @return  The total number of Schedulers collected in this Task.
	 *        | result ==
	 *        |   card({scheduler:Scheduler | hasAsScheduler({scheduler)})
	 */
	public int getNbSchedulers() {
		return schedulers.size();
	}

	/**
	 * Add the given Scheduler to the set of Schedulers of this Task.
	 * 
	 * @param  scheduler
	 *         The Scheduler to be added.
	 * @pre    The given Scheduler is effective and already references
	 *         this Task.
	 *       | (scheduler != null) && (scheduler.getTask() == this)
	 * @post   This Task has the given Scheduler as one of its Schedulers.
	 *       | new.hasAsScheduler(scheduler)
	 */
	public void addScheduler(@Raw Scheduler scheduler) {
		assert (scheduler != null); // && (scheduler.hasAsTask(this));
		schedulers.add(scheduler);
	}

	/**
	 * Remove the given Scheduler from the set of Schedulers of this Task.
	 * 
	 * @param  scheduler
	 *         The Scheduler to be removed.
	 * @pre    This Task has the given Scheduler as one of
	 *         its Schedulers, and the given Scheduler does not
	 *         reference any Task.
	 *       | this.hasAsScheduler(scheduler)
	 * @post   This Task no longer has the given Scheduler as
	 *         one of its Schedulers.
	 *       | ! new.hasAsScheduler(scheduler)
	 * @post   The (former) scheduler does not have this Task as Task
	 * 		 | ! ((new) scheduler).hasAsTask(this)
	 */
	@Raw
	public void removeScheduler(Scheduler scheduler) {
		assert this.hasAsScheduler(scheduler);
		scheduler.removeTask(this);
		schedulers.remove(scheduler);
	}
	
	
	public Set<Scheduler> getSchedulers(){
		Set<Scheduler> newSet = new HashSet<Scheduler>();
		for(Scheduler scheduler: this.schedulers){
			newSet.add(scheduler);
		}
		return newSet;
	}

	/**
	 * Variable referencing a set collecting all the Schedulers
	 * of this Task.
	 * 
	 * @invar  The referenced set is effective.
	 *       | schedulers != null
	 * @invar  Each Scheduler registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each scheduler in schedulers:
	 *       |   ( (scheduler != null) &&
	 *       |     (! scheduler.isTerminated()) )
	 */
	private final Set<Scheduler> schedulers = new HashSet<Scheduler>();


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Task other) {
		if (this.getPriority() == other.getPriority())
			return 0;
		else if(this.getPriority() < other.getPriority())
			return 1;
		else
			return -1;
	}
	
}
