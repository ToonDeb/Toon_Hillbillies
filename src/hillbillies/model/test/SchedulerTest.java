package hillbillies.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.*;

import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.statement.MyStatement;
import hillbillies.part3.programs.statement.NullStatement;

/**
 * A class of Scheduler Tests
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class SchedulerTest {
	private static int[][][] terrainType = 
		{{	{1,0,0},
			{1,0,0},
			{1,0,0}},
				
		 {	{1,0,0},
			{1,0,0},
			{1,1,1}},
		 
		 {	{1,0,0},
			{1,0,0},
			{1,0,0}}};
		
	private static World world;
	private Unit testUnit, otherUnit;
	private Task task1, task2, unassignedTask, unassignedTask2;
	private Faction faction1, faction2;
	private Scheduler scheduler, otherScheduler;
	
	@Before
	public void setUp(){
		
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		world = new World(terrainType, defaultListener);
		
		int[] position = {0,0,1};
		Log log = new Log(position, world);
		world.addLog(log);
		Boulder boulder = new Boulder(position, world);
		world.addBoulder(boulder);
		
		int[] unitPosition = {0,0,1};
		testUnit = new Unit("TestUnit", unitPosition, 50,50,50,50, false);
		world.addUnit(testUnit);

		otherUnit = new Unit("OtherUnit", unitPosition, 50,50,50,50, false);
		world.addUnit(otherUnit);

		
		Iterator<Faction> factions = world.getActiveFactions().iterator();
		faction1 = factions.next();
		faction2 = factions.next();
		
		try{
			testUnit.getFaction().removeUnit(testUnit);
		}
		catch (Exception e){
			
		}
		testUnit.setFaction(faction1);
		faction1.addUnit(testUnit);
		try{
			otherUnit.getFaction().removeUnit(otherUnit);
		}
		catch (Exception e){
			
		}
		otherUnit.setFaction(faction2);
		faction2.addUnit(otherUnit);
		
		try{
			otherUnit.getFaction().removeUnit(otherUnit);
		}
		catch (Exception e){
			
		}
		otherUnit.setFaction(faction1);
		faction1.addUnit(otherUnit);
		SourceLocation sourceLocation = new SourceLocation(0, 0);
		MyStatement statement = new NullStatement(sourceLocation);
		task1 = new Task("name", 0, statement);
		task2 = new Task("name", 100, statement);
		scheduler = faction1.getScheduler();
		scheduler.scheduleTask(task1);
		scheduler.scheduleTask(task2);
		unassignedTask = new Task("name", 1, statement);
		unassignedTask2 = new Task("name", 2, statement);
		otherScheduler = faction2.getScheduler();
	}
	
	@Test
	public void testConstructor$ValidCase(){
		Faction faction = new Faction(world);
		Scheduler scheduler = faction.getScheduler();
		assertTrue(scheduler.getFaction() == faction);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor$InvalidCase(){
		Faction faction = new Faction(world);
		new Scheduler(faction);
		fail("exception expected");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor$NullCase(){
		new Scheduler(null);
		fail("exception expected");
	}
	
	@Test
	public void testGetFaction(){
		Faction faction = new Faction(world);
		assertEquals(faction.getScheduler().getFaction(), faction);
	}
	
	@Test
	public void testIsValidFaction(){
		assertFalse(Scheduler.isValidFaction(null));
		assertFalse(Scheduler.isValidFaction(faction1));
	}
	
	@Test
	public void testGetTaskAt(){
		assertEquals(scheduler.getTaskAt(1), task2);
		assertEquals(scheduler.getTaskAt(2), task1);
		assertTrue(task2.getPriority() > task1.getPriority());
		assertTrue(scheduler.getNbTasks() == 2);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void testGetTaskAt$InvalidIndex(){
		scheduler.getTaskAt(3);
		fail("exception expected");
	}
	
	@Test
	public void testGetNbTasks(){
		assertEquals(scheduler.getNbTasks(), 2);
	}
	
	@Test
	public void testCanHaveAsTask$TrueCase(){
		assertTrue(scheduler.canHaveAsTask(unassignedTask));
		assertTrue(scheduler.canHaveAsTask(task1));
	}
	
	@Test
	public void testCanHaceAsTask$FalseCase(){
		assertFalse(scheduler.canHaveAsTask(null));
		unassignedTask.terminate();
		assertFalse(scheduler.canHaveAsTask(unassignedTask));
	}
	
	@Test
	public void testHasProperTasks(){
		assertTrue(scheduler.hasProperTasks());
	}
	
	@Test
	public void testHasAsTask(){
		assertTrue(scheduler.hasAsTask(task1));
		assertFalse(scheduler.hasAsTask(unassignedTask));
	}
	
	@Test
	public void testScheduleTask(){
		scheduler.scheduleTask(unassignedTask);
		assertTrue(scheduler.hasAsTask(unassignedTask));
		assertTrue(unassignedTask.hasAsScheduler(scheduler));
	}
	
	@Test
	public void testAddMultipleTasks(){
		java.util.List<Task> tasks = new ArrayList<Task>();
		tasks.add(unassignedTask);
		tasks.add(unassignedTask2);
		scheduler.addMultipleTasks(tasks);
		assertTrue(scheduler.hasAsTask(unassignedTask));
		assertTrue(unassignedTask.hasAsScheduler(scheduler));
		assertTrue(scheduler.hasAsTask(unassignedTask2));
		assertTrue(unassignedTask2.hasAsScheduler(scheduler));
	}
	
	@Test
	public void testRemoveTask(){
		task1.removeScheduler(scheduler);
		assertFalse(scheduler.hasAsTask(task1));
		assertFalse(task1.hasAsScheduler(scheduler));
	}
	
	@Test
	public void testRemoveMultipleTasks(){
		java.util.List<Task> tasks = new ArrayList<Task>();
		tasks.add(task1);
		tasks.add(task2);
		scheduler.removeMultipleTasks(tasks);
		assertFalse(scheduler.hasAsTask(task1));
		assertFalse(task1.hasAsScheduler(scheduler));
		assertFalse(scheduler.hasAsTask(task2));
		assertFalse(task2.hasAsScheduler(scheduler));
	}
	
	@Test
	public void testReplaceTask(){
		task1.assignTo(testUnit);
		scheduler.replaceTask(task1, unassignedTask);
		assertTrue(unassignedTask.hasAsScheduler(scheduler));
		assertTrue(scheduler.hasAsTask(unassignedTask));
		assertFalse(task1.hasAsScheduler(scheduler));
		assertFalse(scheduler.hasAsTask(task1));
		assertFalse(task1.isAssigned());
		assertTrue(testUnit.getTask() == null);
	}
	
	@Test
	public void testArePartOf(){
		java.util.List<Task> tasks = new ArrayList<Task>();
		tasks.add(task1);
		tasks.add(task2);
		assertTrue(scheduler.arePartOf(tasks));
		java.util.List<Task> tasks2 = new ArrayList<Task>();
		tasks2.add(unassignedTask);
		tasks2.add(unassignedTask2);
		assertFalse(scheduler.arePartOf(tasks2));
		tasks.add(unassignedTask);
		tasks.add(unassignedTask2);
		otherScheduler.scheduleTask(unassignedTask2);
		assertFalse(scheduler.arePartOf(tasks));
	}
	
	@Test
	public void testIterator(){
		Iterator<Task> iterator = scheduler.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), task2);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), task1);
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testGetNextAvailableTask(){
		assertEquals(scheduler.getNextAvailableTask(), task2);
		task2.assignTo(testUnit);
		assertEquals(scheduler.getNextAvailableTask(), task1);
	}
	
	@Test
	public void testGetAllTasks(){
		assertTrue(scheduler.getAllTasks().contains(task1));
		assertTrue(scheduler.getAllTasks().contains(task2));
		assertFalse(scheduler.getAllTasks().contains(unassignedTask));
	}
	
	 @Test
	 public void testInterruptTaskOf(){
		task1.assignTo(testUnit);
		scheduler.interruptTaskOf(testUnit);
		assertFalse(task1.isAssigned());
		assertFalse(testUnit.getTask() == task1);
	 }
	 
	 @Test
	 public void testAssignNextTaskTo(){
		 scheduler.assignNextTaskTo(testUnit);
		 assertTrue(testUnit.getTask() != null);
	 }
}
