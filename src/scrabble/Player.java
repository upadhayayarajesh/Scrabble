
package scrabble;
import java.util.LinkedList;
public class Player {
    // variable for tray.
    private Tray tray;
    // to find the trun variable boolean.
    private boolean isTurn;
    // logic varaible form players.
    private Logic logic;
    // to get the position  LinkList of int[]
    private LinkedList<int[]> findPosition;
    // a string of worf to find.
    private String findWord;
    // player constructor.
    public Player(Tray tray){
        this.tray = tray;
        isTurn = false;
        findPosition = new LinkedList<>();
        findWord = "";
    }
     // this sets the tray of player class.
    public void setTray(Tray tray) {
        this.tray = tray;
    }
    // this class get the tray  for players.
    public Tray getTray() {
        return tray;
    }
    // THIS class find the position of the tray.
    public LinkedList<int[]> getFindPosition() {
        return findPosition;
    }
    // this classs set the positon  of the next character position .
    public void setposition(LinkedList<int[]> comingPositon) {
        this.findPosition = comingPositon;
    }
    // this method  gives the word.
    public String getWord() {
        return findWord;
    }
    // this class is to set the oerd as our findingWord.
    public void setWord(String word) {
        this.findWord = word;
    }
    // tis set the computer player the tray board Dictionary reader to  computer.
    public void  ComputerPlayer(Tray tray, Board board, DictionaryReader dictionaryReader){
        this.tray= tray;
        this.logic = new Logic(board, dictionaryReader);
        logic.setTray(tray.getTrayList());
        isTurn =false;
    }
    // this find the word and also it's position.
    public void setFindWord() {
        logic.setPlayingList();
        setposition(logic.getPlayingList());
        setWord(logic.getWord());
        if(logic.getWord().equals("")){
            isTurn = true;
        }else {
            isTurn = false;
        }
    }
    // this class is to find wihch turn is it.
    public  boolean isTurn(){
        return isTurn;
    }
    // this is to get the score.
    public int getScore() {
        return logic.getScore();
    }
}



