package task2.controller;

import task2.model.BasicDroid;
import task2.model.ExplorationDroid;
import task2.model.KillerDroid;
import task2.model.MedicDroid;

import java.util.ArrayList;
import java.util.Scanner;

public class BattleArena {
	
	Scanner scanner;
	
	private void createSomeArmy (String basicDroidName, String explorationDroidName, 
			String medicDroidName, String killerDroidName, ArrayList <BasicDroid> someArmy) {
		
		someArmy.add(new ExplorationDroid(explorationDroidName, 0.65, 15, 165, 0.35, 0.75, true));
		someArmy.add(new MedicDroid(medicDroidName, 0.4, 10, 120, 0.15, 0.7, 0.8, true));
		someArmy.add(new BasicDroid (basicDroidName, 0.7, 22, 200, 0.3, true));
		someArmy.add(new KillerDroid(killerDroidName, 0.85, 30, 300, 0.4, 0.2, 0.4, 0.15, true));
	}
	
	private void lookOurTeamHealthPointsAndStates (ArrayList <BasicDroid> ourArmy) {
		
		System.out.println ("\n");
		for (BasicDroid droid : ourArmy) {
			String droidName = droid.getName();
			System.out.print (droidName + " has " + droid.getHealthPoints(droidName) + " health points and he's "); 
			System.out.println ((droid.getState(droidName)) ? " normal" : " stunned");
		}
	}
	
	private int weSelectOpponentSoldier (String ourAttackerName, ArrayList <BasicDroid> opponentArmy) {
		
		int i = 0, medicDroidPosition = -1, attackOpponentPosition = 0;
		boolean flag = true;
		
		opponentArmy.get(i).getName(); // if there're no enemies, exception will be thrown. it looks bad, but is's work
		
		System.out.println("\n");
		for (; i < opponentArmy.size(); ++i) {
			if (opponentArmy.get(i).getName().equals("opponentMedicDroid") && opponentArmy.size() > 2) {
				flag = false;
			}
			
			if (flag)
				System.out.println (i + 1 + " - " + opponentArmy.get(i).getName());
			else {
				System.out.println (i + 1 + " - " + opponentArmy.get(i).getName() + " - he's now protected and you can't attack him");
				medicDroidPosition = i + 1;
				flag = true;
			}
		}
		
		flag = false;
		
		System.out.println ("\n" + ourAttackerName + " turn. Select the opponent");
		
		do {	
			
			if (scanner.hasNextInt()) {
				
				attackOpponentPosition = scanner.nextInt();
				if (attackOpponentPosition < 1 || attackOpponentPosition > opponentArmy.size() || attackOpponentPosition == medicDroidPosition) {
					System.out.println ("Wrong number, plese try again!");
				}
				else {
					System.out.println ("\n" + ourAttackerName + " will attack " + opponentArmy.get(attackOpponentPosition - 1).getName());
					flag = true;
				}
			}
			
			else {
				System.out.println ("It's not a number!");
				scanner.next();
			}
				
		} while (!flag);
		
		return attackOpponentPosition - 1;
	}
	
	private int opponentSelectOurSoldier (String opponentAttackerName, ArrayList <BasicDroid> ourArmy) {
			
		int ourSoldierPosition = -1;
		boolean flag = false;
		
		do {	
			
			ourSoldierPosition = ((int)(Math.random( ) * ourArmy.size()));
			
			if (ourArmy.get(ourSoldierPosition).getName().equals("ourMedicDroid") && ourArmy.size() > 2) {
				continue;
			}	

			else {
				System.out.println ("\n" + opponentAttackerName + " will attack " + ourArmy.get(ourSoldierPosition).getName());
				flag = true;
			}
				
		} while (!flag);
		
		return ourSoldierPosition;
	}
	
	private int weSelectConfederate (ArrayList <BasicDroid> ourArmy) {
		
		int confederatePosition = -1;
		
		for (int i = 0; i < ourArmy.size(); ++i) {
			System.out.println (i + 1 + " - " + ourArmy.get(i).getName());
		}
		
		System.out.println ("\nSelect the confederate");
		
		boolean flag = false;
		
		do {	
			
			if (scanner.hasNextInt()) {
				
				confederatePosition = scanner.nextInt();
				if (confederatePosition < 1 || confederatePosition > ourArmy.size()) {
					System.out.println ("Wrong number, plese try again!");
				}
				else 
					flag = true;
			}
			
			else {
				System.out.println ("It's not a number!");
				scanner.next();
			}
				
		} while (!flag);
		
		return confederatePosition - 1;
	}
	
	private int opponentSelectConfederate (ArrayList <BasicDroid> opponentArmy) {
		
		return ((int)(Math.random( ) * opponentArmy.size()));
	}
	
