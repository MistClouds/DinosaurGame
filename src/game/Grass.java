package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import java.lang.Math; 

/**
 * A class that represents grass
 */
public class Grass extends Ground {

	public Grass() {
		super('g');
		this.addSkill(GameSkills.GROUND);
	}
	
	@Override
	public boolean canActorEnter(Actor actor) {
		if(actor.hasSkill(GameSkills.WATER_ONLY)){
			return false;
		}
		return true;
	}
	
}
