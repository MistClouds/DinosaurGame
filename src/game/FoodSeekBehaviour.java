package game;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.*;

public class FoodSeekBehaviour implements Behaviour{
	
	private Location target;
	private Random random = new Random();
	private char trial;
	private int horiCheck, vertCheck;
	private char[] herbivoreFood = {'t','T','H','+','g'};
	private char[] carnivoreFood = {'C','x','p','P'};
	private char[] plesiosaurFood = {'M', 'f', 'x'};
	private char[] pteranodonFood = {'f', 'x', 'M', 'C'};
	private char[] tRexFood = {'P', 'p', 'C'};


	/**
	 * Constructor.
	 * 
	 * @param actor - actor seeking food
	 * @param map - map of actor
	 */
	//Location location
	public FoodSeekBehaviour(Actor actor, GameMap map) {
		this.target = this.closestFoodSource(actor, map);		
	}
	/**
	 * 
	 * @param actor - The actor of the dinosaur that is calling the closestFoodSource method
	 * @param map - The current game map (has all locations of grass and foods and dinosaurs etc)
	 * @return - Returns the coordinates of the closest food source of that particular dinousaur
	 * 
	 * This returns based on whether the dinosaur is a carnivore or a herbivore.
	 */
	public Location closestFoodSource(Actor actor, GameMap map){
		Location here = map.locationOf(actor);
		while(true){
			//Nested loop to determine closest food source
			nestedloop:
			for(int i = 0; i < 80; i++){
				for(int j = -(i); j <= i; j++){
					if((map.locationOf(actor).x() + j < 80 && map.locationOf(actor).x() + j > 0) &&
							map.locationOf(actor).y() - i < 24 && map.locationOf(actor).y() - i > 0){
						boolean result = charCheck(actor, map.at(map.locationOf(actor).x() + j, map.locationOf(actor).y() - i).getDisplayChar());
						if(result == true){
							target = map.at(map.locationOf(actor).x() + j, map.locationOf(actor).y() - i);
							break nestedloop;
						}
					}
					if((map.locationOf(actor).x() + j < 80 && map.locationOf(actor).x() + j > 0) &&
							(map.locationOf(actor).y() + i < 25 && map.locationOf(actor).y() + i > 0)){
						boolean result = charCheck(actor, map.at(map.locationOf(actor).x() + j, map.locationOf(actor).y() + i).getDisplayChar());
						if(result == true){
							target = map.at(map.locationOf(actor).x() + j, map.locationOf(actor).y() + i);
							break nestedloop;
						}
					}
				}
				for(int h = -i - 1; h <= i - 1; h++){
					if((map.locationOf(actor).x() + i < 80 && map.locationOf(actor).x() + i > 0)&&
							map.locationOf(actor).y() + h < 24 && map.locationOf(actor).y() + h > 0){
						boolean result = charCheck(actor, map.at(map.locationOf(actor).x() +  i, map.locationOf(actor).y() + h).getDisplayChar());
						if(result == true){
							target = map.at(map.locationOf(actor).x() + i, map.locationOf(actor).y() + h);
							break nestedloop;
						}
					}
					if((map.locationOf(actor).x() - i < 80 && map.locationOf(actor).x() - i > 0)&&
							map.locationOf(actor).y() + h < 24 && map.locationOf(actor).y() + h > 0){
						boolean result = charCheck(actor, map.at(map.locationOf(actor).x() - i, map.locationOf(actor).y() + h).getDisplayChar());
						if(result == true){
							target = map.at(map.locationOf(actor).x() - i, map.locationOf(actor).y() + h);
							break nestedloop;
						}
					}
				}
			}
			break;
		}
		return (target);
	}
	
