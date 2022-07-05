package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class SunMoon extends Item{
	/**
	 * 
	 * @param name - The name of the entity 
	 * @param displayChar - Either 'N' for night or 'S' for Sun
	 * @param portable - Whether the item is portable or not (Yes in the case of tag)
	 */
	public SunMoon(String name, char displayChar, boolean portable) {
		super(name, displayChar, portable);
	}
	
	public void addAction(Action action) {
		this.allowableActions.add(action);
	}
	
	@Override
	public void tick(Location currentLocation) {
		if(this.hasSkill(GameSkills.NIGHT) == true){
			this.displayChar = 'N';
		} else {
			this.displayChar = 'S';
		}
	}
}

