package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * Universal Egg class
 * Can hatch into Velociraptor, Protoceratops, Plesiosaur, Pteranodon, TRex
 * Can be picked up/moved/dropped etc
 *
 */

public class Egg extends Item{
	
	private int age = 0;

	/**
	 * 
	 * @param name - Name of egg e.g. "Protoceratops Egg"
	 * @param displayChar - Display character of egg e.g. "e"
	 * @param portable - Whether you can pick up the egg and move it around
	 * 
	 * Initialization of the egg and its attributes
	 */
	public Egg(String name, char displayChar, boolean portable) {
		super(name, displayChar, portable);
		this.addSkill(GameSkills.SELLABLE);
	}
	
	public void addAction(Action action) {
		this.allowableActions.add(action);
	}
	/**
	 * This is how the egg matures, as it ticks it gains age and once the age is greater than 5
	 * it will hatch into a baby dinosaur of it's type (protoceratops or velociraptor)
	 */
	@Override
	public void tick(Location currentLocation) {
		if(this.hasSkill(GameSkills.PLESIOSAUR)){
			int adjacentWater = (int) currentLocation.getExits().stream().map(exit -> exit.getDestination().getGround())
					.filter(ground -> ground.hasSkill(GameSkills.WATER)).count();
			int adjacentReed = (int) currentLocation.getExits().stream().map(exit -> exit.getDestination().getGround())
					.filter(ground -> ground.hasSkill(GameSkills.REED)).count();
			if((adjacentWater + adjacentReed) > 0){
				age++;
			}
		} else {
			age++;
		}
		
		if(age > 5){
			if(this.hasSkill(GameSkills.PROTOCERATOPS) && currentLocation.containsAnActor() == false){
				Protoceratops newProtoceratops = new Protoceratops("Protoceratops", 'p');
				newProtoceratops.addSkill(GameSkills.HATCHLING);
				newProtoceratops.setHungerLevelHatchling();
				currentLocation.addActor(newProtoceratops);
				currentLocation.removeItem(this);
			} else if(this.hasSkill(GameSkills.VELOCIRAPTOR) && currentLocation.containsAnActor() == false){
				Velociraptor newVelociraptor = new Velociraptor("Velociraptor", 'v');
				newVelociraptor.addSkill(GameSkills.HATCHLING);
				//newVelociraptor.setHungerLevelHatchling();
				currentLocation.addActor(newVelociraptor);
				currentLocation.removeItem(this);
			} else if(this.hasSkill(GameSkills.PLESIOSAUR) && currentLocation.containsAnActor() == false){
				//TO DO: Cycle through exits to ensure they spawn on water
				Plesiosaur newPlesiosaur = new Plesiosaur("Plesiosaur", 'l', 100);
				newPlesiosaur.addSkill(GameSkills.HATCHLING);
				currentLocation.addActor(newPlesiosaur);
				currentLocation.removeItem(this);
			} else if(this.hasSkill(GameSkills.PTERANODON) && currentLocation.containsAnActor() == false){
				Pteranodon newPteranodon = new Pteranodon("Pterandon", 'w', 100);
				newPteranodon.addSkill(GameSkills.HATCHLING);
				currentLocation.addActor(newPteranodon);
				currentLocation.removeItem(this);
			} else if(this.hasSkill(GameSkills.TREX) && currentLocation.containsAnActor() == false){
				TRex newTRex = new TRex("TRex", 'r', 500);
				newTRex.addSkill(GameSkills.HATCHLING);
				newTRex.addSkill(GameSkills.GAME_END);
				currentLocation.addActor(newTRex);
				currentLocation.removeItem(this);
			}
		}
	}
}

