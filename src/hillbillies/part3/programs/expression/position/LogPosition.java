package hillbillies.part3.programs.expression.position;

import java.util.Random;
import java.util.Set;

import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of LogPosition expressions
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class LogPosition extends MyExpression<int[]> {
	
	public LogPosition(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public int[] evaluateExpression(Unit unit){
		Set<Log> logs = unit.getWorld().getLogs();
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
		int[] position = this.evaluateExpression(unit);
		return "LogPosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}
}
