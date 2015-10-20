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
 * Room extends GridEntity that can hold a briefcase. It is just another entity in the grid..  
 */

package edu.csupomona.cs.cs141.finalProject;

/**The {@link Room} class is just another child class of {@link GridEntity}. Each room will potentially hold the 
 *  briefcase, which is determined in the {@link Grid} class through randomization. Each room has a specified 
 * coordinate in order to have them equally distributed along the {@link Grid}.
 * This class has given each @param Room its location with row ('r'), and column ('c') coordinate on the {@link Grid}, 
 * and is represented by 'X'. This will always show in the {@link Grid}, because the player has to travel and enter a room in 
 * order to find the hidden @param briefcase and win the game.
 * @author Cesar_2
 */

public class Room extends GridEntity
{

	
	public Room(int r, int c)
	{
		super(r,c,"X");
		changeRepresentation("X");
	}
}