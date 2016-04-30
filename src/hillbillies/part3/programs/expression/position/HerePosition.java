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
public class HerePosition extends PositionExpression{
	
	public HerePosition(SourceLocation sourceLocation){
		super(sourceLocation);
		
	}
	
	public int[] getPosition(World world, Unit unit){
		return unit.getCubePosition();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.getPosition(unit.getWorld(), unit);
		return "HerePosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}
	
	
}
