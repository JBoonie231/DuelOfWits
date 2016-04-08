import java.util.ArrayList;

/**
 * The Model holds all the game logic needed to run the game.
 */

/**
 * @author Joshua Boone, Chase Perdue
 *
 */
public class Model 
{
	private GameState 	gameState;
	private int 		numOfMoves;
	
	
	public Model()
	{
		gameState = new GameState();
		numOfMoves = 0;
	}
	
	/**
 	* Moves a piece in the direction specified by the parameters.
 	*
 	* @Param r   Row of the piece.
 	* @Param c   Column of the piece.
 	* @Param dir Direction to be moved.
 	* 
 	* @Return String error or success message.
 	*/
	public String movePiece(int r, int c, int dir)
	{
		// Check if piece exists, was previously moved, or is opponent
		if (gameState.getGameState()[r][c] == null || gameState.getGameState()[r][c].equals(gameState.getLastMoved()) || 
		   ((gameState.getGameState()[r][c].getPiece() == '1' || gameState.getGameState()[r][c].getPiece() == '2') && (gameState.getGameState()[r][c].getPiece() == '1') != gameState.isCurrentTurn()))
			return "Invalid location.";
			
		// Move piece
		
		switch (dir)
		{
		case 0 : 
			//up move 
			if (r > 0 && gameState.getGameState()[r-1][c] == null)
			{
				gameState.setCurrentTurn(!gameState.isCurrentTurn());
				gameState.setLastMoved(gameState.getGameState()[r][c]);
				gameState.getGameState()[r][c].setRow(gameState.getGameState()[r][c].getRow() - 1);
				gameState.getGameState()[r-1][c] = gameState.getLastMoved();
				gameState.getGameState()[r][c] = null;
				
				break;
			}
			else
				return "Invalid direction.";
			
		case 1 :
			//right move 
			if (c < 3 && gameState.getGameState()[r][c+1] == null)
			{
				gameState.setCurrentTurn(!gameState.isCurrentTurn());
				gameState.setLastMoved(gameState.getGameState()[r][c]);
				gameState.getGameState()[r][c].setCol(gameState.getGameState()[r][c].getCol() + 1);
				gameState.getGameState()[r][c+1] = gameState.getLastMoved();
				gameState.getGameState()[r][c] = null;
				
				break;
			}
			else
				return "Invalid direction.";
			
		case 2 :	
			//down move 
			if (r < 3 && gameState.getGameState()[r+1][c] == null)
			{
				gameState.setCurrentTurn(!gameState.isCurrentTurn());
				gameState.setLastMoved(gameState.getGameState()[r][c]);
				gameState.getGameState()[r][c].setRow(gameState.getGameState()[r][c].getRow() + 1);
				gameState.getGameState()[r+1][c] = gameState.getLastMoved();
				gameState.getGameState()[r][c] = null;
				
				break;
			}
			else
				return "Invalid direction.";
			
		case 3:	
			//left move 
			if (c > 0 && gameState.getGameState()[r][c-1] == null)
			{
				gameState.setCurrentTurn(!gameState.isCurrentTurn());
				gameState.setLastMoved(gameState.getGameState()[r][c]);
				gameState.getGameState()[r][c].setCol(gameState.getGameState()[r][c].getCol() - 1);
				gameState.getGameState()[r][c-1] = gameState.getLastMoved();
				gameState.getGameState()[r][c] = null;
				
				break;
			}
			else
				return "Invalid direction.";
			
		default :
			return "Invalid direction.";
		}
		
		
		numOfMoves++;
		return "Accepted";
	}

	/**
 	* Rotates a piece in the direction specified by the parameters.
 	*
 	* @Param r   Row of the piece.
 	* @Param c   Column of the piece.
 	* @Param rot Rotation to be rotated.
 	* 
 	* @Return String error or success message.
 	*/

