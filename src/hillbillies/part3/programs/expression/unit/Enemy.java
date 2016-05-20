package hillbillies.part3.programs.expression.unit;

import java.util.Random;
import java.util.Set;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of Enemy Expressions
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class Enemy extends MyExpression<Unit> {

	/**
	 * @param sourceLocation
	 */
	public Enemy(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.UNIT#getUnit(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public Unit evaluateExpression(Unit unit) {
		World world = unit.getWorld();
		Set<Faction> factions = world.getActiveFactions();
		Faction enemyFaction = null;
		if (factions.size() == 1)
			enemyFaction = factions.iterator().next();
		else{
			int random = (new Random().nextInt(factions.size()-1));
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
		}
		int random = new Random().nextInt(enemyFaction.getNbUnits());
		int i = -1;
		for (Unit anyUnit: enemyFaction.getUnitsOfFaction()){
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
		return "EnemyUnit: " + this.evaluateExpression(unit).getName();
	}

}
