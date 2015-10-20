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
 * This class is extends GridEntity, but is also the parent class of AdditionalBullet, Radar, and Invincibility.
 *   
 */

package edu.csupomona.cs.cs141.finalProject;

/**
 * The {@link PowerUps} class is a child class of {@link GridEntity}. Each individual @param powerup has a class 
 * of its own, which extend from {@link PowerUps}, and that class extends from {@link GridEntity}.
 * @author Cesar_2
 *
 */

public abstract class PowerUps extends GridEntity
{
	public PowerUps(int r, int c, String rep) 
	{
		super(r,c,rep);
	}
}