package RSPClient;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * @author Sydney Cochran 
 * @version 1.0
 * RSPClient is the class responsible for handling player moves (with JavaFX UI). It initiates the connection 
 * to the server. 
 */
public class RPSClient extends Application {

    private Button rock, paper, scissors, playAgain;
    private Scene play, showResult;
    public static Text openMessage, logicMessage, beginMessage, endMessage;
    private int playerChoice;
    private int otherMove;
    private String result;
    private int player1Score = 0;
    private int player2Score = 0;
    private int me;
    private int otherPlayer;
    private final int rockID = 1;
    private final int paperID = 2;
    private final int scissorsID = 3;
    private DataInputStream fromRSPServer;
    private DataOutputStream toRSPServer;
    private boolean continuePlaying = true;
    private boolean waiting = true;
    private String host = "localhost";   
    
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * connect() is the method used to connect to a server by creating and using a socket. 
     * A new thread is made, players wait for eachother to join, then the game will begin.
     * While the game is going, information is recieved from and sent to the server.  
     */
    private void connect() {
        try {
            Socket socket = new Socket(host, 8000);
            fromRSPServer = new DataInputStream(socket.getInputStream());
            toRSPServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        new Thread(() -> {
            try {
                int player = fromRSPServer.readInt();
                if (player == 1) {
                    me = 1;
                    otherPlayer = 2;
                    Platform.runLater(() -> {
                        openMessage.setText("Waiting for an opponent to join...");
                    });
                    fromRSPServer.readInt();
                    Platform.runLater(() ->
                            openMessage.setText("Player 2 has joined. Select your move."));
                }
                else if (player == 2) {
                    me = 2;
                    otherPlayer = 1;
                    Platform.runLater(() -> {
                        openMessage.setText("Select your move");
                    });
                }
                Platform.runLater(() -> beginMessage.setText("Your points: " + player1Score + "\t Opponent points: "+ player2Score));
                int counter = 1;
                while (continuePlaying) {
                    if (player == 1) {
                        waitForPlayerMove(); 
                        sendMoveOut(); 
                        receiveServerInfo();
                    }
                    else if (player == 2) {
                        waitForPlayerMove();
                        sendMoveOut();
                        receiveServerInfo(); 
                    }
                    if(counter == 5){
                        receiveServerInfo();
                        if(playerChoice == 1 && otherMove == 1){
                            counter = 0;
                            player1Score = 0;
                            player2Score = 0;
                            System.out.println("restart");
                            Platform.runLater(() -> openMessage.setText("Make move."));
                            continue;
                        } else {
                            continuePlaying = false;
                            Platform.runLater(() -> openMessage.setText("Game over."));
                        }
                    }
                    counter++;
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    
    /**
     * start() is the method to set up the GUI using JavaFX. 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Rock-Paper-Scissors");               
        Image rockImage = new Image("rock.jpg");
        Image paperImage = new Image("paper.png");
        Image scissorsImage = new Image("scissors.jpg");
        Font messageFont = Font.font("Comic Sans MS", FontWeight.BOLD, 15);
        openMessage = new Text("Welcome to Rock Paper Scissors!");
        openMessage.setFont(messageFont);
        logicMessage = new Text(" ");
        logicMessage.setFont(messageFont);
        beginMessage = new Text("");
        beginMessage.setFont(messageFont);
        endMessage = new Text();
        endMessage.setFont(messageFont);
        Font buttonFont = Font.font("Comic Sans MS", FontWeight.BOLD, 11);
        rock = new Button(" Rock ");
        rock. setStyle("-fx-background-color: WHITE;");
        rock.setGraphic(new ImageView(rockImage));
        rock.setFont(buttonFont);
        paper = new Button(" Paper ");
        paper. setStyle("-fx-background-color: WHITE;");
        paper.setGraphic(new ImageView(paperImage));
        paper.setFont(buttonFont);
        scissors = new Button("Scissors");
        scissors. setStyle("-fx-background-color: WHITE;");
        scissors.setGraphic(new ImageView(scissorsImage));
        scissors.setFont(buttonFont);
        VBox messageBox = new VBox();
        messageBox.getChildren().addAll(logicMessage,openMessage, beginMessage);
        messageBox.setAlignment(Pos.CENTER);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(rock, paper, scissors);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(5, 5, 5, 5));
        buttonBox.setSpacing(15);
        HBox endMessageBox = new HBox();
        endMessageBox.getChildren().add(endMessage);
        endMessageBox.setAlignment(Pos.BOTTOM_CENTER);
        VBox mainWindow = new VBox(5);
        mainWindow.setStyle("-fx-background-color: LIGHTBLUE;");
        mainWindow.getChildren().addAll(messageBox, buttonBox);
        mainWindow.setAlignment(Pos.CENTER);
        mainWindow.setPadding(new Insets(15, 15, 15, 15));
        BorderPane mainBorder = new BorderPane();
        mainBorder.setCenter(mainWindow);
        playAgain = new Button("Continue");
        BorderPane resultBorder = new BorderPane();
        VBox results = new VBox(10);
        results.getChildren().addAll(endMessage, playAgain);
        results.setAlignment(Pos.CENTER);
        resultBorder.setCenter(results);
        play = new Scene(mainBorder, 600, 600);
        showResult = new Scene(resultBorder, 600, 220);

        rock.setOnAction(e -> {
            playerChoice = rockID;
            waiting = false;
            logicMessage.setText(" ");
            openMessage.setText("waiting for other player...");
        });
        paper.setOnAction(e -> {
            playerChoice = paperID;
            waiting = false;
            logicMessage.setText(" ");
            openMessage.setText("waiting for other player...");
        });
        scissors.setOnAction(e -> {
            playerChoice = scissorsID;
            waiting = false;
            logicMessage.setText(" ");
            openMessage.setText("waiting for other player...");
        });
        playAgain.setOnAction(e -> {
            primaryStage.setScene(play);
        });
        primaryStage.setScene(play);
        primaryStage.show();
        connect();
    }
    /**
     * waitForPlayerMove() is the method used while a player waits for the other player in the server to make a move. 
     * @throws InterruptedException
     */
    private void waitForPlayerMove() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }
    /**
     * sendMoveOut() is the method responsible for sending a move to the server for the other player to recieve. 
     * @throws IOException
     */
    private void sendMoveOut() throws IOException {
        toRSPServer.writeInt(playerChoice);
    }
    /**
     * moveIn() is the method responsible for getting the other players move from the server. 
     * @throws IOException
     */
    private void moveIn() throws IOException {
        otherMove = fromRSPServer.readInt();
    }
    /**
     * makeMove() is the method responsible for logging the players choice of move. (Rock, paper, or scissors)
     * @param i move chosen. 
     * @return result is the string for the chosen move. (Rock, paper, or scissors).
     */
    private String makeMove(int i){
        String result = "";
        if(i == 1){
            result = "Rock";
        } else if(i == 2){
            result = "Paper";
        } else if(i == 3){
            result = "Scissors";
        }
        return result;
    }
    /**
     * receiveServerInfo() is the method to communicate with the server and get the game status. 
     * @throws IOException
     * @throws InterruptedException
     */
    private void receiveServerInfo() throws IOException, InterruptedException {
        int status = fromRSPServer.readInt();
        if(status != 4){
            moveIn();
        }
        if (status == 1) {
            if (me == 1) {
                player1Score++;
                Platform.runLater(() -> openMessage.setText("You won!"));
                Platform.runLater(() -> logicMessage.setText("You picked " + makeMove(playerChoice) +" your opponent picked " + makeMove(otherMove)));
            }
            else if (me == 2) {
                player1Score++;
                Platform.runLater(() ->
                        openMessage.setText("You lost!"));
                Platform.runLater(() -> logicMessage.setText("You picked " + makeMove(playerChoice) + " your opponent picked " + makeMove(otherMove)));
            }
        }
        else if (status == 2) {
            if (me == 2) {
                player2Score++;
                Platform.runLater(() -> openMessage.setText("You won!"));
                Platform.runLater(() -> logicMessage.setText("You picked " + makeMove(playerChoice) + " your opponent picked " + makeMove(otherMove)));
            }
            else if (me == 1) {
                player2Score++;
                Platform.runLater(() ->
                        openMessage.setText("You lost!"));
                Platform.runLater(() -> logicMessage.setText("You picked " + makeMove(playerChoice) + " your opponent picked " + makeMove(otherMove)));
            }
        }
        else if (status == 3) {
            Platform.runLater(() ->
                    openMessage.setText("Tie"));
            Platform.runLater(() -> logicMessage.setText("You picked " + makeMove(playerChoice) + " your opponent picked " + makeMove(otherMove)));
       }
        else {
            System.out.println(me + " " + player1Score + " " + player2Score);
            if (me == 1) {
                if (player1Score > player2Score) {
                    Platform.runLater(() -> openMessage.setText("You won the game. Press Rock to play again or " + "Scissors to quit"));
                } else if (player2Score > player1Score) {
                    Platform.runLater(() -> openMessage.setText("You lost the game. Press Rock to play again or " +  "Scissors to quit"));
                } else {
                    Platform.runLater(() -> openMessage.setText("You tied. Press Rock to play again or " + "Scissors to quit"));
                }
            } else if (me == 2) {
                if (player2Score > player1Score) {
                    Platform.runLater(() -> openMessage.setText("You won the game. Press Rock to play again or " +  "Scissors to quit"));
                } else if(player1Score>player2Score){
                    Platform.runLater(() -> openMessage.setText("You lost the game. Press Rock to play again or " + "Scissors to quit"));
                }else {
                    Platform.runLater(() -> openMessage.setText("You tied. Press Rock to play again or " + "Scissors to quit"));
                }
            }
            waitForPlayerMove();
            sendMoveOut();
            moveIn();
            System.out.println("sent");
        }
        if(me == 1){
            Platform.runLater(() -> beginMessage.setText("Your points: " + player1Score + "\t Opponent points: " + player2Score));
        }else {
            Platform.runLater(() -> beginMessage.setText("Your points: " + player2Score + "\t Opponent points: " + player1Score));
        }
    }  
}
