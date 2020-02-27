package edu.smith.cs.csc212.spooky;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents all of the game state needed to understand the player.
 * At the beginning of this project, it is just the ID or name of a place.
 * 
 * @author jfoley
 *
 */
public class Player {
	/**
	 * The ID of the Place object where the player is currently.
	 */
	private String place;
	
	/**
	 * A player is created at the start of a game with just an initial place.
	 * @param initialPlace - where do we start?
	 */
	public Player(String initialPlace) {
		this.place = initialPlace;
	}

	/**
	 * a set of the places the player has visited
	 */
	Set<String> visited = new HashSet<>();
	
	//https://www.geeksforgeeks.org/set-in-java/ was used to see HashSet code
	
	
	/**
	 * Get access to the place instance variable from outside this class. Adds place to set of places visited.
	 * @return the id of the current location.
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Call this method when the player moves to a new place. Prints if player has already been there.
	 * @param place - the place we are now located at.
	 */
	public void moveTo(String place) {
		this.place = place;
	}
	
	/**
	 * Accesses the places visited.
	 * @return a set of places the player has visited.
	 */
	public Set<String> getVisited() {
		return visited;
	}
	
	/**
	 * This is an array of the items a player acquired
	 */
	Set<String> items = new HashSet<>();
	
	/**
	 * Accesses the items collected.
	 * @return a set of items collected.
	 */
	public Set<String> getItems() {
		return items;
	}
	
}
