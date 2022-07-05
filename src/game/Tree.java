package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Tree extends Ground {
	private int age = 0;
	private int horiCheck, vertCheck;

	public Tree() {
		super('+');
		this.addSkill(GameSkills.GROUND);
	}
	/**
	 * Tree that grows according to the alogrithm depending on the squares around it
	 * @param location - the location of the current tree
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';

		for(horiCheck = -1; horiCheck <= 1; horiCheck++){
			if((location.x() + horiCheck) < 80 && (location.x() + horiCheck) >= 0){
				for(vertCheck = -1; vertCheck <= 1; vertCheck++){
					if((location.y() + vertCheck) < 25 && (location.y() + vertCheck) >= 0){
						if(location.map().at(location.x() + horiCheck, location.y() + vertCheck).getDisplayChar() != '+' &&
								location.map().at(location.x() + horiCheck, location.y() + vertCheck).getDisplayChar() != 't' &&
								location.map().at(location.x() + horiCheck, location.y() + vertCheck).getDisplayChar() != 'T' &&
								location.map().at(location.x() + horiCheck, location.y() + vertCheck).getDisplayChar() != '#' &&
								location.map().at(location.x() + horiCheck, location.y() + vertCheck).getDisplayChar() != '_' &&
								location.map().at(location.x() + horiCheck, location.y() + vertCheck).getDisplayChar() != '~' &&
								location.map().at(location.x() + horiCheck, location.y() + vertCheck).getDisplayChar() != '|'){
							double randTree = Math.random();
							if(randTree < 0.005){
								location.map().at(location.x() + horiCheck, location.y() + vertCheck).setGround(new Tree());
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean canActorEnter(Actor actor) {
		if(actor.hasSkill(GameSkills.WATER_ONLY)){
			return false;
		}
		return true;
	}
}
