package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.part3.programs.statement.MyStatement;

/**
 * A class of ...
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
 * @param  statement
 *         The Statement for this new Task.
 * @effect The Statement of this new Task is set to
 *         the given Statement.
 *       | this.setStatement(statement)
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
	 */
	public Task(String name, int priority, MyStatement statement) throws IllegalArgumentException {
		if (! canHaveAsName(name))
			throw new IllegalArgumentException();
		this.name = name;
		
		this.setPriority(priority);
		
		this.setStatement(statement); //mss immutable?
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
	
}
