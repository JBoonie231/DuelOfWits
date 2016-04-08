/**
 * This is a data structure for the Piece.
 * It holds the name/identifier, row, column and the orientation for a specific piece
 * at any given point in time.
 */

/**
 * @author Joshua Boone, Chase Perdue
 *	
 */
public class Piece 
{
	private char piece;	// 1 = player1 | 2 = player2 | M = mirror | All other invalid
	private int  row;	// Row value 1-4
	private int  col;	// Column value 1-4
	private int  dir;	// 0 = up or negative | 1 = right or positive | 2 = down | 3 = left | All other invalid
	
	/**
	 *	Default constructor for piece.
	 */
	public Piece() 
	{
		piece = ' ';
		row = -1;
		col = -1;
		dir = -1;
	}
	
	/**
	 * Instantiates a piece object with parameter values.
	 * @param p the piece to set     | 1 = player1 | 2 = player2 | M = mirror | All other invalid
	 * @param r the row to set       | Row value 1-4
	 * @param c the column to set    | Column value 1-4
	 * @param d the direction to set | 0 = up or negative | 1 = right or positive | 2 = down | 3 = left | All other invalid
	 */
	public Piece(char p, int r, int c, int d) 
	{
		piece = p;
		row = r;
		col = c;
		dir = d;
	}

	/**
	 * Copy constructor
	 * 
	 * @param p the Piece to set 
	 */
	public Piece(Piece p) 
	{
		piece = p.getPiece();
		row = p.getRow();
		col = p.getCol();
		dir = p.getDir();
	}

	/**
	 * toString() completes a section of the board related to printing a column
	 */
	public String toString()
	{
		return "" + piece + row + col;
	}
	//----------- Getters and Setters -----------\\
	
	/**
	 * @return the piece
	 */
	public char getPiece() {
		return piece;
	}


	/**
	 * @param piece the piece to set
	 */
	public void setPiece(char piece) {
		this.piece = piece;
	}


	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}


	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}


	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}


	/**
	 * @param col the col to set
	 */
	public void setCol(int col) {
		this.col = col;
	}


	/**
	 * @return the dir
	 */
	public int getDir() {
		return dir;
	}


	/**
	 * @param dir the dir to set
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}

}
