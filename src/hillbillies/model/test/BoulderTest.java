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
public class BoulderTest {
	
	@Test
	public void testConstructor(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		int[][][] terrainType = {{{0}}}; 
		World world = new World(terrainType, defaultListener);
		int[] position = {0,0,0};
		Boulder boulder = new Boulder(position, world);
		world.addBoulder(boulder);
		
		assertTrue(world.hasAsBoulder(boulder));
		assertEquals(boulder.getWorld(),world);
		assertTrue(Arrays.equals(boulder.getCubePosition(),position));
	}
}
