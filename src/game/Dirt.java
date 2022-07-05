package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;

import java.lang.Math; 

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {
	public Grass grassGrow = new Grass();

	public Dirt() {
		super('.');
		this.addSkill(GameSkills.GROUND);
	}
	
	@Override
	public void tick(Location location) {
		super.tick(location);
		double randGrow = Math.random();
		if(randGrow < 0.005){
			//displayChar = 'X';
			//location.setGround(grassGrow);
			location.setGround(new Grass());
		}
	}
	
	@Override
	public boolean canActorEnter(Actor actor) {
		if(actor.hasSkill(GameSkills.WATER_ONLY)){
			return false;
		}
		return true;
	}
	
	//@Override
	//public Actions allowableActions(Actor actor, Location location, String direction){
	//	if(location.y() == 0){
	//		return new Actions(new MoveActorAction(location.map().at(12, 12), "to SameMap"));
	//	}
	//	return new Actions(null);
	//}
}
