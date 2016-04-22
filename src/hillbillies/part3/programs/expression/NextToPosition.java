package hillbillies.part3.programs.expression;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Value;
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
		if (otherPosition instanceof Position){
			firstPosition = ((Position)otherPosition).getPosition(world);
		}
		else if (otherPosition instanceof HerePosition){
			firstPosition = ((HerePosition)otherPosition).getPosition(unit);
		}
		
		int counter = 0;
		while (counter < 1000){
			int random = new Random().nextInt(6);
			int[] newposition = this.otherPosition
		}
		return null;
	}
}
