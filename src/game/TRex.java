package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * @author user
 *
 */

public class TRex extends Actor {
	
	private Behaviour wanderBehaviour, foodSeekBehaviour;
	private int maxHungerLevel = 100;
	private int hungerLevel = 50;
	private int hatchlingAge = 0;
	
	public TRex(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addSkill(GameSkills.HEALTHY);
		this.addSkill(GameSkills.TAGABLE);
		//this.addSkill(GameSkills.CARNIVORE);
		this.addSkill(GameSkills.TREX);
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
		
		if(this.hasSkill(GameSkills.HATCHLING) == true){ // TRex hatchling aging flow and maturation
			hatchlingAge += 1;
			
			
			if(hatchlingAge > 20){
				this.displayChar = 'R';
				setMaxHungerLevel(50);
				this.addSkill(GameSkills.ADULT);
				this.addSkill(GameSkills.GAME_END);
				this.removeSkill(GameSkills.HATCHLING);
			}
			
			
			if(hungerLevel == 0){
				Corpse corpse = new Corpse("TRex Corpse", 'x', true);
				map.locationOf(this).addItem(corpse);
				map.removeActor(this); // Plesiosaur hatchling dies
				return new DoNothingAction();
			} else if(hungerLevel < 10){
				this.removeSkill(GameSkills.HEALTHY);
				System.out.println("TRex at (" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ") is hungry");
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
		 * Flow for TRex hunger levels:
		 * when it == 0, it starves and dies and leaves a corpse behind,
		 * when it is less than ten it seeks food etc.
		 */
		this.addSkill(GameSkills.HEALTHY);
		if(this.hasSkill(GameSkills.GAME_END) == true && this.hasSkill(GameSkills.ADULT) == true){
			map.at(0, 0).getItems().get(0).addSkill(GameSkills.GAME_END);
		}
		if(hungerLevel == 0){
			Corpse corpse = new Corpse("TRex Corpse", 'x', true);
			map.locationOf(this).addItem(corpse);
			map.removeActor(this);
			return new DoNothingAction();
		} else if(hungerLevel < 10){
			this.removeSkill(GameSkills.HEALTHY);
			System.out.println("TRex at (" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ") is hungry");
			Action foodSeek = foodSeekBehaviour.getAction(this, map);
			if (foodSeek != null)
				return foodSeek;
		} else if(this.hasSkill(GameSkills.HEALTHY) == true){
			double randBreed = Math.random(); // TRex breeding flow with creation of TRex egg
			if(randBreed < 0.1){
				Egg egg = new Egg("TRex Egg", 'e', true);
				egg.addSkill(GameSkills.TREX);
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

