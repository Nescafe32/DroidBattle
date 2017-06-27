package task2.model;


public class KillerDroid extends BasicDroid {
	
	private double criticalDamage;
	private double stunEnemy;
	
	public KillerDroid(String name, double accuracy, double damage, double healthPoints, double evasion, 
			double criticalDamage, double stunEnemy, double healingAllConfederates, boolean state) {
		
		super(name, accuracy, damage, healthPoints, evasion, state);
		this.criticalDamage = criticalDamage;
		this.stunEnemy = stunEnemy;
	}

	public double getCriticalDamage(double oldDamage) {
		
		return Math.random() > criticalDamage ? oldDamage : oldDamage + oldDamage * criticalDamage;
	}

	public boolean setStunEnemy(String enemyName) {
		
		return Math.random() < stunEnemy; 
	}
}