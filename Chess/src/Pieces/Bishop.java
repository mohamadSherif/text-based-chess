package Pieces;

/**
 * Defines the properties and behavior of the Queen piece
 * @author Archil Shah
 * @author Mohamad Sherif 
 *
 */
public class Bishop extends Piece{
	/**
	 * Name of the Bishop
	 */
	public final String name; 
	
	/**
	 * Initializes the Bishop object with given coordinates and color.
	 * @param x File
	 * @param y Rank
	 * @param col Color
	 */
	public Bishop(int x, int y, String col) {
		setCurrFile(x);
		setCurrRank(y);
		setColor(col);
		if(col.toLowerCase().equals("white"))
			name = "wB";
		else
			name = "bB";
	}

	@Override
	public boolean move(Board board, int endFile, int endRank, String promotion) {
		//save the current position of the King before updating them.
		int oldx = getCurrFile();
		int oldy = getCurrRank();
		
		if(isValid(board, endFile, endRank)) {
			setCurrFile(endFile);
			setCurrRank(endRank);
			board.updateBoard(this, oldx, oldy, endFile, endRank); //pass in the saved position, end poisiton and the updated King
			return true;
		}
		else
			System.out.println("Illegal move, try again");
		return false;
		
	}

	@Override
	public boolean isValid(Board board, int endFile, int endRank) {
		if(endFile>=8 || endFile<0 || endRank>=8 || endRank<0) 
			return false; //end position should not be outside the board

		if(board.getObject(endFile, endRank) instanceof Piece && (((Piece)board.getObject(endFile, endRank)).getColor()).equals(this.getColor())) 
			return false; //move not allowed if the target contains same colored piece
		
		if(getCurrFile()!=endFile && getCurrRank()!=endRank) //can be diagonal
			return checkDiagonal(board, endFile, endRank);
		
		return false; //cannot go horizontal or vertical
	}
	
	/**
	 * Checks if there is any Piece blocking the diagonal path Bishop wants to take
	 * @param board board object
	 * @param endFile target File of the Bishop
	 * @param endRank target Rank of the Bishop
	 * @return true if there is no piece blocking Bishop's diagonal path
	 */
	public boolean checkDiagonal(Board board, int endFile, int endRank) { //checks the diagonals in all 4 directions
		int i = getCurrFile();
		int j = getCurrRank();
		
		if(Math.abs(getCurrFile()-endFile) != Math.abs(getCurrRank()-endRank)) return false;
		
		if(i > endFile && j > endRank) {i--; j--;}
		else if(i < endFile && j > endRank) {i++; j--;}
		else if(i > endFile && j < endRank) {i--; j++;}
		else {i++; j++;}
		
		while(i != endFile && j != endRank) {
			if(board.getObject(i, j) instanceof Piece)
				return false;
			
			if(i > endFile && j > endRank) {i--; j--;}
			else if(i < endFile && j > endRank) {i++; j--;}
			else if(i > endFile && j < endRank) {i--; j++;}
			else {i++; j++;}
		}
		return true;
	}
	
	@Override
	public String getName() {
		return name;
	}//
}
