package Pieces;

import java.util.*;


/**
 * Class to create the chess board object.
 * @author Archil Shah
 * @author Mohamad Sherif 
 *
 */
public class Board {
	/**
	 * A 2 dimensional list of objects which represents the chess board as a collection of Strings and Piece objects.
	 */
	public List<List<Object>> board = new ArrayList<>();
	/**
	 * A Piece object that holds the piece from the last move
	 */
	public Piece lastPiece;
	/**
	 * int value of the starting rank of the last piece
	 */
	public int lastStartRank;
	/**
	 * int value of the starting file of the last piece
	 */
	public int lastStartFile;
	
	/**
	 * Board constructor which fills the board list with black and white cells and various pieces of the chess.
	 */
	public Board() {
		for(int i=0; i<9; i++) {
			ArrayList<Object> list = new ArrayList<>(9);
			if(i==8) {
				for(int k=0; k<8; k++) {
					list.add(" "+(char)(97+k));
				}
			}
			else {
				for(int j=0; j<9;j++) {
					if(j==8)
						list.add(8-i);
					else if(i%2==0 && j%2==0 || i%2==1 && j%2==1)
						list.add(j, "##");
					else
						list.add(j, "  ");
				}
			}
			board.add(list);
		}
		
		List<Object> list = new ArrayList<>(Arrays.asList(new Rook(0,0,"Black"),new Knight(0,1,"Black"),new Bishop(0,2,"Black"),new Queen(0,3,"Black"),new King(0,4,"Black"),new Bishop(0,5,"Black"),new Knight(0,6,"Black"),new Rook(0,7,"Black"),8));
		board.set(0,list);
		list = new ArrayList<>(Arrays.asList(new Pawn(1,0,"Black"),new Pawn(1,1,"Black"),new Pawn(1,2,"Black"),new Pawn(1,3,"Black"),new Pawn(1,4,"Black"),new Pawn(1,5,"Black"),new Pawn(1,6,"Black"),new Pawn(1,7,"Black"),7));
		board.set(1,list);
		list = new ArrayList<>(Arrays.asList(new Pawn(6,0,"White"),new Pawn(6,1,"White"),new Pawn(6,2,"White"),new Pawn(6,3,"White"),new Pawn(6,4,"White"),new Pawn(6,5,"White"),new Pawn(6,6,"White"),new Pawn(6,7,"White"),2));
		board.set(6,list);
		list = new ArrayList<>(Arrays.asList(new Rook(7,0,"White"),new Knight(7,1,"White"),new Bishop(7,2,"White"),new Queen(7,3,"White"),new King(7,4,"White"),new Bishop(7,5,"White"),new Knight(7,6,"White"),new Rook(7,7,"White"),1));
		board.set(7,list);
	}
	
	/**
	 * Gives the Object stored in the x and y coordinates of the board
	 * @param x File
	 * @param y Rank
	 * @return Object with coordinates x and y
	 */
	public Object getObject(int x, int y) {
		return board.get(x).get(y);
	}
	
	/**
	 * Gives the board list that represents the current status of the chess board.
	 * @return board list
	 */
	public List<List<Object>> getBoard(){
		return board;
	}
	
	/**
	 * Updates the 2D board list after a certain piece has been moved
	 * @param updatedP Piece object after moving
	 * @param oldx current file of the piece to be moved
	 * @param oldy current rank of the piece to be moved
	 * @param endX final file of the piece after moving
	 * @param endY final rank of the piece after moving
	 */
	public void updateBoard(Piece updatedP, int oldx, int oldy, int endX, int endY) {
		if(oldx%2==0 && oldy%2==0 || oldx%2==1 && oldy%2==1)
			board.get(oldx).set(oldy, "##");
		else
			board.get(oldx).set(oldy, "  ");
		
		board.get(endX).set(endY, updatedP);
		lastPiece = updatedP;
		lastStartRank = oldy;
		lastStartFile = oldx;
	}
	
	/**
	 * Overloaded updateBoard method to handle enpassant move
	 * @param updatedP Piece object after moving
	 * @param oldx current file of the piece to be moved
	 * @param oldy current rank of the piece to be moved
	 */
	public void updateBoard(Piece updatedP, int oldx, int oldy) {
		if(oldx%2==0 && oldy%2==0 || oldx%2==1 && oldy%2==1)
			board.get(oldx).set(oldy, "##");
		else
			board.get(oldx).set(oldy, "  ");
	}
	
	/**
	 * Checks if the piece at coordinates x and y is white or not
	 * @param x File 
	 * @param y Rank
	 * @return true if Piece is white
	 */
	public boolean isWhitePiece(int x, int y) {
		if(getObject(x, y) instanceof Piece && (((Piece)getObject(x, y)).getColor()).equals("White"))
			return true;
		System.out.println("Invaild Move");
		return false;
	}
	
	/**
	 * Checks if the piece at coordinates x and y is black or not
	 * @param x File 
	 * @param y Rank
	 * @return true if Piece is black
	 */
	public boolean isBlackPiece(int x, int y) {
		if(getObject(x, y) instanceof Piece && (((Piece)getObject(x, y)).getColor()).equals("Black"))
			return true;
		return false;
	}
	
	/**
	 * Searches for the white king in the board.
	 * @return The white King object
	 */
	public King getWhiteKing() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Object o = getObject(i,j);
				if(o instanceof King && ((King) o).getColor().equals("White"))
					return (King)o;
			}
		}
		return null;
	}
	
	/**
	 * Searches for the black king in the board.
	 * @return The black King object
	 */
	public King getBlackKing() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Object o = getObject(i,j);
				if(o instanceof King && ((King) o).getColor().equals("Black"))
					return (King)o;
			}
		}
		return null;
	}
	
	/**
	 * Checks if the black king is killed
	 * @return true if the black king is killed
	 */
	public boolean blackKingKilled() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Object o = getObject(i,j);
				if(o instanceof King && ((King) o).getColor().equals("Black"))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the white king is killed
	 * @return true if the white king is killed
	 */
	public boolean whiteKingKilled() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Object o = getObject(i,j);
				if(o instanceof King && ((King) o).getColor().equals("White"))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Adds the given Piece object to the specified x and y coordinates
	 * @param x File
	 * @param y Rank
	 * @param p new Piece object
	 */
	public void set(int x, int y, Piece p) {
		board.get(x).set(y, p);
	}
	
	/**
	 * Get piece from the last move
	 * @return Piece object of last move
	 */
	public Piece getLastPiece() {
		return lastPiece;
	}
	
	/**
	 * Get starting rank of last piece
	 * @return integer of last rank
	 */
	public int getLastStartRank() {
		return lastStartRank;
	}
	
	/**
	 * Get starting file of last piece
	 * @return integer of last file
	 */
	public int getLastStartFile() {
		return lastStartFile;
	}
	
	/**
	 * Prints the chess board
	 */
	public void printBoard() {
		for(List<Object> l : board) {
			for(Object o : l) {
				if(o instanceof String || o instanceof Integer)
					System.out.print(o+" ");
				else
					System.out.print(((Piece)o).getName()+" ");
			}
			System.out.println();
		}
	}//
}
