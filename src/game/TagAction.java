package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.*;

public class TagAction extends Action {

	private Actor target;

	public TagAction(Actor target) {
		this.target = target;
	}
	/**
	 * @param actor - The actor is the player utilizing the tag action
	 * @param map - The game map
	 * 
	 * This attaches a tag to the target protoceratops and removes them from the map, counting them as "sold".
	 * Money is then added to the player.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		List<Item> playerInventory = actor.getInventory();
		for(int i = 0; i < playerInventory.size(); i++){
			if(playerInventory.get(i).toString() == "Tag" && target.hasSkill(GameSkills.TAGABLE) == true){
				if(target.hasSkill(GameSkills.PROTOCERATOPS)){
					((Player) actor).increaseMoney(100);
					map.removeActor(target);
					return actor + " tagged " + target;
				}
			}
		}
		return "No Tags in Inventory";

	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " tags " + target;
	}
}

