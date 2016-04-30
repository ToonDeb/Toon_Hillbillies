package hillbillies.part3.programs.expression.position;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;
import hillbillies.model.World;
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
public class NextToPosition extends PositionExpression {
	
	public NextToPosition(MyExpression position, SourceLocation sourceLocation){
		super(sourceLocation);
		otherPosition = position;
	}
	
	private static MyExpression otherPosition;

	public int[] getPosition(World world, Unit unit) {
		int[] firstPosition;
		if (otherPosition instanceof PositionExpression){
			firstPosition = ((PositionExpression)otherPosition).getPosition(world, unit);
		}
		else{
			throw new IllegalArgumentException("this type of expression is not possible!");
		}
		int counter = 0;
		while (counter < 100){
			int random = new Random().nextInt(6);
			firstPosition[0] += DIRECTLYNEIGHBOURINGLIST[random][0];
			firstPosition[1] += DIRECTLYNEIGHBOURINGLIST[random][1];
			firstPosition[2] += DIRECTLYNEIGHBOURINGLIST[random][2];
			if (world.isValidWorldPosition(firstPosition))
				return firstPosition;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.getPosition(unit.getWorld(), unit);
		return "NextToPosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}
}