	private int defineAttackerAndDefenderDroids (BasicDroid attackerDroid, double damage, ArrayList <BasicDroid> defenderArmy) {
		
		int position = -1;
		
		if (attackerDroid.getName().matches("our.+")) {
			position = weSelectOpponentSoldier(attackerDroid.getName(), defenderArmy);
		}
		
		else
			position = opponentSelectOurSoldier(attackerDroid.getName(), defenderArmy);
		
		String attackerName = attackerDroid.getName();
		String defenderName = defenderArmy.get(position).getName();
		
		if (attackerDroid.hitSomeDroid(attackerName, defenderName, damage, 
				attackerDroid.getAccuracy(attackerName), defenderArmy.get(position).getEvasion(defenderName), 
				defenderArmy.get(position).getHealthPoints(defenderName))) {
			
			defenderArmy.get(position).setHealthPoints(defenderArmy.get(position).getHealthPoints(defenderName) - attackerDroid.getDamage(attackerName));
			
			if (!attackerDroid.isDroidAlive(defenderArmy.get(position).getHealthPoints(defenderName))) {
				defenderArmy.remove(defenderArmy.get(position));
				System.out.println (defenderName + " HAS BEEN KILLED! " + attackerName + ", probably, looks happy, "
						+ "but he's only droid and successfully hides his emotions. But we know truth.");
			}
		}
		
		return position;
	}
	
	private void ourTurn (ArrayList <BasicDroid> ourArmy, ArrayList <BasicDroid> opponentArmy) {
		
		lookOurTeamHealthPointsAndStates(ourArmy);
		
		for (BasicDroid droid : ourArmy) {
			
			if (droid.getClass().getTypeName().matches(".+ExplorationDroid")) {
				if (!droid.getState(droid.getName())) continue;
				
				double getOpponentsHealthChance = Math.random();
				if (getOpponentsHealthChance > ((ExplorationDroid) droid).getOpponentnsHealth()) {
					System.out.println ("\n" + droid.getName() + " could not find out opponents health.");
				} 
				
				else {
					System.out.println ("\n" + droid.getName() + " found out opponents health:");
					String [] names = new String[opponentArmy.size()];
					double [] healths = new double [names.length];
					for (int i = 0; i < healths.length; ++i) {
						names[i] = opponentArmy.get(i).getName();
						healths[i] = opponentArmy.get(i).getHealthPoints(names[i]);
					}
						
					((ExplorationDroid) droid).tryToGetOpponentsHealthPoints(names, healths);
				}
				
				defineAttackerAndDefenderDroids(droid, droid.getDamage(droid.getName()), opponentArmy);
			}
				
			if (droid.getClass().getTypeName().matches(".+MedicDroid")) {
				if (!droid.getState(droid.getName())) continue;
				
				if (MedicDroid.ifWeCanHelpConfederate % 3 == 0) {
					int position = weSelectConfederate(ourArmy);
					String confederateName = ourArmy.get(position).getName();
					double healthPoints = ((MedicDroid) droid).healingConfederate(droid.getName(), confederateName, ourArmy.get(position).getHealthPoints(confederateName));
					ourArmy.get(position).setHealthPoints(healthPoints);
				}
				
				if (MedicDroid.ifWeCanHelpConfederate++ % 2 == 0) {
					for (BasicDroid droid1 : ourArmy) {
						if (droid1.getState(droid1.getName()) == false) {
							boolean reviveResult = ((MedicDroid) droid).changeConfederateState(droid.getName(), droid1.getName());
							droid1.setState(droid1.getName(), reviveResult);
							break;
						}
					}
				}
				
				defineAttackerAndDefenderDroids(droid, droid.getDamage(droid.getName()), opponentArmy);
			}
			
			if (droid.getClass().getTypeName().matches(".+BasicDroid")) {
				if (!droid.getState(droid.getName())) continue;
				defineAttackerAndDefenderDroids(droid, droid.getDamage(droid.getName()), opponentArmy);
			}
			
			if (droid.getClass().getTypeName().matches(".+KillerDroid")) {
				
				if (!droid.getState(droid.getName())) continue;
				String attackerName = droid.getName();
				double newDamage = ((KillerDroid) droid).getCriticalDamage(droid.getDamage(attackerName));
				
				int position = defineAttackerAndDefenderDroids(droid, newDamage, opponentArmy);
				
				if (((KillerDroid) droid).setStunEnemy(opponentArmy.get(position).getName())) {
					System.out.println (opponentArmy.get(position).getName() + " was successfully stunned. He looks confused... or it's a normal expression on his face?");
					opponentArmy.get(position).setState(opponentArmy.get(position).getName(), false);
				}
			}
		}
	}
	
