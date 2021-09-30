
package scrabble;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static scrabble.Main.cons;
/*
 this class is to display the core in the gui of jhe board.
 */
public class DisplayScore extends Canvas {
    private GraphicsContext graphicsContext;
    private  int computerScore;
    private int humanScore;
    private int leftTray;
    // constructor  for DisplaySocre class.
    public DisplayScore(){
        this.graphicsContext = this.getGraphicsContext2D();
        this.computerScore = 0;
        this.humanScore = 0;
        this.leftTray = 86;
        this.setHeight(550);
        this.setWidth(350);
    }
    // this class set thev computer score.
    public void setComputerScore(int computerScore) {
        this.computerScore = computerScore;
    }
    //  set the core of the Human.
    public void setHumanScore(int humanScore){
        this.humanScore = humanScore;
    }
    // sets the left  unplayed  character ofr the tile.
    public void setLeftTray(int leftTray) {
        this.leftTray = leftTray;
    }
    // this class draw  the score , computer score in the gui board o nthe right side of the board.
    public void draw(){
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0,300,200);
        graphicsContext.setFont(new Font( 20));
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Human score: "+humanScore,20,cons);
        graphicsContext.fillText("Computer score: "+computerScore, 20, 80);
        graphicsContext.fillText("Remaining Characters: "+ leftTray, 20, 120);
        }
}
