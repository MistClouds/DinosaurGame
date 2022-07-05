package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Actor {

	private Menu menu = new Menu();
	private int playerMoney;
	private Action loseGameAction;
	private int turnCount = 0;
	private int armorValue;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 * @param money       Starting value of player money
	 */
	public Player(String name, char displayChar, int hitPoints, int money) {
		super(name, displayChar, hitPoints);
		this.playerMoney = money;
		this.armorValue = 0;
		this.addSkill(GameSkills.LAND_ONLY);
	}
	
	public int getPlayerMoney() {
		return playerMoney;
	}
	
	public void increaseMoney(int Value){
		playerMoney += Value;
	} 
	
	public void decreaseMoney(int Value){
		playerMoney -= Value;
	}
	/**
	 * Populates an array of the players inventory.
	 * @return - Returns an array of inventory items that are sellable.
	 */
	public ArrayList<Integer> getSellableInventoryIndex(){
		ArrayList<Integer> indexArray = new ArrayList<Integer>();
		for(int i = 0; i < super.inventory.size(); i++){
			if(super.inventory.get(i).hasSkill(GameSkills.SELLABLE)){
				indexArray.add(i);
			}
		}
		return indexArray;
	}
	
	public void removeItemByIndex(int index){
		super.inventory.remove(index);
	}
	
	public ArrayList<Item> getSellableInventory(){
		ArrayList<Item> itemSellArray = new ArrayList<Item>();
		for(int i = 0; i < super.inventory.size(); i++){
			if(super.inventory.get(i).hasSkill(GameSkills.SELLABLE)){
				itemSellArray.add(super.inventory.get(i));
			}
		}
		return itemSellArray;
	}
	
	public int getArmorValue(){
		int currentArmor = 0;
		for(int i = 0; i < super.inventory.size(); i++){
			if(super.inventory.get(i).hasSkill(GameSkills.ARMORED)){
				int newArmorValue = ((Armor) inventory.get(i)).getArmorValue();
					if(newArmorValue > currentArmor){
						currentArmor = newArmorValue;
					}
			}
		}
		return currentArmor;
	}
	
	/*@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions list = super.getAllowableActions(otherActor, direction, map);
		System.out.println(list);
		list.add(new QuitGameAction());
		return list;
	}*/
	
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		turnCount++;
		System.out.println("Player Health: " + this.hitPoints + "/" + this.maxHitPoints + " Money: " + this.playerMoney);
		if((turnCount) % 8 < 5){
			map.at(0, 0).getItems().get(0).removeSkill(GameSkills.NIGHT);
			System.out.println("Time of Day: Day");
		} else {
			map.at(0, 0).getItems().get(0).addSkill(GameSkills.NIGHT);
			System.out.println("Time of Day: Night");
		}
		
		if(map.at(0, 0).getItems().get(0).hasSkill(GameSkills.GAME_END) == true){
			return new QuitGameAction("WIN");
		}
		loseGameAction = new QuitGameAction("LOSE");
		if(this.hitPoints <= 0){
			return loseGameAction;
		}
		actions.add(new QuitGameAction("QUIT"));
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}
}
