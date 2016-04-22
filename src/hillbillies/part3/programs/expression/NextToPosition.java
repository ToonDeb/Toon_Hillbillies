package hillbillies.part3.programs.expression;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Constants;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import static hillbillies.model.Constants.DIRECTLYNEIGHBOURINGLIST;
/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class NextToPosition extends MyExpression {
	
	public NextToPosition(MyExpression position, SourceLocation sourceLocation){
		super(sourceLocation);
		this.otherPosition = position;
	}
	
	private static MyExpression otherPosition;

	public int[] getPosition(World world, Unit unit) {
		int[] firstPosition;
		if (otherPosition instanceof WorldPosition){
			firstPosition = ((WorldPosition)otherPosition).getPosition(world);
		}
		else if (otherPosition instanceof HerePosition){
			firstPosition = ((UnitPosition)otherPosition).getPosition(unit);
		}
		else{
			throw new IllegalArgumentException("this type of expression is not possible!");
		}
		
		int counter = 0;
		while (counter < 1000){
			int random = new Random().nextInt(6);
			firstPosition[0] += Constants.DIRECTLYNEIGHBOURINGLIST[random][0];
			firstPosition[1] += Constants.DIRECTLYNEIGHBOURINGLIST[random][1];
			firstPosition[2] += Constants.DIRECTLYNEIGHBOURINGLIST[random][2];
			if (world.isValidWorldPosition(firstPosition))
				return firstPosition;
		}
		return null;
	}
}
