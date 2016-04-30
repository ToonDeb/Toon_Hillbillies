package hillbillies.part3.programs.statement;

import org.antlr.v4.parse.ANTLRParser.action_return;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.logic.TrueExpression;
import hillbillies.part3.programs.expression.position.LiteralPosition;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class Experiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SourceLocation sourceLocation = new SourceLocation(1, 1);
		TrueExpression boolexpr = new TrueExpression(sourceLocation);
		LiteralPosition posexpr = new LiteralPosition(0, 0, 0, sourceLocation);
		Attack attack = new Attack(posexpr, sourceLocation);
		Print print = new Print(posexpr, sourceLocation);
		int[] test = {1,2,3};
		System.out.println("test: " + test[0] + "," + test[1] + "," + test[2]);
	}

}
