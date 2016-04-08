/**
 * HOW TO PLAY:
 * 1) Once the game is running, the game will display a logo and a large body of 
 * 	text detailing the nature of the game. You will be prompted to specify a 
 * 	game mode.
 * 2) Choose a game mode. 
 *	PVP allows two humans to enter move commands until one 
 * 	is defeated.
 *	PVA allows a human player to challenge an Agent. Human is Player1 and 
 *		takes the first turn.
 *	AVA displays the outcome of two agents playing against each other.
 *
 *	ENTER A MOVE:
 *	3) If PVP or PVA is selected, enter a move in the format: 
 *		row column [ direction_of_movement | direction_of_rotation ]
 *			-there is a space between each of the three commands
 *			-row and column are integers 1-4, indicating the location of the piece intended to move.
 *			-the third argument can be EITHER
 *				+ cardinal direction (up, down, left, right, u, d, l, r)
 *				+ rotation (cc, c) where cc represents counter-clockwise and c represents clockwise.
 *		+Example: 	Rotate P1 to face right. = "4 1 c"
 *					Move P1 up one space. = "4 1 u"
 */

/**
 * @author Joshua Boone, Chase Perdue
 *
 */
public class AdversarialGame 
{
	
	/**
	 * Main method that holds the game loop and instances of the main components of the program (MVC).
	 */
	public static void main(String[] args) 
	{
		int 		gameMode 	= -1; 	// 0 = PVA | 1 = PVP | 2 = | AVA
		int 		row; 				// 0-3
		int 		col; 				// 0-3
		int 		dir; 				// 0 = up | 1 = right | 2 = down | 3 = left
		boolean 	rotation; 			// true = clockwise | false = counter-clockwise
		
		String 		userInput;
		String 		temp;
		String[] 	moveSet;
		
		Model 		model 		= new Model();
		View 		view 		= new View();
		Controller 	controller 	= new Controller();
		
		// Begin
		view.printTitle();
		
		// Get user selection for game mode.
		while (gameMode == -1)
		{
			userInput = controller.getInput();
			
			if      (userInput.equalsIgnoreCase("PVA"))
				gameMode = 0;
			else if (userInput.equalsIgnoreCase("PVP"))
				gameMode = 1;
			else if (userInput.equalsIgnoreCase("AVA"))
				gameMode = 2;
			 else if (userInput.equalsIgnoreCase("EXIT") || userInput.equalsIgnoreCase("E"))
	                System.exit(0);
			else
			{
				view.printErrorScreen("ERROR : Invalid Game Mode.");
			}
		}
		
		// Begin Game Loop
		while (true)
		{
			// Check for Goal state
			if (model.getGameState().isGoalState())
			{
				view.printGameScreen(model.getGameState(), model.getNumOfMoves());
			 	view.printEndScreen(model.getGameState(), model.getNumOfMoves());
			 	break;
			}
			
			// Print current Game State, Player Turn, number of moves, and instructions for user input
			view.printGameScreen(model.getGameState(), model.getNumOfMoves());
			
			switch (gameMode)
			{
			case 0 : // PVA
				
				// Check if computer's turn
				if (!model.getGameState().isCurrentTurn())
				{
					model.computerMove();
					break;
				}
				// Else move on to next case
				
			case 1 : // PVP
				
				// Get user input
				while (true)
				{
					userInput = controller.getInput();
					
					// Parse user input to get move data. Expected format : (row column direction|rotation) = (int int String)
					moveSet = userInput.split("\\s");
					
					try 
					{ 
						row = Integer.parseInt(moveSet[0]); 
						row--;
						col = Integer.parseInt(moveSet[1]);
						col--;
						userInput = moveSet[2];
				    }
					catch(NumberFormatException e) 
					{ 
				        view.printErrorScreen("Invalid input.");
				        continue;
				    } 
					catch(NullPointerException e) 
					{
						view.printErrorScreen("Invalid input.");
				        continue;
				    }
					
					if (userInput.equalsIgnoreCase("c"))
					{
						rotation = true;
						temp = model.movePiece(row, col, rotation);
						
						if (temp.equals("Accepted"))
							break;
						else
						{
							view.printErrorScreen(temp);
							continue;
						}
					}
					else if (userInput.equalsIgnoreCase("cc"))
					{
						rotation = false;
						temp = model.movePiece(row, col, rotation);
						
						if (temp.equals("Accepted"))
							break;
						else
						{
							view.printErrorScreen(temp);
							continue;
						}
					}
					else if (userInput.equalsIgnoreCase("up") || userInput.equalsIgnoreCase("u"))
					{
						dir = 0;
						model.movePiece(row, col, dir);
						break;
					}
					else if (userInput.equalsIgnoreCase("right") || userInput.equalsIgnoreCase("r"))
					{
						dir = 1;
						model.movePiece(row, col, dir);
						break;
					}
					else if (userInput.equalsIgnoreCase("down") || userInput.equalsIgnoreCase("d"))
					{
						dir = 2;
						model.movePiece(row, col, dir);
						break;
					}
					else if (userInput.equalsIgnoreCase("left") || userInput.equalsIgnoreCase("l"))
					{
						dir = 3;
						model.movePiece(row, col, dir);
						break;
					}
					else
					{
						view.printErrorScreen("Invalid input.");
				        continue;
					}
				}
				break;
				
			case 2 : // AVA
				
				model.computerMove();
				break;
				
			default :
				
				view.printErrorScreen("ERROR: Unexpected game mode failure!");
		        System.exit(0);
		        
			} // End of switch
		} // End of Game Loop
	} // End of main
} // End of program
