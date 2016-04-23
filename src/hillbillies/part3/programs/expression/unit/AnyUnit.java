package hillbillies.part3.programs.expression.unit;

import java.util.Random;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class AnyUnit extends UNIT {

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
	public Unit getUnit(World world, Unit unit) {
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

}
