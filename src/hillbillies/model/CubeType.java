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
	
	/**
	 * Return whether the cubetype is passable
	 */
	public abstract boolean isPassable();
	
	/**
	 * Return the number associated with the cubetype
	 */
	public abstract int getNumber();
	
	/**
	 * Return the cubetype for the given number
	 * @param 	number
	 * 		 	The number to get the cubetype from
	 * @return  The cubetype with the number given, 
	 * 			if this doesn't exist return null
	 */
	public static CubeType getCubeType(int number){
		for(CubeType type: CubeType.values()){
			if (type.getNumber() == number){
				return type;
			}	
		}
		return null;
	}

}
