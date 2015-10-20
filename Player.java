/**
 * CS 141: Introduction to Programming and Problem Solving
 * Professor: Edwin Rodriguez
 * 
 * Final Project 
 * 
 * <This program creates a text-based game in which the user is
 * a player in the whose main goal is to find a briefcase with intel,
 * placed in one of nine rooms, without getting killed by ninjas
 * that are walking around in the grid. The game grid consists of 
 * 9x9 squares that represents a building. The user is not able to 
 * see anything in the grid other than the rooms representation and 
 * the player, everything else is hidden as if the building was dark
 * represented by the '*' symbol. The user is given options to look, which
 * shows two squares in front of the player in any direction of their 
 * choice. Move method, that moves the player in any direction of the
 * users choice. Shoot, shoots a bullet in any direction of the users choice.
 * Also has an option to save the state of the game to be able to access at 
 * a later time.>
 * 
 * Team: ToBeDetermined
 * <Adrian Cuellar, Kevin Liu, Alberto Alcaraz, Cesar Medina, Oscar Hernandez>
 * 
 * This class extends GrdiEntity and represents a player and has methods for player dying, and returns the amount of
 * ammo and the number of lives.
 * and .    
 */

package edu.csupomona.cs.cs141.finalProject;

/**
 * In this class we have everything needed for the player. We create a @param Player object and make it a child class of 
 * {@link GridEntity}. The Player has @param lives, @param ammo, and @param alive boolean, a @param powerup 'null' 
 * initial value, and is represented by the character 'P' on
 * the Grid. 
 * @author Cesar_2
 *
 */

public class Player extends GridEntity {

	private boolean alive;
	private int ammo;
	private int lives;
	boolean pInvul;
	private int direction;

	public Player()
		// change first argument back to 8
	{	// change second argument back to 0
		super(8, 0, "P");
		ammo = 1;
		lives = 3;
		alive = true;
		pInvul = false;
		changeRepresentation("P");
	}
	
	/**
	 * This method deals with the player's living status, and is called when the {@link Ninja} has killed the player.
	 * If the @param invincibility powerUp is not equipped, and he dies, a life is subtracted from his overall 
	 * lives. If the number of lives = 0, the player is permanently dead.
	 */

	public void die() {
		if ( getInvul() == false) {
			lives--;
		if (lives == 0)
				alive = false;
		}
	}
	
	/**
	 * This method is used whenever the player wants to manually @param respawn back at the initial respawn location. 
	 * The only thing affected is the player losing a life, but he will be respawned back at the initial location 
	 * and keep any @param powerup he currently had. This will be ideally used if a player happens to find himself 
	 * completely surrounded by @param ninjas and they will not let his move at all. The player 
	 * can manually respawn to get himself out of a sticky situation, but at the cost of one life.
	 */

	public void respawnPenalty() {
		lives--;
		if (lives == 0)
			alive = false;
	}
	
	/**
	 * This method will @return ammo amount, which will either be 1 bullet or 0 bullets. 
	 * @return
	 */

	public int getAmmo() {
		return ammo;
	}

	
	/**
	 * These next 2 methods deal with player action of shooting. If the player @param shoot, then he will have no more bullets
	 * left. If the player gets the {@link AdditionalBullet} powerUp, then he will receive one bullet. 
	 * player moves or shoots in the grid. 
	 */
	public void shoot() {
		ammo = 0;
	}

	public void ammoUp() {
		ammo = 1;
	}
	
	
	/**
	 * This deals with the {@link Invincibility} powerUp. This method will check if the powerUp is equipped or not.
	 */

	public void changeInvul() {
		if(pInvul == false)
			pInvul = true;
		else
			pInvul = false;
	}

	/**
	 * This boolean will return whether the player invincibility is equipped or not. It does not determine 
	 * whether it is equipped or not, just returns the true or false. 
	 * @return pInvul
	 */

	public boolean getInvul() {
		return pInvul;
	}
	
	/**
	 * This method is a constructor for an object to @param changeDirection. 
	 * @param direction
	 */

	public void changeDirection(int direction) {
		this.direction = direction;
	}
	
	/**
	 * This will @return lives, which is the number of lives we have currently. 
	 */

	public int returnLives() {
		return lives;
	}
	
	/**
	 * This will @return alive, whether the player has lives or not.
	 */

	public boolean returnAlive() {
		return alive;
	}

	/**
	 * This will @return direction of movement when called.  
	 */
	
	public int getDirection() {
		return direction;
	}
}