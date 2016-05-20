package hillbillies.part3.programs.expression.unit;

import java.util.Random;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of AnyUnit expressions
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class AnyUnit extends MyExpression<Unit> {

	/**
	 * @param sourceLocation
	 */
	public AnyUnit(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.UNIT#getUnit(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Unit evaluateExpression(Unit unit) {
		World world = unit.getWorld();
		int random = new Random().nextInt(world.getNbUnits());
		int i = -1;
		for (Unit anyUnit: world.getUnits()){
			i += 1;
			if (i == random){
				return anyUnit;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "AnyUnit: " + this.evaluateExpression(unit).getName();
	}

}
