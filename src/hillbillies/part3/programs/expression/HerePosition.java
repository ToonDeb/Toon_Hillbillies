package hillbillies.part3.programs.expression;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class HerePosition extends MyExpression{
	
	public HerePosition(SourceLocation sourceLocation){
		super(sourceLocation);
		
	}
	
	public int[] getPosition(Unit unit){
		return unit.getCubePosition();
	}
}
