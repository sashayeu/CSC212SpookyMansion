package edu.smith.cs.csc212.spooky;

public class secretExit extends Exit{
	
	private boolean isSecretAnswer = true;
	
	public secretExit(String target, String description) {
		super(target, description);
		

	}
	
	/**
	 * determines whether the exit is a secret
	 * @return boolean value of whether it is a secret or not
	 */
	
	@Override
	public boolean isSecret() {
		return isSecretAnswer;
	}
	
	/**
	 * once the player searches the room, the secret is set to false 
	 * @return the value false because the exit is no longer secret
	 */
	
	@Override
	public void search() {
		isSecretAnswer = false;
	}

}
