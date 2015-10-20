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
 * This class saves the game.  
 */

package edu.csupomona.cs.cs141.finalProject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * We created a {@link SavedGame} class to save the game. We have also seralized it, along with the 
 * {@link GameEngine} class and the {@link UserInterface} class. Here, we have an @param ArrayList String 'saveLogs'. 
 * It then calls to the {@link UserInterface} class to ask the user if he wants to save and to name that saved file.
 * Then to show the saved game files, we used a for loop to display the files.  
 * 
 */

public class SavedGame implements Serializable{

	/**
	 * Creates a new {@code ArrayList} that will save all the {@code String}s
	 * the user inputs as the saved game files. We are doing this so we can
	 * display these {@code String}s in case the user forgets the name
	 * of the saved game file.
	 */
	ArrayList<String> saveLogs = new ArrayList<String>();
	
	/**
	 * Creates an instance of the {@link UserInterface} class to display an 
	 * appropriate message to the user when saving and loading.
	 */
	UserInterface ui = new UserInterface();
	
	/**
	 * Method that fills the list with the user input
	 * @return name, which is the {@code String} that the
	 * user inputs to represent the name of the saved game
	 * file
	 */
	public String fillSaveList() {
		String name = ui.savePrefrences();
		saveLogs.add(name);
		return name;
	}
	
	/**
	 * Prints the list of saved game files
	 */
	public void showGamesSaved() {
		System.out.println("");
		for(String s: saveLogs) {
			System.out.println(s);
		}
	}
}