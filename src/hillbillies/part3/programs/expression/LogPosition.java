package hillbillies.part3.programs.expression;

import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Log;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class LogPosition extends WorldPosition {
	
	public LogPosition(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public int[] getPosition(World world){
		Set<Log> logs = world.getLogs();
		int random = new Random().nextInt(logs.size());
		int i = 0;
		for (Log log: logs){
			if(i == random)
				return log.getCubePosition();
			i += 1;
		}
		//will never happen
		return null;
	}
}
