package edu.smith.cs.csc212.spooky;

import java.util.HashMap;
import java.util.Map;

/**
 * SpookyMansion, the game.
 * @author jfoley
 *
 */
public class SpookyMansion implements GameWorld {
	private Map<String, Place> places = new HashMap<>();

	/**
	 * Where should the player start?
	 */
	@Override
	public String getStart() {
		return "entranceHall";
	}

	/**
	 * This constructor builds our SpookyMansion game.
	 */
	public SpookyMansion() {
		
		Place entranceHall = insert (Place.create("entranceHall", "You are in the grand entrance of a haunted mansion. \n"
				+ "The front door is locked and you are hungry, you must collect the 5 donuts from around the house.", null));
		entranceHall.addExit(new Exit("upstairs","Walk up the staircase."));
		entranceHall.addExit(new Exit("leftSittingRoom","Walk through the left doorway."));
		entranceHall.addExit(new Exit("library","Walk straight towards the library."));
		entranceHall.addExit(new Exit("fireplaceRoom","Walk through the right doorway."));
		
		
	// EVERYTHING UPSTAIRS
		Place upstairs = insert (Place.create("upstairs", "You are at the top of the stairs.", null));
		upstairs.addExit(new Exit("entranceHall","Go back down the stairs."));
		upstairs.addExit(new Exit("upstairsLeftBedroom","There is an open doorway to your left."));
		upstairs.addExit(new Exit("upstairsRightBedroom","There is an open doorway to your right."));
		//locked exits
		upstairs.addExit(new lockedExit("blueDoor","There is a blue doorway ahead of you.","blue key"));
		upstairs.addExit(new lockedExit("attic","There are stairs leading up to the attic, but it is extremely dark.","flashlight"));
		
		Place upstairsLeftBedroom = insert (Place.create("upstairsLeftBedroom", "You enter a small bedroom. \n"
				+ "There is a bed and dresser. What's under the pillow?", "blue key"));
		upstairsLeftBedroom.addExit(new Exit("upstairs","Go back."));
		
		
		Place upstairsRightBedroom = insert (Place.create("upstairsRightBedroom", "You just entered a room with lots of furniture covered with white sheets.\n"
				+ "Is there anything under the sheets other than furniture?.", "donut 1"));
		upstairsRightBedroom.addExit(new Exit("upstairs","Go back."));
		
		Place blueDoor = insert (Place.create("blueDoor", "You walk into a large bedroom. \n"
				+ "Is there anything under this pillow?","flashlight"));
		blueDoor.addExit(new Exit("upstairs","Go back."));
		blueDoor.addExit(new Exit("dresserRoom","That dresser over there looks a little loose."));
	
		Place dresserRoom = insert (Place.create("dresserRoom", "You walk into the secret room behind the dresser. \n" 
		+"On the floor, you see a bag of donuts!", "donut 2"));
		dresserRoom.addExit(new Exit("blueDoor","Go back"));
//		
		Place attic = insert(Place.create("attic", "You walk up the stairs and it is dark and creepy. There are bats on the ceiling. What's that in the corner?", "donut 3"));
		attic.addExit(new Exit("upstairs","Go back down."));
		
		//EVERYTHING GROUND LEVEL
		Place leftSittingRoom = insert(Place.create("leftSittingRoom", "You walk into an old sitting room. What's that under the couch?", "green key"));
		leftSittingRoom.addExit(new Exit("entranceHall","Go back."));
//		
		Place library = insert(Place.create("library", "You walk into a library", null));
		library.addExit(new Exit("entranceHall","Go back."));
		library.addExit(new Exit("diningRoom","There's a dining room ahead."));
		library.addExit(new Exit("dungeon","There's a strange bookcase, perhaps you can push it aside?"));
		
		Place diningRoom = insert(Place.create("diningRoom", "This looks like it hasn't been cleaned in years. Is there anything under the platter?", null));
		diningRoom.addExit(new Exit("library","Go back to the library."));
		diningRoom.addExit(new Exit("kitchen","Go into the kitchen."));

		Place kitchen = insert(Place.create("kitchen", "Ew. Let's check the fridge.", "muffin"));
		kitchen.addExit(new Exit("diningRoom","Go back to the dining room."));
		
		Place fireplaceRoom = insert(Place.create("fireplaceRoom", "This looks cozy... the fire is bright", null));
		//locked exit
		fireplaceRoom.addExit(new lockedExit("greenDoor", "What's behind the green door?","green key"));
		//secret exit that only appears at the very end
		fireplaceRoom.addExit(new secretExit("outside", "The fire is out! Try crawling through the fireplace..."));
		fireplaceRoom.addExit(new Exit ("entranceHall", "Go back to the entrance hall."));
		
		Place outside = insert(Place.terminal("outside", "You made it outside!",null));
		
		Place greenDoor = insert(Place.create("greenDoor", "This is a music room, so many instruments! Could something be behind the piano?", "donut 4"));
		greenDoor.addExit(new Exit("fireplaceRoom","Go back to the fireplace room."));
		
		//EVERYTHING BELOW GROUND
		Place dungeon = insert(Place.create("dungeon", "You walk into a creepy dungeon, there's a man in the cell." +
		"Hint: his name is muffin man, and he loves muffins", null));
		dungeon.addExit(new Exit("library","Go back."));
		//locked exit, the man will only talk to you when you have a muffin
		dungeon.addExit(new lockedExit("muffinMan","Talk to the man when he isn't hungry.","muffin"));
		
		Place muffinMan = insert(Place.create("muffinMan", "The man tells you to search in the fireplace once you've collected what you came for.", null));
		muffinMan.addExit(new Exit("dungeon","Go back."));

		// Make sure your graph makes sense!
		checkAllExitsGoSomewhere();
	}

	/**
	 * This helper method saves us a lot of typing. We always want to map from p.id
	 * to p.
	 * 
	 * @param p - the place.
	 * @return the place you gave us, so that you can store it in a variable.
	 */
	private Place insert(Place p) {
		places.put(p.getId(), p);
		return p;
	}

	/**
	 * I like this method for checking to make sure that my graph makes sense!
	 */
	private void checkAllExitsGoSomewhere() {
		boolean missing = false;
		// For every place:
		for (Place p : places.values()) {
			// For every exit from that place:
			for (Exit x : p.getVisibleExits()) {
				// That exit goes to somewhere that exists!
				if (!places.containsKey(x.getTarget())) {
					// Don't leave immediately, but check everything all at once.
					missing = true;
					// Print every exit with a missing place:
					System.err.println("Found exit pointing at " + x.getTarget() + " which does not exist as a place.");
				}
			}
		}

		// Now that we've checked every exit for every place, crash if we printed any
		// errors.
		if (missing) {
			throw new RuntimeException("You have some exits to nowhere!");
		}
	}

	/**
	 * Get a Place object by name.
	 */
	public Place getPlace(String id) {
		return this.places.get(id);
	}
}
