package hillbillies.part3.programs.expression.position;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class LiteralPosition extends MyExpression<int[]> {
	
	public LiteralPosition(int x, int y, int z, SourceLocation sourceLocation){
		super(sourceLocation);
		int[] position = {x, y, z};
		this.position = position;
	}
	
	public int[] evaluateExpression(Unit unit){
		if(unit.getWorld().isValidWorldPosition(this.position))
			return this.position;
		else
			throw new IllegalArgumentException("not a valid position in this world");
	}
	
	private final int[] position;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.evaluateExpression(unit);
		return "LiteralPosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}
}
