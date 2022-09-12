/**
 * ComputerMoves is the class responisible for creating a possible move for the computer player. 
 * @author Sydney
 * @version 2.0
 */
package core;

public class ComputerMoves extends CheckersLogic {
	
    int startRow;
    int startColumn;
    int endRow;
    int endColumn;
/**
 * Contstructor method for ComputerMoves class. Takes in parameters listed and stores them in an object of type ComputerMoves.  
 * @param startColumn
 * @param endColumn
 * @param startRow
 * @param endRow
 */
    public ComputerMoves(int startColumn, int endColumn, int startRow, int endRow ){
        this.endColumn = endColumn;
        this.endRow = endRow;
        this.startColumn = startColumn;
        this.startRow = startRow;
    }
}