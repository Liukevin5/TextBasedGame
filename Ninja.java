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
 * This class extends GridEntity. When we create an object of this class, we will use this 
 * object to represent the ninjas in the grid. The ninjas have a stab method to be used on the player.    
 */

package edu.csupomona.cs.cs141.finalProject;

/**
 * The {@link Ninja} class gives the enemies for the game. It is the source for the antagonists for our Player hero. It extends 
 * {@link GridEntity} and each ninja is given its location with row ('r'), and column ('c') coordinate on the {@link Grid}, and is 
 * represented by 'N'. But they will not show in the game, unless the player 'debugs' to show what is in the {@link Grid}.
 * 
 * The @param stab method kills the player so the player will lose a life. 
 * @author Cesar_2
 *
 */

public class Ninja extends GridEntity 
{
	
	public Ninja(int r, int c)
	{
		super(r,c,"N");
	}
		
	public void stab(Player target)
	{
		target.die();
	}
	
}