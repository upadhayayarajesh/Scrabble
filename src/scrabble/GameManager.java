

package scrabble;
import java.util.LinkedList;
import static scrabble.Main.*;
public class GameManager {
    // cretes a board
    private Board board;
    // creates tray for human and computer.
    private Tray humanTray;
    private Tray computerTray;
    // to store the raminag tray for  game.
    private Tray tray2;
    // creates  a  temporary tray for the human to store value of tray
    private Tray tray1;
    // cretes a dictionry reader
    private DictionaryReader dictionaryReader;
    // playerscreated.
    private Player humanPlayer;
    private Player computerPlayer;
    // turn for human and computer.
    private char turn;
    // gameOver check .
    private boolean isGameOver;
    // score for player
    private int humanScore;
    private int comScore;
    //  string variable ofr the last owrd played.
    private String lastWordHuman;
    private String lastWordComputer;
     // checks for winner.
    private Player winner;
    // checks of the first move or not boolean.
    private boolean isFirstMove;
    // constructor class for the  gameManager.
    public GameManager(){
        this.comScore = 0;
        this.board= new Board(boardPath);
        this.board.createBoard();
        this.tray2 = new Tray(tilePath);
        this.tray1 = new Tray(tilePath);
        this.humanTray = new Tray(boardPath);
        humanTray.draw(7);
        computerTray = new Tray(boardPath);
        computerTray.draw(7);
        dictionaryReader = new DictionaryReader(dictionaryPath);
        humanPlayer = new Player(humanTray);
        computerPlayer = new Player(computerTray);
        computerPlayer.ComputerPlayer(computerTray,board, dictionaryReader);
        turn = 'H';
        isGameOver = false;
        humanScore = 0;
        lastWordHuman = "";
        lastWordComputer = "";
        winner = null;
        this.isFirstMove = true;
    }
    // for checking the first move.
    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }
    public boolean isFirstMove() {
        return isFirstMove;
    }
    // to return a boneyard  for the remaining tiles.
    public LinkedList<Character> getTray2() {
        return boneyard;
    }
    // to get and  the score for the human and computer score.
    public int getHumanScore() {
        return humanScore;
    }
    public void setHumanScore(int humanScore) {
        this.humanScore = humanScore;
    }
    public int getComScore() {
        return comScore;
    }
    // get for the board, humanTray, winner, gameOver, computerpLayer, humanPlayer,.
    public Board getBoard() {
        return board;
    }
    public Tray getHumanTray() {
        return humanTray;
    }
    public Player getWinner() {
        return winner;
    }
    public boolean isGameOver() {
        return isGameOver;
    }
    public Player getComputerPlayer() {
        return computerPlayer;
    }
    public Player getHumanPlayer() {
        return humanPlayer;
    }
    // finding the word played by the human in  board.
    public boolean finalWordHuman(String word){
        if(dictionaryReader.getTrie().Search(word.toLowerCase())){
            return true;
        }else {
            return false;
        }
    }
    // is  human is playing character on the board or not.
    public boolean continuousHumanMove(){
        LinkedList<int[]> index = board.getIntHumanList();
        LinkedList<int[]> wIndex = board.getHumanIndex();
        for(int [] arrayList : index){
            boolean array = false;
            for(int [] arrlist2: wIndex){
                if(arrayList[0] == arrlist2[0] && arrayList[1] == arrlist2[1]){
                    array = true;
                    break;
                }
            }
            if(!array){
                return false;
            }
        }
        return true;
    }
    // checks for the game is over or not.
    public boolean gameOver(Player humanPlayer, Player computerPlayer){
        if(tray2.getSize() == 0 &&(humanPlayer.getTray().getSize() == 0 || computerPlayer.getTray().getSize() == 0)){
            return true;
        }
        if(lastWordComputer.equals("") && lastWordHuman.equals("")){
            return true;
        }
        return false;
    }
    // set the winner
    public void setWinner() {
        int reduceComputer = 0;
        int reduceHuman = 0;
        int increComputer = 0;
        int incrHuman = 0;
        int finalComputer = 0;
        int finalHuman = 0;
        for(int  i = 0; i<computerPlayer.getTray().getSize(); i++){
            reduceComputer = reduceComputer + tray1.getScore(computerPlayer.getTray().getTrayList().get(i));
        }
        for(int i = 0; i<humanPlayer.getTray().getSize(); i++){
            reduceHuman = reduceHuman + tray1.getScore(humanPlayer.getTray().getTrayList().get(i));
        }
        if(computerPlayer.getTray().getSize() == 0){
            increComputer = reduceHuman;
        }
        if(humanPlayer.getTray().getSize() == 0){
            incrHuman = reduceComputer;
        }
        finalComputer = comScore - reduceComputer + increComputer;
        incrHuman = humanScore - reduceHuman + incrHuman;
        if(finalComputer != finalHuman){
            if(finalComputer>finalHuman){
                comScore = finalComputer;
                winner = computerPlayer;
            }else {
                humanScore = finalHuman;
                winner = humanPlayer;
            }
        }else {
            if(comScore>humanScore){
                winner = computerPlayer;
            }else if(humanScore>comScore){
                winner = humanPlayer;
            }
        }
    }
    // computer tray
    public void setComputerTray(String word, LinkedList<int[]> coordinates){
        LinkedList<Character> list = new LinkedList<>();
        int count = 0;
        for(int[] arr:coordinates){
            if(board.isChar(arr[0],arr[1])){
                count++;
            }else {
                list.add(word.charAt(count));
                count++;
            }
        }
        for(Character c: list){
            if(c>='a' && c<='z'){
                computerTray.getTrayList().removeFirstOccurrence('*');
            }else {
                computerTray.getTrayList().removeFirstOccurrence(c);
            }
        }
        if(tray2.getSize()>=list.size()){
            computerTray.draw(list.size());
        }else if(tray2.getSize()>0){
            computerTray.draw(tray2.getSize());
        }
        computerPlayer.setTray(computerTray);
    }
    // it is like the main method of this class which  play the gmae by palcing the character in the gui board.
    public void play(){
        if(!isGameOver && turn == 'H'){

            String word = humanPlayer.getWord();
            LinkedList<int[]> position = humanPlayer.getFindPosition();
            if(word.length()>0){
                board.updateBoard(word,position);
                turn = 'C';
            }else {
                turn = 'C';
            }
        }

        if(!isGameOver && turn == 'C'){
            computerPlayer.setFindWord();
            if(computerPlayer.isTurn()){
                lastWordComputer = "";
                turn = 'H';
            }else {
                String word = computerPlayer.getWord();
                LinkedList<int[]> position = computerPlayer.getFindPosition();
                setComputerTray(word,position);
                if(word.length()>0){
                    board.updateBoard(word,position);
                    comScore += computerPlayer.getScore();
                    turn = 'H';
                }else{
                    turn = 'C';
                }
                lastWordComputer = word; }
        }
        if(gameOver(humanPlayer,computerPlayer)){
            isGameOver = true;
            setWinner();
        }
    }
}
