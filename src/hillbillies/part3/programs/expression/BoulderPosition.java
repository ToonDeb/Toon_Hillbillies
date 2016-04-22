package hillbillies.part3.programs.expression;

import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Boulder;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class BoulderPosition extends WorldPosition {
	
	public BoulderPosition(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public int[] getPosition(World world){
		Set<Boulder> boulders = world.getBoulders();
		int random = new Random().nextInt(boulders.size());
		int i = 0;
		for (Boulder boulder: boulders){
			if(i == random)
				return boulder.getCubePosition();
			i += 1;
		}
		//will never happen
		return null;
	}

}
