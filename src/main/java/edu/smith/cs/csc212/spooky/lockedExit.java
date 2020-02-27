package edu.smith.cs.csc212.spooky;

public class lockedExit extends Exit{
	
	String key;
	
	public lockedExit(String target, String description, String key) {
		super(target, description);
		this.key = key;
		
	}
	
	/**
	 * This determines whether the locked exit can be opened by checking whether the player has acquired the key
	 * @param the player needs to be passed in to access the items 
	 * @return whether the exit can be opened
	 */
	
	@Override
	public boolean canOpen(Player player) {
		String key = this.key;
		if (player.items.contains(key)){
			return true;
		} else{
			return false;
		}
	}
	
}
