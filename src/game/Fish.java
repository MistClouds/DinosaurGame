package game;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * A Fish.
 *
 */
public class Fish extends Actor {
	// Will need to change this to a collection if Protoceratops gets additional Behaviours.
	private Behaviour wanderBehaviour;
	private int hungerLevel = 20;

	/** 
	 * Constructor.
	 * All Fish are represented by a 'f' and have 20 max hunger points.
	 * 
	 * @param name - the name of this Fish
	 * @param displayChar - The character that this Fish is displayed by (Usually 'f')
	 */
	public Fish(String name, char displayChar) {
		super(name, displayChar, 1);
		this.addSkill(GameSkills.WATER_ONLY);
		wanderBehaviour = new WanderBehaviour();
	}
	
	public int getHungerLevel() {
		//Mutator for getting hungerLevel
		return hungerLevel;
	}

	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions list = super.getAllowableActions(otherActor, direction, map);
		list.add(new AttackAction(this));
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
		hungerLevel -= 1;
	
		if(hungerLevel == 0){
			map.removeActor(this);
			return new DoNothingAction();
		}  else {
			Action wander = wanderBehaviour.getAction(this, map);
			if (wander != null)
				return wander;
		}
		
		return new DoNothingAction();
	}

}