	public String movePiece(int r, int c, boolean rot)
	{
		// Check if piece exists, was previously moved, or is opponent
		if (gameState.getGameState()[r][c] == null || gameState.getGameState()[r][c].equals(gameState.getLastMoved()) || 
		   ((gameState.getGameState()[r][c].getPiece() == '1' || gameState.getGameState()[r][c].getPiece() == '2') && (gameState.getGameState()[r][c].getPiece() == '1') != gameState.isCurrentTurn()))
			return "Invalid location.";
		
		// rotation move
		if (gameState.getGameState()[r][c].getPiece() == 'M')
		{
			gameState.setCurrentTurn(!gameState.isCurrentTurn());
			gameState.setLastMoved(gameState.getGameState()[r][c]);
			gameState.getLastMoved().setDir((gameState.getLastMoved().getDir() + 1) % 2);
		}
		else
		{
			if (rot)
			{
				gameState.setCurrentTurn(!gameState.isCurrentTurn());
				gameState.setLastMoved(gameState.getGameState()[r][c]);
				gameState.getLastMoved().setDir((gameState.getLastMoved().getDir() + 1) % 4);
			}
			else
			{
				gameState.setCurrentTurn(!gameState.isCurrentTurn());
				gameState.setLastMoved(gameState.getGameState()[r][c]);
				gameState.getLastMoved().setDir((gameState.getLastMoved().getDir() + 3) % 4);
			}
		}
		
		
		numOfMoves++;
		return "Accepted";
	}
	
	public void computerMove()
	{
		ArrayList<GameState> bestMoves;
		
		bestMoves = alphaBetaPrune();
		
		gameState = bestMoves.get((int)(bestMoves.size() * Math.random()));
		
		numOfMoves++;
	}
	
	/**
 	* Alpha Beta Prune goes through game ply expanding gamestates and calling the hueristic to determine the next move.
	* Determinied poor paths are pruned from expansion.
 	* 
 	* @Return ArrayList<GameState> List of best moves.
 	*/
	private ArrayList<GameState> alphaBetaPrune()
	{
		int alpha = -100000; // act like negative infinity
		int beta  =  100000; // act like infinity
		int tempBeta;
		
		ArrayList<GameState> bestMoves;
		ArrayList<GameState> min;
		ArrayList<GameState> max;
		
		bestMoves = new ArrayList<GameState>();
		
		// Populate min ('our' turn) level of alpha-beta prune tree
		min = gameState.getAvailableMoveStates();
		
		// Populate max branches one at a time (opponent turn)
		for (GameState gsMIN : min)
		{
			if (gsMIN.isGoalState())
			{
				bestMoves.clear();
				bestMoves.add(gsMIN);
				return bestMoves;
			}
			
			max   = gsMIN.getAvailableMoveStates();
			beta  = 100000; // act like infinity
			
			// Calculate current max branch leaf values
			for (GameState gsMAX : max)
			{
				tempBeta = heuristic(gsMAX);
				
				if (beta > tempBeta)
					beta = tempBeta;
				
				// Prune
				if (beta <= alpha)
				{
					Counter.counter++;
					break;
				}
			}
			
			// Replace alpha if beta is better, and replace bestMoves list with game state
			if (beta > alpha)
			{
				alpha = beta;
				bestMoves.clear();
				bestMoves.add(gsMIN);
			}
			// If beta is an equally good choice, add game state to bestMoves
			else if (beta == alpha)
			{
				bestMoves.add(gsMIN);
			}
		}
		
		return bestMoves;
	}
	
	/**
 	* Heuristic for the game state evaluating high for favorable moves.  Opponent subHeuristic evaluating to 0 defaults to very low value to ensure low chance of being chosen.
 	*
 	* @Param gs to find heuristic value of.
 	* 
 	* @Return Int Heuristic cost.
 	*/
	private int heuristic(GameState gs)
	{
		int cost = 0;

		if (subHeuristic(gs, true) == 0)
			cost = -100000;
		else
			cost = subHeuristic(gs, true) - subHeuristic(gs, false);
		
		return cost;
	}
	
