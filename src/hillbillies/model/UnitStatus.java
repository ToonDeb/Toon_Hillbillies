package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration introducing different states units can be in.
 *
 * @author  Toon Deburchgrave, Nathan Cornille
 * @version 1.0
 */
@Value
public enum UnitStatus {
	WALKING,
	
	SPRINTING,
	
	WORKING,
	
	ATTACKING,
	
	DEFENDING,
	
	RESTING,
	
	REST,		// initiating the rest-status. when 1 HP is added, go to RESTING status 
	
	IDLE,
	
	FALLING,
	
	DODGING
}
