package ui;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * CheckersGUI is the class responsible for creating and running a checkers game using JavaFX.
 * @author Sydney Cochran
 * @version 1.0
 *
 */
public class CheckersGUI extends Application {
	 	GameBoard gameBoard;
	    private Button newGameButton;
	    private Label message;
	    char choice;

	public static void main(String[] args) throws IOException {
    }
/**
 * start is the method used start the checkers game. The user is asked what mode they would like to play in and all components are added to the window. 
 */
    public void start(Stage stage) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter P to play a human or C to play a computer (Please use a capital letter)");
        choice = scan.nextLine().charAt(0);
        if(choice!='P' && choice !='C'){
            System.out.println("Invalid entry");
        }
        
        message = new Label("White goes first");
        message.setTextFill( Color.BLACK ); 
        message.setFont( Font.font(null, FontWeight.BOLD, 30) );
       newGameButton = new Button("New Game");
        gameBoard = new GameBoard();
        gameBoard.update(); 
        
        gameBoard.setOnMousePressed( e -> gameBoard.mousePressed(e) );
        newGameButton.relocate(600, 740);
        message.relocate(100, 725);
        gameBoard.relocate(70, 70);
       newGameButton.resize(100,30);
        Pane background = new Pane();
        background.setPrefWidth(800);
        background.setPrefHeight(800);
        background.getChildren().addAll(gameBoard, message, newGameButton);
        background.setStyle("-fx-background-color: darkgreen");
        Scene scene = new Scene(background);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("Checkers Game");
        stage.show();
        
        if(choice == 'C'){
            gameBoard.playCPU();
        } else if(choice == 'P'){
            gameBoard.play2Player();
        }
    }
/**
 * Move is a nested class to create an object that will represent a move that can be made by a checkers piece. 
 * @author Sydney Cochran
 *
 */
    private static class Move {
    	
        int startRow, startCol, endRow, endCol;
        
        Move(int r1, int c1, int r2, int c2) {
            startRow = r1;
            startCol = c1;
            endRow = r2;
            endCol = c2;
        }
       
        boolean isJump() {
            return (startRow - endRow == 2 || startRow - endRow == -2);
        }
    }
