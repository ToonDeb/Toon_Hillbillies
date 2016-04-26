package hillbillies.part3.programs.expression.unit;

import java.util.Random;
import java.util.Set;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class Enemy extends UnitExpression {

	/**
	 * @param sourceLocation
	 */
	public Enemy(SourceLocation sourceLocation) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.UNIT#getUnit(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Unit getUnit(World world, Unit unit) {
		Set<Faction> factions = world.getActiveFactions();
		Faction enemyFaction = null;
		int random = new Random().nextInt(factions.size()-1);
		int i = -1;
		for(Faction faction: factions){
			if (faction != unit.getFaction()){
				i +=1;
				if (i == random){
					enemyFaction = faction;
					break;
				}
			}
		}
		
		random = new Random().nextInt(enemyFaction.getNbUnits());
		i = -1;
		for (Unit anyUnit: enemyFaction.getUnitsOfFaction()){
			i += 1;
			if (i == random){
				return anyUnit;
			}
		}
		
		return null;
	}

}
