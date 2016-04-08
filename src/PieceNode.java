import java.util.ArrayList;

/**
 * PieceNode is a node data structure that holds a Piece, whether it's matching a row or column, its parent node, a list of children nodes to expand, and the total cost so far for its path.
 */

/**
 * @author Joshua Boone, Chase Perdue
 *
 */
public class PieceNode 
{
	private Piece 					piece;
	private boolean 				matchRow;	// Matching row = true | Matching column = false
	private PieceNode 				parent;
	private ArrayList<Piece> 		children;
	private int 					totalCost;
	
	public PieceNode() 
	{
		piece 		= null;
		matchRow 	= false;
		parent 		= null;
		children 	= null;
		totalCost	= 0;
	}
	
	public PieceNode(Piece p, boolean mr, PieceNode pn, ArrayList<Piece> c, int tc)
	{
		piece 		= p;
		matchRow 	= mr;
		parent 		= pn;
		children 	= c;
		totalCost	= tc;
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param p is the PieceNode to set 
	 */
	public PieceNode(PieceNode p)
	{
		piece 		= new Piece(p.getPiece());
		matchRow 	= p.isMatchRow();
		parent 		= new PieceNode(p.getParent());
		children 	= new ArrayList<Piece>();
		
		for (Piece temp : p.getChildren())
		{
			children.add(new Piece(temp));
		}
		
		totalCost	= p.getTotalCost();
	}
	
	/**
 	* Calculates the path cost to get to the passed Piece.
	* The distance between pieces used in the goal path are added to the cost at a higher rate, as they have a chance to be blocked.
	* Neccissary rotations along the goal path are added to the cost.
	* Pieces obscuring the goal path are added to the cost, as they must be moved to complete the path.
 	*
 	* @Param node       Piece to path to.
	* @Param obstacles  pieces that could be inbetween the path pieces.
 	* 
 	* @Return Int path cost to get to the node.
 	*/
	public int calcChildCost(Piece node, ArrayList<Piece> obstacles)
	{
		int cost = 0;

		// Generate cost of moving the piece
		// Find difference between rows
		if (matchRow)
		{
			// If columns match, remove from this search branch
			if (piece.getCol() == node.getCol())
				return -1;
			
			// Add the difference of the columns to the cost
			cost = Math.abs(piece.getRow() - node.getRow());
			
			// Add move cost for each piece in between parent and child
			for (Piece temp : obstacles)
			{
				if (temp != null &&
					Math.min(piece.getCol(), node.getCol()) <= temp.getCol() && 
					Math.max(piece.getCol(), node.getCol()) >= temp.getCol())
					cost++;
			}
		}
		
		// Find difference between columns
		else
		{
			// If rows match, remove from this search branch
			if (piece.getRow() == node.getRow())
				return -1;
			
			// Add the difference of the rows to the cost
			cost = Math.abs(piece.getCol() - node.getCol());

			// Add move cost for each piece in between parent and child
			for (Piece temp : obstacles)
			{
				if (temp != null &&
					Math.min(piece.getRow(), node.getRow()) < temp.getRow() && 
					Math.max(piece.getRow(), node.getRow()) > temp.getRow())
					cost++;
			}
		}

		cost *= 105; // Weighted cost of moving a piece

		// Generate cost of rotating the piece
		if (piece.getPiece() == '1' || piece.getPiece() == '2') // Check if piece is a player piece
		{
			if (matchRow)
			{
				// 180 degree turn
				if ((piece.getDir() == 1 && piece.getCol() > node.getCol()) || 
					(piece.getDir() == 3 && piece.getCol() < node.getCol()))
					cost += 200;
				// 90 degree turn
				else if (piece.getDir() == 0 || piece.getDir() == 2)
					cost += 100;
			}
			else
			{
				// 180 degree turn
				if ((piece.getDir() == 0 && piece.getRow() < node.getRow()) || 
					(piece.getDir() == 2 && piece.getRow() > node.getRow()))
					cost += 200;
				// 90 degree turn
				else if (piece.getDir() == 1 || piece.getDir() == 3)
					cost += 100;
			}
		}
		else
		{
			// Check mirror orientation
			if (((parent.getPiece().getRow() < node.getRow()) == 
				 (parent.getPiece().getCol() < node.getCol())) 
				 != (piece.getDir() == 0))
				cost += 100;
		}
		
		return cost;
	}

	/**
	 * @return the piece
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * @param piece the piece to set
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * @return the matchRow
	 */
	public boolean isMatchRow() {
		return matchRow;
	}

	/**
	 * @param matchRow the matchRow to set
	 */
	public void setMatchRow(boolean matchRow) {
		this.matchRow = matchRow;
	}

	/**
	 * @return the parent
	 */
	public PieceNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(PieceNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public ArrayList<Piece> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<Piece> children) {
		this.children = children;
	}

	/**
	 * @return the totalCost
	 */
	public int getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

}
