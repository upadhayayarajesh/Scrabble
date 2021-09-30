
package scrabble;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static scrabble.Main.cons;
/*
 this class is to draw he  gui board ofr the scrabble game.
 */
public class Gui extends Canvas {
    private GraphicsContext graphicsContext;
    private Board board;
    public Gui(Board board){
        this.board = board;
        graphicsContext = this.getGraphicsContext2D();
        if(board!=null) {
            this.setHeight(board.getPosition() * cons);
           this.setWidth(board.getPosition() * cons);
        }
    }
// it uses the square method to draw  board of square.
    public void draw(){
        if(board.isHvMove()){
            board.undoHVMOve();
        }
        int position;
        position = board.getPosition();
        String [][] str = board.getBoard();

        for(int i = 0; i<position; i++){
            for(int j = 0; j<position; j++){
                int x= i*cons;
                int y = j*cons;
                int lMulty = 1;
                int wMulty = 1;
                if(str[i][j].charAt(0)=='2'){
                    wMulty = 2;
                }else if(str[i][j].charAt(0)=='3'){
                    wMulty = 3;
                }else if(str[i][j].charAt(1)=='2'){
                    lMulty = 2;
                }else if(str[i][j].charAt(1)=='3'){
                    lMulty = 3;
                }
                char c = (char)0;
                if(board.isChar(i,j)){
                    c = board.getChar(i,j);
                }

                square(y,x,c,wMulty,lMulty);
            }
        }
    }
// this method draw a square
    private void square(int x, int y, char c, int w, int l){
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(x,y,cons,cons);
        if(w == 2){
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(x,y,cons,cons);
        }else if(w == 3){
            graphicsContext.setFill(Color.DARKGREEN);
            graphicsContext.fillRect(x,y,cons,cons);
        }else if(l == 2){
            graphicsContext.setFill(Color.LIGHTBLUE);
            graphicsContext.fillRect(x,y,cons,cons);
        }else if(l == 3){
            graphicsContext.setFill(Color.DARKBLUE);
            graphicsContext.fillRect(x,y,cons,cons);
        }
        if(c>0){
            if(c<'a'){
                c = (char)(c + 32);
            }
            if(graphicsContext.getFill().equals(Color.DARKBLUE)){
                graphicsContext.setFill(Color.WHITE);
            }else{
                graphicsContext.setFill(Color.BLACK);
            }
            graphicsContext.setFont(new Font(20));
            graphicsContext.fillText(Character.toString(c),x,y+20);
        }
        graphicsContext.setStroke(Color.YELLOW);
        graphicsContext.strokeRect(x,y,cons,cons);
    }}

