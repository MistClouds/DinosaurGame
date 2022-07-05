package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import game.Player;

/**
 * The shop flow that activates when you reach the top left corner of the map.
 *
 */
public class Shop {
	private int shopMoney;
	//private ArrayList<Item> shopItems = new ArrayList<Item>();
	private String[] sellableItems = {"Protoceratops Corpse", "Protoceratops Egg", "Velociraptor Corpse", "Velociraptor Egg", "Plesiosaur Corpse", "Plesiosaur Egg", "Pteranodon Corpse", "Pteranodon Egg", "TRex Corpse", "TRex Egg"};
	private int[] sellableItemPrice = {15, 10, 100, 1000, 100};
	private String[] purchaseItems = {"Herbivore Food", "Carnivore Food", "Marine Animal Food", "Protoceratops Egg", "Velociraptor Egg", "Plesiosaur Egg", "Pteranodon Egg" ,"Tyrannosaurus Rex Egg", "Tag"};
	private char[] purchaseItemChar = {'H','C','M','e','e', 'e', 'e', 'e','|'};
	private int[] purchaseItemPrice = {20, 50, 50, 100, 300, 500, 500, 5000, 0};
	private int playerChoice;
	
	public Shop(int startBalance) {
		shopMoney = startBalance;
	}
	
	public void shopMenu(Actor player){
		System.out.println("Player Balance: " + ((Player) player).getPlayerMoney());
		System.out.println("Welcome to the Shop \n1. Buying \n2. Selling \n0. Leave Shop");
		try {
			String input = readString("Enter your choice: ");
			if(input == "quit"){
				System.out.println("Player has stopped shopping");
				return;
			}
			playerChoice = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.out.println("Invalid Choice, Must be an Integer");
			return;
		}
		if(playerChoice == 1){
			purchaseMenu(player);
		} else if(playerChoice == 2){
			sellMenu(player);
		} else if(playerChoice == 0){
			System.out.println("Player has stopped shopping");
			return;		
		}
		
	}
	/**
	 * The shop flow where you can select and purchase the items (the menu)
	 * @param player - The actor of the player character
	 */
	public void purchaseMenu(Actor player){
		String purchaseString = "Buying \n";
		for(int i = 0; i < purchaseItems.length; i++){
			purchaseString += (i+1) + ". " + purchaseItems[i] + ", Cost: " + purchaseItemPrice[i] + "\n";
		}
		System.out.println(purchaseString);
		
		try {
			playerChoice = Integer.parseInt(readString("Enter your choice: "));
		} catch (NumberFormatException e) {
			System.out.println("Invalid Choice, Must be an Integer");
			return;
		}
		
		if(playerChoice > purchaseItems.length && playerChoice < 1){
			System.out.println("Invalid Choice, Must be an option in shop");
			return;
		}
		if(((Player) player).getPlayerMoney() < purchaseItemPrice[playerChoice-1]){
			System.out.println("Invalid Choice, Not Enough Money");
			return;
		};

		if(playerChoice == 1){
			player.addItemToInventory(new Food(purchaseItems[playerChoice-1], purchaseItemChar[playerChoice-1], true));
		} else if(playerChoice == 2){
			player.addItemToInventory(new Food(purchaseItems[playerChoice-1], purchaseItemChar[playerChoice-1], true));
		} else if(playerChoice == 3){
			player.addItemToInventory(new Food(purchaseItems[playerChoice-1], purchaseItemChar[playerChoice-1], true));
		} else if(playerChoice == 4){
			player.addItemToInventory(new Egg(purchaseItems[playerChoice-1], purchaseItemChar[playerChoice-1], true));
		} else if(playerChoice == 5){
			player.addItemToInventory(new Egg(purchaseItems[playerChoice-1], purchaseItemChar[playerChoice-1], true));
		} else if(playerChoice == 6){
			player.addItemToInventory(new Egg(purchaseItems[playerChoice-1], purchaseItemChar[playerChoice-1], true));
		} else if(playerChoice == 7){
			player.addItemToInventory(new Egg(purchaseItems[playerChoice-1], purchaseItemChar[playerChoice-1], true));
		} else if(playerChoice == 8){
			player.addItemToInventory(new Egg(purchaseItems[playerChoice-1], purchaseItemChar[playerChoice-1], true));
		} else if(playerChoice == 9){
			player.addItemToInventory(new Tag(purchaseItems[playerChoice-1], purchaseItemChar[playerChoice-1], false));
		}
		
			((Player) player).decreaseMoney(purchaseItemPrice[playerChoice-1]);
		
		//int test = Integer.parseInt(playerChoice);
	}
	/**
	 * Selling menu to sell items from your inventory
	 * @param player - The actor of the player character
	 */
	public void sellMenu(Actor player){
		ArrayList<Item> sellItems = ((Player) player).getSellableInventory();
		ArrayList<Integer> sellItemIndex = ((Player) player).getSellableInventoryIndex();
		ArrayList<Integer> sellItemPrice = new ArrayList<Integer>();
		String sellString = "Selling \n";
		for(int i = 0; i < sellItems.size(); i++){
			sellString += (i+1) + ". " + sellItems.get(i).toString() + ", Sell Price ";
			for(int j = 0; j < sellableItems.length; j++){
				if(sellItems.get(i).toString() == sellableItems[j]){
					sellString += sellableItemPrice[j];
					sellItemPrice.add(sellableItemPrice[j]);
				}
			}
			sellString += "\n";
		}
		System.out.println(sellString);
		
		try {
			String input = readString("Enter your choice: ");
			playerChoice = Integer.parseInt(input);
			((Player) player).removeItemByIndex(sellItemIndex.get(playerChoice-1));
			((Player) player).increaseMoney(sellableItemPrice[playerChoice-1]);
			
		} catch (NumberFormatException e) {
			System.out.println("Invalid Choice, Must be an Integer");
			return;
		}
	}
	
	/*public void addItem(Item newItem) {
		//Add an item to the store
		shopItems.add(newItem);
	}
	
	public void removeItem(Item newItem) {
		//Remove an item from the store
		shopItems.add(newItem);
	}
	
	public void getItems() {
		String itemString = "";
		for (int i = 0; i < shopItems.size(); i++) {
			itemString += ((Item) shopItems.get(i)).toString();
			itemString += "\n";
		}
		System.out.println(itemString);
	}
	*/
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
