package hillbillies.model;

import java.util.HashMap;
import java.util.HashSet;
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
 *       
 * @invar   Each Task must have proper Schedulers.
 *        | hasProperSchedulers()
 *       
 * @author  Toon Deburchgrave
 * @version 2.01
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
 	 * @post   The Statement of this new Task is set to
 	 *         the given Statement.
 	 *       | new.getStatement = statement
 	 * @throws IllegalArgumentException
 	 * 		   The statement is not a valid statement for any Task
 	 * 		 | ! isValidStatement(statement)
 	 *       
 	 * @post   This new Task has no Schedulers yet.
	 *       | new.getNbSchedulers() == 0
	 *       
	 */
	public Task(String name, int priority, MyStatement statement) throws IllegalArgumentException {
		if (! canHaveAsName(name))
			throw new IllegalArgumentException("invalid name");
		if (! isValidStatement(statement))
			throw new IllegalArgumentException("invalid statement");
		this.name = name;
		this.setPriority(priority);
		this.statement = statement;
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
	private static boolean canHaveAsName(String name) {
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
	 * Set the priority of this Task to the given priority.
	 * 
	 * @param  priority
	 *         The new priority for this Task.
	 * @post   The priority of this new Task is equal to
	 *         the given priority.
	 *       | new.getPriority() == priority
	 * @effect Sort the tasks in all the schedulers that
	 * 		   contain this task.
	 * 		 | for scheduler in this.getSchedulers
	 * 		 |    do scheduler.sortTasks()
	 */
	@Raw
	private void setPriority(int priority)throws IllegalArgumentException {
		this.priority = priority;
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
	private static boolean isValidStatement(MyStatement statement) {
		return (statement != null);
	}

	/**
	 * Variable registering the Statement of this Task.
	 */
	private final MyStatement statement;

	/**
	 * Return the AssignedVariables of this Task.
	 */
	@Basic @Raw
	private HashMap<String, Assignment> getAssignedVariables() {
		return this.assignedVariable;
	}
	
	/**
	 * Returns the expression assigned to the given variableName
	 * 
	 * @param variableName
	 * 		  The name of the expression to search for
	 * @return The assignment in this Task with as name variableName
	 * 		  | result == assignment
	 * 		  | 	where assignment.getVariableName == variableName
	 */
	public Assignment getAssignment(String variableName){
		return this.getAssignedVariables().get(variableName);
	}

	/**
	 * Variable registering the AssignedVariables of this Task.
	 */
	private HashMap<String, Assignment> assignedVariable = new HashMap<String, Assignment>();

	/**
	 * Return the Unit of this Task.
	 */
	@Basic @Raw
	public Unit getUnit() {
		return this.unit;
	}
	
	/**
	 * Check whether the given Task is a valid Task for
	 * any Unit.
	 *  
	 * @param  unit
	 *         The Unit to check.
	 * @return 
	 *       | result == true if (unit == null) || (unit.getTask() != this) 
	*/
	private boolean isValidUnit(Unit unit) {
		if(unit == null)
			return true;
		else
			return (unit.getTask() == this);
	}

	/**
	 * Set the Unit of this Task to the given Unit.
	 * 
	 * @param  unit
	 *         The new Unit for this Task.
	 * @post   The Unit of this new Task is equal to
	 *         the given Unit.
	 *       | new.getUnit() == unit
	 * @effect If the unit is not null, set the statementIterator
	 * 		 | if (unit != null)
	 * 		 |	do this.setStatementIterator();
	 * @throws IllegalArgumentException
	 * 			the unit is not a valid unit for this task
	 * 		 | !this.isValidUnit(unit)
	 */
	@Raw
	public void setUnit(Unit unit) throws IllegalArgumentException {
		if(!this.isValidUnit(unit))
			throw new IllegalArgumentException();
		this.unit = unit;
		
		if(unit != null){
			this.setStatementIterator();
		}
		
	}
	
	/**
	 * Return whether the Task is assigned to a unit
	 * 
	 * @return true if this task has a unit
	 * 			| result == (this.getUnit() != null)
	 */
	public boolean isAssigned(){
		return(this.getUnit() != null);
	}
	
	
	/**
	 * Assign this task to the given Unit
	 * 
	 * @param unit
	 * 		  The unit to be assigned this task
	 * 
	 * @post  the Unit of this Task is the given unit
	 * 		  | new.getUnit() == unit
	 * @post  The Task of the given unit is this Task
	 * 		  | (new unit).getTask == new
	 */
	public void assignTo(Unit unit){
		unit.setTask(this);
		this.setUnit(unit);
	}

	/**
	 * Variable registering the Unit of this Task.
	 */
	private Unit unit;

	/**
	 * Return the statementIterator of this Task.
	 */
	@Basic @Raw
	private StatementIterator getStatementIterator() {
		return this.iterator;
	}

	/**
	 * Set the statementIterator of this Task to the given statementIterator.
	 * 
	 * @post   The statementIterator of this new Task is equal to the 
	 * 			statemeIterator of the statement.
	 *       | new.getStatementIterator() == 
	 *       |     this.getStatement().iterator(this.getUnit().getWorld(), this.getUnit())
	 * @throws IllegalStateException
	 *         The Task is not assigned to a unit.
	 *       | ! isValidStatementIterator(getStatementIterator())
	 */
	@Raw
	private void setStatementIterator() throws IllegalArgumentException {
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
	 * @post   The Task of the Unit of this Task, is null
	 *       | this.getUnit = unit
	 *       | (new unit).getTask = null
	 * @post the Unit of this Task is null
	 * 		 | new.getUnit == null
	 * @effect This task is removed from all schedulers, and
	 * 		   vice versa
	 * 		 | for scheduler in this.getSchedulers
	 * 		 |	do this.removeScheduler(scheduler)
	 * 
	 * @throws IllegalStateException
	 * 		   When this task is already terminated
	 * 		  | this.isTerminated
	 */
	 public void terminate() throws IllegalStateException{
		 if(this.isTerminated)
			 throw new IllegalStateException("task already terminated");
		 
		 this.isTerminated = true;
		 if(this.getUnit() != null)
			 this.getUnit().setTask(null);
		 
		 this.setUnit(null);
		 
		 for(Scheduler scheduler: this.getSchedulers()){
			 this.removeScheduler(scheduler);
		 }
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
	 
	
	 /**
	  * Go through the different statements of a task.
	  * 
	  * 
	  * @param deltaT
	  * 	   The amount of time that has passed
	  * @throws IllegalStateException
	  * 		This task is terminated or it is not assigned to a unit
	  * 		|(this.isTerminated) || (!this.isAssigned)
	  * 
	  * @effect For every 0.001 deltaT, an action-, assignment- or print-
	  * 		statement from the statementIterator
	  * 		is executed.
	  * 		if the statement is terminal (i.e. an action or print statement
	  * 		If the action can not be executed by the unit, the task is interrupted
	  * 		If an action is executed, the task stops going through the statements.
	  * 		If all statements have been executed/reviewed, this task is terminated.
	  */
	@SuppressWarnings("unchecked")
	public void advanceTime(double deltaT) throws IllegalStateException{
		if((this.isTerminated) || (!this.isAssigned()))
			throw new IllegalStateException("can't advance time when terminated");
		double taskTime = deltaT;
		while (taskTime > 0){
			taskTime -= 0.001;
			if (this.getStatementIterator().isTerminal()){
				MyStatement statement = this.getStatement();
				if(statement instanceof Action){
					Action<?> actionStatement = (Action<?>)statement;
					
					try{
						actionStatement.execute(this.getUnit().getWorld(), this.getUnit());
						this.getUnit().startAction();
					}
					catch(Exception e){
						this.interrupt();
					}
					
					this.terminate();
					return;
				}
				else if (statement instanceof Print){
					Print printStatement = (Print)statement;
					printStatement.execute(this.getUnit());
					this.terminate();
					return;
				}
				else{
					this.terminate();
					return;
				}
				//all other cases, no influence
			}
			else if (this.getStatementIterator().hasNext()){
				MyStatement statement = this.getStatementIterator().next();
				if (statement instanceof NullStatement){
					taskTime += 0.001;
				}
				else if(statement instanceof Assignment){
					Assignment assignStatement = (Assignment)statement;
					if(assignStatement.getReadVariable() != null)
						assignStatement.getReadVariable().setEvaluatedExpression(null);
					Assignment previousAssignment = this.getAssignedVariables().get(assignStatement.getVariableName());
					Unit unit = this.getUnit();
					if(previousAssignment == null){
						this.getAssignedVariables().put(assignStatement.getVariableName(), assignStatement);
					} //check if previous was same type.
					else if((assignStatement.getExpression().evaluateExpression(unit) instanceof Unit &&
							   previousAssignment.getExpression().evaluateExpression(unit) instanceof Unit) ||
						   (assignStatement.getExpression().evaluateExpression(unit) instanceof int[] &&
							   previousAssignment.getExpression().evaluateExpression(unit) instanceof int[]) ||
						   (assignStatement.getExpression().evaluateExpression(unit) instanceof Boolean &&
							   previousAssignment.getExpression().evaluateExpression(unit) instanceof Boolean)){
						this.getAssignedVariables().put(assignStatement.getVariableName(), assignStatement);
					}
					else{
						this.terminate();
						throw new IllegalArgumentException("wrong type of assigned variable");
					}
					
					
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
	 * The task is interrupted, and becomes available again
	 * 
	 * @post   The Task of the Unit of this Task, is null
	 *       | this.getUnit = unit
	 *       | (new unit).getTask = null
	 * @post the Unit of this Task is null
	 * 		 | new.getUnit == null
	 * @effect The priority of this Task is reduced
	 * 		 | this.setPriority(this.getPriority -1);
	 * 
	 * @throws IllegalStateException
	 * 		   The Task is not being excecuted at the moment
	 * 		 | this.getUnit == null
	 */
	public void interrupt() throws IllegalStateException{
		if(this.getUnit() == null)
			throw new IllegalStateException("not being executed!");
		this.getUnit().setTask(null);
		this.setUnit(null);
		this.setPriority(this.getPriority()-1);
	}
	

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
	 *       |   (scheduler != null)
	 */
	@Raw
	public boolean canHaveAsScheduler(Scheduler scheduler) {
		return (scheduler != null);
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
	 * @post   This Task has the given Scheduler as one of its Schedulers.
	 *       | new.hasAsScheduler(scheduler)
	 * @throws IllegalArgumentException  
	 * 		   The given Scheduler is effective. Else, throw exception
	 *       | (scheduler == null)      
	 */
	public void addScheduler(@Raw Scheduler scheduler) {
		if (scheduler == null)
			throw new IllegalArgumentException("scheduler can't be null");
		schedulers.add(scheduler);
	}

	/**
	 * Remove the given Scheduler from the set of Schedulers of this Task.
	 * 
	 * @param  scheduler
	 *         The Scheduler to be removed.
	 * @post   This Task no longer has the given Scheduler as
	 *         one of its Schedulers.
	 *       | ! new.hasAsScheduler(scheduler)
	 * @post   The (former) scheduler does not have this Task as Task
	 * 		 | ! ((new) scheduler).hasAsTask(this)
	 * @throws IllegalArgumentException
	 * 	       This Task has the given Scheduler as one of
	 *         its Schedulers. Else, throw Exception
	 *       | ! this.hasAsScheduler(scheduler)
	 */
	@Raw
	public void removeScheduler(Scheduler scheduler) {
		if (!this.hasAsScheduler(scheduler))
			throw new IllegalArgumentException("scheduler is not in this task");
		schedulers.remove(scheduler);
		scheduler.removeTask(this);
	}
	
	/**
	 * Return a set of all schedulers of this Task.
	 */
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
