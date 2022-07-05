package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents shop location
 */
public class ShopGround extends Ground {
	public Shop mapShop;

	public ShopGround() {
		super('S');
		mapShop = new Shop(1000);
		//mapShop.addItem(new Food("Herbivore Food", 'C', true));
		//mapShop.addItem(new Food("Some Food", 'C', true));
	}
	
	public void startShop(Actor player) {
		mapShop.shopMenu(player);
	}
	
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction){
		return new Actions(new ShopAction(location));
	}
	
}
