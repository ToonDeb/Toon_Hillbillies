package hillbillies.model.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import hillbillies.model.*;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;

/**
 * A class of log tests
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class LogTest {
	
	@Test
	public void testConstructor(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		int[][][] terrainType = {{{0}}}; 
		World world = new World(terrainType, defaultListener);
		int[] position = {0,0,0};
		Log log = new Log(position, world);
		world.addLog(log);
		
		assertTrue(world.hasAsLog(log));
		assertEquals(log.getWorld(),world);
		assertTrue(Arrays.equals(log.getCubePosition(),position));
	}
}
