/**
 * CheckersLogic is the class responsible for the logic behind the checkers game. 
 * @author Sydney Cochran
 * @version 2.0
 */
package core;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import core.CheckersLogic;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;

public class CheckersLogic extends Application {
	public static char[][] gameBoard; 
	protected int numLightPieces; 
	protected int numDarkPieces; 
	protected static char whoseMove;
	protected final static int size = 8;
	Scanner scan = new Scanner(System.in);	
/**
 * isValid is the method that checks if the move a player inputs is actually a valid move. It takes in the starting and ending positions of the checkers piece
 * and goes through the different validity checks. 
 * @param startColumn
 * @param endColumn
 * @param startRow
 * @param endRow
 * @return valid will return true or false based on the validity of the requested move
 * @throws ArrayIndexOutOfBoundsException
 */
	public boolean isValid(int startColumn, int endColumn, int startRow, int endRow) throws ArrayIndexOutOfBoundsException{
		boolean valid = false;
		try {
			if(endRow < 0 || endColumn < 0 || endColumn > 8){
				return valid;
			}
			if(gameBoard[startColumn][startRow] == whoseMove
					&& gameBoard[endColumn][endRow] == '_'
					&& startRow != endRow
					&& startColumn != endColumn){
				if(whoseMove == 'x' && endRow == startRow - 1){
					valid = true;
				} else if( whoseMove == 'o' && endRow == startRow + 1){
					valid = true;
				}
			}
			return valid;
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Error checking validity of move!");
			return false;
		}	
	}
/**
 * makeMove is the method that actually moves the piece on the board. It takes in the parameters below, and changes the board to match the requested move. 
 * @param startRow
 * @param endRow
 * @param startColumn
 * @param endColumn
 */
	public void makeMove(int startRow, int endRow, int startColumn, int endColumn){
	 	gameBoard[startRow][startColumn] = '_';
	 	gameBoard[endRow][endColumn] = whoseMove;
	 }	
/**
 * 	gameOver is the method to check if the game is over. If there are either no x's or no 'o's, the method will return true
 * @return boolean 
 */
	public boolean gameOver() {
	 	return (numLightPieces == 0 || numDarkPieces == 0);
		    }
/**
 * getWinner is the method used to announce the winner of the game. 
 * @return String will either return x or o.
 */
	public String getWinner() {
	 	if (numDarkPieces == 0)
	 		return "x wins!";
	 	else
	 		return "o wins!";
	 }	
/**
 * checkCaptureStatusLight is the method that checks if x is able to capture. 
 * @return boolean will return true if x can capture, or false if x can't capture. 
 */
	public boolean checkCaptureStatusLight(){
		for(int i = 0; i < gameBoard.length; i ++){
			for(int j = 0; j< gameBoard.length; j++){
				if(gameBoard[i][j] == 'x'){
					if(i + 2 < 8 && j - 2 >= 0 && gameBoard[i+2][j-2] == '_' && gameBoard[i+1][j-1] == 'o'){
						return true;
					}
					else if(i - 2 >= 0 && j - 2 >= 0 && gameBoard[i-2][j-2] == '_' && gameBoard[i-1][j-1] == 'o'){
						return true;
					}
				}
			}
		}
		return false;
	}	
/**
 * checkCaptureStatusDark is the method that checks if o is able to capture. 
 * @return boolean will return true if o can capture, or false if it can't capture.
 */
	public boolean checkCaptureStatusDark(){
		for(int i = 0; i < gameBoard.length; i ++){
			for(int j = 0; j< gameBoard.length; j++){
				if(gameBoard[i][j] == 'o'){
					if(i+2 < 8 && j+2 < 8 && gameBoard[i+2][j+2] == '_' && gameBoard[i + 1][j+1] == 'x'){
						return true;
					}
					else if(i-2 >= 0 && j+2<9 && gameBoard[i-1][j+1] == 'x' && gameBoard[i-2][j+2] == '_'){

						return true;
					}
				}
			}
		}
		return false;
	}
/**
 * lightForceCapture is the method used when x must capture o. It informs the player, then takes in the requested move from the user. If the move is not a capture, 
 * 	it is not valid. 
 */
	public void lightForceCapture(){
	 	boolean moved = false;
	 	while(!moved) {
			System.out.println("X you must capture O");
			System.out.println("Enter the location of the piece you would like to move.");
			String startString = scan.nextLine();
			char[] startChar = startString.toCharArray();
			int startRow = Character.getNumericValue(startChar[0]);
			startRow = numberToIdendex(startRow);
			int startColumn = letterToIndex(startChar[1]);
			System.out.println("Enter the location you would like to move to.");
			String endString = scan.nextLine();
			char[] endChar = endString.toCharArray();
			int endRow = Character.getNumericValue(endChar[0]);
			endRow = numberToIdendex(endRow);
			int endColumn = letterToIndex(endChar[1]);
			if (validLightCapture(startColumn, endColumn, startRow, endRow)) {
				gameBoard[startColumn][startRow] = '_';
				gameBoard[endColumn][endRow] = 'x';
				if (startColumn > endColumn) {
					gameBoard[startColumn - 1][startRow - 1] = '_';
				} else {
					gameBoard[startColumn + 1][startRow - 1] = '_';
				}
				moved = true;
			}
			else {
				System.out.println("Invalid move! Enter a valid move.");
			}
		}
	 	numDarkPieces--;
	}
/**
 * darkForceCapture is the method used when o must capture x. It informs the player, then takes in the requested move from the user. If the move is not a capture, 
 * 	it is not valid. 
*/
	public void darkForceCapture(){
		boolean moved = false;
		while(!moved) {
			System.out.println("O you must capture X");
			System.out.println("Enter the location of the piece you would like to move.");
			String startString = scan.nextLine();
			char[] startChar = startString.toCharArray();
			int startRow = Character.getNumericValue(startChar[0]);
			startRow = numberToIdendex(startRow);
			int startColumn = letterToIndex(startChar[1]);		
			System.out.println("Enter the location you would like to move to. ");
			String endString = scan.nextLine();
			char[] endChar = endString.toCharArray();
			int endRow = Character.getNumericValue(endChar[0]);
			endRow = numberToIdendex(endRow);
			int endColumn = letterToIndex(endChar[1]);
			
			if (validDarkCapture(startColumn, endColumn, startRow, endRow)) {
				gameBoard[startColumn][startRow] = '_';
				gameBoard[endColumn][endRow] = 'o';
				if (startColumn > endColumn) {
					gameBoard[startColumn - 1][startRow + 1] = '_';
				} else {
					gameBoard[startColumn + 1][startRow + 1] = '_';
				}
				moved = true;
			}
			else {
				System.out.println("Invalid! Enter a valid move.");
			}
		}
		numLightPieces--;
	}
/**
 * validLightCapture is a method that checks if the piece at the location passed throught the parameters can capture. 
 * @param startColumn
 * @param endColumn
 * @param startRow
 * @param endRow
 * @return
 */
	public boolean validLightCapture(int startColumn, int endColumn, int startRow, int endRow){
	 	boolean result = false;
		if(endRow != startRow - 2){
			return result;
		}
	 	if(gameBoard[startColumn][startRow] == 'x' && gameBoard[endColumn][endRow] == '_'){
	 		if(startColumn-1>0 && gameBoard[startColumn-1][startRow-1] == 'o'){
	 			 result = true;
			}
	 		if(startColumn+1<8 && gameBoard[startColumn+1][startRow-1] == 'o'){
	 			result = true;
			}
		}
	 	return result;
	}	
/**
 * validDarkCapture is a method that checks if the piece at the location passed through the parameters can capture. 
 * @param startColumn
 * @param endColumn
 * @param startRow
 * @param endRow
 * @return
 */
	public boolean validDarkCapture(int startColumn, int endColumn, int startRow, int endRow){
		boolean result = false;
		if(endRow != startRow + 2 || endColumn < 0 || endRow < 0 || startRow - 2 < 0 || startRow + 1 > 7
		|| startColumn + 1 > 7){
			result = false;
		}
		if(gameBoard[startColumn][startRow] == 'o' && gameBoard[endColumn][endRow] == '_'){
			if(startColumn-1>0 && gameBoard[startColumn-1][startRow+1] == 'x'){
				result = true;
			}
			if(startColumn + 1 < 8 && gameBoard[startColumn+1][startRow+1] == 'x'){
				result = true;
			}
		}
		return result;
	}
/**
 * letterToIndex is a method that will take in a char and translate it into the corresponding index on the checkers board. 
 * @param c
 * @return
 */
	public int letterToIndex(char c){
		int result = 9;
		if(c == 'a'){
			result = 0;
		} else if (c == 'b'){
			result = 1;
		}else if (c == 'c'){
			result = 2;
		}else if (c == 'd'){
			result = 3;
		}else if (c == 'e'){
			result = 4;
		}else if (c == 'f'){
			result = 5;
		}else if (c == 'g'){
			result = 6;
		}else if (c == 'h'){
			result = 7;
		}
		return result;
	}
/**
 * numberToIndex is a method that will take in an int that represents a row label and translates it into the corresponding index on the checkers board. 
 * @param x
 * @return
 */
	public int numberToIdendex(int x){
		int result = 9;
		if(x == 1){
			result = 7;
		} else if(x == 2){
			result = 6;
		} else if(x==3){
			result = 5;
		} else if(x == 4){
			result = 4;
		} else if(x== 5){
			result = 3;
		} else if(x==6){
			result = 2;
		} else if(x == 7){
			result = 1;
		} else if(x==8){
			result = 0;
		}
		return result;
	}
/**
 * getMove is a method that will get the players next move. It checks for possible captures, asks user for move, checks if the move is valid, and creates the move. 
 * @throws ArrayIndexOutOfBoundsException
 */
	public void getMove() throws ArrayIndexOutOfBoundsException {
		 int startRow = 0;
		 int startColumn = 0;
		 int endRow = 0;
		 int endColumn = 0;	
		if (whoseMove=='o')
		    System.out.println("Player O it is your turn.");
		else
		    System.out.println("Player X it is your turn.");
		boolean moved = false;
		while (!moved) {
			if(whoseMove == 'x' && checkCaptureStatusLight()){
				lightForceCapture();
				whoseMove = 'o';
				return;
			}else if(whoseMove == 'o' && checkCaptureStatusDark()){
				darkForceCapture();
				whoseMove = 'x';
				return;
			}
			System.out.println("Enter the location of the piece you would like to move.");
		    System.out.println("Enter as a number and letter(for example 3a).");
		    String startString = scan.nextLine();
		    char[] startChar = startString.toCharArray();
		    startRow = Character.getNumericValue(startChar[0]);
		    startRow = numberToIdendex(startRow);
		    startColumn = letterToIndex(startChar[1]);
		    System.out.println("Enter the location you would like to move to.");
		    String endString = scan.nextLine();
            char[] endChar = endString.toCharArray();
            endRow = Character.getNumericValue(endChar[0]);
	        endRow = numberToIdendex(endRow);
	        endColumn = letterToIndex(endChar[1]);
	        try {
	        	if (isValid(startColumn,endColumn,startRow,endRow)) {
					 makeMove(startColumn,endColumn,startRow,endRow);
					 moved = true;
				}else {
					System.out.println("Invalid move! Enter a valid move.");
				}
	        }catch(ArrayIndexOutOfBoundsException e) {
	        	System.out.println("Invalid entry!");
	        	continue;
	        }  
		}
		if (whoseMove == 'o'){
				whoseMove = 'x';
		}else{
			whoseMove = 'o';
		}
	 }
/**
 * getMoveCPU is a method that creates the next move when playing against a computer. It will either make a computer move, or ask the user for a move and if it
 * is valid, create the move. 	
 * @throws ArrayIndexOutOfBoundsException
 */
	public void getMoveCPU() throws ArrayIndexOutOfBoundsException{
	 	int startRow = 0;
	 	int startColumn = 0;
	 	int endRow = 0;
	 	int endColumn = 0;
	 	if (whoseMove=='o')
	 		System.out.println("It is the computer's turn.");
	 	else
	 		System.out.println("Player X it is your turn.");
	 	
	 	boolean moved = false;
	 	while (!moved && whoseMove == 'x' ) {
			if(checkCaptureStatusLight()){
				lightForceCapture();
				whoseMove = 'o';
				return;
			}
			System.out.println("Enter the location of the piece you would like to move.");
			System.out.println("Enter as a number and letter(for example 3a).");
			String startString = scan.nextLine();
			char[] startChar = startString.toCharArray();
			startRow = Character.getNumericValue(startChar[0]);
			startRow = numberToIdendex(startRow);
			startColumn = letterToIndex(startChar[1]);
			System.out.println("Enter the location you would like to move to.");
			String endString = scan.nextLine();
			char[] endChar = endString.toCharArray();
			endRow = Character.getNumericValue(endChar[0]);
			endRow = numberToIdendex(endRow);
			endColumn = letterToIndex(endChar[1]);
			try {
				if (isValid(startColumn,endColumn,startRow,endRow)) {
					makeMove(startColumn,endColumn,startRow,endRow);
					moved = true;
				}
				else{
					System.out.println("Invalid move! Enter a valid move.");
					}
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Invalid entry!");
				continue;
			}	
	 	}
	 	if(whoseMove == 'o'){
	 		CheckersComputerPlayer computer = new CheckersComputerPlayer();
	 		computer.getCPUMove();
			}
		if (whoseMove == 'o'){
			whoseMove = 'x';
		}else{
			whoseMove = 'o';
		}
	}
	public void start(Stage stage) {} 
}