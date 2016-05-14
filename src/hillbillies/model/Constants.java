package hillbillies.model;

/**
 * A class of Constants
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public final class Constants {
	
//	public static final int MAX_X_POSITION = 50;
//	public static final int MAX_Y_POSITION = 50;
//	public static final int MAX_Z_POSITION = 50;
	
	public static final int MAX_OBJECT_WEIGHT = 50;
	public static final int MIN_OBJECT_WEIGHT = 10;
	
	public static final int MAX_NB_UNITS_IN_FACTION = 50;
	public static final int MAX_NB_UNITS_IN_WORLD = 100;
	
	public static final int MAX_NB_ACTIVE_FACTIONS = 5;
	
//	public static final int AIR = 0;
//	public static final int ROCK = 1;
//	public static final int TREE = 2;
//	public static final int WORKSHOP = 3;
//	•0: air
//	•1: rock
//	•2: tree
//	•3: workshop
	
	public static final int MAXSEARCHDEPTH = 100;
	
	// does not contain {0,0,0}
	public static final int[][] NEIGHBOURINGLIST = {
			{-1,-1,-1},{0,-1,-1},{1,-1,-1},
			{-1,0,-1},{0,0,-1},{1,0,-1},
			{-1,1,-1},{0,1,-1},{1,1,-1},
			{-1,-1,0},{0,-1,0},{1,-1,0},
			{0,-1,0},{1,-1,0},{-1,0,0},
			{1,0,0},{-1,1,0},
			{0,1,0},{1,1,0},{-1,-1,1},
			{0,-1,1},{1,-1,1},{-1,0,1},
			{0,0,1},{1,0,1},{-1,1,1},
			{0,1,1},{1,1,1}};
			
	public static final int[][] DIRECTLYNEIGHBOURINGLIST = {
			{-1,0,0},{1,0,0},{0,-1,0},{0,1,0},{0,0,-1},{0,0,1}}; 



}
