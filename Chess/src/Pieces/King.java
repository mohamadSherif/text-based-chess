package Pieces;


/**
 * Defines the properties and behavior of the King piece
 * @author Archil Shah
 * @author Mohamad Sherif 
 *
 */
public class King extends Piece{
	/**
	 * Name of the King
	 */
	public final String name; 
	
	/**
	 * Initializes the King object with given coordinates and color.
	 * @param x File
	 * @param y Rank
	 * @param col Color
	 */
	public King(int x, int y, String col) {
		setCurrFile(x);
		setCurrRank(y);
		setColor(col);
		if(col.toLowerCase().equals("white"))
			name = "wk";
		else
			name = "bk";
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
			System.out.println("Invalid move");
		return false;
	}

	@Override		
	public boolean isValid(Board board, int endFile, int endRank) {
		if(endFile>=8 || endFile<0 || endRank>=8 || endRank<0) 
			return false; 
		if(getCurrFile() == endFile && Math.abs(getCurrRank()-endRank)==2 && isStart() == true) 
			if((getColor().equals("Black") && !blackKillable(endFile, endRank, board)) || (getColor().equals("White") && !whiteKillable(endFile, endRank, board))) 
				return isCastling(board, endFile, endRank);// Check if its castling
		
		if(Math.abs(getCurrFile()-endFile)>1 || Math.abs(getCurrRank()-endRank)>1) 
			return false; 
		
		if(board.getObject(endFile, endRank) instanceof Piece && (((Piece)board.getObject(endFile, endRank)).getColor()).equals(this.getColor())) 
			return false; 
		return true;
	}
	
	/**
	 * Checks if Castling move is valid given the target position
	 * @param board board object
	 * @param endFile target File of the King
	 * @param endRank target Rank of the King
	 * @return true if castling move is possible for the King
	 */
	public boolean isCastling(Board board, int endFile, int endRank) {
		if(getCurrRank() - endRank < 0) { 
			if(board.getObject(endFile, endRank+1) instanceof Rook && ((Rook)board.getObject(endFile, endRank+1)).isStart() == true && checkHorizontal(board, endFile, endRank)) {
				((Rook)board.getObject(endFile, endRank+1)).move(board, endFile, endRank-1, "");
				return true;
			}
		}else {
			if(board.getObject(endFile, endRank-2) instanceof Rook && ((Rook)board.getObject(endFile, endRank-2)).isStart() == true && checkHorizontal(board, endFile, endRank)) {
				((Rook)board.getObject(endFile, endRank-2)).move(board, endFile, endRank+1, "");
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if black king has been checkmated
	 * @param board board object
	 * @return true if black king has been checkmated
	 */
	public boolean isBlackCheckMate(Board board) {
		int x = getCurrFile();
		int y = getCurrRank();
		//if diagonal steps possible, return false
		if(isValidCell(board,x+1,y+1) && !blackKillable(x+1,y+1,board)) return false;
		if(isValidCell(board,x-1,y-1) && !blackKillable(x-1,y-1,board)) return false;
		if(isValidCell(board,x+1,y-1) && !blackKillable(x+1,y-1,board)) return false;
		if(isValidCell(board,x-1,y+1) && !blackKillable(x-1,y+1,board)) return false;
		
		//if vertical or horizontal steps possible, return false
		if(isValidCell(board,x+1,y) && !blackKillable(x+1,y,board)) return false;
		if(isValidCell(board,x,y+1) && !blackKillable(x,y+1,board)) return false;
		if(isValidCell(board,x-1,y) && !blackKillable(x-1,y,board)) return false;
		if(isValidCell(board,x,y-1) && !blackKillable(x,y-1,board)) return false;
		return true;
	}
	
	/**
	 * Checks if white king has been checkmated
	 * @param board board object
	 * @return true if white king has been checkmated
	 */
	public boolean isWhiteCheckMate(Board board) {
		int x = getCurrFile();
		int y = getCurrRank();
		//if diagonal steps possible, return false
		if(isValidCell(board,x+1,y+1) && !whiteKillable(x+1,y+1,board)) return false;
		if(isValidCell(board,x-1,y-1) && !whiteKillable(x-1,y-1,board)) return false;
		if(isValidCell(board,x+1,y-1) && !whiteKillable(x+1,y-1,board)) return false;
		if(isValidCell(board,x-1,y+1) && !whiteKillable(x-1,y+1,board)) return false;
		
		//if vertical or horizontal steps possible, return false
		if(isValidCell(board,x+1,y) && !whiteKillable(x+1,y,board)) return false;
		if(isValidCell(board,x,y+1) && !whiteKillable(x,y+1,board)) return false;
		if(isValidCell(board,x-1,y) && !whiteKillable(x-1,y,board)) return false;
		if(isValidCell(board,x,y-1) && !whiteKillable(x,y-1,board)) return false;
		return true;
	}
	
	/**
	 * Checks if black king has been checked
	 * @param board board object
	 * @param kingX current File of the black king
	 * @param kingY current Rank of the king
	 * @return true if black king has been checked
	 */
	public boolean blackKillable(int kingX, int kingY, Board board) {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Object o = board.getObject(i,j);
				if(o instanceof Piece && ((Piece) o).getColor().equals("White"))
					if(((Piece)o).isValid(board, kingX, kingY))
						return true;
				}
		}
		return false;
	}
	
	/**
	 * Checks if there is any Piece blocking the horizontal path King wants to take
	 * @param board board object
	 * @param endFile target File of the King
	 * @param endRank target Rank of the King
	 * @return true if there is no piece blocking King's horizontal path
	 */
	public boolean checkHorizontal(Board board, int endFile, int endRank) { 
		int i = getCurrRank();
		
		if(i > endRank) i--;
		else i++;

		while(i != endRank) {
			if(getColor().equals("White") && (board.getObject(endFile, i) instanceof Piece || whiteKillable(endFile, i, board))) {
				return false;
			}
			else if(getColor().equals("Black") && (board.getObject(endFile, i) instanceof Piece || blackKillable(endFile, i, board))) {
				return false;
			}
			if(i > endRank) i--;
			else i++;
		}
		return true;
	}

	/**
	 * Checks if white king has been checked
	 * @param board board object
	 * @param kingX current File of the king
	 * @param kingY current Rank of the king
	 * @return true if white king has been checked
	 */
	public boolean whiteKillable(int kingX, int kingY, Board board) {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Object o = board.getObject(i,j);
				if(o instanceof Piece && ((Piece) o).getColor().equals("Black"))
					if(((Piece)o).isValid(board, kingX, kingY))
						return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a cell at the given coordinates is empty
	 * @param board board object
	 * @param x File
	 * @param y Rank
	 * @return true if the cell at given coordinates is empty
	 */
	public boolean isValidCell(Board board, int x, int y) { 
		if(x>=8 || x<0 || y>=8 || y<0) return false;
		Object o = board.getObject(x, y);
		if(o instanceof String) return true;
		return false;
	}

	@Override
	public String getName() {
		return name;
	}//
}
