package hillbillies.part3.programs.expression;


import hillbillies.part3.programs.SourceLocation;

/**
 * A class of readPositionVariables expressions
 *
 * @author Toon Deburchgrave
 * @version 1.0
 */
public class ReadPositionVariable extends ReadVariable<int[]> {

	/**
	 * @param variableName
	 * @param sourceLocation
	 */
	public ReadPositionVariable(String variableName, SourceLocation sourceLocation) {
		super(variableName, sourceLocation);
	}

}
