package task2.model;

public class ExplorationDroid extends BasicDroid {

	private double getOpponentsHealth;
	
	public ExplorationDroid(String name, double accuracy, double damage, double healthPoints, double evasion,
			double getOpponentsHealth, boolean state) {
		
		super(name, accuracy, damage, healthPoints, evasion, true);
		this.getOpponentsHealth = getOpponentsHealth;
	}
	
	public double getOpponentnsHealth () {
		
		return getOpponentsHealth;
	}
	
	public void tryToGetOpponentsHealthPoints (String [] names, double [] healths) {
		
		for (int i = 0; i < names.length; ++i) {
			System.out.println (names[i] + " has " + healths[i] + " health points");
		}
		System.out.println ("\n");
	}
}