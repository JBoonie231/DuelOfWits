/**
 * This is a class responsible for printing to the console.
 * The title and game instructions, the game board after a move takes place, any 
 * player vicrories as well as any errors that are caught.
 */

/**
 * @author Joshua Boone, Chase Perdue
 *
 */
public class View 
{
	String header = "\n";
	public View() 
	{
		
	}

	/**
 	 *  printTitle() prints the game logo and a 'fancy' boarder as well as
 	 *  the selectScreen information.
 	 *
 	 */
	public void printTitle()
	{
		//Border string
		for (int i = 0; i < 68; i++){
			header = "_" + header + "*";
		}
		
		System.out.println(header);
		String doubleSpace = "\n\n";
		System.out.println(doubleSpace);
		
		//Game logo
		System.out.println(	"\t\t" + " ____             _" 						+ "\n" +
							"\t\t" + "|  _ \\ _   _  ___| |" 					+ "\n" +
							"\t\t" + "| | | | | | |/ _ \\ |" 					+ "\n" +
							"\t\t" + "| |_| | |_| |  __/ |" 					+ "\n" +
							"\t\t" + "|____/ \\__,_|\\___|_|  ___ _" 			+ "\n" +
							"\t\t" + "  ___  / _| \\ \\      / (_) |_ ___" 		+ "\n" +
							"\t\t" + " / _ \\| |_   \\ \\ /\\ / /| | __/ __|" 	+ "\n" +
							"\t\t" + "| (_) |  _|   \\ V  V / | | |_\\__ \\" 	+ "\n" +
							"\t\t" + " \\___/|_|      \\_/\\_/  |_|\\__|___/" 	+ "\n" +
							"\t\t\t" + "(c) Boone and Perdue 2015");
		
			System.out.println(doubleSpace);
			System.out.println(header);
			printSelectScreen();
	}
	
	/**
 	 * printSelectScreen displays the game instructions as well as provides the user with a means to select the game mode.
 	 *
 	 */
	public void printSelectScreen()
	{
		System.out.println(	                                                         "\n" 	+ 
				"How To Play:                                                       \n\n" 	+
			"The game begins with two archers in opposite corners of the board and    \n" 	+
			"four reflecting pieces arranged in the center. The game is played by     \n" 	+
			"using one turn at a time to either rotate (your archer or a reflecting   \n" 	+ 
			"piece) ±90° OR move (your archer or a reflecting piece) one grid square  \n" 	+ 
			"in a valid cardinal direction.                                         \n\n" 	+  
			"The player that goes next is not allowed to alter the specific piece     \n" 	+  
			"that was altered in the preceding turn. At the end of a player’s turn,   \n" 	+ 
			"that player’s archer fires an arrow in the cardinal direction it is      \n" 	+ 
			"facing and is bounced off of reflecting pieces in its path. The goal is  \n" 	+ 
			"to rotate and arrange your archer and the reflecting pieces so that your \n" 	+ 
			"arrow reflects its way through the board and strikes your opponent’s     \n" 	+ 
			"archer, winning the game.                                              \n\n"
			);
		System.out.println(	
			"Who would you like to play against?                                    \n" +
				"\t1) Intelligent Agent (PVA)    or                                 \n" +
				"\t2) Human (PVP)    or                                             \n" +
				"\t3) watch an Agent play another Agent (AVA)					  \n\n" +
			"Please indicate your selection below with PVA or PVP followed by enter.\n" + 
			"('EXIT' will terminate the program)");
	}
	
