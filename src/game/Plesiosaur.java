/**
 * 
 */
package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * A carnivorous marine dinosaur.
 *
 */
public class Plesiosaur extends Actor {
	
	private Behaviour wanderBehaviour, foodSeekBehaviour;
	private int maxHungerLevel = 100;
	private int hungerLevel = 50;
	private int hatchlingAge = 0;
	
	public Plesiosaur(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addSkill(GameSkills.HEALTHY);
		this.addSkill(GameSkills.TAGABLE);
		//this.addSkill(GameSkills.CARNIVORE);
		this.addSkill(GameSkills.PLESIOSAUR);
		this.addSkill(GameSkills.WATER_ONLY);
		wanderBehaviour = new WanderBehaviour();
	}

	public void setHungerLevelHatchling(){
		hungerLevel = 15;
	}
	
	public int getHungerLevel() {
		//Mutator for setting customer name
		return hungerLevel;
	}
	
	public void setCurrentToMaxHunger() {
		//Mutator for setting customer name
		hungerLevel = maxHungerLevel;
	}
	
	public void changeHungerLevel(int value) {
		if(hungerLevel + value > maxHungerLevel){
			setCurrentToMaxHunger();
		} else {
			hungerLevel += value;
		}
	}
	
	public void setMaxHungerLevel(int value) {
		//Mutator for setting customer name
		maxHungerLevel = value;
	}

	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions list = super.getAllowableActions(otherActor, direction, map);
		list.add(new AttackAction(this));
		list.add(new TagAction(this));
		list.add(new FeedAction(this));
		return list;
	}
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		foodSeekBehaviour = new FoodSeekBehaviour(this, map);
		hungerLevel -= 1;
		
		if(map.at(0, 0).getItems().get(0).hasSkill(GameSkills.NIGHT) == true){
			return new DoNothingAction();
		}
		
		if(this.hasSkill(GameSkills.HATCHLING) == true){ // Plesiosaur hatchling aging flow and maturation
			hatchlingAge += 1;
			if(hatchlingAge > 20){
				this.displayChar = 'L';
				setMaxHungerLevel(50);
				this.addSkill(GameSkills.ADULT);
				this.removeSkill(GameSkills.HATCHLING);
			}
			if(hungerLevel == 0){
				Corpse corpse = new Corpse("Plesiosaur Corpse", 'x', true);
				map.locationOf(this).addItem(corpse);
				map.removeActor(this); // Plesiosaur hatchling dies
				return new DoNothingAction();
			} else if(hungerLevel < 10){
				this.removeSkill(GameSkills.HEALTHY);
				System.out.println("Plesiosaur at (" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ") is hungry");
				Action foodSeek = foodSeekBehaviour.getAction(this, map);
				if (foodSeek != null)
					return foodSeek;
			} else {
				Action wander = wanderBehaviour.getAction(this, map);
				if (wander != null)
					return wander;
			}
		}
		/**
		 * Flow for velociraptors hunger levels:
		 * when it == 0, it starves and dies and leaves a corpse behind,
		 * when it is less than ten it seeks food etc.
		 */
		this.addSkill(GameSkills.HEALTHY);
		if(hungerLevel == 0){
			Corpse corpse = new Corpse("Plesiosaur Corpse", 'x', true);
			map.locationOf(this).addItem(corpse);
			map.removeActor(this);
			return new DoNothingAction();
		} else if(hungerLevel < 10){
			this.removeSkill(GameSkills.HEALTHY);
			System.out.println("Plesiosaur at (" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ") is hungry");
			Action foodSeek = foodSeekBehaviour.getAction(this, map);
			if (foodSeek != null)
				return foodSeek;
		} else if(this.hasSkill(GameSkills.HEALTHY) == true){
			double randBreed = Math.random(); // Plesiosaur breeding flow with creation of velociraptor egg
			if(randBreed < 0.1){
				Egg egg = new Egg("Plesiosaur Egg", 'e', true);
				egg.addSkill(GameSkills.PLESIOSAUR);
				map.locationOf(this).addItem(egg);
				}
			Action wander = wanderBehaviour.getAction(this, map);
			if (wander != null)
				return wander;
		} else {
			Action wander = wanderBehaviour.getAction(this, map);
			if (wander != null)
				return wander;
		}
		

		return new DoNothingAction();
	
	}


}
