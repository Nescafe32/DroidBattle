package task2.model;

public class MedicDroid extends BasicDroid {
	
	private double healingConfederate;
	private double renewConfederate;
	public static double ifWeCanHelpConfederate = 1;
	public static double ifOpponentCanHelpConfederate = 1;

	public MedicDroid(String name, double accuracy, double damage, double healthPoints, double evasion, 
			double healingConfederate, double renewConfederate, boolean state) {
		
		super(name, accuracy, damage, healthPoints, evasion, state);
		this.healingConfederate = healingConfederate;
		this.renewConfederate = renewConfederate;
	}
	
	public double healingConfederate (String medicName, String confederateName, double confederateHealthPoints) {
		
		double healingChance = Math.random();
		
		if (healingChance > healingConfederate) {
			System.out.println ("Is it really medical droid? Where he bought a medical diploma? " + confederateName + " displeased.");
			return confederateHealthPoints;
		}
		
		System.out.println (confederateName + " can be pleased. " + medicName + " succesfully added him a bit of health.");
		return confederateHealthPoints + 15;
	}
	
	public boolean changeConfederateState (String medicName, String confederateName) {
		
		double renewConfederateChance = Math.random();
		
		if (renewConfederateChance < renewConfederate) {
			System.out.println ("\n" + confederateName + " was stunned, but " + medicName + " successfully revived him!");
			return true;
		}
		
		System.out.println ("\n" + medicName + " tried to revive " + confederateName + ", but it didn't work.");
		return false;
	}
}