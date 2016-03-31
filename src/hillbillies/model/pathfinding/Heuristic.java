package hillbillies.model.pathfinding;

import hillbillies.model.Unit;
import hillbillies.model.World;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class Heuristic {
	public float getCost(World world, Unit unit, int x, int y, int z, int tx, int ty, int tz){
		float dx = tx - x;
		float dy = ty - y;
		float dz = tz - z;
		
		float result = (float) (Math.sqrt((dx*dx)+(dy*dy)+(dz*dz)));
		
		return result;
	}

}
