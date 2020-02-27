package edu.smith.cs.csc212.spooky;

public class GameTime {

	int hour;

	/**
	 * This is a getter method for the hour
	 * @return this returns the time 
	 */
	
	int getHour() {
		return hour;
	}
	
	/**
	 * This method increases the hour and prints the time to the console
	 * @return nothing but it prints a statement to the console 
	 */
	
	void increaseHour() {
		if (hour ==23 ) {
			hour = 0;
		} else {
			hour +=1;
		}
		System.out.println("The time is now: "+hour +":00");
	}	
}
