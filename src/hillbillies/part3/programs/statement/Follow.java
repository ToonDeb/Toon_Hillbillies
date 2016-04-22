package hillbillies.part3.programs.statement;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

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

}