	/**
	 * 
	 * @param actor - The dinosaur actor 
	 * @param displayChar - The character of the map element
	 * @return - Whether the dinousaur can eat the character based on its dietary preferences
	 */
	public boolean charCheck(Actor actor,char displayChar){
		if(actor.hasSkill(GameSkills.HERBIVORE) == true){
			for(char check : herbivoreFood){
				if(check == displayChar){
					return true;
				}
			}
		} else if(actor.hasSkill(GameSkills.CARNIVORE) == true){
			for(char check : carnivoreFood){
				if(check == displayChar){
					return true;
				}
			}
		} else if(actor.hasSkill(GameSkills.PTERANODON) == true){
			for(char check : pteranodonFood){
				if(check == displayChar){
					return true;
				}
			}
		} else if(actor.hasSkill(GameSkills.PLESIOSAUR) == true){
			for(char check : plesiosaurFood){
				if(check == displayChar){
					return true;
				}
			}
		} else if(actor.hasSkill(GameSkills.TREX) == true){
			for(char check : tRexFood){
				if(check == displayChar){
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}
	/**
	 * @param actor - The dinosaur actor looking to make an action
	 * @param map - The map of the current game containing all the locations
	 * getAction checks for the  actions that the dinosaur can do such as consuming a food that is on it's space or attackinga  nearby dinosaur.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		Location here = map.locationOf(actor);
		Location there = target;
		int currentDistance = distance(here, there);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = distance(destination, there);
				if (newDistance == 0){
					
					
					boolean eatCheck = charCheck(actor,map.at(there.x(),there.y()).getDisplayChar());
					if(eatCheck == true && actor.hasSkill(GameSkills.HERBIVORE)){
						if(map.at(there.x(),there.y()).getDisplayChar() == '+' || 
								map.at(there.x(),there.y()).getDisplayChar() == 't' ||
								map.at(there.x(),there.y()).getDisplayChar() == 'T'){
							((Protoceratops) actor).changeHungerLevel(10);
						} 
						if(map.at(there.x(),there.y()).getDisplayChar() == 'g'){
							((Protoceratops) actor).changeHungerLevel(5);
						}
						if(map.at(there.x(),there.y()).getDisplayChar() == 'H'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Protoceratops) actor).setCurrentToMaxHunger();
						}
						map.at(there.x(),there.y()).setGround(new Dirt());
					}
					
					if(eatCheck == true && actor.hasSkill(GameSkills.CARNIVORE)){
						if(map.at(there.x(),there.y()).getDisplayChar() == 'C'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Velociraptor) actor).setCurrentToMaxHunger();
						}
						if(map.at(there.x(),there.y()).getDisplayChar() == 'x'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Velociraptor) actor).setCurrentToMaxHunger();
						}
					}
					if(eatCheck == true && actor.hasSkill(GameSkills.PLESIOSAUR)){
						if(map.at(there.x(),there.y()).getDisplayChar() == 'M'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Plesiosaur) actor).setCurrentToMaxHunger();
						}
						if(map.at(there.x(),there.y()).getDisplayChar() == 'x'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Plesiosaur) actor).setCurrentToMaxHunger();
						}
					}
					if(eatCheck == true && actor.hasSkill(GameSkills.PTERANODON)){
						if(map.at(there.x(),there.y()).getDisplayChar() == 'M'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Pteranodon) actor).setCurrentToMaxHunger();
						}
						if(map.at(there.x(),there.y()).getDisplayChar() == 'x'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Pteranodon) actor).setCurrentToMaxHunger();
						}
						if(map.at(there.x(),there.y()).getDisplayChar() == 'C'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Pteranodon) actor).setCurrentToMaxHunger();
						}
					}
					if(eatCheck == true && actor.hasSkill(GameSkills.TREX)){
						if(map.at(there.x(),there.y()).getDisplayChar() == 'C'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Pteranodon) actor).setCurrentToMaxHunger();
						}
					}
				}
				
				//private char[] tRexFood = {'P', 'p', 'C'};

				
				int newDistance1 = distance(destination, there);
				if (newDistance1 == 1){
					boolean eatCheck1 = charCheck(actor,map.at(there.x(),there.y()).getDisplayChar());
					if(eatCheck1 == true && actor.hasSkill(GameSkills.CARNIVORE)){
						/*if(map.at(there.x(),there.y()).getDisplayChar() == 'C'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Velociraptor) actor).setCurrentToMaxHunger();
						}
						if(map.at(there.x(),there.y()).getDisplayChar() == 'x'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Velociraptor) actor).setCurrentToMaxHunger();
						}*/
						if(map.at(there.x(),there.y()).getDisplayChar() == 'P'){
							Corpse corpse = new Corpse("Protoceratops Corpse", 'x', true);
							map.removeActor((map.at(there.x(),there.y()).getActor()));
							map.at(there.x(),there.y()).addItem(corpse);
						}
						if(map.at(there.x(),there.y()).getDisplayChar() == 'p'){
							Corpse corpse = new Corpse("Protoceratops Corpse", 'x', true);
							map.removeActor((map.at(there.x(),there.y()).getActor()));
							map.at(there.x(),there.y()).addItem(corpse);
						}
					}
					if(eatCheck1 == true && actor.hasSkill(GameSkills.PLESIOSAUR)){
						if(map.at(there.x(),there.y()).getDisplayChar() == 'f'){
							map.removeActor((map.at(there.x(),there.y()).getActor()));
						}
						if(map.at(there.x(),there.y()).getDisplayChar() == 'x'){
							map.at(there.x(),there.y()).removeItem(map.at(there.x(),there.y()).getItems().get(0));
							((Plesiosaur) actor).setCurrentToMaxHunger();
						}
					}
					if(eatCheck1 == true && actor.hasSkill(GameSkills.PTERANODON)){
						if(map.at(there.x(),there.y()).getDisplayChar() == 'f'){
							map.removeActor((map.at(there.x(),there.y()).getActor()));
							((Pteranodon) actor).changeHungerLevel(10);
						}
					}
					if(eatCheck1 == true && actor.hasSkill(GameSkills.TREX)){
						if(map.at(there.x(),there.y()).getDisplayChar() == 'P'){
							Corpse corpse = new Corpse("Protoceratops Corpse", 'x', true);
							map.removeActor((map.at(there.x(),there.y()).getActor()));
							map.at(there.x(),there.y()).addItem(corpse);
							((TRex) actor).changeHungerLevel(15);
						}
						if(map.at(there.x(),there.y()).getDisplayChar() == 'p'){
							Corpse corpse = new Corpse("Protoceratops Corpse", 'x', true);
							map.removeActor((map.at(there.x(),there.y()).getActor()));
							map.at(there.x(),there.y()).addItem(corpse);
							((TRex) actor).changeHungerLevel(15);
						}
					}
				if (newDistance1 < currentDistance) {
					return new MoveActorAction(destination, exit.getName());
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Compute the Manhattan distance between two locations.
	 * 
	 * @param a the first location
	 * @param b the first location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	private int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}

}