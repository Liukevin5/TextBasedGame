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
 * This class is the parent class for every object used in the grid itself. All objects of 
 * type GridEntity will have values for the fields representation, origRep, r (row), and c (column). 
 */

package edu.csupomona.cs.cs141.finalProject;

import java.io.Serializable;

/**
 * Class that represents all items of the grid will extend off of 
 * in order to create a 2dimensional array with objects of the same
 * type
 * 
 * @author Team TBD {Adrian, Kevin, Oscar, Alberto, Cesar}
 *
 */

public class GridEntity implements Serializable {
	
	/**
	 * Field that represents the row of the entity in the grid
	 */
	private int r;
	
	/**
	 * Field that represents the column of the entity in the grid
	 */
	private int c;
	
	/**
	 * Field that represents the hidden status of all entities except
	 * the {@link Player} and {@link Room}s
	 */
	private String representation;
	
	/**
	 * Field that represents the symbol of the the objects in the game. 
	 * During regular game play the user is only allowed to see the
	 * {@link Player} and the {@link Room}s, in debug mode the user is
	 * allowed to see the representation of all {@link PowerUps} and
	 * {@link Ninja}s along with the objects that regular game play 
	 * displays
	 */
	private String origRep;
	
	/**
	 * Constructor for the {@link GridEntity} object that takes two integers
	 * that represent the row and column of the entity in the grid. 
	 * 
	 * @param row an integer value that represents in which row the object will
	 * be in
	 * 
	 * @param column an integer value that represents in which column the
	 * object will be in
	 */
	public GridEntity(int row, int column) 
	{
		r = row;
		c = column;
		
		representation = "*";
		origRep = " ";
	}
	
	/**
	 * Constructor that overloads the first constructor adding a third parameter
	 * that represents the symbol for the object that will be displayed in debug
	 * mode 
	 * 
	 * @param row an integer value that represents in which row the object will
	 * be in
	 * @param column an integer value that represents in which column the
	 * object will be in
	 * @param rep represents the symbol for the object that will be displayed in debug
	 * mode 
	 */
	public GridEntity(int row, int column, String rep)
	{
		r = row;
		c = column;
		representation = "*";
		origRep = rep;
	}
		
	/**
	 * Method that is used to change the coordinates of an object by taking the
	 * parameters and changing their row and column values in the {@link Grid}
	 * 
	 * @param row the new row where the object will be moved to 
	 * @param col the new column where the object will be moved to
	 */
	public void changeCoordinates(int row, int col)
	{
		r = row;
		c = col;
	}
	
	/**
	 * Method used to change the representation of the object in the 
	 * {@link Grid}
	 * @param rep new representation to be shown in the {@link Grid}
	 */
	public void changeRepresentation(String rep)
	{
		representation = rep;
	}
	
	/**
	 * Method used to change the original representation of the obeject in
	 * the {@link Grid}
	 * @param rep new representation to be shown in the {@link Grid}
	 */
	public void changeOrigRep(String rep)
	{
		origRep = rep;
	}
	
	/**
	 * Method that is used to get the representation of the object
	 * @return the representation to be shown on the {@link Grid}
	 */
	public String getRepresentation() 
	{
		return representation;
	}
	
	/**
	 * Method that is used to get the original representation of the object
	 * @return the representation to be shown on the {@link Grid}
	 */
	public String getOrigRepresentation()
	{
		return origRep;
	}
	
	/**
	 * Method used to get the row of the object in the {@link Grid}
	 * @return an integer value that represents the row they are in
	 */
	public int getRow()
	{
		return r;
	}
	
	/**
	 * Method used to get the column of the object in the {@link Grid}
	 * @return an integer value that represents the column they are in
	 */
	public int getCol() 
	{
		return c;
	}
	
}