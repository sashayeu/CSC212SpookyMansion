package edu.smith.cs.csc212.spooky;

import java.util.List;

/**
 * This is our main class for SpookyMansion.
 * It interacts with a GameWorld and handles user-input.
 * It can play any game, really.
 *
 * @author jfoley
 *
 */
public class InteractiveFiction {

	/**
	 * This method actually plays the game.
	 * @param input - a helper object to ask the user questions.
	 * @param game - the places and exits that make up the game we're playing.
	 * @return where - the place the player finished.
	 */
	static String runGame(TextInput input, GameWorld game) {
		// This is the current location of the player (initialize as start).
		Player player = new Player(game.getStart());
		boolean visitedBefore = false;
		GameTime time = new GameTime();
		
		System.out.println("Welcome to my game! Your goal is to collect all of the donuts and leave this haunted house.\n"
				+ "Type 'take' to pick up items and the number of the action you want to perform. Good luck!\n"
				+ "This game was inspired by Garfield's Scary Scavenger Hunt found at\n"
				+ " http://www.friv.com/z/flashx/scaryscavengerhunt/game.html?Other-x-x-www-xx");

		// Play the game until quitting.
		// This is too hard to express here, so we just use an infinite loop with breaks.
		while (true) {
			// Print the description of where you are.
			Place here = game.getPlace(player.getPlace());
			
			System.out.println();
			System.out.println("... --- ...");
			System.out.println(here.getDescription());
			
			//this is responsible for tracking where the player has been before 
			visitedBefore = player.visited.contains(player.getPlace());
			player.getVisited().add(player.getPlace());
			
			//prints if the player has already visited this place
			if (visitedBefore == true) {
				System.out.println("You've been here before!");
			}
				
			// Game over after print!
			if (here.isTerminalState()) {
				break;
			}

			// Show a user the ways out of this place.
			List<Exit> exits = here.getVisibleExits();

			for (int i=0; i<exits.size(); i++) {
				Exit e = exits.get(i);
				System.out.println(" "+i+". " + e.getDescription());
			}

			// Figure out what the user wants to do, for now, only "quit" is special.
			List<String> words = input.getUserWords("?");
			if (words.size() > 1) {
				System.out.println("Only give the system 1 word at a time!");
				continue;
			}

			// Get the word they typed as lowercase, and no spaces.
			// Do not uppercase action -- I have lowercased it.
			String action = words.get(0).toLowerCase().trim();

			if (action.equals("quit")|| action.equals("q") || action.equals("escape")) {
				if (input.confirm("Are you sure you want to quit?")) {
					// quit!
					break;
				} else {
					// go to the top of the game loop!
					continue;
				}
			}
			
			//help command explaining room number and how to quit
			if (action.equals("help")) {
				System.out.println("Type q, quit, or escape to quit. \nType the number of the choices below to proceed with the choice.");
				continue;
			}

			//this command searches for a secret exit in a place, making the exit visible
			//since the only secret exit is the final one, it first makes sure the player has gathered all the food necessary
			if (action.equals("search")) {
				if (player.items.contains("donut 1") && player.items.contains("donut 2")
						&&player.items.contains("donut 3") && player.items.contains("donut 4")
						&& player.items.contains("muffin")) {
					here.search();
				}
				continue;
			}
			
			//"stuff" prints out what items the player has
			if (action.equals("stuff")) {
				if (player.items.size() == 0) {
					System.out.println("You have nothing yet!");
				} else {
					System.out.println("Here are your items:");
					System.out.println(player.items);
				}
				continue;
				
			}
			
			//"rest" advances the time by 2 hours
			if (action.equals("rest")) {
				time.increaseHour();
				time.increaseHour();
				
			}
			
			//"take" collects the items in the room and adds them to the list 
			if (action.equals("take")) {
				if (here.getItem() != null) {
					player.items.add(here.getItem());
				}
				continue;
			}
			
			// From here on out, what they typed better be a number!
			Integer exitNum = null;
			try {
				exitNum = Integer.parseInt(action);
			} catch (NumberFormatException nfe) {
				System.out.println("That's not something I understand! Try a number!");
				continue;
			}

			if (exitNum < 0 || exitNum >= exits.size()) {
				System.out.println("I don't know what to do with that number!");
				continue;
			}
			

			// Move to the room they indicated.
			Exit destination = exits.get(exitNum);
			if (destination.canOpen(player)) {
				player.moveTo(destination.getTarget());
				time.increaseHour();
				
			} else {
				System.out.println("Sorry, this appears to be locked.");
			}
			
		}

		return player.getPlace();
	}

	/**
	 * This is where we play the game.
	 * @param args
	 */
	public static void main(String[] args) {
		// This is a text input source (provides getUserWords() and confirm()).
		TextInput input = TextInput.fromArgs(args);

		// This is the game we're playing.
		GameWorld game = new SpookyMansion();

		// Actually play the game.
		runGame(input, game);

		// You get here by typing "quit" or by reaching a Terminal Place.
		System.out.println("\n\n>>> GAME OVER <<<");
	}

}
