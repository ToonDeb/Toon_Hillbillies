package hillbillies.model;

/**
 * A class of CubeTypes
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public enum CubeType {
	
	AIR{
		public boolean isPassable(){
			return true;
		}
		public int getNumber(){
			return 0;
		}
	},
	
	ROCK{
		public boolean isPassable(){
			return false;
		}
		public int getNumber(){
			return 1;
		}
	},
	
	TREE{
		public boolean isPassable(){
			return false;
		}
		public int getNumber(){
			return 2;
		}
	},
	
	WORKSHOP{
		public boolean isPassable(){
			return true;
		}
		
		public int getNumber(){
			return 3;
		}
	};
	
	public abstract boolean isPassable();
	
	public abstract int getNumber();
	
	public static CubeType getCubeType(int number){
		for(CubeType type: CubeType.values()){
			if (type.getNumber() == number){
				return type;
			}	
		}
		return null;
	}

}
