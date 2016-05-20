package hillbillies.part3.programs.expression.position;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class HerePosition extends MyExpression<int[]>{
	
	public HerePosition(SourceLocation sourceLocation){
		super(sourceLocation);
		
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.evaluateExpression(unit);
		return "HerePosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#evaluateExpression(hillbillies.model.Unit)
	 */
	@Override
	public int[] evaluateExpression(Unit unit) {
		return unit.getCubePosition();
	}
	
	
}
