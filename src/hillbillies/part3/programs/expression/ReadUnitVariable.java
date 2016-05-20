package hillbillies.part3.programs.expression;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ReadUnitVariable expressions
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class ReadUnitVariable extends ReadVariable<Unit> {

	/**
	 * @param variableName
	 * @param sourceLocation
	 */
	public ReadUnitVariable(String variableName, SourceLocation sourceLocation) {
		super(variableName, sourceLocation);
	}

	



}
