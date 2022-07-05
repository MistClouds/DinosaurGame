package game;

import edu.monash.fit2099.demo.conwayslife.Status;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;

import java.lang.Math; 

/**
 * A class that represents water.
 */
public class Water extends Ground {

	public Water() {
		super('~');
		this.addSkill(GameSkills.WATER);
	}
	
	@Override
	public void tick(Location location) {
		super.tick(location);
		double randGrow = Math.random();
		
		//Water
		int adjacentGroundCount = (int) location.getExits().stream().map(exit -> exit.getDestination().getGround())
				.filter(ground -> ground.hasSkill(GameSkills.GROUND)).count();
		
		int adjacentReedCount = (int) location.getExits().stream().map(exit -> exit.getDestination().getGround())
				.filter(ground -> ground.hasSkill(GameSkills.REED)).count();
		
		if(adjacentGroundCount > 0 && randGrow < 0.1){
			location.setGround(new Reed());
		} else if(adjacentGroundCount == 0 && adjacentReedCount > 0 && randGrow < 0.005){
			location.setGround(new Reed());
		}
	}
	
	@Override
	public boolean canActorEnter(Actor actor) {
		if(actor.hasSkill(GameSkills.LAND_ONLY)){
			return false;
		}
		return true;
	}
}
