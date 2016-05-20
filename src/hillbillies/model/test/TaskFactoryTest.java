package hillbillies.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
import hillbillies.part3.programs.TaskFactory;
import hillbillies.part3.programs.expression.logic.*;
import hillbillies.part3.programs.expression.position.*;
import hillbillies.part3.programs.expression.unit.*;
import hillbillies.part3.programs.statement.*;
import hillbillies.part3.programs.statement.action.*;

/**
 * A class of TaskFactory tests
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class TaskFactoryTest {

	private static int[][][] terrainType = 
		{{	{1,0,0},
			{1,0,0},
			{1,0,0}},
				
		 {	{1,0,0},
			{1,0,0},
			{1,1,1}},
		 
		 {	{1,0,0},
			{1,0,0},
			{1,3,0}}};
		
	private static World world;
	private Unit testUnit, otherUnit, otherFactionUnit;
	private Task task1, task2;
	private Faction faction1, faction2;
	private Scheduler scheduler;
	private Log log;
	private Boulder boulder;
	private TaskFactory factory;
	private SourceLocation sourceLocation;
	
	private AndExpression andExpr;
	private FalseExpression falseExpr;
	private IsAlive isAliveExpr;
	private IsCarryingItem isCarryingExpr;
	private IsEnemy isEnemyExpr;
	private IsFriend isFriendExpr;
	private IsPassable isPassableExpr;
	private IsSolid	isSolidExpr;
	private NotExpression notExpr;
	private OrExpression orExpr;
	private TrueExpression trueExpr;
	
	private BoulderPosition boulderPos;
	private HerePosition herePos;
	private LiteralPosition literalPos;
	private LogPosition	logPos;
	private NextToPosition nextToPos;
	private PositionOf posOfPos;
	private WorkshopPosition workshopPos;
	
	private AnyUnit anyUnit;
	private Enemy enemyUnit;
	private Friend friendUnit;
	private ThisUnit thisUnit;
	
//	private ReadBooleanVariable readBool;
//	private ReadPositionVariable readPos;
//	private ReadUnitVariable readUnit;
	
	
	@Before
	public void setUp(){
		factory = new TaskFactory();
		sourceLocation = new SourceLocation(0, 0);
		
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		world = new World(terrainType, defaultListener);
		
		int[] position = {0,0,1};
		log = new Log(position, world);
		world.addLog(log);
		boulder = new Boulder(position, world);
		world.addBoulder(boulder);
		
		int[] unitPosition = {0,0,1};
		testUnit = new Unit("TestUnit", unitPosition, 50,50,50,50, false);
		world.addUnit(testUnit);

		otherUnit = new Unit("OtherUnit", new int[] {1,1,1}, 50,50,50,50, false);
		world.addUnit(otherUnit);
		
		otherFactionUnit = new Unit("OtherFactionUnit", unitPosition, 50,50,50,50,false);
		world.addUnit(otherFactionUnit);

		
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
			otherFactionUnit.getFaction().removeUnit(otherFactionUnit);
		}
		catch (Exception e){
			
		}
		otherFactionUnit.setFaction(faction2);
		faction2.addUnit(otherFactionUnit);
		
		try{
			otherUnit.getFaction().removeUnit(otherUnit);
		}
		catch (Exception e){
			
		}
		otherUnit.setFaction(faction1);
		faction1.addUnit(otherUnit);
		MyStatement statement = new NullStatement(sourceLocation);
		task1 = new Task("name", 0, statement);
		task2 = new Task("name", 100, statement);
		scheduler = faction1.getScheduler();
		scheduler.scheduleTask(task1);
		scheduler.scheduleTask(task2);
		//unassignedTask = new Task("name", 1, statement);
		//unassignedTask2 = new Task("name", 2, statement);
		//otherScheduler = faction2.getScheduler();
		
		trueExpr = (TrueExpression) factory.createTrue(sourceLocation);
		falseExpr = (FalseExpression) factory.createFalse(sourceLocation);
		andExpr = (AndExpression) factory.createAnd(trueExpr, falseExpr, sourceLocation);
		isAliveExpr = (IsAlive) factory.createIsAlive(thisUnit, sourceLocation);
		isCarryingExpr = (IsCarryingItem) factory.createCarriesItem(thisUnit, sourceLocation);
		isEnemyExpr = (IsEnemy) factory.createIsEnemy(thisUnit, sourceLocation);
		isFriendExpr = (IsFriend) factory.createIsFriend(thisUnit, sourceLocation);
		isPassableExpr = (IsPassable) factory.createIsPassable(literalPos, sourceLocation);
		isSolidExpr = (IsSolid) factory.createIsSolid(literalPos, sourceLocation);
		notExpr = (NotExpression) factory.createNot(trueExpr, sourceLocation);
		orExpr = (OrExpression) factory.createOr(falseExpr, falseExpr, sourceLocation);
		
		boulderPos = (BoulderPosition) factory.createBoulderPosition(sourceLocation);
		herePos = (HerePosition) factory.createHerePosition(sourceLocation);
		literalPos = (LiteralPosition) factory.createLiteralPosition(1, 1, 1, sourceLocation);
		logPos = (LogPosition) factory.createLogPosition(sourceLocation);
		nextToPos = (NextToPosition) factory.createNextToPosition(herePos, sourceLocation);
		posOfPos = (PositionOf) factory.createPositionOf(thisUnit, sourceLocation);
		workshopPos = (WorkshopPosition) factory.createWorkshopPosition(sourceLocation);
		
		anyUnit = (AnyUnit) factory.createAny(sourceLocation);
		enemyUnit = (Enemy) factory.createEnemy(sourceLocation);
		friendUnit = (Friend) factory.createFriend(sourceLocation);
		thisUnit = (ThisUnit) factory.createThis(sourceLocation);
		
		//readBool = (ReadBooleanVariable) factory.createReadVariable("bool", sourceLocation);
		//readPos	= (ReadPositionVariable) factory.createReadVariable("pos", sourceLocation);
		//readUnit = (ReadUnitVariable) factory.createReadVariable("unit", sourceLocation);
	
		
	}
	
	@Test
	public void testCreatePositionOf(){
		posOfPos = (PositionOf) factory.createPositionOf(thisUnit, sourceLocation);
		assertTrue(Arrays.equals(posOfPos.evaluateExpression(testUnit), 
				testUnit.getCubePosition()));
	}
	
	@Test
	public void testCreateTask(){
		NullStatement nul = new NullStatement(sourceLocation);
		factory.createTasks("name", 0, nul, null);
	}
	
	@Test
	public void testCreateTrue(){
		trueExpr = (TrueExpression) factory.createTrue(sourceLocation);
		assertTrue(trueExpr.evaluateExpression(testUnit));
		assertTrue(trueExpr.getSourceLocation() == sourceLocation);
	}
	
	@Test
	public void testCreateFalse(){
		falseExpr = (FalseExpression) factory.createFalse(sourceLocation);
		assertFalse(falseExpr.evaluateExpression(testUnit));
		assertEquals(falseExpr.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateAny(){
		anyUnit = (AnyUnit) factory.createAny(sourceLocation);
		assertTrue(world.hasAsUnit(anyUnit.evaluateExpression(testUnit)));
		assertEquals(anyUnit.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateEnemy(){
		enemyUnit = (Enemy) factory.createEnemy(sourceLocation);
		assertTrue(enemyUnit.evaluateExpression(testUnit).getFaction() !=
				testUnit.getFaction());
		assertEquals(enemyUnit.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateFriend(){
		friendUnit = (Friend) factory.createFriend(sourceLocation);
		assertTrue(friendUnit.evaluateExpression(testUnit).getFaction() ==
				otherUnit.getFaction());
		assertEquals(friendUnit.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateThis(){
		thisUnit = (ThisUnit) factory.createThis(sourceLocation);
		assertTrue(thisUnit.evaluateExpression(testUnit) == testUnit);
		assertEquals(thisUnit.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateLiteralPosition(){
		literalPos = (LiteralPosition) factory.createLiteralPosition(1, 1, 1, sourceLocation);
		assertTrue(Arrays.equals(literalPos.evaluateExpression(testUnit), new int[] {1,1,1}));
		assertEquals(literalPos.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateNextToPosition(){
		nextToPos = (NextToPosition) factory.createNextToPosition(herePos, sourceLocation);
		assertTrue(testUnit.isNeighbouringCube(nextToPos.evaluateExpression(testUnit)));
	}
	
	@Test
	public void testCreateSelectedPosition(){
		// only one person, not required
	}
	
	@Test
	public void testCreateWorkshop(){
		workshopPos = (WorkshopPosition) factory.createWorkshopPosition(sourceLocation);
		assertEquals(workshopPos.evaluateExpression(testUnit), world.getWorkshopLocation());
		assertEquals(workshopPos.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateBoulderPosition(){
		boulderPos = (BoulderPosition) factory.createBoulderPosition(sourceLocation);
		assertTrue(Arrays.equals(boulderPos.evaluateExpression(testUnit), 
				world.getBoulders().iterator().next().getCubePosition()));
		assertEquals(boulderPos.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateLogPosition(){
		logPos = (LogPosition) factory.createLogPosition(sourceLocation);
		assertTrue(Arrays.equals(logPos.evaluateExpression(testUnit), 
				world.getLogs().iterator().next().getCubePosition()));
		assertEquals(logPos.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateHerePosition(){
		herePos = (HerePosition) factory.createHerePosition(sourceLocation);
		assertTrue(Arrays.equals(herePos.evaluateExpression(testUnit), 
				testUnit.getCubePosition()));
		assertEquals(herePos.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateOr(){
		orExpr = (OrExpression) factory.createOr(trueExpr, falseExpr, sourceLocation);
		assertTrue(orExpr.evaluateExpression(testUnit));
		orExpr = (OrExpression) factory.createOr(falseExpr, falseExpr, sourceLocation);
		assertFalse(orExpr.evaluateExpression(testUnit));
		assertEquals(orExpr.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateAnd(){
		andExpr = (AndExpression) factory.createAnd(trueExpr, trueExpr, sourceLocation);
		assertTrue(andExpr.evaluateExpression(testUnit));
		andExpr = (AndExpression) factory.createAnd(falseExpr, trueExpr, sourceLocation);
		assertFalse(andExpr.evaluateExpression(testUnit));
		assertEquals(andExpr.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateNot(){
		notExpr = (NotExpression) factory.createNot(trueExpr, sourceLocation);
		assertFalse(notExpr.evaluateExpression(testUnit));
		notExpr = (NotExpression) factory.createNot(falseExpr, sourceLocation);
		assertTrue(notExpr.evaluateExpression(testUnit));
		assertEquals(notExpr.getSourceLocation(), sourceLocation);
	}
	
	@Test
	public void testCreateCarriesItem(){
		isCarryingExpr = (IsCarryingItem) factory.createCarriesItem(thisUnit, sourceLocation);
		assertFalse(isCarryingExpr.evaluateExpression(testUnit));
		testUnit.workAt(testUnit.getCubePosition());
		while(!testUnit.isCarryingItem()){
			testUnit.advanceTime(0.15);
		}
		assertTrue(isCarryingExpr.evaluateExpression(testUnit));
	}
	
	@Test
	public void testCreateIsAlive(){
		isAliveExpr = (IsAlive) factory.createIsAlive(thisUnit, sourceLocation);
		assertTrue(isAliveExpr.evaluateExpression(testUnit));
		testUnit.terminate();
		assertFalse(isAliveExpr.evaluateExpression(testUnit));
	}
	
	@Test
	public void testCreateIsEnemy(){
		isEnemyExpr = (IsEnemy) factory.createIsEnemy(friendUnit, sourceLocation);
		assertFalse(isEnemyExpr.evaluateExpression(testUnit));
		isEnemyExpr = (IsEnemy) factory.createIsEnemy(enemyUnit, sourceLocation);
		assertTrue(isEnemyExpr.evaluateExpression(testUnit));
	}
	
	@Test
	public void testCreateIsFriend(){
		isFriendExpr = (IsFriend) factory.createIsFriend(friendUnit, sourceLocation);
		assertTrue(isFriendExpr.evaluateExpression(testUnit));
		isFriendExpr = (IsFriend) factory.createIsFriend(enemyUnit, sourceLocation);
		assertFalse(isFriendExpr.evaluateExpression(testUnit));
	}
	
	@Test
	public void testCreateIsPassable(){
		isPassableExpr = (IsPassable) factory.createIsPassable(literalPos, sourceLocation);
		assertTrue(isPassableExpr.evaluateExpression(testUnit));
		literalPos = (LiteralPosition) factory.createLiteralPosition(0, 0, 0, sourceLocation);
		isPassableExpr = (IsPassable) factory.createIsPassable(literalPos, sourceLocation);
		assertFalse(isPassableExpr.evaluateExpression(testUnit));
	}
	
	@Test
	public void testCreateIsSolid(){
		isSolidExpr = (IsSolid) factory.createIsSolid(literalPos, sourceLocation);
		assertFalse(isSolidExpr.evaluateExpression(testUnit));
		literalPos = (LiteralPosition) factory.createLiteralPosition(0, 0, 0, sourceLocation);
		isSolidExpr = (IsSolid) factory.createIsSolid(literalPos, sourceLocation);
		assertTrue(isSolidExpr.evaluateExpression(testUnit));
	}
	
	@Test
	public void testCreateAttack(){
		Attack attack = (Attack) factory.createAttack(enemyUnit, sourceLocation);
		attack.execute(world, testUnit);
		assertTrue(testUnit.isAttacking());
	}
	
	@Test
	public void testCreateFollow(){
		Follow follow = (Follow) factory.createFollow(friendUnit, sourceLocation);
		follow.execute(world, testUnit);
		assertTrue(testUnit.isFollowing()||follow.hasArrived());
	}
	
	@Test
	public void testCreateWork(){
		Work work = (Work) factory.createWork(nextToPos, sourceLocation);
		work.execute(world, testUnit);
		assertTrue(testUnit.isWorking());
	}
	
	@Test
	public void testCreateMoveTo(){
		MoveTo move = (MoveTo) factory.createMoveTo(literalPos, sourceLocation);
		move.execute(world, testUnit);
		assertTrue(testUnit.isMoving());
	}
	
	@Test
	public void testCreateSequence(){
		NullStatement statement1 = new NullStatement(sourceLocation);
		Work work = (Work) factory.createWork(nextToPos, sourceLocation);
		List<MyStatement> statements = new ArrayList<MyStatement>();
		statements.add(statement1);
		statements.add(work);
		Sequence sequence = (Sequence) factory.createSequence(statements, sourceLocation);
		assertTrue(sequence.hasAsMyStatement(statement1));
		assertTrue(sequence.hasAsMyStatement(work));
		assertTrue(sequence.iterator(world, testUnit).hasNext());
		Iterator<MyStatement> iterator = sequence.iterator(world, testUnit);
		assertTrue(iterator.next() == statement1);
		assertTrue(iterator.hasNext());
		assertTrue(iterator.next() == work);
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testCreatePrint(){
		Print print = (Print) factory.createPrint(herePos, sourceLocation);
		print.execute(testUnit);
		// see console for output
	}
	
	@Test
	public void testCreateIf(){
		Print print = (Print) factory.createPrint(herePos, sourceLocation);
		Print print2 = (Print) factory.createPrint(nextToPos, sourceLocation);
		IfThenElse ifthen = (IfThenElse) factory.createIf(trueExpr, print, print2, sourceLocation);
		Iterator<MyStatement> iterator = ifthen.iterator(world, testUnit);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), print);
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testCreateWhile(){
		Print print = (Print) factory.createPrint(herePos, sourceLocation);
		WhileLoop whileloop = (WhileLoop) factory.createWhile(trueExpr, print, sourceLocation);
		Iterator<MyStatement> iterator = whileloop.iterator(world, testUnit);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), print);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), print);
		((Print)iterator.next()).execute(testUnit);
		whileloop = (WhileLoop) factory.createWhile(falseExpr, print, sourceLocation);
		iterator = whileloop.iterator(world, testUnit);
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testCreateAssignment(){
//		Assignment boolAssignment = (Assignment) factory.createAssignment("bool", trueExpr, sourceLocation);
//		Assignment posAssignemnt = (Assignment) factory.createAssignment("pos", herePos, sourceLocation);
//		Assignment unitAssignment = (Assignment) factory.createAssignment("unit", thisUnit, sourceLocation);
//		
//		ReadBooleanVariable readBool = (ReadBooleanVariable) factory.createReadVariable("bool", sourceLocation);
//		ReadPositionVariable readPos	= (ReadPositionVariable) factory.createReadVariable("pos", sourceLocation);
//		ReadUnitVariable readUnit = (ReadUnitVariable) factory.createReadVariable("unit", sourceLocation);
//		
//		boolAssignment.
//		assertTrue(readBool.evaluateExpression(testUnit));
//		assertTrue(Arrays.equals(readPos.evaluateExpression(testUnit), testUnit.getCubePosition()));
//		assertEquals(readUnit.evaluateExpression(testUnit), testUnit);
//	
	}
}
