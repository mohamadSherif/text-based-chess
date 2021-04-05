package Pieces;
import java.util.*;


/**
 * Defines the properties and behavior of the Pawn piece
 * @author Archil Shah
 * @author Mohamad Sherif 
 *
 */
public class Pawn extends Piece{
	/**
	 * Name of the Pawn
	 */
	public final String name; 
	/**
	 * Status of the Pawn (Whether it has been moved or not)
	 */
	public boolean isStart = true;
	
	/**
	 * Initializes the Pawn object with given coordinates and color.
	 * @param x File
	 * @param y Rank
	 * @param col Color
	 */
	public Pawn(int x, int y, String col) {
		setCurrFile(x);
		setCurrRank(y);
		setColor(col);
		if(col.toLowerCase().equals("white"))
			name = "wp";
		else
			name = "bp";
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
			isStart=false;
			promote(board, endFile, endRank, promotion);
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
		if(isStart == false) {
			int lastpiececurrfile = board.getLastPiece().getCurrFile();
			int lastmove = Math.abs(board.getLastStartFile() - lastpiececurrfile);
			int lastPieceCurrFile = board.getLastPiece().getCurrFile();
			int lastPieceCurrRank = board.getLastPiece().getCurrRank();
			
			if(board.getLastPiece().getName() == "bp"  && lastmove == 2 && endFile == lastPieceCurrFile-1 && endRank == lastPieceCurrRank) return whiteEnpassant(board, endFile, endRank);
			if(board.getLastPiece().getName() == "wp"  && lastmove == 2 && endFile == lastPieceCurrFile+1 && endRank == lastPieceCurrRank) return blackEnpassant(board, endFile, endRank);
		}
		if(getColor().equals("White")) return isWhiteValid(board, endFile, endRank);
		if(getColor().equals("Black")) return isBlackValid(board, endFile, endRank);
		return false;
	}
	
	/**
	 * Checks if the path to target is valid for the black pawn to move to
	 * @param board board object
	 * @param endFile target File for the black Pawn
	 * @param endRank target Rank for the black Pawn
	 * @return true if the path to target is valid for the black pawn to move to
	 */
	public boolean isBlackValid(Board board, int endFile, int endRank) {
		if(isStart==true) {
			return (endFile-getCurrFile()==1 && getCurrRank()==endRank && checkBlackFront(board, endFile, endRank)) || (endFile-getCurrFile()==2 && getCurrRank()==endRank && checkBlackFront(board, endFile, endRank)) || (Math.abs(getCurrRank()-endRank)==1 && endFile-getCurrFile()==1 && isBlackValidEnd(board, endFile, endRank));
		}
		else if(isStart==false)
			return (endFile-getCurrFile()==1 && getCurrRank()==endRank && checkBlackFront(board, endFile, endRank)) || (Math.abs(getCurrRank()-endRank)==1 && endFile-getCurrFile()==1 && isBlackValidEnd(board, endFile, endRank));
		return false;
	}
	
	/**
	 * Checks if the target position is valid for the black pawn
	 * @param board board object
	 * @param endFile target File for the black Pawn
	 * @param endRank target Rank for the black Pawn
	 * @return true if the target position to target is valid for the black pawn
	 */
	public boolean isBlackValidEnd(Board board, int endFile, int endRank) { //if the end position is valid
		if(board.getObject(endFile, endRank) instanceof Piece && !(((Piece)board.getObject(endFile, endRank)).getColor()).equals(this.getColor()))
			return true;
		return false;
	}
	
	/**
	 * Checks if the path of the black pawn is empty
	 * @param board board object
	 * @param endFile target File for the black Pawn
	 * @param endRank target Rank for the black Pawn
	 * @return true if the path of the black pawn is empty
	 */
	public boolean checkBlackFront(Board board, int endFile, int endRank) { //check if there is a piece in front (not diagonal-this is taken care by isWhiteValid End)
		int x = getCurrFile()+1;
		while(x<=endFile) {
			if(board.getObject(endFile, endRank) instanceof Piece) return false;
			x++;
		}
		return true;
	}
	
