package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * An Action that doesn't do anything.  
 * 
 * Use this to implement waiting or similar actions in game clients.
 */
public class QuitGameAction extends Action {
	
	protected String quitType;

	public QuitGameAction(String quitType) {
		this.quitType = quitType;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		map.removeActor(actor);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		if(quitType == "LOSE"){
			return actor + " loses";
		} else if (quitType == "WIN"){
			return actor + " wins";
		}
		return actor + " quits game";
	}
	
	@Override
	public String hotkey() {
		return "-";
	}
}
