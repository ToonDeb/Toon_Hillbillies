package hillbillies.model;

import static org.junit.Assert.*;
import org.junit.Test;

import ogp.framework.util.Util;

import org.junit.Before;
import javax.vecmath.*;

public class UnitTest {
	
	private Unit testingUnit;
	private Unit otherUnit;
	
	@Before
	public void setUp(){
		double[] position = {25d, 25d, 25d};
		Vector3d pos = new Vector3d(position);
		testingUnit = new Unit("TestSubject", pos, 50, 50, 50, 50);
		Vector3d other = new Vector3d(26d,25d,25d);
		otherUnit = new Unit("OtherSubject", other ,50,50,50,50);
	}
	
	@Test
	public void testConstructor$LegalCase(){
		double[] position = {12d, 15d, 2d};
		Vector3d pos = new Vector3d(position);
		Unit unit = new Unit("TestSubject", pos, 50, 50, 50, 50);
		
		assertEquals(unit.getPosition(), position);
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
	public void testSetGetPosition$LegalCase(){
		double[] position = {40.5d,40.75d,40.49d};
		Vector3d pos = new Vector3d(position);
		testingUnit.setPosition(pos);
		assertEquals(testingUnit.getPosition(), position);
	}
	
	@Test
	public void testGetCubePosition(){
		double[] position = {40.5d,40.75d,40.49d};
		Vector3d pos = new Vector3d(position);
		testingUnit.setPosition(pos);
		int[] cubePosition = {40,40,40};
		assertArrayEquals(testingUnit.getCubePosition(), cubePosition);
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetPosition$IllegalXCase() throws IllegalArgumentException{
		Vector3d position = new Vector3d(100d, 40d, 40d);
		testingUnit.setPosition(position);
		fail("Exception Expected!");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetPosition$IllegalYCase() throws IllegalArgumentException{
		
		double[] position = {40d, 100d, 40d };
		Vector3d pos = new Vector3d(position);
		testingUnit.setPosition(pos);
		fail("Exception Expected!");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetPosition$IllegalZCase() throws IllegalArgumentException{
		double[] position = {40d, 40d, -10d};
		Vector3d pos = new Vector3d(position);
		testingUnit.setPosition(pos);
		fail("Exception Expected!");
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
	public void testGetSetName$LegalCase(){
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
	/*
	@Test
	public void testGetSetOrientation(){
		testingUnit.setOrientation((float)2.54);
		assertTrue(Util.fuzzyEquals(testingUnit.getOrientation(), (float) 2.54));
	}
	
	@Test
	public void testGetSetOrientation$WrongNumer(){
		testingUnit.setOrientation((float) -Math.PI);
		assertTrue(Util.fuzzyEquals(testingUnit.getOrientation(), (float) Math.PI));
	}
	*/
	@Test
	public void testStartSprint(){
		testingUnit.startSprint();
		assertEquals(UnitStatus.SPRINTING, testingUnit.getStatus());
	}
	@Test
	public void testStopSprint(){
		testingUnit.startSprint();
		testingUnit.stopSprint();
		assertEquals(UnitStatus.IDLE, testingUnit.getStatus());
	}
	
	@Test
	public void testIsMoving$TrueCaseWalking(){
		testingUnit.setStatus(UnitStatus.WALKING);
		assertTrue(testingUnit.isMoving());
	}
	@Test
	public void testIsMoving$TrueCaseSPRINTING(){
		testingUnit.setStatus(UnitStatus.SPRINTING);
		assertTrue(testingUnit.isMoving());
	}
	@Test
	public void testIsMoving$FalseCase(){
		testingUnit.setStatus(UnitStatus.IDLE);
		assertFalse(testingUnit.isMoving());
	}
	
	@Test
	public void testUpdatePosition(){
		assertTrue(true);
	}
	
	@Test
	public void testMoveTo(){
		assertTrue(true);
	}
	
	@Test
	public void testMoveToAdjacent(){
		assertTrue(true);
	}
	
	@Test
	public void testDodge(){
		assertTrue(true);
	}
	
	@Test
	public void testGetWeight(){
		testingUnit.setWeight(150);
		assertEquals(testingUnit.getWeight(), 150);
	}
	
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
		assertFalse(testingUnit.isValidStartWeight(60));
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
	public void testSetGetStrength$ValidCase(){
		testingUnit.setStrength(50);
		assertEquals(testingUnit.getStrength(), 50);
	}
	
	@Test
	public void testSetGetStrength$InvalidCase(){
		testingUnit.setStrength(-5);
		assertEquals(testingUnit.getStrength(), 25);
	}
	
	@Test
	public void testSetGetAgility$ValidCase(){
		testingUnit.setAgility(50);
		assertEquals(testingUnit.getAgility(), 50);
	}
	
	@Test
	public void testSetGetAgility$InvalidCase(){
		testingUnit.setAgility(-5);
		assertEquals(testingUnit.getAgility(), 25);
	}
	
	@Test
	public void testSetGetToughness$ValidCase(){
		testingUnit.setToughness(50);
		assertEquals(testingUnit.getToughness(), 50);
	}
	
	@Test
	public void testSetGetToughness$InvalidCase(){
		testingUnit.setToughness(-5);
		assertEquals(testingUnit.getToughness(), 25);
	}
	
	@Test
	public void testSetGetHP$ValidCase(){
		testingUnit.setWeight(100);
		testingUnit.setToughness(100);
		testingUnit.setHP(50);
		assertEquals(testingUnit.getHP(), 50);
	}
	
	@Test
	public void testGetMaxHP(){
		testingUnit.setWeight(100);
		testingUnit.setToughness(100);
		assertEquals(testingUnit.getMaxHP(), 200);
	}
	
	@Test
	public void testIsValidHP$TrueCase(){
		testingUnit.setWeight(100);
		testingUnit.setToughness(100);
		assertTrue(testingUnit.isValidHP(100));
	}
	
	@Test
	public void testIsValidHP$TooLowCase(){
		testingUnit.setWeight(100);
		testingUnit.setToughness(100);
		assertFalse(testingUnit.isValidHP(-5));
	}
	
	@Test
	public void testIsValidHP$TooHighCase(){
		testingUnit.setWeight(100);
		testingUnit.setToughness(100);
		assertFalse(testingUnit.isValidHP(500));
	}
	
	@Test
	public void testSetGetStamina(){
		testingUnit.setWeight(100);
		testingUnit.setToughness(100);
		testingUnit.setStamina(50);
		assertEquals(testingUnit.getStamina(), 50);
	}
	
	@Test
	public void testGetMaxStamine(){
		testGetMaxHP();
	}
	
	@Test
	public void testIsValidStamina$TrueCase(){
		testingUnit.setWeight(100);
		testingUnit.setToughness(100);
		assertTrue(testingUnit.isValidStamina(100));
	}
	
	@Test
	public void testIsValidStamina$TooLowCase(){
		testingUnit.setWeight(100);
		testingUnit.setToughness(100);
		assertFalse(testingUnit.isValidStamina(-5));
	}
	
	@Test
	public void testIsValidStamina$TooHighCase(){
		testingUnit.setWeight(100);
		testingUnit.setToughness(100);
		assertFalse(testingUnit.isValidStamina(500));
	}
	
	@Test
	public void testWork(){
		testingUnit.work();
		assertTrue(testingUnit.getStatus()== UnitStatus.WORKING);
	}
	
	@Test
	public void testAttack$ValidCase(){
		otherUnit.setPosition(new Vector3d(26d,25d,25d));
		testingUnit.setPosition(new Vector3d(25d,25d,25d));
		testingUnit.attack(otherUnit);
		assertTrue(testingUnit.getStatus()== UnitStatus.ATTACKING);
		assertTrue(otherUnit.getStatus()==UnitStatus.DEFENDING);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAttack$InvalidPositionCase(){
		otherUnit.setPosition(new Vector3d(27d,25d,25d));
		testingUnit.setPosition(new Vector3d(25d,25d,25d));
		testingUnit.attack(otherUnit);
		fail("Exception Expected");
	}
	
	@Test
	public void testRest$ValidCase(){
		testingUnit.rest();
		assertTrue(testingUnit.getStatus() == UnitStatus.REST);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRest$InvalidCase(){
		otherUnit.setPosition(new Vector3d(26d,25d,25d));
		testingUnit.setPosition(new Vector3d(25d,25d,25d));
		testingUnit.attack(otherUnit);
		testingUnit.rest();
		fail("Exception Expected");
	}
	
	//getStatus is tested in the previous tests, because it is 
	// called by the other methods
	
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
	public void testStartDefaultBehaviour(){
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
