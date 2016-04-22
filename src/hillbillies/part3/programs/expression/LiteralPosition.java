package hillbillies.part3.programs.expression;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class LiteralPosition extends WorldPosition {
	
	public LiteralPosition(int x, int y, int z, SourceLocation sourceLocation){
		super(sourceLocation);
		int[] position = {x, y, z};
		this.position = position;
	}
	
	public int[] getPosition(World world){
		return this.position;
	}
	
	private final int[] position;
}
