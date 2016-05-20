package hillbillies.model.test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.*;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

/**
 * A class of world tests
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class WorldTest {
	
	private World testWorld;
	private Faction faction1;
	
	private Log log;
	private Boulder boulder;
	private Unit testUnit;
	
	@Before
	public void setUp(){
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
		testWorld = new World(terrainType, defaultListener);
		
		int[] position = {1,1,1};
		log = new Log(position, testWorld);
		testWorld.addLog(log);
		boulder = new Boulder(position, testWorld);
		testWorld.addBoulder(boulder);
		
		int[] unitPosition = {0,0,1};
		testUnit = new Unit("TestUnit", unitPosition, 50,50,50,50, false);
		testWorld.addUnit(testUnit);
		faction1 = testWorld.getActiveFactions().iterator().next();
	}
	
	@Test
	public void testConstructor$LegalCase(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
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
		World world = new World(terrainType, defaultListener);
		assertTrue(world.getNbCubesX()==3);
		assertTrue(world.getNbCubesY()==3);
		assertTrue(world.getNbCubesZ()==3);
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructor$TerrainNullCase(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		new World(null, defaultListener); 
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructor$ListenerNullCase(){
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
		new World(terrainType, null); 
	}
	
	@Test
	public void testGetConnectedToBorder(){
		assertTrue(this.testWorld.getConnectedToBorder() instanceof ConnectedToBorder);
	}
	
	@Test
	public void testIsPassableTerrain$TrueCase(){
		int[] position = {1,1,1};
		assertTrue(this.testWorld.isPassableTerrain(position));
	}
	
	@Test
	public void testIsPassableTerrain$FalseCase(){
		int[] position = {1,1,0};
		assertFalse(this.testWorld.isPassableTerrain(position));
	}
	
	@Test
	public void testIsPassableTerrain$FalsePositionCase(){
		int[] position = {-1,-1,-1};
		assertFalse(this.testWorld.isPassableTerrain(position));
	}
	
	@Test
	public void testIsValidWorldPosition$TrueCase(){
		int[] position = {2,2,2};
		assertTrue(this.testWorld.isValidWorldPosition(position));
	}
	
	@Test
	public void testIsValidWorldPosition$FalseTooLowCase(){
		int[] position = {-1,0,0};
		assertFalse(this.testWorld.isValidWorldPosition(position));
	}
	
	@Test
	public void testIsValidWorldPosition$FalseTooHighCase(){
		int[] position = {0,3,0};
		assertFalse(this.testWorld.isValidWorldPosition(position));
	}
	
	@Test
	public void testHasSolidBelow$TrueCase(){
		int[] position = {1,1,1};
		assertTrue(this.testWorld.hasSolidBelow(position));
	}
	
	@Test
	public void testHasSolidBelow$FalseCase(){
		int[] position = {1,1,2};
		assertFalse(this.testWorld.hasSolidBelow(position));
	}
	
	@Test
	public void testHasSolidBelow$ZeroCase(){
		int[] position = {2,2,0};
		assertTrue(this.testWorld.hasSolidBelow(position));
	}
	
	@Test
	public void testIsNeighbouringSolid$TrueCase(){
		int[] position = {1,0,2};
		assertTrue(this.testWorld.isNeighbouringSolid(position));
	}
	
	@Test
	public void testIsNeighbouringSolid$FalseCase(){
		int[] position = {0,0,2};
		assertFalse(this.testWorld.isNeighbouringSolid(position));
	}
	
	@Test
	public void testGetNbCubesX(){
		assertEquals(this.testWorld.getNbCubesX(),3);
	}
	
	@Test
	public void testGetNbCubesY(){
		assertEquals(this.testWorld.getNbCubesY(),3);
	}
	
	@Test
	public void testGetNbCubesZ(){
		assertEquals(this.testWorld.getNbCubesZ(),3);
	}
	
	@Test
	public void testGetCubeType(){
		assertTrue(this.testWorld.getCubeType(0, 0, 0) == CubeType.ROCK);
		assertTrue(this.testWorld.getCubeType(1, 1, 1) == CubeType.AIR);
	}
	
	@Test
	public void testSetCubeType(){
		this.testWorld.setCubeType(0, 0, 0, 0);
		assertEquals(this.testWorld.getCubeType(0, 0, 0), CubeType.AIR);
		int[][][] newTerrainType = 
		{{	{0,0,0},
			{0,0,0},
			{0,0,0}},
				
		 {	{0,0,0},
			{1,1,0},
			{0,0,0}},
		 
		 {	{0,0,0},
			{0,0,0},
			{0,0,0}}};
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();

		World world = new World(newTerrainType, defaultListener);
		world.setCubeType(1, 1, 0, 0);
		assertEquals(world.getCubeType(1, 1, 1), CubeType.AIR);
		assertEquals(world.getCubeType(1, 1, 0), CubeType.AIR);
		
	}
	
	@Test
	public void testHasAsLog$TrueCase(){
		assertTrue(this.testWorld.hasAsLog(this.log));
		
	}
	
	@Test
	public void testHasAsLog$FalseCase(){
		int[] position = {1,1,1};
		Log log = new Log(position, this.testWorld);
		assertFalse(this.testWorld.hasAsLog(log));
	}
	
	@Test
	public void testCanHaveAsLog$TrueCase(){
		int[] position = {1,1,1};
		Log log = new Log(position, this.testWorld);
		assertTrue(this.testWorld.canHaveAsLog(log));
	}
	
	@Test
	public void testCanHaveAsLog$FalseCase(){
		assertFalse(this.testWorld.canHaveAsLog(null));
	}
	
	@Test
	public void testGetNbLogs(){
		assertEquals(this.testWorld.getNbLogs(), 1);
	}
	
	@Test
	public void testAddLog$ValidCase(){
		int[] position = {1,1,1};
		Log log = new Log(position, this.testWorld);
		this.testWorld.addLog(log);
		assertTrue(this.testWorld.hasAsLog(log));
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddLog$NullCase(){
		this.testWorld.addLog(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddLog$OtherWorldCase(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		int[][][] terrainType = {{{0}}};
		World world = new World(terrainType, defaultListener);
		int[] position = {0,0,0};
		Log log = new Log(position,world);
		this.testWorld.addLog(log);
	}
	
	@Test
	public void testRemoveLog(){
		this.testWorld.removeLog(this.log);
		assertFalse(this.testWorld.hasAsLog(this.log));
	}
	
	@Test
	public void testGetLogs(){
		Set<Log> logs = this.testWorld.getLogs();
		assertTrue(logs.contains(log));
		assertTrue(logs.size() == 1);
	}
	
	//Boulder is identical to Log, so it also works
	
	@Test
	public void testHasAsUnit$TrueCase(){
		assertTrue(testWorld.hasAsUnit(testUnit));
	}
	
	@Test
	public void testHasAsUnit$FalseCase(){
		Unit unit = new Unit("Hillbilly", new int[] {0,0,0}, 50, 50, 50, 50, false);
		assertFalse(testWorld.hasAsUnit(unit));
	}
	
	@Test
	public void testCanHaveAsUnit(){
		assertFalse(testWorld.canHaveAsUnit(null));
		assertTrue(testWorld.canHaveAsUnit(testUnit));
	}
	
	@Test
	public void testHasProperUnits(){
		assertTrue(testWorld.hasProperUnits());
	}
	
	@Test
	public void testGetNbUnits(){
		assertTrue(testWorld.getNbUnits() == 1);
	}
	
	@Test
	public void testAddUnit(){
		Unit unit = new Unit("Johnny", new int[] {1,1,1}, 50, 50, 50, 50, false);
		testWorld.addUnit(unit);
		assertTrue(testWorld.hasAsUnit(unit));
		assertTrue(unit.getWorld()==testWorld);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddUnit$InvalidPosition(){
		Unit unit = new Unit("Johnny", new int[] {0,0,0}, 50, 50, 50, 50, false);
		testWorld.addUnit(unit);
		fail("exception expected");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddUnit$NullCase(){
		testWorld.addUnit(null);
		fail("exception expected");
	}
	
	@Test
	public void testRemoveUnit$LegalCase(){
		testUnit.setWorld(null);
		testWorld.removeUnit(testUnit);
		assertFalse(testWorld.hasAsUnit(testUnit));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemoveUnit$IllegalCase(){
		testWorld.removeUnit(testUnit);
		fail("exception expected");
	}
	
	@Test
	public void testGetUnits(){
		assertTrue(testWorld.getUnits().contains(testUnit));
		assertTrue(testWorld.getUnits().size() == 1);
	}
	
	@Test
	public void testGetNbActiveFactions(){
		assertEquals(this.testWorld.getNbActiveFactions(), 1);
	}
	
	@Test
	public void testGetActiveFactions(){
		Set<Faction> factions = this.testWorld.getActiveFactions();
		assertTrue(factions.contains(faction1));
		assertTrue(factions.size()==1);
	}
	
	@Test
	public void testSpawnUnit(){
		Unit unit = this.testWorld.spawnUnit(false);
		assertEquals(unit.getWorld(), this.testWorld);
		assertTrue(testWorld.hasAsUnit(unit));
		assertEquals(unit.getDefaultBoolean(), false);
	}
	
	@Test
	public void testAssignFactionTo(){
		Unit unit = new Unit("Johnny", new int[] {1,1,1}, 50, 50, 50, 50, false);
		testWorld.assignFactionTo(unit);
		assertTrue(unit.getFaction() != null);
		assertTrue(testWorld.hasAsFaction(unit.getFaction()));
	}
	
	@Test
	public void testLogAtPosition$ValidCase(){
		int[] position = {1,1,1};
		assertEquals(this.testWorld.logAtPosition(position), this.log);
	}
	
	@Test
	public void testLogAtPosition$NullCase(){
		int[] position = {0,0,1};
		assertEquals(this.testWorld.logAtPosition(position), null);
	}
	
	@Test
	public void testBoulderAtPosition$ValidCase(){
		int[] position = {1,1,1};
		assertEquals(this.testWorld.boulderAtPosition(position), this.boulder);
	}
	
	@Test
	public void testBoulderAtPosition$NullCase(){
		int[] position = {0,0,1};
		assertEquals(this.testWorld.boulderAtPosition(position), null);
	}
	
	@Test
	public void testAdvanceTime(){
		this.testWorld.advanceTime(0.1);
	}
	
	@Test(expected = Exception.class)
	public void testGetWorkshopLocation(){
		testWorld.getWorkshopLocation();
	}
	
	@Test
	public void testScheduleToRemove(){
		testUnit.terminate();
		testWorld.advanceTime(0.2);
		assertFalse(testWorld.hasAsUnit(testUnit));
		assertFalse(testUnit.getWorld() == testWorld);
	}
}
