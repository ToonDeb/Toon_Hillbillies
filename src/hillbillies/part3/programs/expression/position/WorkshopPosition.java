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
public class WorkshopPosition extends PositionExpression {
	
	/**
	 * @param sourceLocation
	 */
	public WorkshopPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	public int[] getPosition(World world, Unit unit){
		return world.getWorkshopLocation();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.getPosition(unit.getWorld(), unit);
		return "WorkshopPosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}
}
