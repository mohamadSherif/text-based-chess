package Pieces;

/**
 * Defines the properties and behavior of the Queen piece
 * @author Archil Shah
 * @author Mohamad Sherif 
 *
 */
public class Queen extends Piece{
	/**
	 * Name of the Queen
	 */
	public final String name; 
	
	/**
	 * Initializes the Queen object with given coordinates and color.
	 * @param x File
	 * @param y Rank
	 * @param col Color
	 */
	public Queen(int x, int y, String col) {
		setCurrFile(x);
		setCurrRank(y);
		setColor(col);
		if(col.toLowerCase().equals("white"))
			name = "wQ";
		else
			name = "bQ";
	}
	@Override
	public boolean move(Board board, int endFile, int endRank, String promotion) {
		
		int oldx = getCurrFile();
		int oldy = getCurrRank();
		
		if(isValid(board, endFile, endRank)) {
			setCurrFile(endFile);
			setCurrRank(endRank);
			board.updateBoard(this, oldx, oldy, endFile, endRank); 
			return true;
		}
		else
			System.out.println("Illegal move, try again");
		return false;
	}

	@Override
	public boolean isValid(Board board, int endFile, int endRank) {
		if(endFile>=8 || endFile<0 || endRank>=8 || endRank<0) 
			return false; 

		if(board.getObject(endFile, endRank) instanceof Piece && (((Piece)board.getObject(endFile, endRank)).getColor()).equals(this.getColor())) 
			return false; 

		
		if(getCurrFile()!=endFile || getCurrRank()!=endRank) 
			return checkBlocking(board, endFile, endRank);
		return true;
	}
	
	/**
	 * Checks if there is any Piece blocking the path Queen wants to take
	 * @param board board object
	 * @param endFile target File of the Queen
	 * @param endRank target Rank of the Queen
	 * @return true if there is no piece blocking Queen's path
	 */
	public boolean checkBlocking(Board board, int endFile, int endRank) {
		int i=getCurrFile(); int j=getCurrRank();
		if(i==endFile && j!=endRank)
			return checkHorizontal(board, endFile, endRank);
		if(i!=endFile && j==endRank)
			return checkVertical(board, endFile, endRank);
		if(i!=endFile && j!=endRank)
			return checkDiagonal(board, endFile, endRank);
		return true;
	}
	
	/**
	 * Checks if there is any Piece blocking the horizontal path Queen wants to take
	 * @param board board object
	 * @param endFile target File of the Queen
	 * @param endRank target Rank of the Queen
	 * @return true if there is no piece blocking Queen's horizontal path
	 */
	public boolean checkHorizontal(Board board, int endFile, int endRank) { //row of end position is the same
		int i = getCurrRank();
		
		if(i > endRank) i--;
		else i++;
		
		while(i != endRank) {
			if(board.getObject(endFile, i) instanceof Piece)
				return false;
			if(i > endRank) i--;
			else i++;
		}
		return true;
	}
	
	/**
	 * Checks if there is any Piece blocking the vertical path Queen wants to take
	 * @param board board object
	 * @param endFile target File of the Queen
	 * @param endRank target Rank of the Queen
	 * @return true if there is no piece blocking Queen's vertical path
	 */
	public boolean checkVertical(Board board, int endFile, int endRank) { //col of end position is the same
		int i = getCurrFile();
		
		if(i > endFile) i--;
		else i++;
		
		while(i != endFile) {
			if(board.getObject(i, endRank) instanceof Piece)
				return false;
			if(i > endFile) i--;
			else i++;
		}
		return true;
	}
	
	/**
	 * Checks if there is any Piece blocking the diagonal path Queen wants to take
	 * @param board board object
	 * @param endFile target File of the Queen
	 * @param endRank target Rank of the Queen
	 * @return true if there is no piece blocking Queen's diagonal path
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
