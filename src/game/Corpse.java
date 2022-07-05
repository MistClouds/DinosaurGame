package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class Corpse extends Item{

	/**
	 * 
	 * @param name - The name of the corpse (i.e. "velociraptor corpse" or "protoceratops corpse")
	 * @param displayChar - The character to display the corpse as (usually 'x')
	 * @param portable - Whether the corpse can be picked up
	 * Class for a corpse actor which is spawned when a dinosaur dies, through starvation or
	 * an attack from a Velociraptor.
	 * 
	 */
	public Corpse(String name, char displayChar, boolean portable) {
		super(name, displayChar, portable);
		this.addSkill(GameSkills.CORPSE);
	}
	
	public void addAction(Action action) {
		this.allowableActions.add(action);
	}
	
}