/**
 * GameBoard is the nested class that is responsible for creating and updating the checkers gameBoard's graphical elements depending on user clicks. 
 * @author Sydney Cochran
 *
 */
    private class GameBoard extends Canvas {

        CheckersData gameBoard;
        boolean playingGame = true; 
        int playerTurn;
        int startRow, startCol;   
        boolean computerPlayer;
        Move[] possibleMoves;

        /**
         * Constructor for GameBoard. It creates an object of CheckersData to represent the information of the gameBoard. 
         */
        GameBoard() {
            super(700,700);  
            gameBoard = new CheckersData();
        }

        /**
         * gameOver is the method used to signal a checkers game is over. It will set the message to what is passed in as the parameter. 
         * @param str to be set as the message. 
         */
        void gameOver(String str) {
            message.setText(str);
           newGameButton.setDisable(false);
            playingGame = false;
        }
        /**
         * getPiece is the method used to get the piece that has been clicked on. It is called by the mousePressed method.
         * @param row row of piece
         * @param col column of piece
         */
        void getPiece(int row, int col) {
        	try {
        	       for (int i = 0; i < possibleMoves.length; i++){
                       if (possibleMoves[i].startRow == row && possibleMoves[i].startCol == col) {
                           this.startRow = row;
                           this.startCol = col;
                           update();
                           return;
                       }
                   }
                   if (startRow < 0) {
                       return;
                   }
                   for (int i = 0; i < possibleMoves.length; i++) {
                       if (possibleMoves[i].startRow == this.startRow && possibleMoves[i].startCol == this.startCol
                       		&& possibleMoves[i].endRow == row && possibleMoves[i].endCol == col && possibleMoves[i] != null) 
                       {
                       	makeMove(possibleMoves[i]);
                           return;
                       }
                   }
        	}catch(NullPointerException e) {
        		System.out.println("Null pointer exception caught!");
        	}
     
        }
        /**
         * play2Player is a method called when the game is being set up. It will set the game in two player mode. 
         */
        void play2Player() {
            computerPlayer = false;
            playerTurn = CheckersData.WHITE;
            possibleMoves = gameBoard.getMoves(CheckersData.WHITE);
            startRow = -1;
            update();
        }
        /**
         * playCPU is a method called when the game is being set up. It will set the game in computer player mode. 
         */
        void playCPU(){
            playerTurn = CheckersData.WHITE; 
            possibleMoves = gameBoard.getMoves(CheckersData.WHITE);
            startRow = -1;
            computerPlayer = true;
            update();
        }
        /**
         * makeMove is the method that will execute the moves requested, and then either continue or end the game. 
         * @param move move to be executed
         */
        void makeMove(Move move) {
            gameBoard.makeMove(move);
           
            if(!computerPlayer) {
            	if (playerTurn == CheckersData.WHITE) {
            		playerTurn = CheckersData.BLACK;
            		possibleMoves = gameBoard.getMoves(playerTurn);
            		if (possibleMoves == null) {
            			gameOver("Black has no moves.  White wins.");
            		}else if (possibleMoves[0].isJump()) {
            			message.setText("Black must jump.");
            		}else {
            			message.setText("Black's turn");
            		}
            	} else {
            		playerTurn = CheckersData.WHITE;
            		possibleMoves = gameBoard.getMoves(playerTurn);
            		if (possibleMoves == null)
            			gameOver("White has no moves.  Black wins.");
            		else if (possibleMoves[0].isJump())
            			message.setText("White must jump.");
            		else
            			message.setText("White's turn");
            	}
            } else {
            	try {
            		playerTurn = CheckersData.BLACK;
                	possibleMoves = gameBoard.getMoves(playerTurn);
                	if (possibleMoves == null)
                		gameOver("Black has no moves.  White wins.");
                	else if (possibleMoves[0].isJump())
                		message.setText("Black must jump.");
                	else
                		message.setText("Black's turn");
                	if(possibleMoves[0] != null) {
                		if(possibleMoves[0].isJump()){
                			gameBoard.makeMove(possibleMoves[0]);
                		}else
                		{
                			Random r = new Random();
                			gameBoard.makeMove(possibleMoves[r.nextInt(possibleMoves.length)]);
                		} 
                	}
                	playerTurn = CheckersData.WHITE;
                	possibleMoves = gameBoard.getMoves(playerTurn);
                	if(possibleMoves[0].isJump()){
                		message.setText("White must jump.");
                	}else{
                		message.setText("White's turn.");
                	}
            	}catch(NullPointerException e) {
            		System.out.print("Null pointer exception caught!");
            	}
            }		
            this.startRow = -1;
            update();
        }
        /**
         * update is the method that will update the gameBoard after moves are made. 
         */
        public void update() {
            GraphicsContext g = getGraphicsContext2D();
            g.setFont( Font.font(18) );
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if ( row % 2 == col % 2 )
                        g.setFill(Color.GRAY);
                    else
                        g.setFill(Color.LIGHTGRAY);
                        g.fillRect(col*80, row*80, 80, 80);

                    switch (gameBoard.pieceAt(row,col)) {
                        case CheckersData.WHITE:
                            g.setFill(Color.WHITE);
                            g.fillOval(10 + col*80, 10 + row*80, 60, 60);
                            break;
                        case CheckersData.BLACK:
                            g.setFill(Color.BLACK);
                            g.fillOval(10 + col*80, 10 + row*80, 60, 60);
                            break;
                    }
                }
            }
        }

        /**
         * mousePressed is the method that handles user clicking. The location of a click is stored and later used.
         * @param e
         */
        public void mousePressed(MouseEvent e) {
        	try {
        		if (playingGame == false)
                    message.setText("Click \"New Game\" to start a new game.");
                	if(e.getSource().equals(newGameButton)) {
                		gameBoard.setUp();
                		if(choice == 'C') {
                			playCPU();
                		}else {
                			play2Player();
                		}
                	}
                else if(!computerPlayer) {
                    int col = (int)((e.getX() - 10) / 80);
                    int row = (int)((e.getY() - 10) / 80);
                    if (col >= 0 && col < 8 && row >= 0 && row < 8)
                        getPiece(row,col);
                } else {
                        int col = (int)((e.getX() - 10) / 80);
                        int row = (int)((e.getY() - 10) / 80);
                        if (col >= 0 && col < 8 && row >= 0 && row < 8)
                            getPiece(row,col);
                }
        	}catch(NullPointerException x) {
        		System.out.println("Null pointer exception caught!");
        	}
        }  
 }

