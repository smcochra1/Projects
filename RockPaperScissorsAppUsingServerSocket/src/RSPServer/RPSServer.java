package RSPServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 
 * @author Sydney Cochran 
 * @version 1.0
 * RPSServer is the class that handles all game logic and runs the server. 
 */
public class RPSServer extends Application {
	
    private int session = 1; // Number a session

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * 
     * @author Sydney Cochran 
     * @Version 1.0
     * SER216 Project 5
     * Last Updated: 04/14/2022
     * Handling is a nested class responsible for handing and running the server for a Rock-Paper-Scissors game. 
     */
    class Handling implements Runnable{
        private Socket player1;
        private Socket player2;
        private DataInputStream fromPlayer1;
        private DataOutputStream toPlayer1;
        private DataInputStream fromPlayer2;
        private DataOutputStream toPlayer2;
        private boolean continueToPlay = true;
        
        /**
         * Handling() is the constructor for class Handling. 
         * @param player1 First player in server
         * @param player2 Second player in server
         */
        public Handling(Socket player1, Socket player2) {
            this.player1 = player1;
            this.player2 = player2;
        }
        
        /**
         * send() is the method responsible for sending information from the server. 
         * @param out
         * @param move
         * @throws IOException
         */
        private void send(DataOutputStream out, int move)
                throws IOException {
            out.writeInt(move); // Send row index

        }
        
        /**
         * getWinner() is the method responsible for determining the winner of a Rock-Paper-Scissor game
         * @param player1
         * @param player2
         * @return
         */
        private boolean getWinner(int player1, int player2){
            boolean result = false;
            switch(player1){
                case 1:
                    if(player2 == 1){
                        result = false;
                    } else if (player2 == 2){
                        result = false;
                    } else if( player2 == 3){
                        result = true;
                    }
                    break;
                case 2:
                    if(player2 == 1){
                        result = true;
                    } else if (player2 == 2){
                        result = false;
                    } else if( player2 == 3){
                        result = false;
                    }
                    break;
                case 3:
                    if(player2 == 1){
                        result = false;
                    } else if (player2 == 2){
                        result = true;
                    } else if( player2 == 3){
                        result = false;
                    }
                    break;
            }
            return result;
        }
        
        /**
         * tieCheck() is the method to check if there is a tie. It checks if the player's scores are the same. 
         * @param player1
         * @param player2
         * @return
         */
        private boolean tieCheck(int player1, int player2){
            return player1==player2;
        }
        
        /**
         * run() is the method responsible for running the game through the server. 
         */
        public void run() {
            try {
                DataInputStream fromPlayer1 = new DataInputStream(player1.getInputStream());
                DataOutputStream toPlayer1 = new DataOutputStream(player1.getOutputStream());
                DataInputStream fromPlayer2 = new DataInputStream( player2.getInputStream());
                DataOutputStream toPlayer2 = new DataOutputStream(player2.getOutputStream());
                toPlayer1.writeInt(1);
                int counter = 1;
                while (true) {
                    int player1Move = fromPlayer1.readInt();
                    int player2Move = fromPlayer2.readInt();
                    if(getWinner(player1Move,player2Move)){
                        toPlayer1.writeInt(1);
                        toPlayer2.writeInt(1);
                        send(toPlayer1, player2Move);
                        send(toPlayer2, player1Move);
                    } else if(tieCheck(player1Move,player2Move)){
                        toPlayer1.writeInt(3);
                        toPlayer2.writeInt(3);
                        send(toPlayer1, player2Move);
                        send(toPlayer2, player1Move);
                    } else if (getWinner(player2Move,player1Move)){
                        toPlayer1.writeInt(2);
                        toPlayer2.writeInt(2);
                        send(toPlayer1, player2Move);
                        send(toPlayer2, player1Move);
                    }
                    if(counter == 5){
                        System.out.println("enter");
                        toPlayer1.writeInt(4);
                        toPlayer2.writeInt(4);
                        player1Move = fromPlayer1.readInt();
                        player2Move = fromPlayer2.readInt();
                        System.out.println("received");
                        send(toPlayer1,player2Move);
                        send(toPlayer2,player1Move);
                        if(player1Move == 1 && player2Move == 1){
                            System.out.println("extra");
                            counter = 0;
                            continue;
                        } else {
                            break;
                        }
                    }
                    counter++;
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }                                  
   }

    /**
     * start() is the method to set up the GUI using JavaFX and creating a server. 
     */
    @Override
    public void start(Stage primaryStage) {
        TextArea text = new TextArea();
        Scene scene = new Scene(new ScrollPane(text), 450, 200);
        primaryStage.setTitle("RPSServer"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        new Thread( () -> {
            try {
                ServerSocket socket = new ServerSocket(8000);
                Platform.runLater(() -> text.appendText(new Date() +
                        ": Server started at socket 8000\n"));
                while (true) {
                    Platform.runLater(() -> text.appendText(new Date() + ": Wait for players to join " + session + '\n'));
                    Socket player1 = socket.accept();
                    Platform.runLater(() -> {
                        text.appendText(new Date() + ": Player 1 joined session " + session + '\n');
                        text.appendText("Player 1's IP address" + player1.getInetAddress().getHostAddress() + '\n');
                    });
                    new DataOutputStream(
                            player1.getOutputStream()).writeInt(1);
                    Socket player2 = socket.accept();
                    Platform.runLater(() -> {
                        text.appendText(new Date() + ": Player 2 joined session " + session + '\n');
                        text.appendText("Player 2's IP address" +player2.getInetAddress().getHostAddress() + '\n');
                    });
                    new DataOutputStream(
                            player2.getOutputStream()).writeInt(2);
                    Platform.runLater(() ->
                            text.appendText(new Date() +": Start a thread for session " + session++ + '\n'));
                    new Thread(new Handling(player1, player2)).start();
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
