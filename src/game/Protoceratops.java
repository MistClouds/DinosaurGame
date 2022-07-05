package game;


import edu.monash.fit2099.demo.mars.KickAction;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * A herbivorous dinosaur.
 *
 */
public class Protoceratops extends Actor {
	// Will need to change this to a collection if Protoceratops gets additional Behaviours.
	private Behaviour wanderBehaviour, foodSeekBehaviour;
	private int maxHungerLevel = 50;
	private int hungerLevel = 30;
	private int hatchlingAge = 0;

	/** 
	 * Constructor.
	 * All Protoceratops are represented by a 'p' and have 50 max hunger points.
	 * 
	 * @param name - the name of this Protoceratops
	 * @param displayChar - The character that this Protoceratops is displayed by (Usually 'P')
	 */
	public Protoceratops(String name, char displayChar) {
		super(name, displayChar, 100);
		this.addSkill(GameSkills.TAGABLE);
		this.addSkill(GameSkills.HERBIVORE);
		this.addSkill(GameSkills.PROTOCERATOPS);
		wanderBehaviour = new WanderBehaviour();
	}
	
	public void setHungerLevelHatchling(){
		hungerLevel = 10;
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

	/**
	 * Figure out what to do next.
	 * 
	 * 
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		foodSeekBehaviour = new FoodSeekBehaviour(this, map);
		hungerLevel -= 1;
		
		if(map.at(0, 0).getItems().get(0).hasSkill(GameSkills.NIGHT) == true){
			return new DoNothingAction();
		}
		//this.displayChar = 'P';
		
		/**
		 * If this dinosaur is a hatchling it will wait and continue to mature into an adult
		 */
		if(this.hasSkill(GameSkills.HATCHLING) == true){
			hatchlingAge += 1;
			if(hatchlingAge > 20){
				this.displayChar = 'P';
				setMaxHungerLevel(50);
				this.addSkill(GameSkills.ADULT);
				this.removeSkill(GameSkills.HATCHLING);
			}
			/**
			 * if it has 0 hunger it dies
			 */
			if(hungerLevel == 0){
				Corpse corpse = new Corpse("Protoceratops Corpse", 'x', true);
				map.locationOf(this).addItem(corpse);
				map.removeActor(this);
				return new DoNothingAction();
			} else if(hungerLevel < 10){
				this.removeSkill(GameSkills.HEALTHY);
				System.out.println("Protoceratops at (" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ") is hungry");
				Action foodSeek = foodSeekBehaviour.getAction(this, map);
				if (foodSeek != null)
					return foodSeek;
			} else {
				Action wander = wanderBehaviour.getAction(this, map);
				if (wander != null)
					return wander;
			}
		}
		this.addSkill(GameSkills.HEALTHY);
		if(hungerLevel == 0){ // At 0 hunger, death
			Corpse corpse = new Corpse("Protoceratops Corpse", 'x', true);
			map.locationOf(this).addItem(corpse);
			map.removeActor(this);
			return new DoNothingAction();
		} else if(hungerLevel < 10){ // At less than ten hunger it will seek food
			this.removeSkill(GameSkills.HEALTHY);
			System.out.println("Protoceratops at (" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ") is hungry");
			Action foodSeek = foodSeekBehaviour.getAction(this, map);
			if (foodSeek != null)
				return foodSeek;
		} else if(this.hasSkill(GameSkills.HEALTHY) == true){
			double randBreed = Math.random();
			if(randBreed < 0.1){ // 10% chance to breed and produce an egg
				Egg egg = new Egg("Protoceratops Egg", 'e', true);
				egg.addSkill(GameSkills.PROTOCERATOPS);
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
