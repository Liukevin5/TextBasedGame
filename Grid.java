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
 * The Grid encompasses the grid itself. It is in charge of movement, looking, restoring powerups, 
 * restoring rooms, respawning the player, respawning rooms, checking the win condition, initializing ninjas and the grid overall.  
 */

package edu.csupomona.cs.cs141.finalProject;

/**
 * Here we import @param Serializable in order to save the {@link Grid}, and @param Random to generate random numbers 
 * throughout this class. 
 */


import java.io.Serializable;
import java.util.Random;

/**
 * Here is where we name all our variables. We have variables to produce the
 * coordinates for the @param briefcase, a boolean expression for each powerup (whether
 * its equipped or not), a @param Player object, 6 @param ninja objects, @param Powerup objects 
 * that represent each powerUp, a call to the {@link GridEntity}, 9 @param Room objects, and an
 * invulnerability turn counter. This class also has a Grid constructor that
 * initializes a {@link Grid} object.
 *
 */

public class Grid implements Serializable {

	private Random rd = new Random();
	private int briefcaseRow;
	private int briefcaseCol;
	private boolean radUse;
	private boolean invulUse;
	private boolean bulletUse;
	private Player player;
	private Ninja ninja1;
	private Ninja ninja2;
	private Ninja ninja3;
	private Ninja ninja4;
	private Ninja ninja5;
	private Ninja ninja6;
	private PowerUps addBull;
	private PowerUps rad;
	private PowerUps invul;
	GridEntity[][] grid;
	private Room room1;
	private Room room2;
	private Room room3;
	private Room room4;
	private Room room5;
	private Room room6;
	private Room room7;
	private Room room8;
	private Room room9;
	private int invulTurnCount;

	public Grid() {
		grid = new GridEntity[9][9];
		radUse = false;
		invulUse = false;
		bulletUse = false;
	}

	/**
	 * This method is used to create a randomized number between 0-8 (9 numbers), and 
	 * one of the 9 @param Room is chosen to contain the @param briefcase. It contains the coordinate 
	 * for each briefcase and will have a 'B' labeled for the room containing the 
	 * briefcase. It will only show when the game in debug mode, not in the normal gameplay.
	 */
	
	private void chooseRoom() {
		int randomRoom = rd.nextInt(8);

		switch (randomRoom) {
		case 0: {
			
			grid[1][1].changeOrigRep("B");
			briefcaseRow = 1;
			briefcaseCol = 1;
			break;
		}

		case 1: {
			
			grid[1][4].changeOrigRep("B");
			briefcaseRow = 1;
			briefcaseCol = 4;
			break;
		}

		case 2: {
			
			grid[1][7].changeOrigRep("B");
			briefcaseRow = 1;
			briefcaseCol = 7;
			break;
		}

		case 3: {
			
			grid[4][1].changeOrigRep("B");
			briefcaseRow = 4;
			briefcaseCol = 1;
			break;
		}

		case 4: {
			
			grid[4][4].changeOrigRep("B");
			briefcaseRow = 4;
			briefcaseCol = 4;
			break;
		}

		case 5: {
			
			grid[4][7].changeOrigRep("B");
			briefcaseRow = 4;
			briefcaseCol = 7;
			break;
		}

		case 6: {
			
			grid[7][1].changeOrigRep("B");
			briefcaseRow = 7;
			briefcaseCol = 1;

			break;
		}

		case 7: {
			
			grid[7][4].changeOrigRep("B");
			briefcaseRow = 7;
			briefcaseCol = 4;
			break;
		}

		default: {
			
			grid[7][7].changeOrigRep("B");
			briefcaseRow = 7;
			briefcaseCol = 7;
			break;
		}
		}

	}
	
	/**
	 * The @param RoomSet is used to create the rooms in the {@link Grid}. Its coordinates are given to 
	 * the {@link Grid} and place it in a certain coordinate. 
	 */

	private void roomSet() {

		room1 = new Room(1, 1);
		room2 = new Room(1, 4);
		room3 = new Room(1, 7);

		room4 = new Room(4, 1);
		room5 = new Room(4, 4);
		room6 = new Room(4, 7);

		room7 = new Room(7, 1);
		room8 = new Room(7, 4);
		room9 = new Room(7, 7);

		grid[1][1] = room1;
		grid[1][4] = room2;
		grid[1][7] = room3;

		grid[4][1] = room4;
		grid[4][4] = room5;
		grid[4][7] = room6;

		grid[7][1] = room7;
		grid[7][4] = room8;
		grid[7][7] = room9;

	}
	
	/**
	 * The @param setPowerUps method calls to the set methods for each Powerup. When the program runs and gets 
	 * to this method, it will call to other methods that will set powerups in the {@link Grid}.
	 */

	private void setPowerUps() {
		setBulletPow();
		setRadPow();
		setInvulPow();

	}
	
	/**
	 * This method will run after every turn to see if any powerups need to be restored.
	 * With integers for each powerups' row and column, a coordinate is checked to see
	 * if the powewrup is not used, or the space is not occupied by a @param ninja, a @param Player, 
	 * then the powerup will be replaced back to where it was originally and replaces 
	 * space with whatever letter is represented by the powerup.
	 */

	public void restorePowerUp() {
		int radRow = rad.getRow();
		int radCol = rad.getCol();
		int bullRow = addBull.getRow();
		int bullCol = addBull.getCol();
		int invulRow = invul.getRow();
		int invulCol = invul.getCol();
		if (radUse == false && !(grid[radRow][radCol] instanceof Ninja)
				&& !(grid[radRow][radCol] instanceof Player)) {
			grid[radRow][radCol] = new Radar(radRow, radCol);
			grid[radRow][radCol].changeOrigRep("R");
		}
		if (bulletUse == false && !(grid[bullRow][bullCol] instanceof Ninja)
				&& !(grid[bullRow][bullCol] instanceof Player)) {
			grid[bullRow][bullCol] = new AdditionalBullet(bullRow, bullCol);
			grid[bullRow][bullCol].changeOrigRep("A");
		}
		if (invulUse == false && !(grid[invulRow][invulCol] instanceof Ninja)
				&& !(grid[invulRow][invulCol] instanceof Player)) {
			grid[invulRow][invulCol] = new Invincibility(invulRow, invulCol);
			grid[invulRow][invulCol].changeOrigRep("I");
		}
	}

