package hillbillies.model.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Test;

import hillbillies.model.*;

import ogp.framework.util.Util;

import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.statement.MyStatement;
import hillbillies.part3.programs.statement.NullStatement;

import org.junit.*;

import javax.vecmath.*;

/**
 * A class of unit tests
 *
 * @author  ...
 * @version 1.0
 */
public class UnitTest {
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
	private Unit testUnit, otherUnit, farUnit, otherFactionUnit;
	private Task task;
	
	@BeforeClass
	public static void setUpBeforeClass(){
		
	}
	
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

		int[] farPosition = {2,2,1};
		farUnit = new Unit("TestUnit", farPosition, 50,50,50,50, false);
		world.addUnit(farUnit);

		
		int[] besidesPosition = {1,0,1};
		otherFactionUnit = new Unit("OtherFactionUnit", besidesPosition, 50,50,50,50, false);
		world.addUnit(otherFactionUnit);
		
		Iterator<Faction> factions = world.getActiveFactions().iterator();
		Faction faction1 = factions.next();
		Faction faction2 = factions.next();
		
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
		SourceLocation sourceLocation = new SourceLocation(0, 0);
		MyStatement statement = new NullStatement(sourceLocation);
		task = new Task("name", 0, statement);
	}
	
	@Test
	public void testConstructorLegalCase(){
		int[] position = {0, 0, 1};
		Vector3d vectorPosition = new Vector3d(0.5,0.5,1.5);
		Unit unit = new Unit("TestSubject", position, 50, 50, 50, 50, false);

		
		assertEquals(unit.getPosition(), vectorPosition);
		assertEquals(unit.getStrength(), 50);
		assertEquals(unit.getAgility(), 50);
		assertEquals(unit.getWeight(), 50);
		assertEquals(unit.getToughness(), 50);
		assertEquals(unit.getName(), "TestSubject");
		assertFalse(unit.getDefaultBoolean());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor$IllegalNameCase(){
		int[] position = {1,1,1};
		new Unit("testSubject", position, 50,50,50,50, false);
		fail("Exception Expected!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor$IllegalPositionCase()
			throws IllegalArgumentException{
		int[] position = {5,1,1};
		Unit unit = new Unit("TestSubject", position, 50,50,50,50,false);
		world.addUnit(unit);
		fail("Exception Expected!");
	}
	
	@Test
	public void testConstructor$IllegalStrengthCase(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 50,24,50,50,false);
		assertEquals(unit.getStrength(), 25);
	}
	
	@Test
	public void testConstructor$IllegalAgilityCase(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 50,50,102,50,false);
		assertEquals(unit.getAgility(), 25);
	}
	
	@Test
	public void testConstructor$IllegalToughnessCase(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 50,50,50,0,false);
		assertEquals(unit.getToughness(), 25);
	}
	
	@Test
	public void testConstructor$IllegalWeightCase(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 30,50,50,50,false);
		assertEquals(unit.getWeight(), 50);
	}
	
	@Test
	public void testConstructor$DefaultBooleanTrue(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 50,50,50,50, true);
		assertTrue(unit.getDefaultBoolean());
	}
	
	/* 	Supertype GameObject tests	*/
	
	@Test 
	public void testGetPosition(){
		assertEquals(testUnit.getPosition(), new Vector3d(0.5,0.5,1.5));
	}
	
	@Test
	public void testIsValidPosition$TrueCase(){
		Vector3d position = new Vector3d(1.5,1.5,0.5);
		assertTrue(GameObject.isValidPosition(position, world));
	}
	
	@Test
	public void testIsValidPosition$FalseCase(){
		Vector3d position = new Vector3d(-1.5,1.5,0.5);
		assertFalse(GameObject.isValidPosition(position, world));
	}
	
	@Test
	public void testToCubePosition(){
		Vector3d position = new Vector3d(2.9,1.1,3.5);
		assertTrue(GameObject.toCubePosition(position)[0] == 2 &&
				GameObject.toCubePosition(position)[1] == 1 &&
				GameObject.toCubePosition(position)[2] == 3 );
	}
	
	@Test
	public void testToVectorPosition(){
		int[] position = new int[] {2,1,3};
		assertTrue(GameObject.toVectorPosition(position).x == 2.5d &&
				GameObject.toVectorPosition(position).y == 1.5d &&
				GameObject.toVectorPosition(position).z == 3.5d);
	}
	
	@Test
	public void testSetAtPosition$ValidCase(){
		testUnit.setAtPosition(new int[] {1,1,1});
		assertTrue(Arrays.equals(testUnit.getCubePosition(), new int[] {1,1,1}));
		assertTrue(testUnit.getPosition().equals(new Vector3d(1.5,1.5,1.5)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAtPosition$InvalidCase(){
		testUnit.setAtPosition(new int[] {5,5,5});
		fail("exception expected");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAtPosition$NullCase(){
		testUnit.setAtPosition(null);
		fail("exception expected");
	}
	
	@Test
	public void testSetPosition$ValidCase(){
		testUnit.setPosition(new Vector3d(2.9,1.1,0.5));
		assertTrue(Arrays.equals(testUnit.getCubePosition(), new int[] {2,1,0}));
		assertTrue(testUnit.getPosition().equals(new Vector3d(2.9,1.1,0.5)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetPosition$InvalidCase(){
		testUnit.setPosition(new Vector3d(2.9,1.1,3.5));
		fail("exception expected");
	}
	
	@Test
	public void testGetCubePosition(){
		assertTrue(Arrays.equals(testUnit.getCubePosition(), new int[] {0,0,1}));
	}
	
	@Test
	public void testUpdateFall(){
		testUnit.setAtPosition(new int[] {0,0,2});
		world.advanceTime(0.0001);
		assertTrue(testUnit.isFalling());
		while(testUnit.isFalling()){
			testUnit.updateFall(0.1);
		}
		assertTrue(testUnit.getHP() < testUnit.getMaxHP());
		assertFalse(Arrays.equals(testUnit.getCubePosition(), new int[] {0,0,2}));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateFall$InvalidCase(){
		testUnit.updateFall(-0.01);
		fail("exception expected");
	}
	
//	@Test
//	public void testGetFallDepth(){
//		testUnit.setAtPosition(new int[] {0,0,2});
//		world.advanceTime(0.0001);
//		assertTrue(testUnit.getFallDepth() != 0);
//	}
	
	@Test
	public void testTakeFallDamage(){
		testUnit.setAtPosition(new int[] {0,0,2});
		world.advanceTime(0.0001);
		testUnit.takeFallDamage(2);
		assertTrue(testUnit.getHP() == testUnit.getMaxHP() - 20);
		assertTrue(testUnit.getStatus() == UnitStatus.IDLE);
	}
	
	@Test
	public void testStartFall$ValidCase(){
		testUnit.setAtPosition(new int[] {0,0,2});
		testUnit.startFall();
		assertTrue(testUnit.isFalling());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStartFall$InvalidCase(){
		testUnit.startFall();
		fail("exception expected");
	}
	
	@Test
	public void testIsFalling$TrueCase(){
		testUnit.setAtPosition(new int[] {0,0,2});
		testUnit.startFall();
		assertTrue(testUnit.isFalling());
	}
	
	@Test
	public void testIsFalling$FalseCase(){
		assertFalse(testUnit.isFalling());
	}
	
	@Test
	public void testGetCubePositionBelow(){
		int[] belowPos = testUnit.getCubePositionBelow();
		assertTrue(Arrays.equals(belowPos, new int[] {0,0,0}));
	}
	
	@Test
	public void testIsNeighbouringCube$TrueCase(){
		int[] belowPos = testUnit.getCubePositionBelow();
		assertTrue(testUnit.isNeighbouringCube(belowPos));
	}
	
	@Test
	public void testIsNeighbouringCube$FalseCase(){
		int[] pos = {2,2,2};
		assertFalse(testUnit.isNeighbouringCube(pos));
	}
	
	@Test
	public void testIsNeighbouringCube$FalsePosCase(){
		int[] pos = {3,2,2};
		assertFalse(testUnit.isNeighbouringCube(pos));
	}
	
	@Test
	public void testGetWorld(){
		assertTrue(testUnit.getWorld() == world);
	}
	
	@Test
	public void testIsValidWorld$TrueCase(){
		assertTrue(testUnit.isValidWorld(null));
		assertTrue(testUnit.isValidWorld(world));
	}
	
	@Test
	public void testIsValidWorld$FalseCase(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		World otherWorld = new World(terrainType, defaultListener);
		int i = 0;
		while(i < 100){
			otherWorld.spawnUnit(false);
			i+=1;
			
		}
		assertFalse(testUnit.isValidWorld(otherWorld));
	}
	
	@Test
	public void testSetWorld$ValidCase(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		World otherWorld = new World(terrainType, defaultListener);
		testUnit.setWorld(otherWorld);
		assertTrue(testUnit.getWorld() == otherWorld);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetWorld$InvalidCase(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		World otherWorld = new World(terrainType, defaultListener);
		int i = 0;
		while(i < 100){
			otherWorld.spawnUnit(false);
			i+=1;
		}
		testUnit.setWorld(otherWorld);
		fail("exception expected");
	}
	
	
	/* End Supertype GameObject tests */
	
	@Test
	public void testfacadeMoveToAdjacent$LegalCase(){
		testUnit.facadeMoveToAdjacent(1, 0, 0);
		assertTrue(testUnit.isMoving());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testfacadeMoveToAdjacent$IllegalArgumentCase(){
		testUnit.facadeMoveToAdjacent(0, 0, 2);
		fail("Exception Expected!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testfacadeMoveToAdjacent$IllegalNewPositionCase(){
		testUnit.facadeMoveToAdjacent(0, 0, -1);
		fail("Exception Expected!");
	}
	
	@Test
	public void testMoveTo$LegalCase(){
		int[] destination =  {2,2,1};
		testUnit.moveTo(destination);
		assertTrue(testUnit.isMoving());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMoveTo$IllegalDestinationCase(){
		int[] destination = {1,5,1};
		testUnit.moveTo(destination);
		fail("Exception Expected!");
	}
	
	@Test
	public void testStartFollowing$LegalCase(){
		testUnit.startFollowing(farUnit);
		assertTrue(testUnit.isFollowing());
	}
	
	@Test (expected = NullPointerException.class)
	public void testStartFollowing$NullCase(){
		testUnit.startFollowing(null);
		fail("exception expected");
	}
	
	@Test
	public void testIsFollowing$TrueCase(){
		testUnit.startFollowing(farUnit);
		assertTrue(testUnit.isFollowing());
	}
	
	@Test
	public void testIsFollowing$FalseCase(){
		assertFalse(testUnit.isFollowing());
	}
	@Test
	public void testIsMoving$TrueCase(){
		int[] destination = {1,1,1};
		testUnit.moveTo(destination);
		assertTrue(testUnit.isMoving());
	}
	
	@Test
	public void testIsMoving$FalseCase(){
		assertFalse(testUnit.isMoving());
	}
	
	@Test
	public void testResetPath(){
		testUnit.moveTo(farUnit.getCubePosition());
		testUnit.resetPath();
		assertTrue(testUnit.getPath() == null);
	}
	
	@Test
	public void testGetPath(){
		testUnit.moveTo(farUnit.getCubePosition());
		assertTrue(testUnit.getPath() != null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTakeFallDamage$IllegalStateCase(){
		testUnit.takeFallDamage(1);
		fail("Exception Expected!");
	}
	
	@Test
	public void testGetSpeed$StandStillCase(){
		assertTrue(Util.fuzzyEquals(testUnit.getSpeed(), 0));
	}
	
	@Test
	public void testGetSpeed$MovingCase(){
		testUnit.facadeMoveToAdjacent(0, 1, 0);
		assertTrue(Util.fuzzyEquals(testUnit.getSpeed(), 1.5));
	}
	
	@Test
	public void testGetSpeed$MovingUpCase(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 50,50,50,50, true);
		world.addUnit(unit);
		unit.facadeMoveToAdjacent(0, 0, 1);
		assertTrue(Util.fuzzyEquals(unit.getSpeed(), 0.75));
	}
	
	@Test
	public void testGetSpeed$MovingDownCase(){
		int[] position = {1,1,2};
		Unit unit = new Unit("TestSubject", position, 50,50,50,50, false);
		world.addUnit(unit);
		unit.facadeMoveToAdjacent(0, 0, -1);
		assertTrue(Util.fuzzyEquals(unit.getSpeed(), 1.8));
	}
	
	@Test
	public void testGetSpeed$SpringtingCase(){
		testUnit.facadeMoveToAdjacent(0, 1, 0);
		testUnit.startSprint();
		assertTrue(Util.fuzzyEquals(testUnit.getSpeed(), 3));
	}
	
	@Test
	public void testStartSprint$LegalCase(){
		testUnit.facadeMoveToAdjacent(0, 1, 0);
		testUnit.startSprint();
		assertTrue(testUnit.isSprinting());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStartSprint$IllegalCase(){
		testUnit.startSprint();
		fail("Exception Expected!");
	}
	
	@Test
	public void testStopSprint$LegalCase(){
		testUnit.facadeMoveToAdjacent(0, 1, 0);
		testUnit.startSprint();
		testUnit.stopSprint();
		assertFalse(testUnit.isSprinting());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStopSprint$IllegalCase(){
		testUnit.stopSprint();
		fail("Exception Expected!");
	}
	
	@Test
	public void testGetName(){
		assertEquals(testUnit.getName(),"TestUnit");
	}
	
	@Test
	public void testGetName$LegalCase(){
		testUnit.setName("GLaDOS");
		assertEquals(testUnit.getName(), "GLaDOS");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetName$NullCase(){
		testUnit.setName(null);
		fail("Exception Expected!");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetName$IllegalCharCase(){
		testUnit.setName("De$ta");
		fail("Exception Expected!");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetName$IllegalFirstCharCase(){
		testUnit.setName("lowerCase");
		fail("Exception Expected!");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetName$IllegalLengthCase(){
		testUnit.setName("N");
		fail("Exception Expected!");
	}
	
	@Test
	public void testGetOrientation(){
		assertTrue(Util.fuzzyEquals(testUnit.getOrientation(), (double) Math.PI/2));
		testUnit.attack(otherFactionUnit);
		assertTrue(Util.fuzzyEquals(testUnit.getOrientation(), 0));
		assertTrue(Util.fuzzyEquals(otherFactionUnit.getOrientation(), Math.PI));
	}
	
	@Test
	public void testGetWeight(){
		assertEquals(testUnit.getWeight(), 50);
	}
	
	@Test
	public void testSetWeight$ValidValue(){
		testUnit.setAgility(50);
		testUnit.setStrength(50);
		testUnit.setWeight(100);
		assertEquals(testUnit.getWeight(), 100);
	}
	
	@Test
	public void testSetWeight$InvalidValue(){
		testUnit.setAgility(100);
		testUnit.setStrength(100);
		testUnit.setWeight(50);
		assertEquals(testUnit.getWeight(), 100);
	}
	
	@Test
	public void testGetStrength(){
		assertEquals(testUnit.getStrength(), 50);
	}
	
	
	@Test
	public void testGetAgility(){
		assertEquals(testUnit.getAgility(), 50);
	}
	
	@Test
	public void testGetToughness(){
		assertEquals(testUnit.getToughness(), 50);
	}
	
	@Test
	public void testGetHP(){
		assertEquals(testUnit.getHP(), 50);
	}
	
	@Test
	public void testGetMaxHP(){
		assertEquals(testUnit.getMaxHP(), 50);
	}
	
	@Test
	public void testGetMaxStamina(){
		assertEquals(testUnit.getMaxStamina(), 50);
	}
	
	@Test
	public void testWorkAt$LegalCase(){
		int[] workTarget = {1,0,1};
		testUnit.workAt(workTarget);
		assertTrue(Util.fuzzyEquals(testUnit.getOrientation(), 0));
		assertTrue(testUnit.isWorking());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testWorkAt$IllegalPositionCase(){
		int[] workTarget = {2,0,1};
		testUnit.workAt(workTarget);
	}
	
	@Test
	public void testIsWorking$TrueCase(){
		int[] workTarget = {1,0,1};
		testUnit.workAt(workTarget);
		assertTrue(testUnit.isWorking());
	}
	
	@Test
	public void testIsWorking$FalseCase(){
		assertFalse(testUnit.isWorking());
	}
	
	@Test
	public void testIsCarryingLog$FalseCase(){
		assertFalse(testUnit.isCarryingLog());
	}
	
	@Test
	public void testIsCarryingBoulder$FalseCase(){
		assertFalse(testUnit.isCarryingBoulder());
	}
	
	@Test
	public void testAttack$ValidCase(){
		testUnit.attack(otherFactionUnit);
		assertTrue(testUnit.isAttacking());
		assertTrue((otherFactionUnit.getStatus() == UnitStatus.DEFENDING) ||
				(otherFactionUnit.getStatus() == UnitStatus.DODGING));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAttack$InvalidPositionCase(){
		testUnit.attack(farUnit);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAttack$SameFactionCase(){
		testUnit.attack(otherUnit);
	}
	
	@Test
	public void testIsAttacking$TrueCase(){
		testUnit.attack(otherFactionUnit);
		assertTrue(testUnit.isAttacking());
	}
	
	@Test
	public void testIsAttackig$FalseCase(){
		assertFalse(testUnit.isAttacking());
	}
	
	@Test
	public void testRest$ValidCase(){
		testUnit.rest();
		assertTrue(testUnit.isResting());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRest$InvalidAttackCase(){
		testUnit.attack(otherFactionUnit);
		testUnit.rest();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRest$InvalidDefendCase(){
		testUnit.attack(otherFactionUnit);
		otherFactionUnit.rest();
	}
	
	@Test
	public void testIsResting$TrueCase(){
		testUnit.rest();
		assertTrue(testUnit.isResting());
	}
	
	@Test
	public void testIsResting$FalseCase(){
		assertFalse(testUnit.isResting());
	}
	
	@Test
	public void testGetStatus(){
		assertTrue(testUnit.getStatus() == UnitStatus.IDLE);
	}
	
	@Test
	public void testIsValidTime$TrueCase(){
		assertTrue(Unit.isValidTime(0.1));
	}
	
	@Test
	public void testIsValidTime$TooHighCase(){
		assertFalse(Unit.isValidTime(1d));
	}
	
	@Test
	public void testIsValidTime$TooLowCase(){
		assertFalse(Unit.isValidTime(-0.1d));
	}
	
	@Test
	public void testTerminate(){
		Faction faction = testUnit.getFaction();
		testUnit.terminate();
		assertTrue(testUnit.isTerminated());
		assertFalse(testUnit.isCarryingItem());
		assertTrue(testUnit.getStatus() == UnitStatus.IDLE);
		assertTrue(testUnit.getFaction() == null);
		assertFalse(faction.hasAsUnit(testUnit));
	}
	
	@Test
	public void testIsTerminated$TrueCase(){
		testUnit.terminate();
		assertTrue(testUnit.isTerminated());
	}
	
	@Test
	public void testIsTerminated$FalseCase(){
		assertFalse(testUnit.isTerminated());
	}
	
	@Test
	public void testStartAndGetDefaultBehaviour(){
		testUnit.startDefaultBehaviour();
		assertTrue(testUnit.getDefaultBoolean());
	}
	
	@Test
	public void testStopAndGetDefaultBehaviour(){
		testUnit.startDefaultBehaviour();
		testUnit.stopDefaultBehaviour();
		assertFalse(testUnit.getDefaultBoolean());
	}
	
	@Test
	public void testGetFaction(){
		assertTrue(testUnit.getWorld().hasAsFaction(testUnit.getFaction()));
	}
	
	@Test
	public void testIsValidFaction$TrueCase(){
		assertTrue(Unit.isValidFaction(testUnit.getFaction()));
	}
	
	@Test
	public void testGetExperience(){
		assertEquals(testUnit.getExperience(), 0);
	}
	
	@Test
	public void testInterruptTask(){
		testUnit.getFaction().getScheduler().scheduleTask(task);
		testUnit.advanceTime(0.001);
		testUnit.interruptTask();
		assertTrue(testUnit.getTask() == null);
		assertTrue(task.getUnit() == null);
	}
	
	@Test
	public void testGetTask(){
		testUnit.getFaction().getScheduler().scheduleTask(task);
		testUnit.advanceTime(0.001);
		assertTrue(testUnit.getTask() == task);
	}
	
	@Test
	public void testSetTask$ValidCase(){
		testUnit.setTask(task);
		task.setUnit(testUnit);
		assertTrue(testUnit.getTask() == task);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetTask$InvalidCase(){
		task.setUnit(testUnit);
		testUnit.setTask(task);
		fail("exception expected");
	}
	
	@Test
	public void testStartAction(){
		testUnit.startAction();
		assertFalse(testUnit.hasFinishedAction());
	}
	
	@Test
	public void testHasFinishedAction(){
		assertTrue(testUnit.hasFinishedAction());
	}
	
}
