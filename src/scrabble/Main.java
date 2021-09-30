
package scrabble;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.*;
/*
this is the main class that uses all the other classes to make the game to be played.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    // a contant for the square of the gui.
    public  static int cons =35;
    // board , tilr and dictionary file path locationreader.
    public  static String boardPath="src//resources//scrabble_board.txt";
    public  static String dictionaryPath="src//resources//dictionary.txt";
    public  static String tilePath="src//resources//scrabble_tiles.txt";
    // a tray class instance to play game.
    public static Tray tray = new Tray(tilePath);
    // create a 100 character tray  for the game.
    public  static LinkedList<Character> boneyard= tray.createFullTray();
    //  start method.
    public void start(Stage stage){
        // game manger variable to use game logic.
        GameManager gameManager = new GameManager();
        // // human trya to paly that into board.
        TrayClass humanTray = new TrayClass(gameManager.getHumanTray());
        // board gui to amke a board gui.
        Gui boardGui = new Gui(gameManager.getBoard());
        // DisplayScore variable to display the score in the gui board.
        DisplayScore bDisplayScore = new DisplayScore();
        // a pane to add everything into it.
        BorderPane borderPane = new BorderPane();
        // a paly button to play the game.
        Button play =  new Button("Play");
        // enter button to place a word into the gui board.
        Button enter = new Button("Enter");
        // Hbox of 10 space to  add buttons and human tray in it.
        HBox hBox = new HBox(10);
        // adding   button to hBOx.
        hBox.getChildren().addAll(play,enter);
        // setting the hBox to the top of the scene.
        borderPane.setTop(hBox);
        // setting the title of the scene to scrabble.
        stage.setTitle("Scrabble");
        // drawing the board on scene.
        boardGui.draw();
        // drawing the HumanTray in scene.
        humanTray.draw();
        bDisplayScore.draw();
        //
        List<Character> chioce = new ArrayList<>();
        for(int i = 'a'; i<='z'; i++){
            chioce.add((char)i);
        }
        // this is a CHoiceDialog for the  '*' sign foerthe choice to be palced.
        ChoiceDialog<Character> characterChoiceDialog = new ChoiceDialog<>('a', chioce);
        // wA
        Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setHeaderText(null);
        warning.setContentText("!!! Select Play Button !!!");
        // play  button set on action. game start.
        play.setOnMouseClicked(event -> {
            if(!gameManager.getBoard().isHuman()){
                gameManager.getBoard().setHuman(true);
                humanTray.setBoolean(true);
                gameManager.getBoard().setHorizontal(true);
            }
        });
        // when clicked on the HumanTray. it enable to select the character from humanTray place it on the  board.
        humanTray.setOnMouseClicked(event -> {
            humanTray.setBoolean(true);
            if(humanTray.isBoolean()){
                LinkedList<Character> list = gameManager.getHumanTray().getTrayList();
                double x = event.getX();
                int clickedX = (int)(x/cons);
                LinkedList<Integer> trayPlayed = humanTray.getPlayed();
                if(x<=280 && !trayPlayed.contains(clickedX)){
                    if(!humanTray.isFinalChar()){
                        humanTray.getPlayed().removeLast();
                    }
                    humanTray.setFinalChar(false);
                    humanTray.getPlayed().add(clickedX);
                    char c = list.get(clickedX);
                    if(c=='*'){
                        Optional<Character> result = characterChoiceDialog.showAndWait();
                        if (result.isPresent()){
                            c = result.get();
                        }else {
                            trayPlayed.removeLast();
                            c = (char)0;
                        }
                    }
                    gameManager.getBoard().setNewChar(true);
                    gameManager.getBoard().setCharHuman(c);
                    humanTray.draw();
                }}
        });
        boardGui.setOnMouseClicked(event -> {
            if(gameManager.getBoard().isHuman() && gameManager.getBoard().isNewChar() && !humanTray.isFinalChar()){
                double x = event.getX();
                double y = event.getY();
                int indexY = (int)x/cons;
                int indexX = (int)y/cons;
                gameManager.getBoard().getIntHumanList().add(new int[]{indexX, indexY});
                if(gameManager.getBoard().isLegalMove()){
                    gameManager.getBoard().setChar(gameManager.getBoard().getCharHuman(),indexX,indexY);
                    gameManager.getBoard().setNewChar(false);
                    humanTray.setFinalChar(true);
                    boardGui.draw();
                }else {
                    humanTray.setFinalChar(true);
                    gameManager.getBoard().setNewChar(false);
                    gameManager.getBoard().getIntHumanList().removeLast();
                    humanTray.getPlayed().removeLast();
                    humanTray.draw();
                }

            }
        });
        // warning for  not a word.
        Alert warning2 = new Alert(Alert.AlertType.WARNING);
        warning2.setHeaderText(null);
        warning2.setContentText("!!! Not a word!!!");
        // warning for the gamer over.
        Alert warning3 = new Alert(Alert.AlertType.WARNING);
        warning3.setHeaderText("!!!Game Over!!!");
        // when enter button is  setOnAction.
        enter.setOnMouseClicked(event -> {
            if(humanTray.isBoolean()){
                String word = gameManager.getBoard().getHumanWord();
                if(gameManager.getBoard().getIntHumanList().size() == 0){
                }else {
                    if(!gameManager.continuousHumanMove()){
                        for(int[] array: gameManager.getBoard().getIntHumanList()){
                            gameManager.getBoard().removeChar(array[0],array[1]);
                        }
                    }else if(gameManager.isFirstMove() &&
                            (!gameManager.getBoard().isChar(gameManager.getBoard().getPosition()/2, gameManager.getBoard().getPosition()/2) ||
                                    gameManager.getBoard().getIntHumanList().size()<2)){
                        for(int[] arr: gameManager.getBoard().getIntHumanList()){
                            gameManager.getBoard().removeChar(arr[0],arr[1]);
                        }
                    } else if(!gameManager.finalWordHuman(word)){
                        for(int[] arr: gameManager.getBoard().getIntHumanList()){
                            gameManager.getBoard().removeChar(arr[0],arr[1]);
                        }
                        warning2.showAndWait();
                    }else {
                        gameManager.getBoard().setFirst(false);
                        gameManager.setFirstMove(false);
                        boolean charUsed = false;
                        if(humanTray.getPlayed().size() == 7){
                            charUsed = true;
                        }
                        for(int[] arr: gameManager.getBoard().getIntHumanList()){
                            gameManager.getBoard().removeChar(arr[0],arr[1]);
                        }
                        int score = gameManager.getHumanScore();
                        score = score + gameManager.getBoard().getTotScore(gameManager.getBoard().getHumanIndex(), word,charUsed);
                        gameManager.setHumanScore(score);
                        for(int i:humanTray.getPlayed()){
                            gameManager.getHumanPlayer().getTray().getTrayList().set(i, '?');
                        }
                        gameManager.getHumanPlayer().getTray().getTrayList().removeAll(Collections.singletonList((Character)'?'));
                        if(gameManager.getTray2().size()>=humanTray.getPlayed().size()){
                            gameManager.getHumanTray().draw(humanTray.getPlayed().size());
                        }else if(gameManager.getTray2().size()>0){
                            gameManager.getHumanTray().draw(gameManager.getTray2().size());
                        }
                        gameManager.getHumanPlayer().setWord(word);
                        gameManager.getHumanPlayer().setposition(gameManager.getBoard().getHumanIndex());
                        gameManager.play();
                        bDisplayScore.setComputerScore(gameManager.getComScore());
                        bDisplayScore.setHumanScore(gameManager.getHumanScore());
                        bDisplayScore.setLeftTray(gameManager.getTray2().size());
                        bDisplayScore.draw();
                        if(gameManager.isGameOver()){
                            warning3.showAndWait();
                            stage.close();
                        }
                    }
                    humanTray.setFinalChar(true);
                    humanTray.setBoolean(false);
                    gameManager.getBoard().setNewChar(false);
                    gameManager.getBoard().setHorizontal(false);
                    gameManager.getBoard().setHuman(false);
                    gameManager.getBoard().getIntHumanList().clear();
                    gameManager.getBoard().getHumanIndex().clear();
                    gameManager.getBoard().setHumanWord("");
                    humanTray.getPlayed().clear();
                    boardGui.draw();
                    humanTray.draw();
                }
            }

        });
        // adding humanTray to Hbox.
        hBox.getChildren().add(humanTray);
        //setting the borderPane at the Left of scene.
        borderPane.setRight(bDisplayScore);
        // settign the boardGUi in  thr center
        borderPane.setCenter(boardGui);
        // creating a scene to add the  borderPane
        Scene scene= new Scene(borderPane);
        // adding a scene to stage
        stage.setScene(scene);
        // showing the stage.
        stage.show();
    }
}
