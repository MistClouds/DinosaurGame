package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import java.lang.Math; 

/**
 * A class that represents a reed
 */
public class Reed extends Ground {

	public Reed() {
		super('|');
		this.addSkill(GameSkills.REED);
	}
	
	@Override
	public void tick(Location location) {
		super.tick(location);
		double randFish = Math.random();
		
		int x = (int) location.getExits().stream().map(exit -> exit.getDestination().getGround())
				.filter(ground -> ground.hasSkill(GameSkills.REED)).count();
		if(x > 6){
			location.setGround(new Water());
		}
		if(location.getDisplayChar() == '|' && randFish < 0.1){
			location.addActor(new Fish("Fish", 'f'));
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
