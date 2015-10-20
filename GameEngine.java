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
 * This class is used to translate the user input from UserInterface to this class and works from this class to the Grid.
 *   
 */

package edu.csupomona.cs.cs141.finalProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class represents the entity that will run the logic of the game
 * 
 * @author Team TBD {Adrian, Kevin, Oscar, Alberto, Cesar}
 *
 */

public class GameEngine {
	/**
	 * Field that represents a {@link #Grid} for the game
	 */
	Grid gameGrid;
	/**
	 * Field that will represent the {@link #Player}'s lives
	 */
	private int userLives;
	/**
	 * Field that represents the {@link #Player}'s ammo
	 */
	private int ammo;
	/**
	 * Field that represents the {@link #Player}'s allocate the player to the
	 * proper direction in the grid
	 */
	private int userDirection;
	/**
	 * Field that decides whether the hidden {@link #Grid} will be printed out 
	 * or the one that reveals all the {@link #PowerUps} and {@link Ninja}s in
	 * the grid
	 */
	private boolean debug;
	/**
	 * Field that creates an instance of the {@link #SavedGames}s class to store
	 * a list of strings that the user inputs as the names of the saved games
	 * they want to store
	 */
	SavedGame saveList = new SavedGame();
	/**
	 * Creating an instance of the {@link #UserInterface} to display certain 
	 * messages that correspond with action of the game
	 */
	UserInterface ui = new UserInterface();
	/**
	 * Constructor method for the {@link GameEngine} that initializes a {@link Grid}
	 */
	
	public GameEngine() {
		gameGrid = new Grid();
		gameGrid.initialize();
		debug = false;// change to false later
	}
	/**
	 * Method that runs the majority of the logic for the game. It displays
	 * all the menus at their appropriate times, displays the grid, displays
	 * saved games to load if user chooses.
	 */

	public void turn() {

		int userChoice1;

		userChoice1 = ui.startUpMenu();
		ui.displayGameMechanics();
		
		File file = new File("defaultSaves.dat");
		if(file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("No games saved");
			}
		} else if(file.exists() == true && file.length() == 0 && userChoice1 != 1) {
			System.out.println("\nNo games saved!\n");
		//	ui.startUpMenu();
			turn();
		} else {
			loadGameList();
		}

		if (userChoice1 == 1) {
			while (!(gameGrid.checkWinCondition())) {
				if (gameGrid.returnAlive() == false) {
					System.out.println("you have died");

					if (ui.continuation() == 1) {
						gameGrid.initialize();
						turn();
					} else {
						System.exit(0);
					}

				}

				ui.displayLivesAndAmmo(gameGrid.returnAmmo(),
						gameGrid.returnLives());
				printGrid(debug);
				// making change here
				case2();
			}
			// try catch here
			if (ui.continuation() == 1) {
				gameGrid.initialize();
				turn();
			} else {
				System.exit(0);
			}

		} else if (userChoice1 == 2) {
			if(file.length() == 0) {
				ui.startUpMenu();
			}else if(file.length() != 0) {
				if(file.length() == 0) {
				    ui.startUpMenu();
				   } else {
				   loadGameList();
				   loadGame();
				   }
			}
			while (!(gameGrid.checkWinCondition())) {
				if (gameGrid.returnAlive() == false) {
					System.out.println("you have died");

					if (ui.continuation() == 1) {
						gameGrid.initialize();
						turn();
					} else {
						System.exit(0);
					}

				}

				ui.displayLivesAndAmmo(gameGrid.returnAmmo(),
						gameGrid.returnLives());
				printGrid(debug);
				// making change here
				case2();
				
			}
			if (ui.continuation() == 1) {
				gameGrid.initialize();
				turn();
			} else {
				System.exit(0);
			}

		}