	/**
 	 * printGameScreen displays the gameState after a move has taken place. The board is 
 	 * 	printed column by column by adding successive strings to a row string that eventually 
 	 * 	holds all information about that row before printing and assembling the next one.
 	 * @param gameState gameState holds the current confifuration of pieces on the board.
 	 * @param numOfMoves numOfMoves holds the move counter that allows the function to 
 	 * 	display which player has just moved or made the winning move.
 	 *
 	 */
	public void printGameScreen(GameState gameState, int numOfMoves)
	{
		//print border
		System.out.println(header);
		
		//print player name
		if((numOfMoves&1) == 1)
		{	//AND first bit of integer with 1 to get 1/true when even.
			System.out.println("\nPlayer 2's Turn");
		}
		else
		{	//is odd
			System.out.println("\nPlayer 1's Turn");
		}
		
		//print a small linebreak
		System.out.println("");
		
		//print top of game board
		System.out.println(
				"\t\t    1   2   3   4	\n" +
				"\t\t  -----------------"
				);
		
		//print body of board
		String start = "\t\t";
		String row = "";
		String wrap = "|";
		String end = "|\n";
		
		int r, c; r = c = 0;
		row = row + start + r + wrap;
		char p;
		int dir;
		while(r<4)
		{	//row = row.concat(start.concat(r.concat(wrap)));
			//start the row with tabs, the row number and a |.
			
			c = 0;
			row = "\t\t" + (r+1) + " |";
			while(c<4)
			{
				if (gameState.getGameState()[r][c] == null)
				{
					p = ' ';
					dir = 0;
				}
				else
				{
					p = gameState.getGameState()[r][c].getPiece();
					dir = gameState.getGameState()[r][c].getDir();
				}
				
				String cell = formatCell(p, dir);
				if (c != 4) //all but the last column
				{
					row = row + cell + wrap;					
				}
				else	//print last cell + boarder
				{
					row = row + cell + end;					
				}
				c++;
			}
			System.out.println(row);
			System.out.println("\t\t  -----------------");
			r++;
		}
		
		System.out.println("");	
		System.out.println("Enter: row(1-4) col(1-4) dir(up|right|left|down)|rot(c|cc)");
	}

	/**
 	 * printEndScreen displays the player responsible for the deciding move as well as 
 	 *	the number of moves the game took to complete. Leads into the printAgantStats function.
 	 * @param gameState gameState holds the current confifuration of pieces on the board.
 	 * @param numOfMoves numOfMoves holds the move counter that allows the function to 
 	 * 	display which player has just moved or made the winning move.
 	 *
 	 */
	public void printEndScreen(GameState gameState, int numOfMoves)
	{
		System.out.println("Congratulations!");
		if (gameState.isCurrentTurn())
			System.out.println("Player 2 WINS!");
		else
			System.out.println("Player 1 WINS!");
		
		System.out.println("Total number of moves : " + numOfMoves);
		printAgentStats();
	}
	
	/**
 	 * printErrorScreen displays all errors that are thrown by methods as the need arises.
 	 * @param error error string holds the error message to be displayed.
 	 *
 	 */
	public void printErrorScreen(String error)
	{
		 System.out.println("The program has encountered an error: " + error);
	}
	
	/**
 	 * printAgentStats displays the number of times a branch was pruned by the A-B algorithm.
 	 *
 	 */
	public void printAgentStats()
	{
		long branches = Counter.counter;
		System.out.println(branches + "  branches were pruned this round."); 
	}

	/**
 	 * formatCell is called by the printGameScreen function and sends the properly formatted 
 	 * 	contents of a cell of a single cell on the game board to be displayed.
 	 * @param p char p holds the piece identifier
  	 * @param dir int dir holds the current orientation of a piece
 	 *
 	 */
	private String formatCell(char p, int dir)
    {
		String cell = "";
		if(p != ' ')
		{
			if (p == 'M') 
			{
				if(dir == 0)
				{
					cell = " \\ ";
				}
				else 
				{
					cell = " / ";
				}
			}
			if (p == '1')
			{
				if (dir == 0)
				{
				cell = "P1^";
				}
				else if (dir == 1)
				{
					cell = "P1>";
				}
				else if (dir == 2)
				{
					cell = "P1v";
				}
				else if (dir == 3)
				{
					cell = "P1<";
				}										
			}
			if (p == '2')
			{
				if (dir == 0)
				{
				cell = "P2^";
				}
				else if (dir == 1)
				{
					cell = "P2>";
				}
				else if (dir == 2)
				{
					cell = "P2v";
				}
				else if (dir == 3)
				{
					cell = "P2<";
				}										
			}
		}
		else
		{
			cell = "   ";
		}
		return cell;
	}
}