	/**
 	* SubHeuristic estimates the shortest moves neccessary to reach the goal.
	* The distance between pieces used in the goal path are added to the cost at a higher rate, as they have a chance to be blocked.
	* Neccissary rotations along the goal path are added to the cost.
	* Pieces obscuring the goal path are added to the cost, as they must be moved to complete the path.
	*
	* The Various paths are calculated using a least cost search through the available peices.  Expanded childen are sorted into a list to expand.  Once the goal is reached, the cost is returned.
 	*
 	* @Param gs       to find heuristic value of.
	* @Param opponent determins where to start the expansion list.
 	* 
 	* @Return Int subHeuristic cost.
 	*/
	private int subHeuristic(GameState gs, boolean opponent)
	{
		int 					cost 		= 0;
		ArrayList<Piece> 		tempPList;
		Piece 					tempPiece;
		PieceNode				tempPN;
		ArrayList<PieceNode> 	pieceNodesToExpand;
		ArrayList<Piece>[]		obstacles;
		
		tempPList 			= new ArrayList<Piece>();
		tempPiece 			= new Piece();
		pieceNodesToExpand 	= new ArrayList<PieceNode>();
		obstacles 			= new ArrayList[8];
		obstacles[0] 		= new ArrayList<Piece>(); // Row 1 contents
		obstacles[1] 		= new ArrayList<Piece>(); // Row 2 contents
		obstacles[2] 		= new ArrayList<Piece>(); // Row 3 contents
		obstacles[3] 		= new ArrayList<Piece>(); // Row 4 contents
		obstacles[4] 		= new ArrayList<Piece>(); // Column 1 contents
		obstacles[5] 		= new ArrayList<Piece>(); // Column 2 contents
		obstacles[6] 		= new ArrayList<Piece>(); // Column 3 contents
		obstacles[7] 		= new ArrayList<Piece>(); // Column 4 contents
		
		// Iterate through state to populate child list and initial node
		for(int r=0; r < gs.getGameState().length; r++)
		{
			for(int c=0; c < gs.getGameState()[r].length; c++)
			{
				if (gs.getGameState()[r][c] != null)
				{
					if ((gs.getGameState()[r][c].getPiece() == '1' && gs.isCurrentTurn() == !opponent) ||
						(gs.getGameState()[r][c].getPiece() == '2' && !gs.isCurrentTurn() == !opponent))
						tempPiece = gs.getGameState()[r][c];
					
					else
						tempPList.add(new Piece(gs.getGameState()[r][c]));
					
					obstacles[r].add(gs.getGameState()[r][c]);
					obstacles[c + 4].add(gs.getGameState()[r][c]);
				}
			}	
		}

		pieceNodesToExpand.add(new PieceNode(tempPiece, true, null, tempPList, 0));  // Search tree row first
		pieceNodesToExpand.add(new PieceNode(tempPiece, false, null, tempPList, 0)); // Search tree column first
		
		// Expand nodes using least cost method
		while (true)
		{
			tempPN = pieceNodesToExpand.get(0);
			pieceNodesToExpand.remove(0);
			
			// Check for goal condition
			if ((tempPN.getPiece().getPiece() == '1' && !gs.isCurrentTurn() == !opponent) || 
				(tempPN.getPiece().getPiece() == '2' && gs.isCurrentTurn() == !opponent))
			{
				cost = tempPN.getTotalCost();
				break;
			}
			
			// Expand the children in the node
			for (Piece temp : tempPN.getChildren())
			{
				// Get the cost for aligning child piece
				if (tempPN.isMatchRow())
					cost = tempPN.calcChildCost(temp, obstacles[tempPN.getPiece().getRow() - 1]);
				else
					cost = tempPN.calcChildCost(temp, obstacles[tempPN.getPiece().getCol() + 3]);
				
				if (cost == -1)
					continue;
				
				// Add to cost if piece was moved last turn and it's not the opponent's turn
				if (!opponent && temp == gs.getLastMoved())
					cost += 100;
					
				cost += tempPN.getTotalCost();
				// Sort expanded node into least cost list
				for (int x = 0; x < pieceNodesToExpand.size(); x++)
				{
					if (cost < pieceNodesToExpand.get(x).getTotalCost())
					{
						pieceNodesToExpand.add(x, new PieceNode(temp, !tempPN.isMatchRow(), tempPN, new ArrayList<Piece>(tempPN.getChildren()), cost));
						pieceNodesToExpand.get(x).getChildren().remove(temp);
						break;
					}
					if (x == pieceNodesToExpand.size() - 1)
					{
						pieceNodesToExpand.add(new PieceNode(temp, !tempPN.isMatchRow(), tempPN, new ArrayList<Piece>(tempPN.getChildren()), cost));
						pieceNodesToExpand.get(x+1).getChildren().remove(temp);
						break;
					}
				}
			}
		}
		
		return cost;
	}

	/**
	 * @return the gameState
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * @param gameState the gameState to set
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * @return the numOfMoves
	 */
	public int getNumOfMoves() {
		return numOfMoves;
	}

	/**
	 * @param numOfMoves the numOfMoves to set
	 */
	public void setNumOfMoves(int numOfMoves) {
		this.numOfMoves = numOfMoves;
	}
	
}
