package game;

import java.util.Random;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.ShopGround;

import java.util.*;

public class ShopAction extends Action {
	
	private Location shopLocation;
	private Random rand = new Random();
	
	public ShopAction(Location shopLocation) {
		this.shopLocation = shopLocation;
	}
	/**
	 * The shop action that the player will execute should they stand in the shop and choose to open it
	 * @param actor - The player actor 
	 * @param map - The game map that contains all the locations of actors and map elements
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		ShopGround shopGround = new ShopGround();
		shopGround.startShop(actor);
		return null;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " uses shop";
	}
	
}
