package hillbillies.model.test;

import static org.junit.Assert.*;
import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.model.UnitStatus;
import ogp.framework.util.Util;

import org.junit.Before;
import javax.vecmath.*;

public class UnitTest {
	
	private Unit unit;
	private Unit otherUnit;
	private Unit farUnit;
	private Unit positionUnit;
	
	@Before
	public void setUp(){
		int[] pos = {25, 25, 25};
		unit = new Unit("TestSubject", pos, 50, 50, 50, 50);
		int[] other = {26,25,25};
		otherUnit = new Unit("OtherSubject", other ,50,50,50,50);
		int[] far = {1, 3, 5};
		farUnit = new Unit("FarUnit", far, 50, 50, 50, 50);
		int[] position = {40,40,40};
		positionUnit = new Unit("PositionUnit", position, 50,50,50,50);
	}
	
	@Test
	public void testConstructor$LegalCase(){
		int[] position = {12, 15, 2};
		Vector3d vectorPosition = new Vector3d(12.5,15.5,2.5);
		Unit unit = new Unit("TestSubject", position, 50, 50, 50, 50);
		
		assertEquals(unit.getPosition(), vectorPosition);
		assertEquals(unit.getStrength(), 50);
		assertEquals(unit.getAgility(), 50);
		assertEquals(unit.getWeight(), 50);
		assertEquals(unit.getToughness(), 50);
		assertEquals(unit.getName(), "TestSubject");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor$IllegalPositionCase()
			throws IllegalArgumentException{
		double[] position = {51d, 51d, 51d};
		Vector3d pos = new Vector3d(position);
		new Unit("TestSubject", pos, 50, 50, 50, 50);
		fail("Exception Expected!");
	}
	
	@Test
	public void testConstructor$IllegalStrengthCase()
			throws IllegalArgumentException{
		double[] position = {40d,40d,40d};
		Vector3d pos = new Vector3d(position);
		Unit unit = new Unit("TestSubject", pos, 50, 24, 50, 50);
		assertEquals(unit.getStrength(), 25);
	}
	
	@Test
	public void testConstructor$IllegalAgilityCase()
			throws IllegalArgumentException{
		double[] position = {40d,40d,40d};
		Vector3d pos = new Vector3d(position);
		Unit unit = new Unit("TestSubject", pos, 50, 50, 150, 50);
		assertEquals(unit.getAgility(), 25);
	}
	
	@Test
	public void testConstructor$IllegalToughnessCase()
			throws IllegalArgumentException{
		double[] position = {40d,40d,40d};
		Vector3d pos = new Vector3d(position);
		Unit unit = new Unit("TestSubject", pos, 50, 50, 50, 250);
		assertEquals(unit.getToughness(), 25);
	}
	
	@Test
	public void testConstructor$IllegalWeightCase(){
		double[] position = {40d,40d,40d};
		Vector3d pos = new Vector3d(position);
		Unit unit = new Unit("TestSubject", pos, 30, 50, 50, 50);
		assertEquals(unit.getWeight(), 50);
	}
	
	
	@Test
	public void testGetPosition$LegalCase(){
		Vector3d pos = new Vector3d(40.5d,40.75d,40.49d);
		assertEquals(positionUnit.getPosition(), pos);
	}
	
	@Test
	public void testGetCubePosition(){
		int[] cubePosition = {40,40,40};
		assertArrayEquals(positionUnit.getCubePosition(), cubePosition);
	}
	
	@Test
	public void testIsValidPosition$TrueCase(){
		double[] position = {1.58d, 49.89d, 35.45875d};
		Vector3d pos = new Vector3d(position);
		assertTrue(Unit.isValidPosition(pos));
	}
	
	@Test
	public void testIsValidPosition$NegativeCase(){
		double[] position = {-5.54d, 5.4785d, 6.69d};
		Vector3d pos = new Vector3d(position);
		assertFalse(Unit.isValidPosition(pos));
	}
	
	@Test
	public void testIsValidPosition$ToHighCase(){
		double[] position = {55.54d, 5.4785d, 6.69d};
		Vector3d pos = new Vector3d(position);
		assertFalse(Unit.isValidPosition(pos));
	}
	
	@Test
	public void testMoveToAdjacent$LegalCase(){
		Vector3d destination = new Vector3d(26.5d,25.5d,25.5d);
		testingUnit.moveToAdjacent(destination);
		assertTrue(testingUnit.getStatus()==UnitStatus.WALKING);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testMoveToAdjacent$IllegalCase(){
		Vector3d destination = new Vector3d(1.5d,1.5d,1.5d);
		testingUnit.moveToAdjacent(destination);
		fail("Exception expected");
	}
	
	@Test
	public void testMoveTo$LegalCase(){
		Vector3d destination = new Vector3d(5.5d,40.5d,5.5d);
		testingUnit.moveTo(destination);
		assertTrue(testingUnit.getStatus()==UnitStatus.WALKING);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testMoveTo$IllegalCase(){
		Vector3d destination = new Vector3d(-5d,1d,1d);
		testingUnit.moveTo(destination);
		fail("Exception expected");
	}
	
	@Test
	public void testIsMoving$TrueWalkingCase(){
		Vector3d destination = new Vector3d(5d,40d,5d);
		testingUnit.moveTo(destination);
		assertTrue(testingUnit.isMoving());
	}
	
	@Test
	public void testIsMoving$TrueSpringtingCase(){
		Vector3d destination = new Vector3d(5d,40d,5d);
		testingUnit.moveTo(destination);
		testingUnit.startSprint();
		assertTrue(testingUnit.isMoving());
	}
	
	public void testIsMoving$FalseCase(){
		testingUnit.attack(otherUnit);
		assertFalse(testingUnit.isMoving());
	}
	
	@Test
	public void testStartSprint(){
		testingUnit.startSprint();
		assertEquals(UnitStatus.SPRINTING, testingUnit.getStatus());
	}
	@Test
	public void testStopSprint(){
		testingUnit.startSprint();
		testingUnit.stopSprint();
		assertEquals(UnitStatus.WALKING, testingUnit.getStatus());
	}
	
	@Test
	public void testGetName$LegalCase(){
		testingUnit.setName("GLaDOS");
		assertEquals(testingUnit.getName(), "GLaDOS");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetName$NullCase(){
		testingUnit.setName(null);
		fail("Exception Expected!");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetName$IllegalCharCase(){
		testingUnit.setName("De$ta");
		fail("Exception Expected!");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetName$IllegalFirstCharCase(){
		testingUnit.setName("lowerCase");
		fail("Exception Expected!");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetName$IllegalLengthCase(){
		testingUnit.setName("N");
		fail("Exception Expected!");
	}
	
	// setName uses the same tests as isValidName, because it
	// calls the method to throw the exception
	
	@Test
	public void testGetOrientation(){
		assertTrue(Util.fuzzyEquals(testingUnit.getOrientation(), (double) Math.PI/2));
		testingUnit.attack(otherUnit);
		assertTrue(Util.fuzzyEquals(testingUnit.getOrientation(), 0));
		assertTrue(Util.fuzzyEquals(otherUnit.getOrientation(), Math.PI));
	}
	
	
	@Test
	public void testGetWeight(){
		assertEquals(testingUnit.getWeight(), 50);
	}
	/*
	@Test
	public void testIsValidWeight$TrueCase(){
		testingUnit.setAgility(50);
		testingUnit.setStrength(50);
		assertTrue(testingUnit.isValidWeight(100));
	}
	
	@Test
	public void testIsValidWeight$FalseTooLowCase(){
		testingUnit.setAgility(50);
		testingUnit.setStrength(50);
		assertFalse(testingUnit.isValidWeight(10));
	}
	
	@Test
	public void testIsValidStartWeight$TrueCase(){
		testingUnit.setAgility(50);
		testingUnit.setStrength(50);
		assertTrue(testingUnit.isValidStartWeight(60));
	}
	
	@Test
	public void testIsValidStartWeight$FalseTooHighCase(){
		testingUnit.setAgility(50);
		testingUnit.setStrength(50);
		assertFalse(testingUnit.isValidStartWeight(150));
	}
	
	@Test
	public void testIsValidStartWeight$FalseTooLowCase(){
		testingUnit.setAgility(50);
		testingUnit.setStrength(50);
		assertFalse(testingUnit.isValidStartWeight(15));
	}
	
	@Test
	public void testSetWeight$ValidValue(){
		testingUnit.setAgility(50);
		testingUnit.setStrength(50);
		testingUnit.setWeight(100);
		assertEquals(testingUnit.getWeight(), 100);
	}
	
	@Test
	public void testSetWeight$InvalidValue(){
		testingUnit.setAgility(100);
		testingUnit.setStrength(100);
		testingUnit.setWeight(50);
		assertEquals(testingUnit.getWeight(), 100);
	}
	*/
	
	@Test
	public void testIsValidUnitAttribute$TrueCase(){
		assertTrue(Unit.isValidUnitAttribute(50));
	}
	
	@Test
	public void testIsValidUnitAttribute$NegativeCase(){
		assertFalse(Unit.isValidUnitAttribute(-5));
	}
	
	@Test
	public void testIsValidUnitAttribute$TooLargeCase(){
		assertFalse(Unit.isValidUnitAttribute(500));
	}
	
	@Test
	public void testIsValidUnitAttribute$ZeroCase(){
		assertFalse(Unit.isValidUnitAttribute(0));
	}
	
	@Test
	public void testIsValidStartAttribute$TrueCase(){
		assertTrue(Unit.isValidStartAttribute(50));
	}
	
	@Test
	public void testIsValidStartAttribute$NegativeCase(){
		assertFalse(Unit.isValidStartAttribute(-5));
	}
	
	@Test
	public void testIsValidStartAttribute$TooLargeCase(){
		assertFalse(Unit.isValidStartAttribute(150));
	}
	
	@Test
	public void testIsValidStartAttribute$ZeroCase(){
		assertFalse(Unit.isValidStartAttribute(0));
	}
	
	@Test
	public void testGetStrength$ValidCase(){
		assertEquals(testingUnit.getStrength(), 50);
	}
	
	
	@Test
	public void testGetAgility(){
		assertEquals(testingUnit.getAgility(), 50);
	}
	
	@Test
	public void testGetToughness(){
		assertEquals(testingUnit.getToughness(), 50);
	}
	
	@Test
	public void testGetMaxHP(){
		assertEquals(testingUnit.getMaxHP(), 50);
	}
	
	@Test
	public void testIsValidHP$TrueCase(){
		assertTrue(testingUnit.isValidHP(50));
	}
	
	@Test
	public void testIsValidHP$TooLowCase(){
		assertFalse(testingUnit.isValidHP(-5));
	}
	
	@Test
	public void testIsValidHP$TooHighCase(){
		assertFalse(testingUnit.isValidHP(500));
	}
	
	@Test
	public void testGetMaxStamine(){
		testGetMaxHP();
	}
	
	@Test
	public void testIsValidStamina$TrueCase(){
		assertTrue(testingUnit.isValidStamina(50));
	}
	
	@Test
	public void testIsValidStamina$TooLowCase(){
		assertFalse(testingUnit.isValidStamina(-5));
	}
	
	@Test
	public void testIsValidStamina$TooHighCase(){
		assertFalse(testingUnit.isValidStamina(500));
	}
	
	@Test
	public void testWork(){
		testingUnit.work();
		assertTrue(testingUnit.getStatus()== UnitStatus.WORKING);
	}
	
	@Test
	public void testAttack$ValidCase(){
		testingUnit.attack(otherUnit);
		assertTrue(testingUnit.getStatus()== UnitStatus.ATTACKING);
		assertTrue(otherUnit.getStatus()==UnitStatus.DEFENDING);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAttack$InvalidPositionCase(){
		testingUnit.attack(farUnit);
		fail("Exception Expected");
	}
	
	@Test
	public void testRest$ValidCase(){
		testingUnit.rest();
		assertTrue(testingUnit.getStatus() == UnitStatus.REST);
	}
	
	@Test (expected = AssertionError.class)
	public void testRest$InvalidCase(){
		testingUnit.attack(otherUnit);
		testingUnit.rest();
		fail("AssertionError Expected");
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
		testingUnit.terminate();
		assertTrue(testingUnit.isTerminated());
	}
	
	@Test
	public void testIsTerminated(){
		assertFalse(testingUnit.isTerminated());
	}
	
	@Test
	public void testStartDefaultBehaviour(){ //TODO: check by seeing which of the effects has happened
		testingUnit.startDefaultBehaviour();
		assertTrue(testingUnit.getDefaultBoolean());
	}
	
	@Test
	public void testStopDefaultBehaviour(){
		testingUnit.startDefaultBehaviour();
		testingUnit.stopDefaultBehaviour();
		assertFalse(testingUnit.getDefaultBoolean());
	}
	
	//getDefaultBoolean is tested in the previous tests.
}
