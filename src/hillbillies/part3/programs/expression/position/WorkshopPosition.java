package hillbillies.part3.programs.expression.position;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of WorkshopPosition expressions
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class WorkshopPosition extends MyExpression<int[]> {
	
	/**
	 * @param sourceLocation
	 */
	public WorkshopPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	public int[] evaluateExpression(Unit unit){
		return unit.getWorld().getWorkshopLocation();
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.evaluateExpression(unit);
		return "WorkshopPosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}
}
