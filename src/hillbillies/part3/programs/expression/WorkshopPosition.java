package hillbillies.part3.programs.expression;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.World;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class WorkshopPosition {
	
	public WorkshopPosition(){
		
	}
	
	public int[] getWorkshopPosition(World world){
		return world.getWorkshopLocation();
	}
}
