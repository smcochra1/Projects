/**
 * CheckersTextConsole is the class responsible for creating the ui of the checkers game. When a CheckersTextConsole
 * object is instantiated, the game is printed in the console and ready to be played. 
 * @author Sydney Cochran 
 * @version 2.0 
 */
package ui;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import core.CheckersLogic;
//
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
//

public class CheckersTextConsole extends CheckersLogic {
/**
 * Constructor method for CheckersTextConsole. It sets the values on the board to x, o, or _. 
 */
	public CheckersTextConsole() {
		gameBoard = new char[size][size];
		numLightPieces = 12;
		numDarkPieces = 12;
		whoseMove = 'x';
		int i, j;
		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				gameBoard[i][j] = '_';
			}
		}
		for (i = 0; i < size; i += 2) {
			gameBoard[i][1] = 'o';
			gameBoard[i][5] = 'x';
			gameBoard[i][7] = 'x';
		}
		for (i = 1; i < size; i += 2) {
			gameBoard[i][0] = 'o';
			gameBoard[i][2] = 'o';
			gameBoard[i][6] = 'x';
		}
	}
/**
 * printBoard is a method that iterates through the 2D array to print the board. 
 */
	public void printBoard() {
		int i, j;
		for (i = 0; i < size; i++) {
			System.out.print((8 - i) + " ");
			for (j = 0; j < size; j++) {
				System.out.print("|");
				System.out.print(" " + gameBoard[j][i] + " ");
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println("    a   b   c   d   e   f   g   h ");
	}
	public static void main(String args[]) throws IOException {
		Scanner scanInt = new Scanner(System.in);
		Scanner scan = new Scanner(System.in);
		boolean cpuStatus;	
		System.out.println("Enter 1 to open a window for Checkers, or enter 2 to continue playing on the text console: ");
		int play = scanInt.nextInt();
		if(play <= 0 || play > 2) {
			System.out.println("Invalid Choice! Please try again.");
			System.out.println("Enter 1 to open a window for Checkers, or enter 2 to continue playing on the text console: ");
			play = scanInt.nextInt();
		}
		if(play == 1) {
			Application.launch(CheckersGUI.class);
		}else if(play == 2) {
			CheckersTextConsole game = new CheckersTextConsole();
			System.out.println("Enter C to verse computer, or H to play in 2 player mode.");
			String s = scan.nextLine();	
			if (s.equals("C") || (s.equals("c"))) {
				cpuStatus = true;
			}else {
				cpuStatus = false;
			}		
			game.printBoard();
			if (cpuStatus) {
				while (!game.gameOver()) {
					game.getMoveCPU();
					game.printBoard();
				}
				System.out.println(game.getWinner());
			} else {
				while (!game.gameOver()) {
					game.getMove();
					game.printBoard();
				}
				System.out.println(game.getWinner());
			}
		}
		
	}
}