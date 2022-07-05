package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * The Armor class
 * Can be obtained by the Player if they hand in one of each dinosaur egg to the Collector
 * Normal armor varieties can be purchased from the Shop.
 *
 */

public class Armor extends Item{
	
	private int armorValue;

	/**
	 * 
	 * @param name - Name of armor 
	 * @param displayChar - Display character of armor e.g. "e"
	 * @param portable - Whether you can pick up the armor and move it around
	 * @param armorValue - The damage that can be blocked by this armor
	 * 
	 * Initialization of the armor and its attributes
	 */
	public Armor(String name, char displayChar, boolean portable, int armorValue) {
		super(name, displayChar, portable);
		this.armorValue = armorValue;
	}
	
	public int getArmorValue() {
		//Mutator for setting customer name
		return armorValue;
	}
	
}

