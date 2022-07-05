package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.*;

public class FeedAction extends Action {

	private Actor target;

	public FeedAction(Actor target) {
		this.target = target;
	}
    /**
     * The action of the player feeding the herbivore food to a target protoceratops. This can be done
     * with the food in their inventory or on the ground.
     * @param actor - The player actor
     * @param map - The map
     */
	@Override
	public String execute(Actor actor, GameMap map) {
		List<Item> playerInventory = actor.getInventory();
		for(int i = 0; i < playerInventory.size(); i++){
			if(playerInventory.get(i).toString() == "Herbivore Food" && target.hasSkill(GameSkills.PROTOCERATOPS) == true){
				actor.removeItemFromInventory(playerInventory.get(i));
				((Protoceratops) target).setCurrentToMaxHunger();
				return actor + " fed " + target;
			}
		}
		return "No Food in Inventory";
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " feeds " + target;
	}
}