	private void opponentTurn (ArrayList <BasicDroid> opponentArmy, ArrayList <BasicDroid> ourArmy) throws InterruptedException {
		
		for (BasicDroid droid : opponentArmy) {
			
			if (droid.getClass().getTypeName().matches(".+ExplorationDroid")) {
				if (!droid.getState(droid.getName())) continue;
				
				double getOpponentsHealthChance = Math.random();
				if (getOpponentsHealthChance > ((ExplorationDroid) droid).getOpponentnsHealth()) {
					Thread.sleep(50);
					System.out.println ("\n" + droid.getName() + " could not find out opponents health.\n");
				} 
				
				else {
				
					String [] names = new String[ourArmy.size()];
					double [] healths = new double [names.length];
					for (int i = 0; i < healths.length; ++i) {
						names[i] = ourArmy.get(i).getName();
						healths[i] = ourArmy.get(i).getHealthPoints(names[i]);
					}
						
					((ExplorationDroid) droid).tryToGetOpponentsHealthPoints(names, healths);
				}
				
				Thread.sleep(50);
				defineAttackerAndDefenderDroids(droid, droid.getDamage(droid.getName()), ourArmy);
			}
				
			if (droid.getClass().getTypeName().matches(".+MedicDroid")) {
				if (!droid.getState(droid.getName())) continue;
				Thread.sleep(200);
				if (MedicDroid.ifOpponentCanHelpConfederate % 3 == 0) {
					int position = opponentSelectConfederate(opponentArmy);
					String confederateName = opponentArmy.get(position).getName();
					double healthPoints = ((MedicDroid) droid).healingConfederate(droid.getName(), confederateName, opponentArmy.get(position).getHealthPoints(confederateName));
					opponentArmy.get(position).setHealthPoints(healthPoints);
				}
				
				if (MedicDroid.ifOpponentCanHelpConfederate++ % 2 == 0) {
					for (BasicDroid droid1 : opponentArmy) {
						if (droid1.getState(droid1.getName()) == false) {
							boolean reviveResult = ((MedicDroid) droid).changeConfederateState(droid.getName(), droid1.getName());
							droid1.setState(droid1.getName(), reviveResult);
							break;
						}
					}
				}
				
				Thread.sleep(200);
				if ((defineAttackerAndDefenderDroids(droid, droid.getDamage(droid.getName()), ourArmy)) == -1) return;
			}
			
			if (droid.getClass().getTypeName().matches(".+BasicDroid")) {
				if (!droid.getState(droid.getName())) continue;
				Thread.sleep(200);
				if ((defineAttackerAndDefenderDroids(droid, droid.getDamage(droid.getName()), ourArmy)) == -1) return;
			}
			
			if (droid.getClass().getTypeName().matches(".+KillerDroid")) {
				
				if (!droid.getState(droid.getName())) continue;
				Thread.sleep(200);
				String attackerName = droid.getName();
				double newDamage = ((KillerDroid) droid).getCriticalDamage(droid.getDamage(attackerName));
				
				int position = defineAttackerAndDefenderDroids(droid, newDamage, ourArmy);
				if (position == -1)
					return;
				
				if (((KillerDroid) droid).setStunEnemy(ourArmy.get(position).getName())) {
					System.out.println (ourArmy.get(position).getName() + " was successfully stunned. Let's hope that our medic droid will be able to revive him.");
					ourArmy.get(position).setState(ourArmy.get(position).getName(), false);
				}
			}
		}
	}

	public void startTheGreatBattle () {
		
		scanner = new Scanner (System.in);
		
		ArrayList <BasicDroid> ourSoldiers, opponentSoldiers;
		ourSoldiers = new ArrayList <> ();
		opponentSoldiers = new ArrayList <> ();
		
		// create our great army
		createSomeArmy("ourBasicDroid", "ourExplorationDroid", 
				"ourMedicDroid", "ourKillerDroid", ourSoldiers);
		
		
		// create opponents not great army
		createSomeArmy("opponentBasicDroid", "opponentExplorationDroid", 
				 "opponentMedicDroid", "opponentKillerDroid", opponentSoldiers);
		
		int roundsOfBattle = 0;
		
		double selectFirstMove = Math.random();
		
		// LET'S FIGHT AND KILL THEM ALLLLLLL!!!!!!!!!!!!!!!!
		while (true) {
			
			if (ourSoldiers.size() == 0) { 
				System.out.println ("Our army lost this great battle :("); 
				break; 
			}
			
			else if (opponentSoldiers.size() == 0) { 
				System.out.println ("WE WON THIS BATTLE!!!!!!! CONGRATULATIONS!!!!!"); 
				break; 
			}
			
			System.out.println ("\n\n========================ROUND " + ++roundsOfBattle + "========================");

			try 
			{
				if (selectFirstMove < 0.5) {
					ourTurn (ourSoldiers, opponentSoldiers);
					opponentTurn (opponentSoldiers, ourSoldiers);
				}
				
				else {
					opponentTurn (opponentSoldiers, ourSoldiers);
					ourTurn (ourSoldiers, opponentSoldiers);
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.print ("\n\nThe battle is over. ");
			} catch (InterruptedException e) {
				System.out.println ("Smth went wrong. Are you sure that you know how the threads work?");
			}
		}
		
		scanner.close();
	}
}