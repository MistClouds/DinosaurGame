package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Collector.
 */
public class Collector extends Actor {
	
	private Action onlyAction;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the Collector in the UI
	 * @param displayChar Character to represent the Collector in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Collector(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.onlyAction = new CollectorAction(this);
	}
	
	/**
	 * Populates an array of the players inventory.
	 * @return - Returns an array of inventory items that are sellable.
	 */
	
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions list = super.getAllowableActions(otherActor, direction, map);
		//System.out.println(list);
		list.add(onlyAction);
		return list;
	}
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		return new DoNothingAction();
	}
}