/**
 * CheckersData is the class responsible for holding the data of the game. Piece location and validity of moves are stored. 
 * @author Sydney Cochran
 *
 */
    private static class CheckersData {
        static final int EMPTY = 0, WHITE = 1, BLACK = 2;
        int[][] gameBoard;

        /**
         * Constructor for CheckersData sets the gameBoard sizes and sets the pieces on the gameBoard using the setUp method. 
         */
        CheckersData() {
            gameBoard = new int[8][8];
            setUp();
        }
        /**
         * setUp will set the positions of the pieces in the beginning of the game. 
         */
        void setUp() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if ( row % 2 == col % 2 ) {
                        if (row < 3)
                            gameBoard[row][col] = BLACK;
                        else if (row > 4)
                            gameBoard[row][col] = WHITE;
                        else
                            gameBoard[row][col] = EMPTY;
                    }
                    else {
                        gameBoard[row][col] = EMPTY;
                    }
                }
            }
        }

        /**
         * pieceAt is the method to get the piece at a requested location
         * @param row
         * @param col
         * @return content in gameBoard[row][col]
         */
        int pieceAt(int row, int col) {
            return gameBoard[row][col];
        }

        /**
         * makeMove is the method to build a move. 
         * @param move move that has been checked
         */
        void makeMove(Move move) {
            makeMove(move.startRow, move.startCol, move.endRow, move.endCol);
        }

        /**
         * makeMove is the method to build a move.
         * @param fromRow
         * @param fromCol
         * @param toRow
         * @param toCol
         */
        void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
            gameBoard[toRow][toCol] = gameBoard[fromRow][fromCol];
            gameBoard[fromRow][fromCol] = EMPTY;
            if (fromRow - toRow == 2 || fromRow - toRow == -2) {
                int jumpRow = (fromRow + toRow) / 2;
                int jumpCol = (fromCol + toCol) / 2;
                gameBoard[jumpRow][jumpCol] = EMPTY;
            }
        }

        /**
         * getMoves is the method to get all of the legal moves a player has. 
         * @param player player to check moves
         * @return null, list of regular moves, or list of jump moves. 
         */
        Move[] getMoves(int player) {
            ArrayList<Move> moves = new ArrayList<>();

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (gameBoard[row][col] == player) {
                        if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                            moves.add(new Move(row, col, row+2, col+2));
                        if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                            moves.add(new Move(row, col, row-2, col+2));
                        if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                            moves.add(new Move(row, col, row+2, col-2));
                        if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                            moves.add(new Move(row, col, row-2, col-2));
                    }
                }
            }
            if (moves.size() == 0) {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (gameBoard[row][col] == player) {
                            if (hasMove(player,row,col,row+1,col+1))
                                moves.add(new Move(row,col,row+1,col+1));
                            if (hasMove(player,row,col,row-1,col+1))
                                moves.add(new Move(row,col,row-1,col+1));
                            if (hasMove(player,row,col,row+1,col-1))
                                moves.add(new Move(row,col,row+1,col-1));
                            if (hasMove(player,row,col,row-1,col-1))
                                moves.add(new Move(row,col,row-1,col-1));
                        }
                    }
                }
            }
            if (moves.size() == 0)
                return null;
            else {
                Move[] moveArray = new Move[moves.size()];
                for (int i = 0; i < moves.size(); i++)
                    moveArray[i] = moves.get(i);
                return moveArray;
            }
        } 

        /**
         * canJump is the method to determine if a player can make a jump legally. 
         * @param player
         * @param r1
         * @param c1
         * @param r2
         * @param c2
         * @param r3
         * @param c3
         * @return true or false depending on if the piece can legally jump
         */
        private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {

            if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
                return false;
            if (gameBoard[r3][c3] != EMPTY)
                return false; 
            if (player == WHITE) {
                if (gameBoard[r1][c1] == WHITE && r3 > r1)
                    return false; 
                if (gameBoard[r2][c2] != BLACK)
                    return false;  
                return true;  
            }
            else {
                if (gameBoard[r1][c1] == BLACK && r3 < r1)
                    return false;  
                if (gameBoard[r2][c2] != WHITE)
                    return false; 
                return true;  
            }
        } 
        /**
         * hasMove is the method to determine whether a player has a legal move or not. 
         * @param player
         * @param r1
         * @param c1
         * @param r2
         * @param c2
         * @return true or false depending on if the player has a legal move
         */
        private boolean hasMove(int player, int r1, int c1, int r2, int c2) {

            if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
                return false; 
            if (gameBoard[r2][c2] != EMPTY)
                return false;  
            if (player == WHITE) {
                if (gameBoard[r1][c1] == WHITE && r2 > r1)
                    return false;  
                return true; 
            }
            else {
                if (gameBoard[r1][c1] == BLACK && r2 < r1)
                    return false;  
                return true;  
            }
        }  
    } 
}
