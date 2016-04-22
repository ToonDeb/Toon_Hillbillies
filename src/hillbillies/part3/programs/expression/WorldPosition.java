package hillbillies.part3.programs.expression;

import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public abstract class WorldPosition extends MyExpression {
	
	/**
	 * @param sourceLocation
	 */
	public WorldPosition(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	public abstract int[] getPosition(World world);

}
