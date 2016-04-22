package hillbillies.part3.programs;

import hillbillies.model.Unit;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class UNIT implements MyExpression {
	
	public UNIT(Unit unit){
		this.unit = unit;
	}
	
	public Unit getUnit(){
		return this.unit;
	}
	
	private final Unit unit;
}
