package hillbillies.part3.programs.statement;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of statements
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public abstract class MyStatement {
	
	public MyStatement(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation(){
		return this.sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
	
	public abstract StatementIterator iterator(World world, Unit unit);

}
