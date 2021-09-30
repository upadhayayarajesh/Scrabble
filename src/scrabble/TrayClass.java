
package scrabble;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.LinkedList;

import static scrabble.Main.cons;

/*
this class is to draw the  human tray in the GUI.
 */
public class TrayClass extends Canvas {
    // creates a variable of the tray.
    private Tray tray;
    // a boolean variable.
    private boolean PBoolean;
    //  graphicsContext vabiable to use the graphics coontext of the canvas.
    private GraphicsContext graphicsContext;
    private LinkedList<Integer> playedTray;
    // to check for the lastChar.
    private boolean lastChar;
    // positon for x and y.
    private  int y= 0;
    private  int x= 0;
    // constructor class for the TrayCLass.
    public TrayClass(Tray tray){
        lastChar = true;
        this.tray = tray;
        graphicsContext = this.getGraphicsContext2D();
        this.setHeight(cons);
        this.setWidth(300);
        playedTray =new LinkedList<>();
        PBoolean = false;
    }
    // Draw method to draw the huamn tray in the gui .
    public void draw(){
        LinkedList<Character> list = tray.getTrayList();
        int count = 0;
        for(Character c: list){
            x=cons*count;
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRect(x,y,cons,cons);
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.strokeRect(x,y,cons,cons);
            graphicsContext.setFont(new Font( 30));
            graphicsContext.fillText(String.valueOf(c), x+10,y+20);
            if(playedTray.contains(x/cons)){
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(x,y,cons,cons);
            }
            count++;
        }

    }
    // to get the played character.
    public LinkedList<Integer> getPlayed() {
        return playedTray;
    }
    // to set if it is boolean.
    public boolean isBoolean() {
        return PBoolean;
    }
    // to get the final character played.
    public boolean isFinalChar() {
        return lastChar;
    }
    // to set the final  charaacter played.
    public void setFinalChar(boolean lastChar) {
        this.lastChar = lastChar;
    }
    // to set boolean
    public void setBoolean(boolean aBoolean) {
        this.PBoolean = aBoolean;
    }

}
