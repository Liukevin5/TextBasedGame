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
 * This class messages the user and sends user''s input to Game Engine. 
 */

package edu.csupomona.cs.cs141.finalProject;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class that displays all messages to the user and takes input to allow the
 * {@link GameEngine} to decide what to do in the game
 * 
 * @author Team TBD {Adrian, Kevin, Oscar, Alberto, Cesar}
 *
 */

public class UserInterface implements Serializable{
	/**
	 * Field that represents the users choice from the messages prompted
	 */
	int choice;
	
	/**
	 * Field that represents the direction they want the {@link Player} to move
	 */
	int direction;

	/**
	 * This method shows the user the mechanics of the game. It explains how
	 * certain objects work and some concepts of the game. Does not need a
	 * return statement because this is only displayed at the start of a new
	 * game
	 */
    public void displayGameMechanics(){
    	System.out.println("Game Mechanics");
    	System.out.println("1) When You pick up an Invincibility 'I' you can't be killed by a"
    			+ " ninja for 5 turns");
    	System.out.println("2) When on Invincibility you're not allowed to move into a ninja");
    	System.out.println("3) When you pick up an Ammo powerUp 'A' your gun will be set back to"
    			+ " 1 ammo");
    	System.out.println("4) The 'R' is a radar (it will reveal the room with the briefcase as 'B')");
    	System.out.println("5) Manual Respawn will cost 1 Life. It's main purpose is if you"
    			+ " somehow managed to pick up an Invincibility, but get surrounded by Ninjas" 
    			+ " and cannot move");
    	System.out.println("6) You can only enter rooms from above, and you can only exit from above");
    }
	
    /**
	 * Method that greets the user to the game and asks whether they would like
	 * to start a new game, load, or quit. Also checks if the user's input is a 
	 * correct value and of the type that it expects.
	 * 
	 * @return a numerical value that represents the user's {@link choice}
	 */
	public int startUpMenu() {
		while (true) {

			try {
				Scanner kb = new Scanner(System.in);
				System.out.println("Welcome to FIND THE BRIEFCASE");
				System.out
						.println("Would you like to: \n1.Start a new game \n2.Load game \n3.Quit");

				choice = kb.nextInt();
				
				kb.nextLine();
				while (choice != 1 && choice != 2 && choice != 3) {
					System.out
							.println("the number inputed was either too high or too low, please re-enter a choice");
					System.out
							.println("Would you like to: \n1.Start a new game \n2.Load game \n3.Quit ");
					choice = kb.nextInt();
					kb.nextLine();

				}
				// kb.nextLine();
				return choice;
			}

			catch (InputMismatchException e) {
				System.out.println("You must enter an int, please re-enter an int");
				// choice = kb.nextInt();
				// kb.nextLine();
				// continue;
			}
		}

	}

	/**
	 * This method displays all the actions that can be done in the game. A {@link Player} can
	 * look, move, and shoot. For user purposes the user can decide to show the {@link Grid} in 
	 * debug mode and reveal the whole {@link Grid} to make sure everything is working 
	 * properly and have a choice to remove the debug mode. The user can also save, or quit
	 * the program. Lastly, the user can manually respawn the player which will "kill" the player
	 * and respawn him at the default restart point in some weird case where the player is trapped
	 * by ninjas and the player has the invincibility {@link PowerUps}
	 * Method makes sure that the users input matches the choice for the prompt.
	 * 
	 * @return a numerical value that represents the user's {@link choice}
	 */
	
public int mainPhase1(){
	while (true) {
		try {
			Scanner kb = new Scanner(System.in);
			System.out.println("would you like to \n1. Look \n2. Move \n3. Shoot \n" 
					+ "4. Debug \n5. Remove Debug \n6. Save \n7. Quit \n8. Manual Respawn");
			choice = kb.nextInt();
			kb.nextLine(); // SHADOW IN SHOOT

			while (choice != 1 && choice != 2 && choice != 3 && choice != 4
					&& choice != 5 && choice != 6 && choice !=7 && choice !=8) {
				System.out
						.println("integer is too high or low, please re-enter a choice");
				System.out
						.println("would you like to \n1. Look \n2. Save  \n3. Quit \n4. Debug \n5 Remove Debug");
				choice = kb.nextInt();
				kb.nextLine();

			}
			return choice;
		} catch (InputMismatchException e) {
			System.out.println("You must enter an int, plese enter an int");
		}

	}	
}

/**
 * Method that displays the menu for which direction they would like to choose.
 * This method is displayed when the {@link Player} looks, moves, or shoots.
 * Method makes sure that the users input matches the choice for the prompt.
 * 
 * @return numerical value that represents the user's {@link choice}
 */