	/**
	 * Checks if the path to target is valid for the white pawn to move to
	 * @param board board object
	 * @param endFile target File for the white Pawn
	 * @param endRank target Rank for the white Pawn
	 * @return true if the path to target is valid for the white pawn to move to
	 */
	public boolean isWhiteValid(Board board, int endFile, int endRank) {
		if(isStart==true) {
			return (getCurrFile()-endFile==1 && getCurrRank()==endRank && checkWhiteFront(board, endFile, endRank)) || (getCurrFile()-endFile==2 && getCurrRank()==endRank && checkWhiteFront(board, endFile, endRank)) || (Math.abs(getCurrRank()-endRank)==1 && getCurrFile()-endFile==1 && isWhiteValidEnd(board, endFile, endRank));
		}
		else if(isStart==false) {
			return (getCurrFile()-endFile==1 && getCurrRank()==endRank && checkWhiteFront(board, endFile, endRank)) || (Math.abs(getCurrRank()-endRank)==1 && getCurrFile()-endFile==1 && isWhiteValidEnd(board, endFile, endRank));
		}
		return false;
	}
	
	/**
	 * Checks if the target position is valid for the white pawn
	 * @param board board object
	 * @param endFile target File for the white Pawn
	 * @param endRank target Rank for the white Pawn
	 * @return true if the target position to target is valid for the white pawn
	 */
	public boolean isWhiteValidEnd(Board board, int endFile, int endRank) { //if the end position is valid
		if(board.getObject(endFile, endRank) instanceof Piece && !(((Piece)board.getObject(endFile, endRank)).getColor()).equals(this.getColor()))
			return true;
		return false;
	}
	
	/**
	 * Checks if the path of the white pawn is empty
	 * @param board board object
	 * @param endFile target File for the white Pawn
	 * @param endRank target Rank for the white Pawn
	 * @return true if the path of the white pawn is empty
	 */
	public boolean checkWhiteFront(Board board, int endFile, int endRank) { //check if there is a piece in front (not diagonal-this is taken care by isWhiteValid End)
		int x = getCurrFile()-1;
		while(x>=endFile) {
			if(board.getObject(endFile, endRank) instanceof Piece) return false;
			x--;
		}
		return true;
	}
	
	 /**
	  * Promotes a qualified pawn to the piece that the user wants (by default it is Queen)
	  * @param board board object
	  * @param endFile target File for the white Pawn
	  * @param endRank target Rank for the white Pawn
	  * @param promotion data for the piece that the user wants the pawn to promote to 
	  */
	public void promote(Board board, int endFile, int endRank, String promotion) {
		if((getColor().equals("White") && endFile==0) || (getColor().equals("Black") && endFile==7))
		{
			if(promotion.equalsIgnoreCase("N")) 
				board.set(endFile, endRank, new Knight(endFile, endRank, getColor()));	
			else if(promotion.equalsIgnoreCase("B"))
				board.set(endFile, endRank, new Bishop(endFile, endRank, getColor()));
			else if(promotion.equalsIgnoreCase("R"))
				board.set(endFile, endRank, new Rook(endFile, endRank, getColor()));
			else
				board.set(endFile, endRank, new Queen(endFile, endRank, getColor()));
		}
	}
	
	/**
	  * Triggered when a black piece has an Enpassant
	  * @param board board object
	  * @param endFile target File for the white Pawn
	  * @param endRank target Rank for the white Pawn
	  * @return true after the white pawn is removed
	  */
	public boolean blackEnpassant(Board board, int endFile, int endRank) {
		int lastPieceCurrFile = board.getLastPiece().getCurrFile();
		int lastPieceCurrRank = board.getLastPiece().getCurrRank();

		board.updateBoard(this, lastPieceCurrFile, lastPieceCurrRank);
				
		return true;
	}
	
	/**
	  * Triggered when a white piece has an Enpassant
	  * @param board board object
	  * @param endFile target File for the white Pawn
	  * @param endRank target Rank for the white Pawn
	  * @return true after the white pawn is removed
	  */
	public boolean whiteEnpassant(Board board, int endFile, int endRank) {
		int lastPieceCurrFile = board.getLastPiece().getCurrFile();
		int lastPieceCurrRank = board.getLastPiece().getCurrRank();
		
		board.updateBoard(this, lastPieceCurrFile, lastPieceCurrRank);
		
		return true;
	}
		
	@Override
	public String getName() {
		return name;
	}//
	
}

