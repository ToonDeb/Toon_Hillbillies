package hillbillies.part3.programs.expression.position;

import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Log;
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
public class LogPosition extends PositionExpression {
	
	public LogPosition(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public int[] getPosition(World world, Unit unit){
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

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.getPosition(unit.getWorld(), unit);
		return "LogPosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}
}
