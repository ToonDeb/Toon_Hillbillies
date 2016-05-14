package hillbillies.part3.programs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hillbillies.model.Faction;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.expression.ReadBooleanVariable;
import hillbillies.part3.programs.expression.ReadPositionVariable;
import hillbillies.part3.programs.expression.ReadUnitVariable;
import hillbillies.part3.programs.expression.logic.AndExpression;
import hillbillies.part3.programs.expression.logic.FalseExpression;
import hillbillies.part3.programs.expression.logic.IsAlive;
import hillbillies.part3.programs.expression.logic.IsCarryingItem;
import hillbillies.part3.programs.expression.logic.IsEnemy;
import hillbillies.part3.programs.expression.logic.IsFriend;
import hillbillies.part3.programs.expression.logic.IsPassable;
import hillbillies.part3.programs.expression.logic.IsSolid;
import hillbillies.part3.programs.expression.logic.NotExpression;
import hillbillies.part3.programs.expression.logic.OrExpression;
import hillbillies.part3.programs.expression.logic.TrueExpression;
import hillbillies.part3.programs.expression.position.BoulderPosition;
import hillbillies.part3.programs.expression.position.HerePosition;
import hillbillies.part3.programs.expression.position.LiteralPosition;
import hillbillies.part3.programs.expression.position.LogPosition;
import hillbillies.part3.programs.expression.position.NextToPosition;
import hillbillies.part3.programs.expression.position.PositionOf;
import hillbillies.part3.programs.expression.position.WorkshopPosition;
import hillbillies.part3.programs.expression.unit.AnyUnit;
import hillbillies.part3.programs.expression.unit.Enemy;
import hillbillies.part3.programs.expression.unit.Friend;
import hillbillies.part3.programs.expression.unit.ThisUnit;
import hillbillies.part3.programs.statement.*;
import hillbillies.part3.programs.statement.action.Attack;
import hillbillies.part3.programs.statement.action.Follow;
import hillbillies.part3.programs.statement.action.Work;


