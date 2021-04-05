package Pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Class which defines the rules for game playing and user input
 * @author Archil Shah
 * @author Mohamad Sherif 
 *
 */
public class demo {
	/**
	 * Main method of the application where game playing takes place
	 * @param args
	 */
	public static void main(String[] args) {
		Board board = new Board();
		board.printBoard();
		System.out.println();

		boolean offer=false;
		String promotion = "";
		
		while(true) {
			//white turn
			Piece p = null;
			List<Object> input;
			do {
				while(true) {
					input = getInput("White");
					System.out.println();
					if(input.size()==2) break;
					Object o = board.getObject((int)input.get(1), (int)input.get(2));
					if((o instanceof Piece) && ((Piece)o).getColor().equals("White")) {
						p = (Piece)board.getObject((int)input.get(1), (int)input.get(2));
						break;
					}
					System.out.println("Only White Piece allowed");
				}
				
				
				if(offer==true) { //if black has offered to draw
					if(input.size()==2 && (((String)input.get(1)).toLowerCase()).equals("draw")) {
						System.out.println("Match Drawn");
						System.exit(0); //matched drawn
					}
					else offer = false; //offer turned down
				}
				if(input.size()==2 && (((String)input.get(1)).toLowerCase()).equals("resign")) {
					System.out.println("Black wins");
					System.exit(0); //black wins
				}
				
				if(input.size()==6 && ((String)input.get(5)).equalsIgnoreCase("draw?"))
					offer = true; //offered black to draw
				else if(input.size()==6) {//promotion stated
					promotion = (String)input.get(5);
				}
			} while(!p.move(board, (int)input.get(3), (int)input.get(4), promotion));
			
			board.printBoard();
			System.out.println();
			
			if(board.blackKingKilled()) {
				System.out.println("White wins");
				System.exit(0);
			}
			
			if(p.isValid(board, board.getBlackKing().getCurrFile(), board.getBlackKing().getCurrRank())) {
				if((board.getBlackKing()).isBlackCheckMate(board)) {
					System.out.println("Checkmate: White wins");
					System.exit(0);
				}
				System.out.println("Check");
			}
			//if checkMate -> print("White wins") break;
			
			promotion = "";
			//black turn
			do {
				
				while(true) {
					input = getInput("Black");
					if(input.size()==2) break;
					Object o = board.getObject((int)input.get(1), (int)input.get(2));
					if((o instanceof Piece) && ((Piece)o).getColor().equals("Black")) {
						p = (Piece)board.getObject((int)input.get(1), (int)input.get(2));
						break;
					}
					System.out.println("Only Black Piece allowed");
				}
								
				if(offer==true) { //if white has offered to draw
					if(input.size()==2 && (((String)input.get(1)).toLowerCase()).equals("draw")) {
						System.out.println("Match Drawn");
						System.exit(0); //matched drawn
					}
					else offer = false;
				}
				if(input.size()==2 && (((String)input.get(1)).toLowerCase()).equals("resign")) {
					System.out.println("White wins");
					System.exit(0); //white wins
				}
				
				if(input.size()==6 && ((String)input.get(5)).equalsIgnoreCase("draw?"))
					offer = true; //offered black to draw
				else if(input.size()==6) //promotion stated
					promotion = (String)input.get(5);
				
			}while(!p.move(board, (int)input.get(3), (int)input.get(4), promotion));
			
			board.printBoard();
			System.out.println();
			if(board.whiteKingKilled()) {
				System.out.println("Black wins");
				System.exit(0);
			}
			
			if(p.isValid(board, board.getWhiteKing().getCurrFile(), board.getWhiteKing().getCurrRank())) {
				if((board.getWhiteKing()).isWhiteCheckMate(board)) {
					System.out.println("Checkmate: Black wins");
					System.exit(0);
				}
				System.out.println("Check");
			}
		}
	}
	
	/**
	 * Gives the list of input data (by prompting the user) as instances of Object class 
	 * @param color The color of the piece for which you want the input data
	 * @return The list of input data as instances of Object class
	 */
	public static List<Object> getInput(String color) { //works fine
		Scanner kbd = new Scanner(System.in);
		System.out.print(color+"'s move: ");
		String input = kbd.nextLine();
		List<Object> result = new ArrayList<>(); 
		String[] inputArr = input.split(" ");
		if(inputArr.length==2) {
			String start = inputArr[0];
			String end = inputArr[1];
			
			int startY = (int)start.charAt(0)-97;
			int startX = 8-Integer.parseInt(start.charAt(1)+"");
			
			int endY = (int)end.charAt(0)-97;
			int endX = 8-Integer.parseInt(end.charAt(1)+"");
			
			result.add('c'); //c: continue 
			result.add(startX); result.add(startY); result.add(endX); result.add(endY);
		}
		
		if(inputArr.length==3) {
			String start = inputArr[0];
			String end = inputArr[1];
			
			int startY = (int)start.charAt(0)-97;
			int startX = 8-Integer.parseInt(start.charAt(1)+"");
			
			int endY = (int)end.charAt(0)-97;
			int endX = 8-Integer.parseInt(end.charAt(1)+"");
			
			result.add('o'); //o: offered (draw?)
			result.add(startX); result.add(startY); result.add(endX); result.add(endY); result.add(inputArr[2]);
		}
	
		if(inputArr.length==1) {
			result.add('e'); //e: end (resign/draw)
			result.add(inputArr[0]);
		}
		
		return result;
	}//
}
