package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CollectorAction extends Action {
	
	private Actor target;
	private String requiredEggs;
	private int requiredCheck;
	
	private String[] tradableItems = {"Protoceratops Egg", "Velociraptor Egg", "Plesiosaur Egg", "Pteranodon Egg", "TRex Egg"};
	private int[] tradedItems= {0, 0, 0, 0, 0};

	
	public CollectorAction(Actor target) {
		this.target = target;
	}
	/**
	 * The shop action that the player will execute should they stand in the shop and choose to open it
	 * @param actor - The player actor 
	 */
	
	public void collectorMenu(Actor actor){
		System.out.println("I am the collector. If I am provided an egg for every creature, I will grant you special armor.");
		
		//while loop begins here
		//prints out what is required
		requiredEggs = "";
		requiredCheck = 0;
		for(int i = 0; i < tradableItems.length; i++){
			if(tradedItems[i] == 0){
				requiredCheck++;
				requiredEggs += tradableItems[i] + ", ";
			}
		}
		if(requiredCheck == 0){
			System.out.println("You have no more business here. \n");
			return;
		}
		System.out.println("I still require the following: ");
		System.out.println(requiredEggs);
		
		//provide options
		ArrayList<Item> playerItems = ((Player) actor).getSellableInventory();
		ArrayList<Integer> playerItemIndex = ((Player) actor).getSellableInventoryIndex();

		for(int i = 0; i < playerItems.size(); i++){
			String tradeString = (i + 1) + ". Trade the collector the ";
			for(int j = 0; j < tradableItems.length; j++){
				if(playerItems.get(i).toString() == tradableItems[j]){
					tradeString += playerItems.get(i).toString();
				}
			}
			tradeString += "\n";
			System.out.println(tradeString);
		}
		try {
			String input = readString("Enter your choice: ");
			int playerChoice = Integer.parseInt(input);
			for(int check = 0; check < tradableItems.length; check++){
				if(playerItems.get(playerChoice-1).toString() == tradableItems[check]){
					tradedItems[check]++;
				}
			}
			((Player) actor).removeItemByIndex(playerItemIndex.get(playerChoice-1));			
		} catch (NumberFormatException e) {
			System.out.println("Invalid Choice, Must be a valid option");
			return;
		}
		requiredCheck = 0;
		for(int i = 0; i < tradableItems.length; i++){
			if(tradedItems[i] == 0){
				requiredCheck++;
			}
		}
		if(requiredCheck == 0){
			System.out.println("Thank you. Have this armor");
			Armor magicArmor = new Armor("Magic Armor", 'A', true, 100);
			actor.addItemToInventory(magicArmor);
		}
		return;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		collectorMenu(actor);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " trades with the collector";
	}
	
	private String readString(String prompt) {
		System.out.print(prompt);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)
				);
		String s = null;
		try {
			s = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
}
