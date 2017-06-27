package task2.model;

public class BasicDroid {
		
	protected String name;
	protected double accuracy;
	protected double damage;
	protected double healthPoints;
	protected double evasion;
	protected boolean state = true;
	
	public BasicDroid(String name, double accuracy, double damage, double healthPoints, double evasion, boolean state) {
		
		super();
		this.name = name;
		this.accuracy = accuracy;
		this.damage = damage;
		this.healthPoints = healthPoints;
		this.evasion = evasion;
		this.state = state;
	}
	
	public String getName() {
		
		return name;
	}
	
	public double getAccuracy(String name) {
		
		return accuracy;
	}
	
	public double getDamage(String name) {
		
		return damage;
	}
	
	public double getHealthPoints(String name) {
		
		return healthPoints;
	}
	
	public double setHealthPoints(double newHealth) {
		
		return this.healthPoints = newHealth;
	}
	
	public double getEvasion(String name) {
		
		return evasion;
	}
	
	public boolean getState(String name) {
		
		return state;
	}
	
	public void setState(String name, boolean state) {
		
		this.state = state;
	}
	
	@Override
	public String toString () {
		return name;
	}
	
	public boolean hitSomeDroid (String attackerName, String defenderName, double attackerDamage, double attackerAccuracy, 
			double defenderEvasion, double healthPoints) {
		
		double accuracyChance = Math.random();
		double defenderEvasionChance = Math.random();
		
		if (accuracyChance > attackerAccuracy) {
			System.out.println (attackerName + " hits in the sky. Very bad (or not?)");
			return false;
		}
		
		if (defenderEvasionChance < defenderEvasion) {
			System.out.println (defenderName + " escaped from " + attackerName + "'s attack" + ". Great reaction!");
			return false;
		}
		
		System.out.println (attackerName + " hits " + defenderName);
		
		return true;
	}
	
	public boolean isDroidAlive (double healthPoints) {
		
		return healthPoints > 0;
	}
}