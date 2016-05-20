package hillbillies.model.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import javax.vecmath.Vector3d;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.*;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.statement.MyStatement;
import hillbillies.part3.programs.statement.NullStatement;

/**
 * A class of log tests
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class BoulderTest {
	
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
	private Task task1, task2;
	private Faction faction1, faction2;
	private Scheduler scheduler;
	private Log log;
	private Boulder boulder;
	
	@Before
	public void setUp(){
		
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
		//unassignedTask = new Task("name", 1, statement);
		//unassignedTask2 = new Task("name", 2, statement);
		//otherScheduler = faction2.getScheduler();
	}
	
	@Test
	public void testConstructor(){
		int[] position = {0,0,1};
		Boulder boulder = new Boulder(position, world);
		world.addBoulder(boulder);
		
		assertTrue(world.hasAsBoulder(boulder));
		assertEquals(boulder.getWorld(),world);
		assertTrue(Arrays.equals(boulder.getCubePosition(),position));
		assertTrue(GameItem.isValidWeight(boulder.getWeight()));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor$IllegalPosition(){
		int[] position = {0,0,0};
		Boulder boulder = new Boulder(position, world);
		world.addBoulder(boulder);
		fail("exception expected");
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructor$IllegalWorld(){
		int[] position = {0,0,0};
		new Boulder(position, null);
		fail("exception expected");
	}
	
	/* 		Supertype GameItem Tests	 */
	@Test
	public void testGetWeight(){
		assertTrue(GameItem.isValidWeight(boulder.getWeight()));
	}
	
	@Test
	public void testIsValidWeight(){
		assertTrue(Boulder.isValidWeight(Constants.MIN_OBJECT_WEIGHT));
		assertTrue(Boulder.isValidWeight(Constants.MAX_OBJECT_WEIGHT));
		assertFalse(Boulder.isValidWeight(Constants.MIN_OBJECT_WEIGHT-1));
		assertFalse(Boulder.isValidWeight(Constants.MAX_OBJECT_WEIGHT + 1));
	}
	
	@Test
	public void testTakeFallDamage(){
		boulder.takeFallDamage(2);
	}
	
	@Test
	public void testAdvanceTime(){
		boulder.setAtPosition(new int[] {0,0,2});
		int i = 0;
		while(i < 20){
			boulder.advanceTime(0.15);
			i ++;
		}
		assertFalse(Arrays.equals(boulder.getCubePosition(), new int[] {0,0,2}));
		assertTrue(Arrays.equals(boulder.getCubePosition(), new int[] {0,0,1}));
	}
	
	@Test
	public void testTerminate(){
		boulder.terminate();
		assertTrue(boulder.getWorld() == null);
		assertFalse(world.hasAsBoulder(boulder));
		assertTrue(boulder.isTerminated());
	}
	
	@Test
	public void testIsTerminated(){
		assertFalse(boulder.isTerminated());
		boulder.terminate();
		assertTrue(boulder.isTerminated());
	}
	
	/*  end SuperType GameItem tests */
	
	
	
	/* 	Supertype GameObject tests	*/
	
	@Test 
	public void testGetPosition(){
		assertEquals(boulder.getPosition(), new Vector3d(0.5,0.5,1.5));
	}
	
	@Test
	public void testIsValidPosition$TrueCase(){
		Vector3d position = new Vector3d(1.5,1.5,0.5);
		assertTrue(Boulder.isValidPosition(position, world));
	}
	
	@Test
	public void testIsValidPosition$FalseCase(){
		Vector3d position = new Vector3d(-1.5,1.5,0.5);
		assertFalse(Boulder.isValidPosition(position, world));
	}
	
	@Test
	public void testToCubePosition(){
		Vector3d position = new Vector3d(2.9,1.1,3.5);
		assertTrue(Boulder.toCubePosition(position)[0] == 2 &&
				Boulder.toCubePosition(position)[1] == 1 &&
				Boulder.toCubePosition(position)[2] == 3 );
	}
	
	@Test
	public void testToVectorPosition(){
		int[] position = new int[] {2,1,3};
		assertTrue(Boulder.toVectorPosition(position).x == 2.5d &&
				Boulder.toVectorPosition(position).y == 1.5d &&
				Boulder.toVectorPosition(position).z == 3.5d);
	}
	
	@Test
	public void testSetAtPosition$ValidCase(){
		boulder.setAtPosition(new int[] {1,1,1});
		assertTrue(Arrays.equals(boulder.getCubePosition(), new int[] {1,1,1}));
		assertTrue(boulder.getPosition().equals(new Vector3d(1.5,1.5,1.5)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAtPosition$InvalidCase(){
		boulder.setAtPosition(new int[] {5,5,5});
		fail("exception expected");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAtPosition$NullCase(){
		boulder.setAtPosition(null);
		fail("exception expected");
	}
	
	@Test
	public void testSetPosition$ValidCase(){
		boulder.setPosition(new Vector3d(2.9,1.1,0.5));
		assertTrue(Arrays.equals(boulder.getCubePosition(), new int[] {2,1,0}));
		assertTrue(boulder.getPosition().equals(new Vector3d(2.9,1.1,0.5)));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetPosition$InvalidCase(){
		boulder.setPosition(new Vector3d(2.9,1.1,3.5));
		fail("exception expected");
	}
	
	@Test
	public void testGetCubePosition(){
		assertTrue(Arrays.equals(boulder.getCubePosition(), new int[] {0,0,1}));
	}
	
	@Test
	public void testUpdateFall(){
		boulder.setAtPosition(new int[] {0,0,2});
		world.advanceTime(0.0001);
		assertTrue(boulder.isFalling());
		while(boulder.isFalling()){
			boulder.updateFall(0.1);
		}
		assertFalse(Arrays.equals(boulder.getCubePosition(), new int[] {0,0,2}));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateFall$InvalidCase(){
		boulder.updateFall(-0.01);
		fail("exception expected");
	}
	
//	@Test
//	public void testGetFallDepth(){
//		testUnit.setAtPosition(new int[] {0,0,2});
//		world.advanceTime(0.0001);
//		assertTrue(testUnit.getFallDepth() != 0);
//	}
	
	@Test
	public void testStartFall$ValidCase(){
		boulder.setAtPosition(new int[] {0,0,2});
		boulder.startFall();
		assertTrue(boulder.isFalling());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStartFall$InvalidCase(){
		boulder.startFall();
		fail("exception expected");
	}
	
	@Test
	public void testIsFalling$TrueCase(){
		boulder.setAtPosition(new int[] {0,0,2});
		boulder.startFall();
		assertTrue(boulder.isFalling());
	}
	
	@Test
	public void testIsFalling$FalseCase(){
		assertFalse(boulder.isFalling());
	}
	
	@Test
	public void testGetCubePositionBelow(){
		int[] belowPos = boulder.getCubePositionBelow();
		assertTrue(Arrays.equals(belowPos, new int[] {0,0,0}));
	}
	
	@Test
	public void testIsNeighbouringCube$TrueCase(){
		int[] belowPos = boulder.getCubePositionBelow();
		assertTrue(boulder.isNeighbouringCube(belowPos));
	}
	
	@Test
	public void testIsNeighbouringCube$FalseCase(){
		int[] pos = {2,2,2};
		assertFalse(boulder.isNeighbouringCube(pos));
	}
	
	@Test
	public void testIsNeighbouringCube$FalsePosCase(){
		int[] pos = {3,2,2};
		assertFalse(boulder.isNeighbouringCube(pos));
	}
	
	@Test
	public void testGetWorld(){
		assertTrue(boulder.getWorld() == world);
	}
	
	@Test
	public void testIsValidWorld$TrueCase(){
		assertTrue(boulder.isValidWorld(null));
		assertTrue(boulder.isValidWorld(world));
	}
	
	@Test
	public void testIsValidWorld$FalseCase(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		World otherWorld = new World(terrainType, defaultListener);
		assertTrue(boulder.isValidWorld(otherWorld));
		assertTrue(boulder.isValidWorld(null));
	}
	
	@Test
	public void testSetWorld$ValidCase(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		World otherWorld = new World(terrainType, defaultListener);
		boulder.setWorld(otherWorld);
		assertTrue(boulder.getWorld() == otherWorld);
	}
	
	
	/* End Supertype GameObject tests */
}
