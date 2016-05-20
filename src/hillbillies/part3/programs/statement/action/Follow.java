package hillbillies.part3.programs.statement.action;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of follow statements
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */

public class Follow extends Action<Unit> {

	/**
	 * @param expression
	 * @param sourceLocation
	 */
	public Follow(MyExpression<Unit> expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.Action#execute(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public void execute(World world, Unit unit) {
		Unit following = this.getExpression().evaluateExpression(unit);
		if(!following.isNeighbouringCube(unit.getCubePosition())){
			unit.startFollowing(following);
		}
		else{
			this.hasArrived = true;
			return;
		}
		
	}
	
	public boolean hasArrived(){
		return this.hasArrived;
	}
	
	
	
	private boolean hasArrived = false;
	
	

}
