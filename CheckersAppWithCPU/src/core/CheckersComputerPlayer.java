/**
 * CheckersComputerPlayer is the class responsible for creating a computer player and determining its move. The class will store all 
 * possible moves in a linked list. The class extends CheckersLogic.
 * @author Sydney 
 * @version 2.0
 */
package core;
import java.util.LinkedList;
import java.util.Random;

public class CheckersComputerPlayer extends CheckersLogic {

    LinkedList<ComputerMoves> listOfMoves = new LinkedList<>();
/**
 * getCPUMove is a method that will determine the next move for the computer player using the checkCaptureStatusDark, 
 * validDarkCapture, and makeMove methods from the CheckersLogic class. If the computer can capture the other player, 
 * it will. If not, it will pick a random valid move from the list. 
 */
    public void getCPUMove() {
        if (checkCaptureStatusDark()) {
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard.length; j++) {
                    if(i-2 >= 0 && j + 2 < 8 && validDarkCapture(i,i-2,j,j+2)){
                        gameBoard[i][j] = '_';
                        gameBoard[i-1][j+1] = '_';
                        gameBoard[i-2][j+2] = 'o';
                    }
                    if(i + 2 < 8 && j + 2 < 8 && validDarkCapture(i,i+2,j,j+2)){
                        gameBoard[i][j] = '_';
                        gameBoard[i+1][j+1] = '_';
                        gameBoard[i+2][j+2] = 'o';
                    }
                }
            }
        }
       else {
            Random rand = new Random();
            ComputerMoves computerMove = listOfMoves.get(rand.nextInt(listOfMoves.size()));
            makeMove(computerMove.startColumn, computerMove.endColumn, computerMove.startRow, computerMove.endRow);
        }
    }
/**
 * Contructor method for CheckersComputerPlayer class. Goes through the board and adds all possble moves to the the linked list.   
 */
    public CheckersComputerPlayer() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (gameBoard[i][j] == 'o') {
                    if (i - 1 >= 0 && j + 1 < 8 && gameBoard[i - 1][j + 1] == '_') {
                        ComputerMoves newMove = new ComputerMoves(i, i - 1, j, j + 1);
                        listOfMoves.add(newMove);
                    }
                    if (i + 1 < 8 && j + 1 < 8 && gameBoard[i + 1][j + 1] == '_') {
                        ComputerMoves newMove = new ComputerMoves(i, i + 1, j, j + 1);
                        listOfMoves.add(newMove);
                    }
                }
            }
        }
    }
}