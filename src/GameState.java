import java.util.ArrayList;

/**
 * GameState is a data structure that holds the state of the game board.
 * Each piece is represented in its place in an array by a Piece data structure.
 * The turn for the state is held here as well as the last piece moved.
 */

/**
 * @author Joshua Boone, Chase Perdue
 *
 */

/**
 * This constructor initializes this game state to the game's initial configuration.
 *
 */

public class GameState 
{
	private Piece[][] gameState;
	private boolean currentTurn; // Player1 = true | Player2 = false
	private Piece lastMoved;
	
	private Piece tempPiece;
	
	/**
	 * Initializes the beginning Game State
	 *     1   2   3   4
	 *   -----------------
	 * 1 |   |   |   |P2v|
	 *   -----------------
	 * 2 |   | / | \ |   |
	 *   -----------------
	 * 3 |   | \ | / |   |
	 *   -----------------
	 * 4 |P1^|   |   |   |
	 *   -----------------
	 */
	public GameState() 
	{
		gameState = new Piece[4][4];
		
		tempPiece = new Piece('1', 4, 1, 0); // Player 1
		gameState[3][0] = tempPiece;
		
		tempPiece = new Piece('2', 1, 4, 2); // Player 2
		gameState[0][3] = tempPiece;
		
		tempPiece = new Piece('M', 2, 2, 1); // Mirror 1
		gameState[1][1] = tempPiece;
		
		tempPiece = new Piece('M', 2, 3, 0); // Mirror 2
		gameState[1][2] = tempPiece;
		
		tempPiece = new Piece('M', 3, 2, 0); // Mirror 3
		gameState[2][1] = tempPiece;
		
		tempPiece = new Piece('M', 3, 3, 1); // Mirror 4
		gameState[2][2] = tempPiece;
		
		currentTurn = true;
		
		lastMoved = null;
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param gs the GameState to set 
	 */
	public GameState(GameState gs)
	{
		Piece[][] tempState = gs.getGameState();
		
		gameState = new Piece[4][4];
		
		for(int r=0; r < tempState.length; r++)
			for(int c=0; c < tempState[r].length; c++)
				if(tempState[r][c] != null)
					gameState[r][c] = new Piece(tempState[r][c]);
		
		currentTurn = gs.isCurrentTurn();
		
		lastMoved = gs.getLastMoved();
	}
	
	/**
	 * This method generates all valid states that can be reached from this state.
	 *
	 * @return the piece ArrayList<GameState> | List of possible move states.
	 */
	public ArrayList<GameState> getAvailableMoveStates()
	{
		ArrayList<GameState> availableGameStates = new ArrayList<GameState>();
		GameState tempState;
		
		// Iterate through current state
		for(int r=0; r < gameState.length; r++)
		{
			for(int c=0; c < gameState[r].length; c++)
			{
				// Check if piece exists, was previously moved, or is opponent
				if (gameState[r][c] == null || gameState[r][c].equals(lastMoved) || 
				    ((gameState[r][c].getPiece() == '1' || gameState[r][c].getPiece() == '2') && (gameState[r][c].getPiece() == '1') != currentTurn))
					continue;
				
				// Add rotation move(s)
				if (gameState[r][c].getPiece() == 'M')
				{
					tempState = new GameState(this);
					tempState.setCurrentTurn(!tempState.isCurrentTurn());
					tempState.setLastMoved(tempState.getGameState()[r][c]);
					tempState.getLastMoved().setDir((tempState.getLastMoved().getDir() + 1) % 2);
					
					availableGameStates.add(tempState);
				}
				else
				{
					tempState = new GameState(this);
					tempState.setCurrentTurn(!tempState.isCurrentTurn());
					tempState.setLastMoved(tempState.getGameState()[r][c]);
					tempState.getLastMoved().setDir((tempState.getLastMoved().getDir() + 1) % 4);
					
					availableGameStates.add(tempState);
					
					tempState = new GameState(this);
					tempState.setCurrentTurn(!tempState.isCurrentTurn());
					tempState.setLastMoved(tempState.getGameState()[r][c]);
					tempState.getLastMoved().setDir((tempState.getLastMoved().getDir() + 3) % 4);
					
					availableGameStates.add(tempState);
				}
					
				// Add motion move(s)
				
				//Add up move 
				if (r > 0 && gameState[r-1][c] == null)
				{
					tempState = new GameState(this);
					tempState.setCurrentTurn(!tempState.isCurrentTurn());
					tempState.setLastMoved(tempState.getGameState()[r][c]);
					tempState.getGameState()[r][c].setRow(tempState.getGameState()[r][c].getRow() - 1);
					tempState.getGameState()[r-1][c] = tempState.getLastMoved();
					tempState.getGameState()[r][c] = null;
					
					availableGameStates.add(tempState);
				}
					
				//Add down move 
				if (r < 3 && gameState[r+1][c] == null)
				{
					tempState = new GameState(this);
					tempState.setCurrentTurn(!tempState.isCurrentTurn());
					tempState.setLastMoved(tempState.getGameState()[r][c]);
					tempState.getGameState()[r][c].setRow(tempState.getGameState()[r][c].getRow() + 1);
					tempState.getGameState()[r+1][c] = tempState.getLastMoved();
					tempState.getGameState()[r][c] = null;
					
					availableGameStates.add(tempState);
				}
					
				//Add left move 
				if (c > 0 && gameState[r][c-1] == null)
				{
					tempState = new GameState(this);
					tempState.setCurrentTurn(!tempState.isCurrentTurn());
					tempState.setLastMoved(tempState.getGameState()[r][c]);
					tempState.getGameState()[r][c].setCol(tempState.getGameState()[r][c].getCol() - 1);
					tempState.getGameState()[r][c-1] = tempState.getLastMoved();
					tempState.getGameState()[r][c] = null;
					
					availableGameStates.add(tempState);
				}
					
				//Add right move 
				if (c < 3 && gameState[r][c+1] == null)
				{
					tempState = new GameState(this);
					tempState.setCurrentTurn(!tempState.isCurrentTurn());
					tempState.setLastMoved(tempState.getGameState()[r][c]);
					tempState.getGameState()[r][c].setCol(tempState.getGameState()[r][c].getCol() + 1);
					tempState.getGameState()[r][c+1] = tempState.getLastMoved();
					tempState.getGameState()[r][c] = null;
					
					availableGameStates.add(tempState);
				}
			}
		}
		
		return availableGameStates;
	}
	
	/**
	 * Checks if the previous player's turn resulted in a goal state.
	 *
	 * @return true if goal state for previous turn's player 
	 */
	public boolean isGoalState() 
	{
		int rowPos = 0;
		int colPos = 0;
		int rowInc = 0;
		int colInc = 0;
		
		int temp;
		
		// Iterate through current state
		for(int r=0; r < gameState.length; r++)
		{
			for(int c=0; c < gameState[r].length; c++)
			{
				// Set player position for goal check
				if(gameState[r][c] == null)
					continue;
				if ((gameState[r][c].getPiece() == '1' || gameState[r][c].getPiece() == '2') &&
				   ((gameState[r][c].getPiece() == '1') != currentTurn || (gameState[r][c].getPiece() == '2') == currentTurn))
				{
					rowPos = r;
					colPos = c;
					
					if (gameState[r][c].getDir() == 0)
						rowInc = -1;
					else if (gameState[r][c].getDir() == 1)
						colInc = 1;
					else if (gameState[r][c].getDir() == 2)
						rowInc = 1;
					else if (gameState[r][c].getDir() == 3)
						colInc = -1;
					
					break;
				}
			}
			if (rowInc != 0 || colInc != 0)
				break;
		}
		
		// Follow Goal line path until out-of-bounds or reach the other player
		while(true)
		{
			rowPos += rowInc;
			colPos += colInc;
			
			try 
			{ 
				// If empty space, keep going
				if (gameState[rowPos][colPos] == null)
					continue;
				
				// Check if other player reached
				if ((gameState[rowPos][colPos].getPiece() == '1' || gameState[rowPos][colPos].getPiece() == '2') &&
				   ((gameState[rowPos][colPos].getPiece() == '1') == currentTurn || (gameState[rowPos][colPos].getPiece() == '2') != currentTurn))
					return true;
				else if ((gameState[rowPos][colPos].getPiece() == '1' || gameState[rowPos][colPos].getPiece() == '2') &&
						((gameState[rowPos][colPos].getPiece() == '1') != currentTurn || (gameState[rowPos][colPos].getPiece() == '2') == currentTurn))
					return false;
				
				// Else we assume it is a mirror piece
				else
				{
					if (gameState[rowPos][colPos].getDir() == 1)
					{
						temp = rowInc;
						rowInc = -colInc;
						colInc = -temp;
					}
					else
					{
						temp = rowInc;
						rowInc = colInc;
						colInc = temp;
					}
				}
		    }
			catch(Exception e) 
			{
		        return false;
		    }
		}
	}

	//----------- Getters and Setters -----------\\
	
	/**
	 * @return the gameState
	 */
	public Piece[][] getGameState() {
		return gameState;
	}

	/**
	 * @param gameState the gameState to set
	 */
	public void setGameState(Piece[][] gameState) {
		this.gameState = gameState;
	}

	/**
	 * @return the currentTurn
	 */
	public boolean isCurrentTurn() {
		return currentTurn;
	}

	/**
	 * @param currentTurn the currentTurn to set
	 */
	public void setCurrentTurn(boolean currentTurn) {
		this.currentTurn = currentTurn;
	}

	/**
	 * @return the lastMoved
	 */
	public Piece getLastMoved() {
		return lastMoved;
	}

	/**
	 * @param lastMoved the lastMoved to set
	 */
	public void setLastMoved(Piece lastMoved) {
		this.lastMoved = lastMoved;
	}

	/**
	 * @return the tempPiece
	 */
	public Piece getTempPiece() {
		return tempPiece;
	}

	/**
	 * @param tempPiece the tempPiece to set
	 */
	public void setTempPiece(Piece tempPiece) {
		this.tempPiece = tempPiece;
	}

}
