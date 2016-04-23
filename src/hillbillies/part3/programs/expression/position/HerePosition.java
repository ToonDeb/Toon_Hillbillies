package hillbillies.part3.programs.expression.position;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class HerePosition extends Position{
	
	public HerePosition(SourceLocation sourceLocation){
		super(sourceLocation);
		
	}
	
	public int[] getPosition(World world, Unit unit){
		return unit.getCubePosition();
	}
}
