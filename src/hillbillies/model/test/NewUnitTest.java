package hillbillies.model.test;

import static org.junit.Assert.*;
import org.junit.Test;

import hillbillies.model.*;

import ogp.framework.util.Util;

import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;

import org.junit.*;

import javax.vecmath.*;

/**
 * A class of unit tests
 *
 * @author  ...
 * @version 1.0
 */
public class NewUnitTest {
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
	private static Faction faction1;
	private static Faction faction2;
	private Log log;
	private Boulder boulder;
	private Unit testUnit, otherUnit, farUnit, otherFactionUnit;
	
	@BeforeClass
	public static void setUpBeforeClass(){
		
	}
	
	@Before
	public void setUp(){
		
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		world = new World(terrainType, defaultListener);
		
		faction1 = new Faction(world);
		faction2 = new Faction(world);
		world.addFaction(faction1);
		world.addFaction(faction2);	
		
		
		int[] position = {0,0,1};
		Log log = new Log(position, world);
		world.addLog(log);
		Boulder boulder = new Boulder(position, world);
		world.addBoulder(boulder);
		
		int[] unitPosition = {0,0,1};
		testUnit = new Unit("TestUnit", unitPosition, 50,50,50,50, world, faction1, false);
		world.addUnit(testUnit);
		faction1.addUnit(testUnit);
		
		otherUnit = new Unit("OtherUnit", unitPosition, 50,50,50,50, world, faction1, false);
		world.addUnit(otherUnit);
		faction1.addUnit(otherUnit);
		
		int[] farPosition = {2,2,1};
		farUnit = new Unit("TestUnit", farPosition, 50,50,50,50, world, faction2, false);
		world.addUnit(farUnit);
		faction2.addUnit(farUnit);
		
		int[] besidesPosition = {1,0,1};
		otherFactionUnit = new Unit("OtherFactionUnit", besidesPosition, 50,50,50,50, world, faction2, false);
		world.addUnit(otherFactionUnit);
		faction2.addUnit(otherFactionUnit);
	}
	
	@Test
	public void testConstructorLegalCase(){
		int[] position = {0, 0, 1};
		Vector3d vectorPosition = new Vector3d(0.5,0.5,1.5);
		Unit unit = new Unit("TestSubject", position, 50, 50, 50, 50, world, faction1, false);

		
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
		new Unit("testSubject", position, 50,50,50,50,world, faction1, false);
		fail("Exception Expected!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor$IllegalPositionCase()
			throws IllegalArgumentException{
		int[] position = {5,1,1};
		new Unit("TestSubject", position, 50,50,50,50,world, faction1,false);
		fail("Exception Expected!");
	}
	
	@Test
	public void testConstructor$IllegalStrengthCase(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 50,24,50,50,world, faction1,false);
		assertEquals(unit.getStrength(), 25);
	}
	
	@Test
	public void testConstructor$IllegalAgilityCase(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 50,50,102,50,world, faction1,false);
		assertEquals(unit.getAgility(), 25);
	}
	
	@Test
	public void testConstructor$IllegalToughnessCase(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 50,50,50,0,world, faction1,false);
		assertEquals(unit.getToughness(), 25);
	}
	
	@Test
	public void testConstructor$IllegalWeightCase(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 30,50,50,50,world, faction1,false);
		assertEquals(unit.getWeight(), 50);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor$IllegalWorldCase(){
		int[] position = {1,1,1};
		new Unit("TestSubject", position, 50,50,50,50,null, faction1,false);
		fail("Exception Expected!");
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructor$IllegalFactionCase(){
		int[] position = {1,1,1};
		new Unit("TestSubject", position, 50,50,50,50, world, null, false);
		fail("Exception Expected!");
	}
	
	@Test
	public void testConstructor$DefaultBooleanTrue(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 50,50,50,50, world, faction1, true);
		assertTrue(unit.getDefaultBoolean());
	}
	
	@Test
	public void testNewMoveToAdjacent$LegalCase(){
		testUnit.newMoveToAdjacent(1, 0, 0);
		assertTrue(testUnit.isMoving());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNewMoveToAdjacent$IllegalArgumentCase(){
		testUnit.newMoveToAdjacent(0, 0, 2);
		fail("Exception Expected!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNewMoveToAdjacent$IllegalNewPositionCase(){
		testUnit.newMoveToAdjacent(0, 0, -1);
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
	public void testIsMoving$TrueCase(){
		int[] destination = {1,1,1};
		testUnit.moveTo(destination);
		assertTrue(testUnit.isMoving());
	}
	
	@Test
	public void testIsMoving$FalseCase(){
		assertFalse(testUnit.isMoving());
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
		testUnit.newMoveToAdjacent(0, 1, 0);
		assertTrue(Util.fuzzyEquals(testUnit.getSpeed(), 1.5));
	}
	
	@Test
	public void testGetSpeed$MovingUpCase(){
		int[] position = {1,1,1};
		Unit unit = new Unit("TestSubject", position, 50,50,50,50, world, faction1, true);
		unit.newMoveToAdjacent(0, 0, 1);
		assertTrue(Util.fuzzyEquals(unit.getSpeed(), 0.75));
	}
	
	@Test
	public void testGetSpeed$MovingDownCase(){
		int[] position = {1,1,2};
		Unit unit = new Unit("TestSubject", position, 50,50,50,50, world, faction1, false);
		unit.newMoveToAdjacent(0, 0, -1);
		assertTrue(Util.fuzzyEquals(unit.getSpeed(), 1.8));
	}
	
	@Test
	public void testGetSpeed$SpringtingCase(){
		testUnit.newMoveToAdjacent(0, 1, 0);
		testUnit.startSprint();
		assertTrue(Util.fuzzyEquals(testUnit.getSpeed(), 3));
	}
	
	@Test
	public void testStartSprint$LegalCase(){
		testUnit.newMoveToAdjacent(0, 1, 0);
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
		testUnit.newMoveToAdjacent(0, 1, 0);
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
		assertEquals(testUnit.getWeight(), 50);
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
		assertTrue(otherFactionUnit.getStatus() == UnitStatus.DEFENDING);
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
		testUnit.terminate();
		assertTrue(testUnit.isTerminated());
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
		assertEquals(testUnit.getFaction(), faction1);
	}
	
	@Test
	public void testIsValidFaction$TrueCase(){
		assertTrue(Unit.isValidFaction(faction1));
	}
	//TODO: isvalidfaction, falsecase
	
	@Test
	public void testGetExperience(){
		assertEquals(testUnit.getExperience(), 0);
	}
}
