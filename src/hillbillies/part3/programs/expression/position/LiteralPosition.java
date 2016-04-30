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
public class LiteralPosition extends PositionExpression {
	
	public LiteralPosition(int x, int y, int z, SourceLocation sourceLocation){
		super(sourceLocation);
		int[] position = {x, y, z};
		this.position = position;
	}
	
	public int[] getPosition(World world, Unit unit){
		if(world.isValidWorldPosition(this.position))
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
		int[] position = this.getPosition(unit.getWorld(), unit);
		return "LiteralPosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}
}
