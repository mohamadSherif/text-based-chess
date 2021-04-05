package Pieces;

/**
 * Abstract class Piece serving as a blueprint for the specific pieces of the chess.
 * @author Archil Shah
 * @author Mohamad Sherif 
 *
 */
public abstract class Piece {
	
	/**
	 * Current File of the Piece
	 */
	public int currFile;
	
	/**
	 * Current Rank of the Piece
	 */
	public int currRank;
	
	/**
	 * Color of the Piece
	 */
	public String color;
	
	/**
	 * start = true tells that the piece has not been moved before
	 */
	public boolean start = true;
	
	/**
	 * Moves the current piece to the specified location on the board
	 * @param b represents the chess board object 
	 * @param endFile represents the final File for the piece
	 * @param endRank represents the final Rank for the piece
	 * @param promotion tells promoting piece for the Pawn  
	 * @return true if the move is valid
	 */
	public abstract boolean move(Board b, int endFile, int endRank, String promotion);
	
	/**
	 * Checks if the move is valid before executing the move
	 * @param b represents the chess board object 
	 * @param endFile represents the final File for the piece
	 * @param endRank represents the final Rank for the piece
	 * @return if the move is valid or not
	 */
	public abstract boolean isValid(Board b, int endFile, int endRank);
	
	/** 
	 * Gives the name of the Piece
	 * @return the name of the Piece
	 */
	public abstract String getName();
	
	/**
	 * Gives the current File value of the piece
	 * @return the current File of the Piece
	 */
	public int getCurrFile() {
		return currFile;
	}
	
	/**
	 * Sets the current file to the given file
	 * @param currFile given file
	 */
	public void setCurrFile(int currFile) {
		this.currFile = currFile;
	}
	
	/**
	 * Gives the current rank value of the piece
	 * @return the current rank of the piece
	 */
	public int getCurrRank() {
		return currRank;
	}
	
	/**
	 * Sets the current rank to the given rank
	 * @param currRank given rank
	 */
	public void setCurrRank(int currRank) {
		this.currRank = currRank;
	}
	
	/**
	 * Gives the color of the piece
	 * @return color of the piece
	 */
	public String getColor() {
		return color;
	}
	
	/**
	 * Sets the color of the piece 
	 * @param color given color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * Tells if the piece has been moved before or not
	 * @return true if the piece has not been moved before
	 */
	public boolean isStart(){
		return this.start;
	}
	
	/**
	 * Sets the status of the start field with the given value 
	 * @param start true or false
	 */
	public void setStart(boolean start){
		this.start = start;
	}
	//
}