	/**
	 * If a @param Player is about to walk over a powerup, it will be used setting these
	 * to be true so they will not be restored by the @param RestorePowerUp method, but 
	 * then it will not be used by @param ninjas so it will later be restored after
	 * the turn ends. 
	 */
	
	private void useInvul() {
		invulUse = true;
	}

	private void useBull() {
		bulletUse = true;
	}

	private void useRad() {
		radUse = true;
	}

	/**
	 * These set methods were called to from the above method @param setPowerUps. 
	 * It calls to each of the set methods for each powerup below. A random
	 * coordinate on the grid is chosen and the powerup will be placed there
	 * as long as the space is not occupied by the @param Player, a @param ninja, 
	 * a @param room, or another powerup. If the generated coordinate is already occupied 
	 * and a powerup cannot be placed, then the method will call itself again
	 * to randomize another coordinate on the grid and start the process over. 
	 */
	
	private void setBulletPow() {
		int row = generateRandomRow();
		int col = generateRandomCol();
		if (getGridLocation(row, col) instanceof Player
				|| getGridLocation(row, col) instanceof Ninja
				|| getGridLocation(row, col) instanceof Room
				|| getGridLocation(row, col) instanceof PowerUps)

			setBulletPow();
		else {
			addBull = new AdditionalBullet(row, col);
			grid[row][col] = addBull;
		}

	}

	private void setInvulPow() {
		int row = generateRandomRow();
		int col = generateRandomCol();
		if (getGridLocation(row, col) instanceof Player
				|| getGridLocation(row, col) instanceof Ninja
				|| getGridLocation(row, col) instanceof Room
				|| getGridLocation(row, col) instanceof PowerUps)

			setInvulPow();
		else {
			invul = new Invincibility(row, col);
			grid[row][col] = invul;
		}
	}

	private void setRadPow() {
		int row = generateRandomRow();
		int col = generateRandomCol();
		if (getGridLocation(row, col) instanceof Player
				|| getGridLocation(row, col) instanceof Ninja
				|| getGridLocation(row, col) instanceof Room
				|| getGridLocation(row, col) instanceof PowerUps)

			setRadPow();
		else {
			rad = new Radar(row, col);
			grid[row][col] = rad;
		}
	}
	
	/**
	 * These generateRow/generateColumn methods are used throughout the {@link Grid} class
	 * for other objects' purpose of coming up with a random row and column to set 
	 * a coordinate of the grid. The value is then returned and that is the random 
	 * row or column selected for whatever other method is using this one for. 
	 * @return
	 */

	private int generateRandomRow() {
		int r = rd.nextInt(9);
		return r;
	}

	private int generateRandomCol() {
		int c = rd.nextInt(9);
		return c;
	}
	
	/**
	 * The @param fill method just creates a grid with empty spaces. Other methods will put 
	 * the @param powerUps, @param ninjas, @param Rooms, @param Player, and '*' in the grid. 
	 * 
	 */