		else if (userChoice1 == 3) {
			System.exit(0);
		} else if (userChoice1 == 4) {
			debug = true;
		}

	}
	
	/**
	 * Method used to make the {@link #turn()} method code cleaner and shorter. 
	 * This method is just a {@code switch} statement that checks user input 
	 * and reacts to that input. In this case it decides whether the user wants
	 * the player to move, shoot, save the game, debug, exit debug, quit, or 
	 * manual respawn.
	 */
	private void case1()
	{	int direction;
		direction = ui.directionMenu();
		gameGrid.look(direction);

		printGrid(debug);
		int userChoice2 = ui.mainPhase2();

		switch (userChoice2) {
		case 1:
			pMove();
			break;
		case 2:
			pShoot();
			break;

		case 3:
			saveGame();
			break;
		case 4:
			debug = true;
			break;
		case 5:
			debug = false;
			break;
		case 6:
			System.exit(0);
			break;
		case 7:
			gameGrid.manualRespawn();
		}

	}
	
	/**
	 * Method used to help clean up code for the {@link #turn()} method. This {@code switch}
	 * statement is used to help the user decide if they want to look, move shoot, debug,
	 * exit debug, save, quit, or respawn. This method is different from the previous
	 * method because this method allows the user to look and after the user looks 
	 * the user should not be able to look again in the same turn so we remove the option
	 * to look again with the previous method.
	 */
	private void case2()
	{
		int userChoice1 = ui.mainPhase1();
		switch (userChoice1) {
		// move shoot save quit
		case 1:
		case1();
			break;
		case 2:
			pMove();
			break;
		case 3:
			pShoot();
			break;

		case 4:
			debug = true;
			break;

		case 5:
			debug = false;

			break;
		case 6:
			saveGame();
			break;
		case 7:
			System.exit(0);
			break;
		case 8:
			gameGrid.manualRespawn();

		}
	}
	
	/**
	 * Method used when the user decides they want the player to
	 * shoot by asking the direction they would like to shoot
	 */
	private void pShoot() {
		gameGrid.playerAction(1, ui.directionMenu());
		gameGrid.NinjaActions();
		gameGrid.restoreRoom();
		gameGrid.restorePowerUp();
		printGrid(debug);
	}

	/**
	 * Method used when the user decides they want the player to
	 * move by asking the direction they would like the player to
	 * move in
	 */
	private void pMove() {
		int n = 2;
		do {
			n = gameGrid.playerAction(2, ui.directionMenu(0));
		} while (n != 1);
		gameGrid.restoreRoom();
		gameGrid.NinjaActions();
		gameGrid.restoreRoom();
		gameGrid.restorePowerUp();
		printGrid(debug);
	}

	/**
	 * Method used to save the list of string inputs that represent the
	 * save game files and also saves the grid 
	 */
	private void saveGame() {
		String save = saveList.fillSaveList();
		try {
			FileOutputStream fos = new FileOutputStream("defaultSaves.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(saveList);
			oos.close();

		} catch (FileNotFoundException e) {
			System.out.println("No such file exists. Try again");
		} catch (IOException e) {
			System.out.println("File error");
		}

		try {
			FileOutputStream fos = new FileOutputStream(save);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(gameGrid);
			oos.close();

		} catch (FileNotFoundException e) {
			System.out.println("No such file exists. Try again");
		} catch (IOException e) {
			System.out.println("File error");
		}
	}

	/**
	 * Method that loads the string lists that the user has inputed as 
	 * saved games in case they don't remember what the file names are.
	 */
	private void loadGameList() {
		try {
			FileInputStream fis = new FileInputStream("defaultSaves.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			saveList = (SavedGame) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such file exists. Try again");
			loadGame();
		} catch (IOException e) {
			System.out.println("");
		} catch (ClassNotFoundException e) {
			System.out.println("Class does not exist");
		}

	}

	/**
	 * Method that displays the saved game file names and then loads 
	 * the game grid.
	 */

	private void loadGame() {

		saveList.showGamesSaved();
		String load = ui.loadPrefrences();
		try {
			FileInputStream fis = new FileInputStream(load);
			ObjectInputStream ois = new ObjectInputStream(fis);
			gameGrid = (Grid) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such file exists. Try again");
			loadGame();
		} catch (IOException e) {
			System.out.println("File error");
		} catch (ClassNotFoundException e) {
			System.out.println("Class does not exist");
		}

	}

	/**
	 * Method used to determine if the grid is going to be "revealed" aka debug
	 * mode which displays every object in the grid, or decides if the grid will
	 * be hidden and only display rooms and the player.
	 * 
	 * @param debug a boolean value that is originally {@code false} and then later
	 * set to true if the user decides to display the grid in debug mode.
	 */

	private void printGrid(boolean debug) {
		if (!(debug)) {
			System.out.println("");
			gameGrid.getGrid();
			System.out.println();
		} else {
			System.out.println("");
			gameGrid.getRealGrid();
		}
	}
}