/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class TaskFactory implements ITaskFactory<MyExpression, MyStatement, Task> {

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createTasks(java.lang.String, int, java.lang.Object, java.util.List)
	 */
	@Override
	public List<Task> createTasks(String name, int priority, MyStatement activity, List<int[]> selectedCubes) {
		List<Task> tasks = new ArrayList<Task>();
//		for(int[] selectedCube: selectedCubes){
//			tasks.add(new Task(name, priority, activity));
//		}
		tasks.add(new Task(name, priority, activity));
		return tasks;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createAssignment(java.lang.String, java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyStatement createAssignment(String variableName, MyExpression value, SourceLocation sourceLocation) {
		Assignment assignment = new Assignment(variableName, value, sourceLocation);
		this.assignmentmap.put(variableName, assignment);
		return assignment;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createWhile(java.lang.Object, java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyStatement createWhile(MyExpression condition, MyStatement body, SourceLocation sourceLocation) {
		return new WhileLoop(condition, body, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createIf(java.lang.Object, java.lang.Object, java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyStatement createIf(MyExpression condition, MyStatement ifBody, MyStatement elseBody,
			SourceLocation sourceLocation) {
		return new IfThenElse(condition, ifBody, elseBody, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createBreak(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyStatement createBreak(SourceLocation sourceLocation) {
		return null;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createPrint(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyStatement createPrint(MyExpression value, SourceLocation sourceLocation) {
		return new Print(value, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createSequence(java.util.List, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyStatement createSequence(List<MyStatement> statements, SourceLocation sourceLocation) {
		return new Sequence(statements, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createMoveTo(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyStatement createMoveTo(MyExpression position, SourceLocation sourceLocation) {
		return new hillbillies.part3.programs.statement.action.MoveTo(position, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createWork(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyStatement createWork(MyExpression position, SourceLocation sourceLocation) {
		return new Work(position, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createFollow(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyStatement createFollow(MyExpression unit, SourceLocation sourceLocation) {
		return new Follow(unit, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createAttack(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyStatement createAttack(MyExpression unit, SourceLocation sourceLocation) {
		return new Attack(unit, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createReadVariable(java.lang.String, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createReadVariable(String variableName, SourceLocation sourceLocation) {
		Assignment assignment = this.assignmentmap.get(variableName);
		MyExpression<?> expression = assignment.getExpression();
		
		TerrainChangeListener modelListener = new DefaultTerrainChangeListener();
		World world = new World(new int[][][]{{{3}}}, modelListener);
		world.spawnUnit(false);
		world.spawnUnit(false); //to have an enemy unit
		Unit unit = (Unit) world.getUnits().toArray()[0];
		Faction faction = unit.getFaction();
		Unit newunit = new Unit("Steegmans", new int[] {0, 0, 0}, 50, 50, 50, 50, world, faction, false);
		
		if(expression.evaluateExpression(unit) instanceof Unit){
			return new ReadUnitVariable(variableName, sourceLocation);
		}
		else if(expression.evaluateExpression(unit) instanceof int[]){
			return new ReadPositionVariable(variableName, sourceLocation);
		}
		else if(expression.evaluateExpression(unit) instanceof Boolean){
			return new ReadBooleanVariable(variableName, sourceLocation);
		}
		else
			throw new IllegalArgumentException();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createIsSolid(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createIsSolid(MyExpression position, SourceLocation sourceLocation) {
		return new IsSolid(position, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createIsPassable(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createIsPassable(MyExpression position, SourceLocation sourceLocation) {
		return new IsPassable(position, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createIsFriend(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createIsFriend(MyExpression unit, SourceLocation sourceLocation) {
		return new IsFriend(unit, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createIsEnemy(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createIsEnemy(MyExpression unit, SourceLocation sourceLocation) {
		return new IsEnemy(unit, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createIsAlive(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createIsAlive(MyExpression unit, SourceLocation sourceLocation) {
		return new IsAlive(unit, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createCarriesItem(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createCarriesItem(MyExpression unit, SourceLocation sourceLocation) {
		return new IsCarryingItem(unit, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createNot(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createNot(MyExpression expression, SourceLocation sourceLocation) {
		return new NotExpression(expression, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createAnd(java.lang.Object, java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createAnd(MyExpression left, MyExpression right, SourceLocation sourceLocation) {
		return new AndExpression(left, right, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createOr(java.lang.Object, java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createOr(MyExpression left, MyExpression right, SourceLocation sourceLocation) {
		return new OrExpression(left, right, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createHerePosition(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createHerePosition(SourceLocation sourceLocation) {
		return new HerePosition(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createLogPosition(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createLogPosition(SourceLocation sourceLocation) {
		return new LogPosition(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createBoulderPosition(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createBoulderPosition(SourceLocation sourceLocation) {
		return new BoulderPosition(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createWorkshopPosition(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createWorkshopPosition(SourceLocation sourceLocation) {
		return new WorkshopPosition(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createSelectedPosition(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createSelectedPosition(SourceLocation sourceLocation) {
		return null;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createNextToPosition(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createNextToPosition(MyExpression position, SourceLocation sourceLocation) {
		return new NextToPosition(position, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createLiteralPosition(int, int, int, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		return new LiteralPosition(x, y, z, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createThis(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createThis(SourceLocation sourceLocation) {
		return new ThisUnit(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createFriend(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createFriend(SourceLocation sourceLocation) {
		return new Friend(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createEnemy(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createEnemy(SourceLocation sourceLocation) {
		return new Enemy(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createAny(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createAny(SourceLocation sourceLocation) {
		return new AnyUnit(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createTrue(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createTrue(SourceLocation sourceLocation) {
		return new TrueExpression(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createFalse(hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createFalse(SourceLocation sourceLocation) {
		return new FalseExpression(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.ITaskFactory#createPositionOf(java.lang.Object, hillbillies.part3.programs.SourceLocation)
	 */
	@Override
	public MyExpression createPositionOf(MyExpression unit, SourceLocation sourceLocation) {
		return new PositionOf(unit, sourceLocation);
	}
	
	private HashMap<String, Assignment> assignmentmap = new HashMap<String, Assignment>();

}
