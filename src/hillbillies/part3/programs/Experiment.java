package hillbillies.part3.programs;

import java.util.ArrayList;
import java.util.Iterator;

import hillbillies.model.Faction;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.expression.ReadVariable;
import hillbillies.part3.programs.expression.logic.TrueExpression;
import hillbillies.part3.programs.statement.Assignment;
import hillbillies.part3.programs.statement.MyStatement;
import hillbillies.part3.programs.statement.Print;
import hillbillies.part3.programs.statement.Sequence;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class Experiment {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		TaskFactory factory = new TaskFactory();
		
		SourceLocation sourceLocation = new SourceLocation(0, 0);
		TrueExpression expression = new TrueExpression(sourceLocation);
		Assignment assignment = (Assignment) factory.createAssignment("test", expression, sourceLocation);
		ReadVariable<?> variable = (ReadVariable<Boolean>) factory.createReadVariable("test", sourceLocation);
		Print print = (Print) factory.createPrint(variable, sourceLocation);
		ArrayList<MyStatement> list = new ArrayList<MyStatement>();
		list.add(assignment);
		list.add(print);
		Sequence sequence = (Sequence) factory.createSequence(list, sourceLocation);
		Task task = factory.createTasks("nono", 0, sequence, null).get(0);
		
		TerrainChangeListener modelListener = new DefaultTerrainChangeListener();
		World world = new World(new int[][][]{{{0}}}, modelListener);
		Faction faction = new Faction(world);
		Unit unit = new Unit("Steegmans", new int[] {0, 0, 0}, 50, 50, 50, 50, world, faction, false);
		
		task.assignTo(unit);
		task.getAssignedVariables().put(assignment.getVariableName(), assignment);
		Iterator<MyStatement> iterator = task.iterator();
		System.out.println(iterator.next());
		((Print)iterator.next()).execute(unit);
	}

}
