package Pieces;

/**
 * Defines the properties and behavior of the Queen piece
 * @author Archil Shah
 * @author Mohamad Sherif 
 *
 */
public class Knight extends Piece{
	/**
	 * Name of the Knight
	 */
	public final String name; 
	
	/**
	 * Initializes the Knight object with given coordinates and color.
	 * @param x File
	 * @param y Rank
	 * @param col Color
	 */
	public Knight(int x, int y, String col) {
		setCurrFile(x);
		setCurrRank(y);
		setColor(col);
		if(col.toLowerCase().equals("white"))
			name = "wN";
		else
			name = "bN";
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
		if(Math.abs(getCurrFile()-endFile)==Math.abs(getCurrRank()-endRank))
			return false; 
		
		if(board.getObject(endFile, endRank) instanceof Piece && (((Piece)board.getObject(endFile, endRank)).getColor()).equals(this.getColor())) 
			return false; 
		
		if(getCurrFile()!=endFile && getCurrRank()!=endRank) 
			return checkPosition(board, endFile, endRank);
		return false;
	}
	
	/**
	 * Checks if the target position is valid.
	 * @param board board object
	 * @param endFile target File of the Knight
	 * @param endRank target Rank of the Knight
	 * @return true if the target position is valid
	 */
	public boolean checkPosition(Board board, int endFile, int endRank) { 
		int ix = getCurrFile();
		int jy = getCurrRank();
				
		if(ix+1 == endFile && jy+2 == endRank)
			return true;
		if(ix+2 == endFile && jy+1 == endRank)
			return true;
		if(ix-1 == endFile && jy+2 == endRank)
			return true;
		if(ix-2 == endFile && jy+1 == endRank)
			return true;
		if(ix-2 == endFile && jy-1 == endRank)
			return true;
		if(ix-1 == endFile && jy-2 == endRank)
			return true;
		if(ix+1 == endFile && jy-2 == endRank)
			return true;
		if(ix+2 == endFile && jy-1 == endRank)
			return true;
		
		return false;
	}
	
	@Override
	public String getName() {
		return name;
	}//
}
