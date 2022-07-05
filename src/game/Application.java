package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.demo.mars.MartianItem;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.MoveActorAction;
import edu.monash.fit2099.engine.World;

/**
 * The main class for the Dinosaur Park game.
 *
 */
public class Application {
	//public Shop mapShop;

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(
				new Dirt(), new Wall(), new Floor(), new Tree(), new Grass(), new Water(), new Reed());
		
		List<String> northMap = Arrays.asList(
		".....................................~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~...........",
		"........................................~~~~~~~~~~~~~~~~~~~~~~~~~~~~~...........",
		"...........................................~~~~~~~~~~~~~~~~~~___~~~~~~~~........",
		"...................................~~~~~~~~~~~~~~~~~~~~~~~~~~___~~~~~~~~~~~.....",
		"...............................................~~~~~~~~~~~~~~___~~~~~~~.........",
		"................~~~~..................................~~~~~~~~~~~~~~~~~.........",
		"............~~~~~~~~~~~~~..................................~~~~~~~~~~~~~~~~~....",
		"...........~~~~~~~....................+++.....................~~~~~~............",
		"............~~~~~~~~~~~~~..............++++.................~~~~~~~~~~..........",
		"..............~~~~~~~~.............+++++.......................~~~~~~...........",
		"..................~~~~~~~~...........++++++.......................~~~~~~~.......",
		".....................~~~~~...........+++........................................",
		"................................................................................",
		"............+++.................................................................",
		".............+++++..............................................................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"..........................................................................++....",
		"................................................................................");
		GameMap gameNorthMap = new GameMap(groundFactory, northMap );
		world.addGameMap(gameNorthMap);
		
		
		List<String> map = Arrays.asList(
		"................................................................................",
		"................................................................................",
		".....#######....................................................................",
		".....#_____#....................................................................",
		".....#_____#....................................................................",
		".....###.###....................................................................",
		"................................................................................",
		"......................................+++.......................................",
		".......................................++++.....................................",
		"...................................+++++........................................",
		".....................................++++++.....................................",
		"......................................+++.......................................",
		".....................................+++........................................",
		"................................................................................",
		"............+++.................................................................",
		".............+++++..............................................................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".......~~~~~~~~~~~~~~~...................................................++.....",
		"....~~~~~~~~~~~~~~......................................................++.++...",
		".....~~~~~~~~~~~~~~~~....................................................++++...",
		"..........~~~~~~~~~~~~~~..................................................++....",
		"..~~~~~~~~~~~~~~~~~~~~..........................................................");
		GameMap gameMap = new GameMap(groundFactory, map );
		world.addGameMap(gameMap);
		gameMap.at(8, 3).setGround(new ShopGround());
		
		Actor player = new Player("Player", '@', 100, 100);//100, 100
		world.addPlayer(player, gameMap.at(26, 12));
		//world.addPlayer(player, gameMap.at(9, 4));
		//world.addPlayer(player, gameMap.at(9, 0));
		//world.addPlayer(player, gameNorthMap.at(2, 20));
		//world.addPlayer(player, gameNorthMap.at(62, 2));

		//Food teleporter = new Food("teleportNorth", '^', false);
        //teleporter.addAction(new MoveActorAction(gameNorthMap.at(7, 2), "to NorthMap"));
        //gameMap.at(9, 0).addItem(teleporter);
        
        //Food teleporter2 = new Food("teleportBase", '*', false);
        //teleporter2.addAction(new MoveActorAction(gameMap.at(9, 0), "to BaseMap"));
        //gameNorthMap.at(7, 2).addItem(teleporter2);
        
        for(int i = 0; i < 80; i++){
        	Food teleporterNorth = new Food("teleportNorth", '^', false);
        	teleporterNorth.addAction(new MoveActorAction(gameNorthMap.at(i, 24), "to NorthMap"));
        	gameMap.at(i, 0).addItem(teleporterNorth);
        	
        	Food teleporterBase = new Food("teleportBase", '*', false);
            teleporterBase.addAction(new MoveActorAction(gameMap.at(i, 0), "to BaseMap"));
            gameNorthMap.at(i, 24).addItem(teleporterBase);
        }
        
		gameMap.at(32, 13).addActor(new Velociraptor("Velociraptor", 'V'));
		gameMap.at(27, 12).addActor(new Pteranodon("Pteranodon", 'W', 100));
		gameMap.at(30, 12).addActor(new Protoceratops("Protoceratops", 'P'));
		//gameMap.at(8, 5).addActor(new Protoceratops("Protoceratops", 'P'));
		gameMap.at(5, 21).addActor(new Plesiosaur("Plesiosaur", 'L', 100));
		gameMap.at(20, 20).addActor(new TRex("TRex", 'R', 500));
		//gameMap.at(8, 5).addActor(new Protoceratops("Protoceratops", 'P'));

		

		gameMap.at(32, 12).addActor(new Protoceratops("Protoceratops", 'P'));
		gameNorthMap.at(62, 3).addActor(new Collector("Collector", 'C', 100));
		
		
		SunMoon trial = new SunMoon("SunMoon", 'S', false);
		trial.addSkill(GameSkills.NIGHT);
		gameMap.at(0, 0).addItem(trial);
		
		gameNorthMap.at(0, 0).addItem(new SunMoon("SunMoon", 'S', false));
		

		player.addItemToInventory(new Food("CarnivoreFood", 'C', true));
		player.addItemToInventory(new Egg("Velociraptor Egg", 'C', true));
		player.addItemToInventory(new Food("Herbivore Food", 'H', true));
		player.addItemToInventory(new Tag("Tag", '|', false));

		player.addItemToInventory(new Egg("TRex Egg", 'C', true));
		player.addItemToInventory(new Egg("Protoceratops Egg", 'C', true));
		player.addItemToInventory(new Egg("Pteranodon Egg", 'C', true));
		player.addItemToInventory(new Egg("Plesiosaur Egg", 'C', true));
		
			
		world.run();
	}
}