	private void fill() {
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid.length; col++) {
				grid[row][col] = new GridEntity(row, col);
			}
		}
	}
	
	/**
	 * This generates the @param Player and makes him always spawn on the bottom-leftmost
	 * corner of the grid.
	 */

	private void generatePlayer() {
		player = new Player();
		grid[8][0] = player;
	}
	
	/**
	 * This method is used if the @param Player chooses to just manually respawn back at 
	 * the initial location. This would be ideally used if the player is somehow
	 * stuck at one spot, most likely surrounded by @param ninjas, and he cannot move, and 
	 * he is equipped with @param invincibility. If the player is in that situation, the 
	 *  ninjas will be forced to continually try to kill the @param Player if he is adjacent
	 * to them. The @param Player will not be able to move, and his powerup will just be 
	 * consumed uselessly. The player will be given the choice to manually respawn back
	 * in the initial respawn location whenever he likes, but at the cost of a life.  
	 */

	public void manualRespawn() {
		player.respawnPenalty();
		playerRespawn();
	}
	
	/**
	 * This method respawns the player. First we check if the initial respawn spot is occupied by a @param ninja, 
	 * then if so it will find a new coordinate to move the @param ninja. It will move the @param ninja occupying your 
	 * respawn spot to a new set of coordinates, as long as those coordinates are not occupied by another 
	 * @param ninja or a powerup or a room. If it is, it calls the method again to generate new coordinates 
	 * until the new set is clear of anything inside those coordinates.  
	 */

	private void playerRespawn() {
		grid[player.getRow()][player.getCol()] = new GridEntity(
				player.getRow(), player.getCol(), " ");
		if (grid[8][0] instanceof Ninja) {
			int row = generateRandomRow();
			int col = generateRandomCol();
			if (grid[row][col] instanceof Ninja
					|| getGridLocation(row, col) instanceof Room
					|| getGridLocation(row, col) instanceof PowerUps) {
				playerRespawn();
			} else if (ninja1.getRow() == 8 && ninja1.getCol() == 0) {
				grid[row][col] = ninja1;
				ninja1.changeCoordinates(row, col);
				player.changeCoordinates(8, 0);
				grid[8][0] = player;
			} else if (ninja2.getRow() == 8 && ninja2.getCol() == 0) {
				grid[row][col] = ninja2;
				ninja2.changeCoordinates(row, col);
				player.changeCoordinates(8, 0);
				grid[8][0] = player;
			} else if (ninja3.getRow() == 8 && ninja3.getCol() == 0) {
				grid[row][col] = ninja3;
				ninja3.changeCoordinates(row, col);
				player.changeCoordinates(8, 0);
				grid[8][0] = player;
			} else if (ninja4.getRow() == 8 && ninja4.getCol() == 0) {
				grid[row][col] = ninja4;
				ninja4.changeCoordinates(row, col);
				player.changeCoordinates(8, 0);
				grid[8][0] = player;
			} else if (ninja5.getRow() == 8 && ninja5.getCol() == 0) {
				grid[row][col] = ninja5;
				ninja5.changeCoordinates(row, col);
				player.changeCoordinates(8, 0);
				grid[8][0] = player;
			} else if (ninja6.getRow() == 8 && ninja6.getCol() == 0) {
				grid[row][col] = ninja6;
				ninja6.changeCoordinates(row, col);
				player.changeCoordinates(8, 0);
				grid[8][0] = player;
			}
		} else {
			player.changeCoordinates(8, 0);
			grid[8][0] = player;
		}

	}

	/**
	 * This method calls to other methods that generate each individual @param ninja.
	 */

	private void generateNinjas() {
		generateNinja1();
		generateNinja2();
		generateNinja3();
		generateNinja4();
		generateNinja5();
		generateNinja6();

	}
	
	/**
	 * This method will generate @param ninja1. The ninja is placed in random spots on the grid, as long as the space is 
	 * not occupied the @param Player, a @param Room, a powerup, or another @param ninja. Also, the
	 * @param ninja is placed at least 3 spaces from the @param Player. 
	 */

	private void generateNinja1() {
		int row = generateRandomRow();
		int col = generateRandomCol();

		int distance = (8 - row) + (0 + col);   // This is how far the ninja is
												// from the player

		if (distance < 3 || getGridLocation(row, col) instanceof Player
				|| getGridLocation(row, col) instanceof Ninja
				|| getGridLocation(row, col) instanceof Room
				|| getGridLocation(row, col) instanceof PowerUps)
			generateNinja1();
		else {
			ninja1 = new Ninja(row, col);
			grid[row][col] = ninja1;
		}
	}
	
	/**
	 * This method will generate @param ninja2. The @param ninja is placed in random spots on the grid, as long as the space is 
	 * not occupied the @param Player, a @param Room, a powerup, or another @param ninja. Also, the
	 * @param ninja is placed at least 3 spaces from the player. 
	 */

	private void generateNinja2() {
		int row = generateRandomRow();
		int col = generateRandomCol();

		int distance = (8 - row) + (0 + col);   // This is how far the ninja is
												// from the player

		if (distance < 3 || getGridLocation(row, col) instanceof Player
				|| getGridLocation(row, col) instanceof Ninja
				|| getGridLocation(row, col) instanceof Room
				|| getGridLocation(row, col) instanceof PowerUps)
			generateNinja2();
		else {
			ninja2 = new Ninja(row, col);
			grid[row][col] = ninja2;
		}
	}
	
	/**
	 * This method will generate @param ninja3. The @param ninja is placed in random spots on the grid, as long as the space is 
	 * not occupied the @param Player, a @param Room, a powerup, or another @param ninja. Also, the
	 * @param ninja is placed at least 3 spaces from the player. 
	 */

	private void generateNinja3() {
		int row = generateRandomRow();
		int col = generateRandomCol();

		int distance = (8 - row) + (0 + col); // This is how far the ninja is
												// from the player

		if (distance < 3 || getGridLocation(row, col) instanceof Player
				|| getGridLocation(row, col) instanceof Ninja
				|| getGridLocation(row, col) instanceof Room
				|| getGridLocation(row, col) instanceof PowerUps)
			generateNinja3();
		else {
			ninja3 = new Ninja(row, col);
			grid[row][col] = ninja3;
		}
	}
	
	/**
	 * This method will generate @param ninja4. The @param ninja is placed in random spots on the grid, as long as the space is 
	 * not occupied the @param Player, a @param Room, a powerup, or another @param ninja. Also, the
	 * @param ninja is placed at least 3 spaces from the player.  
	 */

	private void generateNinja4() {
		int row = generateRandomRow();
		int col = generateRandomCol();

		int distance = (8 - row) + (0 + col); // This is how far the ninja is
												// from the player

		if (distance < 3 || getGridLocation(row, col) instanceof Player
				|| getGridLocation(row, col) instanceof Ninja
				|| getGridLocation(row, col) instanceof Room
				|| getGridLocation(row, col) instanceof PowerUps)
			generateNinja4();
		else {
			ninja4 = new Ninja(row, col);
			grid[row][col] = ninja4;
		}
	}
	
	/**
	 * This method will generate @param ninja5. The @param ninja is placed in random spots on the grid, as long as the space is 
	 * not occupied the @param Player, a @param Room, a powerup, or another @param ninja. Also, the
	 * @param ninja is placed at least 3 spaces from the player. 
	 */

	private void generateNinja5() {
		int row = generateRandomRow();
		int col = generateRandomCol();

		int distance = (8 - row) + (0 + col); // This is how far the ninja is
												// from the player

		if (distance < 3 || getGridLocation(row, col) instanceof Player
				|| getGridLocation(row, col) instanceof Ninja
				|| getGridLocation(row, col) instanceof Room
				|| getGridLocation(row, col) instanceof PowerUps)
			generateNinja5();
		else {
			ninja5 = new Ninja(row, col);
			grid[row][col] = ninja5;
		}
	}
	
	/**
	 * This method will generate @param ninja6. The @param ninja is placed in random spots on the grid, as long as the space is 
	 * not occupied the @param Player, a @param Room, a powerup, or another @param ninja. Also, the
	 * @param ninja is placed at least 3 spaces from the player. 
	 */

	private void generateNinja6() {
		int row = generateRandomRow();
		int col = generateRandomCol();

		int distance = (8 - row) + (0 + col); // This is how far the ninja is
												// from the player

		if (distance < 3 || getGridLocation(row, col) instanceof Player
				|| getGridLocation(row, col) instanceof Ninja
				|| getGridLocation(row, col) instanceof Room
				|| getGridLocation(row, col) instanceof PowerUps)
			generateNinja6();
		else {
			ninja6 = new Ninja(row, col);
			grid[row][col] = ninja6;
		}
	}
	
	/**
	 * This method initializes everything on the grid.
	 */

	public void initialize() {
		fill();
		roomSet();
		chooseRoom();
		generatePlayer();
		setPowerUps();
		generateNinjas();
	}
	
	/**
	 * This method returns an object from the grid depending on the location of the coordinates.
	 * @param r
	 * @param c
	 * @return grid
	 */

	private GridEntity getGridLocation(int r, int c) {
		return grid[r][c];
	}

	/**
	 * This will return an array of adjacent locations that the @param ninja will use 
	 * to decide where to move to or if it can stab a @param Player. This method takes 
	 * {@link GridEntity} as the argument in its parameters. 
	 * @param GridEntry
	 * @return possibleLocation
	 */
	
	private GridEntity[] getAdjacentGridLocations(GridEntity GridEntry) {
		int r = GridEntry.getRow();
		int c = GridEntry.getCol();
		GridEntity[] possibleLocations;
		
		
		
		// Check for corners and edges. Make sure its bigger than one but less
		// than 8 for arrays of size 4. It'll be size 3 for when its on an edge
		// but on a corner. ANd size 2 for all 4 edges

		if (r > 0 && r < 8 && c == 0) {
			possibleLocations = new GridEntity[3];
			possibleLocations[0] = grid[r - 1][c];
			possibleLocations[1] = grid[r][c + 1];
			possibleLocations[2] = grid[r + 1][c];
		} else if (r == 0 && c > 0 && c < 8) {
			possibleLocations = new GridEntity[3];
			possibleLocations[0] = grid[r][c + 1];
			possibleLocations[1] = grid[r + 1][c];
			possibleLocations[2] = grid[r][c - 1];
		} else if (c == 8 && r > 0 && r < 8) {
			possibleLocations = new GridEntity[3];
			possibleLocations[0] = grid[r][c - 1];
			possibleLocations[1] = grid[r - 1][c];
			possibleLocations[2] = grid[r + 1][c];
		} else if (r == 8 && c > 0 && c < 8) {
			possibleLocations = new GridEntity[3];
			possibleLocations[0] = grid[r - 1][c];
			possibleLocations[1] = grid[r][c + 1];
			possibleLocations[2] = grid[r][c - 1];
		} else if (r == 0 && c == 0) {
			possibleLocations = new GridEntity[2];
			possibleLocations[0] = grid[r][c + 1];
			possibleLocations[1] = grid[r + 1][c];
		} else if (r == 8 && c == 0) {
			possibleLocations = new GridEntity[2];
			possibleLocations[0] = grid[r - 1][c];
			possibleLocations[1] = grid[r][c + 1];
		} else if (r == 0 && c == 8) {
			possibleLocations = new GridEntity[2];
			possibleLocations[0] = grid[r][c - 1];
			possibleLocations[1] = grid[r + 1][c];
		} else if (r == 8 && c == 8) {
			possibleLocations = new GridEntity[2];
			possibleLocations[0] = grid[r - 1][c];
			possibleLocations[1] = grid[r][c - 1];
		} else {
			possibleLocations = new GridEntity[4];
			possibleLocations[0] = grid[r - 1][c];
			possibleLocations[1] = grid[r][c + 1];
			possibleLocations[2] = grid[r + 1][c];
			possibleLocations[3] = grid[r][c - 1];
		}
		return possibleLocations;
	}
	
	/**
	 * This method will check when the player shoots, all the items within the straight line of the direction
	 * the player shot in. 
	 * @param GridEntry
	 * @param direction
	 * @return possibleLocations
	 */

	private GridEntity[] getPossibleTargets(GridEntity GridEntry, int direction)

	{
		int r = GridEntry.getRow();
		int c = GridEntry.getCol();
		GridEntity[] possibleLocations;
		if (direction == 0) {
			int arraysize = r;
			possibleLocations = new GridEntity[arraysize];
			for (int i = 0; i < arraysize; i++)
				possibleLocations[i] = grid[arraysize - i][c];
		} else if (direction == 90) {
			int arraysize = grid.length - c;
			possibleLocations = new GridEntity[arraysize];
			for (int i = 0; i < arraysize; i++)
				possibleLocations[i] = grid[r][c + i];
		} else if (direction == 180) {
			int arraysize = grid.length - r;
			possibleLocations = new GridEntity[arraysize];
			for (int i = 0; i < arraysize; i++)
				possibleLocations[i] = grid[r + i][c];
		} else {
			int arraysize = c;
			possibleLocations = new GridEntity[arraysize];
			for (int i = 0; i < arraysize; i++)
				possibleLocations[i] = grid[r][arraysize - i];
		}
		return possibleLocations;
	}

	/**
	 * GetGrid represents what every player will see on the grid, just the rooms and the dark spaces
	 * will be represented by '*'
	 */
	
	public void getGrid() {
		for (int row = 0; row < 9; row++) {
			System.out.println("");
			for (int col = 0; col < 9; col++)
				System.out
						.print("[" + grid[row][col].getRepresentation() + "]");
		}
	}

	/**
	 * This is what is shown when debug mode is activated.
	 */
	
	public void getRealGrid() {
		for (int row = 0; row < 9; row++) {
			System.out.println("");
			for (int col = 0; col < 9; col++)
				System.out.print("[" + grid[row][col].getOrigRepresentation()
						+ "]");

		}
		System.out.println("");
	}
	
	/**
	 * This method takes the player @param desiredDirection as its argument then calls the @param shadow method. Then it uses if else statements, 
	 * depending on the @param desiredDirection, then it will call the @param changeDirection method in the {@link Player} class. 
	 * @param desiredDirection
	 */

	public void look(int desiredDirection) {
		shadow();
		if (desiredDirection == 1) {
			player.changeDirection(0);
		}

		else if (desiredDirection == 4) {
			player.changeDirection(90);
		} else if (desiredDirection == 2) {
			player.changeDirection(180);
		} else {
			player.changeDirection(270);
		}
		System.out.println(changeGridToPlayer());
	}

	/**
	 * This method changes the blocks on the grid, depending on direction, to show what actually . 
	 * @return "ALL CLEAR" if there are no ninjas
	 * @return "NINJA AHEAD!!" if there is at least one ninja ahead
	 */

	private String changeGridUp() {
		int pR1 = player.getRow() - 1;
		int pR2 = player.getRow() - 2;
		int ninCount = 0;
		int c = player.getCol();

		for (int r = 0; r < grid.length; r++) {

			if (r == pR1 || r == pR2) {
				if (!(grid[pR1][c] instanceof Room)) {
					grid[r][c].changeRepresentation(grid[r][c]
							.getOrigRepresentation());

					if (grid[r][c] instanceof Ninja) {
						ninCount++;
					}
				} else {
					System.out.println("You cant look through walls");
					break;
				}
			}

		}
		if (ninCount == 0)
			return "ALL CLEAR";
		else
			return "NINJA AHEAD!!";
	}
	
	/**
	 * This method changes the blocks on the grid, depending on direction, to show what actually . 
	 * @return "ALL CLEAR" if there are no ninjas
	 * @return "NINJA AHEAD!!" if there is at least one ninja ahead
	 */

	private String changeGridRight() {
		int pC1 = player.getCol() + 1;
		int pC2 = player.getCol() + 2;
		int ninCount = 0;
		int r = player.getRow();
		// for (int r = 0; r < Grid.length; r++)
		for (int c = 0; c < grid[r].length; c++) {
			if ((c == (pC1) || c == (pC2))) {
				if (!(grid[r][pC1] instanceof Room)) {
					grid[r][c].changeRepresentation(grid[r][c]
							.getOrigRepresentation());
					if (grid[r][c] instanceof Ninja)
						ninCount++;
				} else {
					System.out.println("You cant look through walls");
					break;
				}
			}
		}
		if (ninCount == 0)
			return "ALL CLEAR";
		else
			return "NINJA AHEAD!!";
	}
	
	/**
	 * This method changes the blocks on the grid, depending on direction, to show what actually . 
	 * @return "ALL CLEAR" if there are no ninjas
	 * @return "NINJA AHEAD!!" if there is at least one ninja ahead
	 */

	private String changeGridDown() {
		int pR1 = player.getRow() + 1;
		int pR2 = player.getRow() + 2;
		int ninCount = 0;
		int c = player.getCol();
		for (int r = 0; r < grid.length; r++)
		// for (int c = 0; c < Grid[r].length; c++)
		{
			if ((r == (pR1) || r == (pR2))) {
				if (!(grid[pR1][c] instanceof Room)) {
					grid[r][c].changeRepresentation(grid[r][c]
							.getOrigRepresentation());
					if (grid[r][c] instanceof Ninja)
						ninCount++;
				} else {
					System.out.println("You cant look through walls");
					break;
				}
			}
		}
		if (ninCount == 0)
			return "ALL CLEAR";
		else
			return "NINJA AHEAD!!";
	}
	
	/**
	 * This method changes the blocks on the grid, depending on direction, to show what actually . 
	 * @return "ALL CLEAR" if there are no ninjas
	 * @return "NINJA AHEAD!!" if there is at least one ninja ahead
	 */

	private String changeGridLeft() {
		int pC1 = player.getCol() - 1;
		int pC2 = player.getCol() - 2;
		int ninCount = 0;
		int r = player.getRow();
		// for (int r = 0; r < Grid.length; r++)
		for (int c = 0; c < grid[r].length; c++) {
			if ((c == (pC1) || c == (pC2))) {
				if (!(grid[r][pC1] instanceof Room)) {
					grid[r][c].changeRepresentation(grid[r][c]
							.getOrigRepresentation());
					if (grid[r][c] instanceof Ninja)
						ninCount++;
				} else {
					System.out.println("You cant look through walls");
					break;
				}
			}
		}
		if (ninCount == 0)
			return "ALL CLEAR";
		else
			return "NINJA AHEAD!!";
	}
	
	/**
	 * This method calls to the @param changeGrid directions. It gets the direction from the {@link Player}, then calls to the different methods
	 * based on the directions.
	 * 
	 */

	private String changeGridToPlayer() { 
		int n = player.getDirection();
		if (n == 0)
			return changeGridUp();
		else if (n == 90)
			return changeGridRight();
		else if (n == 180)
			return changeGridDown();
		else
			return changeGridLeft();
	}

	/**
	 * The turn of the player where they look, then shoot or move. UI method decides what 
	 * will happen. 
	 * @param desiredAction
	 * @param desiredDirection
	 * @return
	 */
	
	public int playerAction(int desiredAction, int desiredDirection) {
		if (desiredAction == 1) {
			shoot(desiredDirection);
			return 1;
		} else {
			return movePlayer(desiredDirection);

		}
	}

	/**
	 * This method takes an integer for direction. It uses that direction for get possible
	 * targets, which will return an array of a size needed for however many possible 
	 * spots are being shot through. Those spots are then analyzed for the first instance
	 * of a ninja, which will then be replaced by the normal blank spot.
	 * In order to go through the list of targets, when going in descending order, you
	 * need to just increase the respective coordinate of the player by 1.
	 * Otherwise, you need to just descend base on the length of the array for
	 * the position of the targets. A bullet will only stop before leaving the grid if it 
	 * has hit a ninja or a room. 
	 * @param direction
	 * @return
	 */
	
	private int shoot(int direction) {
		invulP();
		if (player.getAmmo() == 1) {

			if (direction == 1)
				shootUp();
			else if (direction == 4 && checkifInRoom() == false)
				shootRight();
			else if (direction == 2 && checkifInRoom() == false)
				shootDown();
			else if (direction == 3 && checkifInRoom() == false)
				shootLeft();
			else
				System.out
						.println("You tried shooting through the walls of the room you are in, wasting your bullet");
			shadow();
			player.shoot();

		} else {
			System.out.println("You're out of Ammo!");
		}
		return 1;
	}
	
	/**
	 * This method shoots upward and determines if the bullet shot was a hit or miss. 
	 */

	private void shootUp() {
		int c = player.getCol();
		GridEntity[] targets = getPossibleTargets(player, 0);
		for (int i = 1; i <= targets.length; i++) {
			if (grid[targets.length - i][c] instanceof Ninja) {
				grid[targets.length-i][c].changeCoordinates(10,10);
				grid[targets.length - i][c] = new GridEntity(
						targets.length - i, c);
				System.out.println("A ninja has been killed");
				return;
			} else if (grid[targets.length - i][c] instanceof Room) {
				System.out
						.println("Unfortunately, you hit a room causing your bullet to stop in its tracks");
				return;
			}
		}
		System.out.println("Unfortunately, you hit nothing at all");
	}

	/**
	 * This method shoots right and determines if the bullet shot was a hit or miss. 
	 */

	private void shootRight() {
		int r = player.getRow();
		int c = player.getCol();

		GridEntity[] targets = getPossibleTargets(player, 90);
		for (int i = 1; i < targets.length; i++) {
			if (c + 1 >= 9) {
				System.out.println("Unfortunately, you hit nothing at all");
				return;
			} else if (grid[r][c + i] instanceof Ninja) {
				grid[r][c + i].changeCoordinates(10,10);
				grid[r][c + i] = new GridEntity(r, c + i);
				System.out.println("A ninja has been killed");
				return;
			} else if (grid[r][c + i] instanceof Room) {
				System.out
						.println("Unfortunately, you hit a room causing your bullet to stop in its tracks");
				return;
			}
		}
		System.out.println("Unfortunately, you hit nothing at all");
	}
	
	/**
	 * This method shoots downward and determines if the bullet shot was a hit or miss. 
	 */

	private void shootDown() {
		int r = player.getRow();
		int c = player.getCol();
		GridEntity[] targets = getPossibleTargets(player, 180);
		for (int i = 1; i < targets.length; i++) {
			if (r + 1 >= 9) {
				System.out.println("Unfortunately, you hit nothing at all");
				return;
			} else if (grid[r + i][c] instanceof Ninja) {
				  grid[r + i][c].changeCoordinates(10,10);
				grid[r + i][c] = new GridEntity(r + i, c);
				System.out.println("A ninja has been killed");
				return;
			} else if (grid[r + i][c] instanceof Room) {
				System.out
						.println("Unfortunately, you hit a room causing your bullet to stop in its tracks");
				return;
			}
		}
		System.out.println("Unfortunately, you hit nothing at all");
	}
	
	/**
	 * This method shoots left and determines if the bullet shot was a hit or miss. 
	 */

	private void shootLeft() {
		int r = player.getRow();
		GridEntity[] targets = getPossibleTargets(player, 270);
		for (int i = 1; i <= targets.length; i++) {
			if (grid[r][targets.length - i] instanceof Ninja) {
				 grid[r][targets.length-i].changeCoordinates(10,10);
				grid[r][targets.length - i] = new GridEntity(r, targets.length
						- i);
				System.out.println("A ninja has been killed");
				return;
			} else if (grid[r][targets.length - i] instanceof Room) {
				System.out
						.println("Unfortunately, you hit a room causing your bullet to stop in its tracks");
				return;
			}
		}
		System.out.println("Unfortunately, you hit nothing at all");
	}


	/**
	 * Takes player input to see where they want to move and it checks if it is 
	 * a valid location. If so, you move to that spot. Otherwise, gives an invalid
	 * message. It also checks if you moved into a powerup. If so, it gives the 
	 * player his powerup. Also, if the player is in a room, it will only accept
	 * commands to move up (out of the room[GameEngine checks this]). It will not 
	 * allow the player to move any other direction while in a room.
	 * @param direction
	 * @return
	 */
	
	private int movePlayer(int direction) {
		int r = player.getRow();
		int c = player.getCol();
		boolean room = checkifInRoom();
		if (direction == 5) {
			manualRespawn();
			return 1;
		}
		if (direction == 1 && r != 0 && !(grid[r - 1][c] instanceof Room)
				&& !(invulTurnCount > 0 && grid[r - 1][c] instanceof Ninja)) {
			invulP();
			return playerMovement(r - 1, c);
		} else if (direction == 4 && c != 8
				&& !(grid[r][c + 1] instanceof Room) && room == false
				&& !(invulTurnCount > 0 && grid[r][c + 1] instanceof Ninja)) {
			invulP();
			return playerMovement(r, c + 1);
		} else if (direction == 2 && r != 8 & room == false
				&& !(invulTurnCount > 0 && grid[r + 1][c] instanceof Ninja)) {
			invulP();
			return playerMovement(r + 1, c);
		} else if (direction == 3 && c != 0
				&& !(grid[r][c - 1] instanceof Room) && room == false
				&& !(invulTurnCount > 0 && grid[r][c - 1] instanceof Ninja)) {
			invulP();
			return playerMovement(r, c - 1);
		} else {
			System.out.print("Cannot go in that direction, try again \n");
			return 2;
		}

	}
	
	/**
	 * This method deals with the @param Invincibility powerUp. It keeps track of the number of turns left being 
	 * invincible and prints turns left. It then calls to the {@link Player} class to change the value of the field for 
	 * the {@link Player}. 
	 */

	private void invulP() {
		if (invulUse == true && invulTurnCount > 0) {
			System.out.println(invulTurnCount + " turns of invincibility left");
			invulTurnCount--;

		} else if (invulUse == true)
			player.changeInvul();
	}

	/**
	 * This method is only used because @param movePlayerUp needs another check because it needs to check
	 * if the {@link Player} needs to move out of a room. If so, it has to resotre that @param Room.
	 * @param r
	 * @param c
	 * @return 1
	 */

	private int playerMovement(int r, int c) {
		if (grid[r][c] instanceof PowerUps) {
			if (r == rad.getRow() && c == rad.getCol()) {
				grid[r][c].changeOrigRep("*");
				useRad();
			} else if (r == addBull.getRow() && c == addBull.getCol()) {
				grid[r][c].changeOrigRep("*");
				useBull();
				player.ammoUp();
			} else if (r == invul.getRow() && c == invul.getCol()) {
				player.changeInvul();
				grid[r][c].changeOrigRep("*");
				invulTurnCount = 4;
				useInvul();
			}
		}
		replace(player, grid[r][c]);
		shadow();
		return 1;
	}
	
	/**
	 * This method checks for @param instanceof {@link Player} because if {@link Player} is in 
	 * a @param Room, that @param Room technically does not exist in the grid since {@link Player} 
	 * is in that spot, but only cares for that if {@link Player} is in the @param Room while 
	 * also being in a position next to the @param Ninja. 
	 * 
	 * @param possLoc
	 * @param ninja
	 * @return
	 */

	private boolean ninjaAction(GridEntity[] possLoc, Ninja ninja) {
		int randomRoom = rd.nextInt(possLoc.length);
		int r = possLoc[randomRoom].getRow();
		int c = possLoc[randomRoom].getCol();
		int ninRoomCountAround = 0;
		/**
		 * This cheks if the ninja is moving into a room, and if so, will call itself again
		 * to make the ninja move to another direction via recursion. Also checks if 
		 * ninja is in a room, to call itself again.
		 */
		for (int i = 0; i < possLoc.length; i++) {
			if (possLoc[i] instanceof Player && checkifInRoom() == false) {
				if (invulTurnCount > 0) {
					ninja.stab(player);
					System.out.println("a ninja attempted to stab you, you're invincibility "
							+ "blocked it");
					return true;
				} else {
					ninja.stab(player);
					System.out.println("you've been stabbed by a ninja");
					playerRespawn();
					return true;
				}
			}

			if (possLoc[i] instanceof Ninja || possLoc[i] instanceof Room
					|| possLoc[i] instanceof Player)
				ninRoomCountAround++;
		}
		if (ninRoomCountAround == possLoc.length)
			return false;
		else if (possLoc[randomRoom] instanceof Room
				|| possLoc[randomRoom] instanceof Ninja
				|| possLoc[randomRoom] instanceof Player) {
			return ninjaAction(possLoc, ninja);

		} else {
			replace(ninja, grid[r][c]);
			return true;
		}
	}

	/**
	 * This method checks if each ninja is still in the grid, if so it calls to @param NinjaAction, which is a boolean, so
	 * if it returns false, it means that specific  ninja has not moved yet, so we make it so that every 
	 *  ninja gets to move. This is important for when a ninja might be cornered by other @param ninjas that might move after him. 
	 * All @param ninjas get to move.    
	 * 
	 */

	public void NinjaActions() {
		   boolean n1Action = false;
		    boolean n2Action = false;
		    boolean n3Action = false;
		    boolean n4Action = false;
		    boolean n5Action = false;
		    boolean n6Action = false;
		    

		    if (ninja1.getRow()<9 && ninja1.getCol() <9)
		     n1Action = ninjaAction(getAdjacentGridLocations(ninja1), ninja1);
		    if (ninja2.getRow()<9 && ninja2.getCol() <9)
		     n2Action = ninjaAction(getAdjacentGridLocations(ninja2), ninja2);
		    if (ninja3.getRow()<9 && ninja3.getCol() <9)
		     n3Action = ninjaAction(getAdjacentGridLocations(ninja3), ninja3);
		    if (ninja4.getRow()<9 && ninja4.getCol() <9)
		     n4Action = ninjaAction(getAdjacentGridLocations(ninja4), ninja4);
		    if (ninja5.getRow()<9 && ninja5.getCol() <9)
		     n5Action = ninjaAction(getAdjacentGridLocations(ninja5), ninja5);
		    if (ninja6.getRow()<9 && ninja6.getCol() <9)
		     n6Action = ninjaAction(getAdjacentGridLocations(ninja6), ninja6);
		    
		    while (n1Action == false
		      && ninja1.getRow()<9 && ninja1.getCol() <9) {
		     n1Action = ninjaAction(getAdjacentGridLocations(ninja1), ninja1);
		    }
		    while (n2Action == false
		      && ninja2.getRow()<9 && ninja2.getCol() <9) {
		     n2Action = ninjaAction(getAdjacentGridLocations(ninja2), ninja2);
		    }
		    while (n3Action == false
		      && ninja3.getRow()<9 && ninja3.getCol() <9) {
		     n3Action = ninjaAction(getAdjacentGridLocations(ninja3), ninja3);
		    }
		    while (n4Action == false
		      && ninja4.getRow()<9 && ninja4.getCol() <9) {
		     n4Action = ninjaAction(getAdjacentGridLocations(ninja4), ninja4);
		    }
		    while (n5Action == false
		      && ninja5.getRow()<9 && ninja5.getCol() <9) {
		     n5Action = ninjaAction(getAdjacentGridLocations(ninja5), ninja5);
		    }
		    while (n6Action == false
		      && ninja6.getRow()<9 && ninja6.getCol() <9) {
		     n6Action = ninjaAction(getAdjacentGridLocations(ninja6), ninja6);

		    }

		 }


	/** 
	 * This happens after every time the {@link Player} has moved into a room to see 
	 * if the briefcase is inside. It checks if the coordinates of where the 
	 * briefcase was placed matches the current coordinates of the player 
	 * inside the room. Returns true if the game is won. Otherwise, it just 
	 * tells the player that it does not contain the @param briefcase. 
	 * @return true if the {@link Player} coordinates match the @param briefcase coordinates
	 * @return false if the {@link Player} coordinates do not the @param briefcase coordinates
	 * 
	 * 
	 */
	
	public boolean checkWinCondition() {
		if (player.getRow() == briefcaseRow && player.getCol() == briefcaseCol) {
			
			System.out.println("You found the briefcase. Congratulations!");

			return true;
		} else {
			Room[] roomArray = new Room[] { room1, room2, room3, room4, room5,
					room6, room7, room8, room9 };
			for (int i = 0; i < roomArray.length; i++) {
				if (player.getRow() == roomArray[i].getRow()
						&& player.getCol() == roomArray[i].getCol()) {
					System.out
							.println("Sorry, there appears to be nothing here");
				}
			}
			return false;
		}
	}

	/**
	 * This checks if the {@link Player} is in a room. If so, it will return the room
	 * they are in so it can be restored. Else, it returns 'null'.
	 * @param player
	 * @return room 
	 */
	
	private boolean checkifInRoom() {
		Room[] roomArray = new Room[] { room1, room2, room3, room4, room5,
				room6, room7, room8, room9 };
		boolean room = false;
		for (int i = 0; i < roomArray.length; i++)
			if (player.getRow() == roomArray[i].getRow()
					&& player.getCol() == roomArray[i].getCol()) {
				room = true;
				break;
			}
		return room;

	}
	
	/**
	 * This method calls to the {@link Player} class and the method @param returnLives() to return the number of lives
	 * @return lives
	 */

	public int returnLives() {

		return player.returnLives();
	}
	
	/**
	 * This method calls to the {@link Player} class and the method @param returnAmmo() to return the number of ammo
	 * @return ammo
	 */

	public int returnAmmo() {
		return player.getAmmo();
	}

	/**
	 * Only replaces orig2 rep, because that will usually be a grid entity in 
	 * general or a powerup, which will be restored by restorePowerUps anyways.
	 * Ninja or Player will always be called.
	 * @param entity1
	 * @param entity2
	 */
	
	private void replace(GridEntity entity1, GridEntity entity2) {
		int e1R = entity1.getRow();
		int e1C = entity1.getCol();
		int e2R = entity2.getRow();
		int e2C = entity2.getCol();
		
		/**
		 * makes sure the Invincibility powerUp does not kill ninjas when 
		 * being in the same square as them, i.e. touching them, like the
		 * Mario Star power up.
		 */
		
		if (entity1 instanceof Player && entity2 instanceof Ninja) {
			System.out
					.println("Unfortunately you just walked straight into a ninja. He stabbed you");
			ninja1.stab(player);
			playerRespawn();
			return;
		}
		grid[e1R][e1C] = entity2;
		grid[e2R][e2C] = entity1;
		if (grid[e1R][e1C] instanceof Room
				|| grid[e1R][e1C] instanceof PowerUps) {
			entity2 = new GridEntity(e1R, e1C, " ");
			grid[e1R][e1C] = entity2;
		}

		entity1.changeCoordinates(e2R, e2C);
		entity2.changeCoordinates(e1R, e1C);
		entity2.changeOrigRep(" ");
	}
	
	/**
	 * This method calls to the {@link Player} class and the method @param returnAlive() to return 
	 * if the {@link Player} is alive
	 * @return alive
	 */

	public boolean returnAlive() {
		return player.returnAlive();
	}
	
	/**
	 * This method is used to restore a @param Room in the grid after they have been cleared from the grid, because 
	 * the @param replace method clears the room from the grid after the {@link Player} moves off the @param Room 
	 * coordinates. This is similar to how @param powerUps are restored.    
	 */

	public void restoreRoom() {
		if (!(grid[room1.getRow()][room1.getCol()] instanceof Player)) {

			grid[room1.getRow()][room1.getCol()] = new Room(room1.getRow(),
					room1.getCol());

		}
		if (!(grid[room2.getRow()][room2.getCol()] instanceof Player)) {

			grid[room2.getRow()][room2.getCol()] = new Room(room2.getRow(),
					room2.getCol());
		}
		if (!(grid[room3.getRow()][room3.getCol()] instanceof Player)) {

			grid[room3.getRow()][room3.getCol()] = new Room(room3.getRow(),
					room3.getCol());
		}
		if (!(grid[room4.getRow()][room4.getCol()] instanceof Player)) {

			grid[room4.getRow()][room4.getCol()] = new Room(room4.getRow(),
					room4.getCol());
		}
		if (!(grid[room5.getRow()][room5.getCol()] instanceof Player)) {

			grid[room5.getRow()][room5.getCol()] = new Room(room5.getRow(),
					room5.getCol());
		}
		if (!(grid[room6.getRow()][room6.getCol()] instanceof Player)) {

			grid[room6.getRow()][room6.getCol()] = new Room(room6.getRow(),
					room6.getCol());
		}
		if (!(grid[room7.getRow()][room7.getCol()] instanceof Player)) {

			grid[room7.getRow()][room7.getCol()] = new Room(room7.getRow(),
					room7.getCol());
		}
		if (!(grid[room8.getRow()][room8.getCol()] instanceof Player)) {

			grid[room8.getRow()][room8.getCol()] = new Room(room8.getRow(),
					room8.getCol());
		}
		if (!(grid[room9.getRow()][room9.getCol()] instanceof Player)) {

			grid[room9.getRow()][room9.getCol()] = new Room(room9.getRow(),
					room9.getCol());
		}
		if (radUse == true
				&& !(grid[briefcaseRow][briefcaseCol] instanceof Player)) {
			grid[briefcaseRow][briefcaseCol].changeOrigRep("B");
			grid[briefcaseRow][briefcaseCol].changeRepresentation("B");
		}
		if (!(grid[briefcaseRow][briefcaseCol] instanceof Player))
			grid[briefcaseRow][briefcaseCol].changeOrigRep("B");
	}
	
	/**
	 * This method hides everything except the @param Player and @param Rooms when in the normal game mode, it changes
	 * their representations to just a '*', except the @param Player and @param Rooms. 
	 */

	private void shadow() {
		for (int r = 0; r <= 8; r++) {
			for (int c = 0; c <= 8; c++) {
				if (!(grid[r][c] instanceof Player)
						&& !(grid[r][c] instanceof Room))
					grid[r][c].changeRepresentation("*");
			}
		}

	}

}