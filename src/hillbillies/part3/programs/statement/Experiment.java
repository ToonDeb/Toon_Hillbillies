package hillbillies.part3.programs.statement;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.antlr.v4.parse.ANTLRParser.action_return;

import hillbillies.model.Faction;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.TaskFactory;
import hillbillies.part3.programs.TaskParser;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.expression.logic.FalseExpression;
import hillbillies.part3.programs.expression.logic.TrueExpression;
import hillbillies.part3.programs.expression.position.LiteralPosition;

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
	public static void main(String[] args) {
		
		int[][][] terrainType = 
			{{	{1,0,0},
				{1,0,0},
				{1,0,0}},
					
			 {	{1,0,0},
				{1,0,0},
				{1,0,0}},
			 
			 {	{1,1,1},
				{1,0,0},
				{0,0,0}}};
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		World testWorld = new World(terrainType, defaultListener);
		Faction faction1 = new Faction(testWorld);
		testWorld.addFaction(faction1);
		int[] unitPosition = {0,0,1};
		Unit testUnit = new Unit("TestUnit", unitPosition, 50,50,50,50, testWorld, faction1, false);
		
		
		ITaskFactory<MyExpression, MyStatement, Task> factory = new TaskFactory();
		List<Task> task = TaskParser.parseTasksFromString(
				"name: \"operate workshop\"\npriority: -100\nactivities: if (is_solid boulder || carries_item this) then moveTo position_of this; moveTo boulder; fi",
				factory, Collections.emptyList());
		Task test = task.get(0);
		System.out.println(test.getStatement().iterator(testWorld, testUnit).hasNext());
		System.out.println("yay, i guess?");
		
	}

}