	public int directionMenu() {
		while (true) {
			try {
				Scanner kb = new Scanner(System.in);
				System.out
						.println("In what direction?: \n1.Up \n2.Down \n3.Left \n4.Right ");
				direction = kb.nextInt();

				kb.nextLine();
				while (direction != 1 && direction != 2 && direction != 3
						&& direction != 4) {
					System.out
							.println("the input was either too high or low, please re-enter a choice");
					System.out
							.println("In what direction?: \n1.Up \n2.Down \n3.Left \n4.Right ");
					direction = kb.nextInt();
					kb.nextLine();

				}
				return direction;
			} catch (InputMismatchException e) {
				System.out.println("please enter an int");
			}
		}
	}
	
	/**
	 * Method that displays the menu for which direction they would like to choose.
	 * This method is displayed when the {@link Player} looks, moves, or shoots.
	 * Method makes sure that the users input matches the choice for the prompt. 
	 * 
	 * @return a numerical value that represents the user's {@link choice}
	 */
	public int directionMenu(int x) {
		while (true) {
			try {
				Scanner kb = new Scanner(System.in);
				System.out
						.println("In what direction?: \n1.Up \n2.Down \n3.Left \n4.Right \n5.Manual Respawn");
				direction = kb.nextInt();

				kb.nextLine();
				while (direction != 1 && direction != 2 && direction != 3
						&& direction != 4 && direction!=5) {
					System.out
							.println("the input was either too high or low, please re-enter a choice");
					System.out
							.println("In what direction?: \n1.Up \n2.Down \n3.Left \n4.Right ");
					direction = kb.nextInt();
					kb.nextLine();

				}
				return direction;
			} catch (InputMismatchException e) {
				System.out.println("please enter an int");
			}
		}
	}
	
	/**
	 * Method that is displayed only after the {@link Player} has looked in a certain
	 * direction to make sure they can not look into any other direction in one turn.
	 * Method makes sure that the users input matches the choice for the prompt.
	 * 
	 * @return a numerical value that represents the user's {@link choice}
	 */
	public int mainPhase2() {
		while (true) {
			try {
				Scanner kb = new Scanner(System.in);
				System.out.println("\nWould you like to: \n1.Move \n2.Shoot \n3.Save game \n"
						+ "4.Debug Mode \n5 Exit Debug \n6 Quit \n7. Manual Respawn");
				choice = kb.nextInt();
				kb.nextLine();

				while (choice != 1 && choice != 2 && choice != 3 && choice != 4
						&& choice != 5 && choice !=6 && choice !=7)  {
					System.out
							.println("the input was either too high or low, please re-enter a choice");
					System.out.println("\nWould you like to: \n1.Move \n2.Shoot \n3.Save game \n"
							+ "4.Debug Mode \n5 Exit Debug \n6 Quit");
					choice = kb.nextInt();
					kb.nextLine();
				}

				return choice;
			} catch (InputMismatchException e) {
				System.out.println("please enter an int");
			}
		}
	}

	/**
	 * Method that is displayed after the {@link Player} has either died or 
	 * won the game.
	 * Method makes sure that the users input matches the choice for the prompt.
	 * 
	 * @return a numerical value that represents the user's {@link choice}
	 */
	public int continuation() {
		while (true) {
			try {

				Scanner kb = new Scanner(System.in);
				System.out.println("do you want to continue?");
				System.out.println("1: yes   2: no");
				choice = kb.nextInt();
				kb.nextLine();

				while (choice != 1 && choice != 2) {
					System.out.println("your input was either too big or too small, please re-enter a value");
					choice = kb.nextInt();
					kb.nextLine();
				}
				return choice;
			} catch (InputMismatchException e) {
				System.out
						.println("please re-enter an integer value corresponding to the choices");
			}
		}
	}

	/**
	 * Method that displays a message during the game that shows the status of their ammo
	 * and lives
	 * 
	 * @param ammo the {@link Player}'s status of ammo
	 * @param lives {@link Player}'s remaining lives
	 */
	public void displayLivesAndAmmo(int ammo, int lives) {
		System.out.println("ammo: " + ammo);
		System.out.println("lives: " + lives);
	}

	/**
	 * Method that prompts the user for a string input that will be the
	 * name of the file created to save the game state.
	 * 
	 * @return the string the user chose to name their save game file
	 */
	public String savePrefrences() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter a name for your save file. Include '.dat' after the name");
		String saveName = input.nextLine();
		
		return saveName;
	}
	
	/**
	 * Method that prompts the user for a string input that will load
	 * the game file that they want to play
	 * 
	 * @return the string the user is choosing to load
	 */
	public String loadPrefrences() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the name of the game state you wish to restore.");
		String loadName = input.nextLine();
		
		return loadName;
	}
}