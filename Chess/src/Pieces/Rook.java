package Pieces;

/**
 * Defines the properties and behaviour of the Rook piece
 * @author Archil Shah
 * @author Mohamad Sherif 
 *
 */
public class Rook extends Piece{ // hello
	/**
	 * Name of the Rook
	 */
	public final String name; 
	
	/**
	 * Initializes the Rook object with given coordinates and color.
	 * @param x File
	 * @param y Rank
	 * @param col Color
	 */
	public Rook(int x, int y, String col) {
		setCurrFile(x);
		setCurrRank(y);
		setColor(col);
		if(col.toLowerCase().equals("white"))
			name = "wR";
		else
			name = "bR";
	}
	
	@Override
	public boolean move(Board board, int endFile, int endRank, String promotion) {
		int oldx = getCurrFile();
		int oldy = getCurrRank();
		
		if(isValid(board, endFile, endRank)) {
			setCurrFile(endFile);
			setCurrRank(endRank);
			board.updateBoard(this, oldx, oldy, endFile, endRank);
			setStart(false);
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
		
		if(endFile!=getCurrFile() && endRank!=getCurrRank())
			return false; 
		
		if(getCurrFile()!=endFile || getCurrRank()!=endRank)
			return checkBlocking(board, endFile, endRank);
		return true;
	}
	/**
	 * Checks if there is any Piece blocking the path Rook wants to take
	 * @param board board object
	 * @param endFile target File of the Rook
	 * @param endRank target Rank of the Rook
	 * @return true if there is no piece blocking Rook's path
	 */
	public boolean checkBlocking(Board board, int endFile, int endRank) {
		int i=getCurrFile(); int j=getCurrRank();
		if(i==endFile && j!=endRank)
			return checkHorizontal(board, endFile, endRank);
		if(i!=endFile && j==endRank)
			return checkVertical(board, endFile, endRank);
		return true;
	}
	
	/**
	 * Checks if there is any Piece blocking the horizontal path Rook wants to take
	 * @param board board object
	 * @param endFile target File of the Rook
	 * @param endRank target Rank of the Rook
	 * @return true if there is no piece blocking Rook's horizontal path
	 */
	public boolean checkHorizontal(Board board, int endFile, int endRank) { 
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
	 * Checks if there is any Piece blocking the vertical path Rook wants to take
	 * @param board board object
	 * @param endFile target File of the Rook
	 * @param endRank target Rank of the Rook
	 * @return true if there is no piece blocking Rook's vertical path
	 */
	public boolean checkVertical(Board board, int endFile, int endRank) { 
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
	
	@Override
	public String getName() {
		return name;
	}//
}
