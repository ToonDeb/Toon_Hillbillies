package hillbillies.part3.programs.expression.position;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

import static hillbillies.model.Constants.DIRECTLYNEIGHBOURINGLIST;
/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class NextToPosition extends MyExpression<int[]> {
	
	public NextToPosition(MyExpression<int[]> position, SourceLocation sourceLocation){
		super(sourceLocation);
		otherPosition = position;
	}
	
	private static MyExpression<int[]> otherPosition;

	public int[] evaluateExpression(Unit unit) {
		int[] firstPosition;
		
		firstPosition = otherPosition.evaluateExpression(unit);
		
		int counter = 0;
		while (counter < 100){
			int random = new Random().nextInt(6);
			firstPosition[0] += DIRECTLYNEIGHBOURINGLIST[random][0];
			firstPosition[1] += DIRECTLYNEIGHBOURINGLIST[random][1];
			firstPosition[2] += DIRECTLYNEIGHBOURINGLIST[random][2];
			if (unit.getWorld().isValidWorldPosition(firstPosition))
				return firstPosition;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.evaluateExpression(unit);
		return "NextToPosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}
}
