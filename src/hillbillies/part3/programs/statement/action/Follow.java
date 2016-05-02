package hillbillies.part3.programs.statement.action;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.expression.unit.UnitExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class Follow extends Action {

	/**
	 * @param expression
	 * @param sourceLocation
	 */
	public Follow(MyExpression expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.statement.Action#execute(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public void execute(World world, Unit unit) {
		Unit following = ((UnitExpression)this.getExpression()).getUnit(world, unit);
		if(!following.isNeighbouringCube(unit.getCubePosition())){
			unit.moveTo(following.getCubePosition());
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
