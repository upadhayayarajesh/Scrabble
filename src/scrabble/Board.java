
package scrabble;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import static scrabble.Main.dictionaryPath;
import static scrabble.Main.tilePath;
public class Board {
    //  a linkedList for  storing the interger showing the position of the  human list.
    private LinkedList<int[]> intHumanList;
    // char  variable for storing the human character
    private char charHuman;
    //  new char boolean for storing the  new character played or not.
    private boolean newChar;
    // string variable for the word played.
    private String humanWord;
    // linked list of integer for storing the  counter of the  intHumanList
    private LinkedList<int[]> humanIndex;
    // for checking the first move.
    private boolean isFirst;
    // for the board
    private String [][] board;
    //  create abboard list.
    private LinkedList<String> boardList;
    // to sotre the postion.
    private int position;
    // is horixzontal  or vertical move.
    private boolean isHvMove;
    // sotring the coordinate of the characted on board.
    private String[][] point;
    // to creeate human and computer tray.
    private Tray tray;
    // for reading the dictionary.
    private DictionaryReader dictionaryReader;
    // to find the horizontal move or not.
    private boolean isHorizontal;
    // counter for tthe player playing.
    private boolean isHuman;
    // board constructor.
    public Board(String boardPath)  {
        this.isFirst = true;
        this.newChar = false;
        this.isHorizontal = false;
        this.boardList = new LinkedList<>();
        this.isHuman = false;
        this.intHumanList = new LinkedList<>();
        String line = null;
        try { BufferedReader reader = new BufferedReader(new FileReader(boardPath));
            while ((line = reader.readLine()) != null){
                boardList.add(line);
            }
        }catch (Exception e){ }
        this.position = Integer.parseInt(boardList.get(0));
        board = new String[position][position];
        isHvMove = false;
        point = new String[position][position];
        for(int i = 0; i< position; i++){
            for(int j = 0; j< position; j++){
                point[i][j] = "*";
            }
        }
        this.tray = new Tray(tilePath);
        this.dictionaryReader = new DictionaryReader(dictionaryPath);
        humanIndex = new LinkedList<>();
    }
    // createBoard of given   row and column
    public void createBoard(){
        for(int i = 0; i< position; i++){
            String [] strArr = boardList.get(i+1).split(" ");
            for(int j = 0; j< position; j++){
                board[i][j] = strArr[j];
            }
        }
    }
    // to set if it is the first move.
    public void setFirst(boolean first) {
        isFirst = first;
    }
    // to set if the word  palyed by the computer.
    public void setHumanWord(String humanWord) {
        this.humanWord = humanWord;
    }
    // to get function to get the Index of the  character playing.( char)
    public LinkedList<int[]> getHumanIndex() {
        return humanIndex;
    }
    // to check for the new charactwer or not.
    public boolean isNewChar() {
        return newChar;
    }
    // set the new character.
    public void setNewChar(boolean newChar) {
        this.newChar = newChar;
    }
    //  human played  list index ( word)
    public LinkedList<int[]> getIntHumanList() {
        return intHumanList;
    }
    // retrun board.
    public String[][] getBoard(){
        return board;
    }
    // set the index of the   word played.
    public void setWordInd(){
        humanIndex.clear();
        StringBuilder bString = new StringBuilder("");
        int x = intHumanList.getFirst()[0];
        int y = intHumanList.getFirst()[1];
        int xx = x;
        int yy = y;
        if(isHorizontal){
            bString.append(findWord(x,y,'L'));
            yy -=  bString.length();
            bString.append(getChar(x,y));
            bString.append(findWord(x,y,'R'));
            humanWord = bString.toString();
            for(int i = 0; i<bString.length(); i++){
                humanIndex.add(new int[]{xx,yy+i});
            }
        }else {
            bString.append(findWord(x,y,'U'));
            xx -= bString.length();
            bString.append(getChar(x,y));
            bString.append(findWord(x,y,'D'));
            humanWord = bString.toString();
            for(int i = 0; i<bString.length(); i++){
                humanIndex.add(new int[]{xx+i,yy});
            }
        }
    }
    // get the word played.
    public String getHumanWord() {
        setWordInd();
        return humanWord;
    }
    // set the character played by human.
    public void setCharHuman(char charHuman) { this.charHuman = charHuman; }
    // check for the position of the next character.
    public boolean isNext(int x, int y){
        int left = y-1;
        int right = y+1;
        int up = x - 1;
        int down = x+1;


        if(up>=0 && isChar(up,y)){
            return true;
        }else if(down< position && isChar(down,y)){
            return true;
        }else if(left >= 0 && isChar(x,left)){
            return true;
        }else if(right< position && isChar(x,right)){
            return true;
        }
        return false;
    }
    // check for the legal move or not.
    public boolean isLegalMove(){
        if(charHuman == (char)0){
            return false;
        }
        if(isHorizontal){
            if(intHumanList.size()==1){
                int x = intHumanList.getLast()[0];
                int y = intHumanList.getLast()[1];
                if(!isFirst && !isNext(x,y)){
                    return false;
                }
            }
            if(intHumanList.size()>1){
                if(intHumanList.getLast()[0] != intHumanList.getFirst()[0]){
                    return false;
                }
            }
            int x = intHumanList.getLast()[0];
            int y = intHumanList.getLast()[1];

            if(!isFirst && !legalMove(x,y, charHuman)){
                return false;
            } }
        return true;
    }
    //  it get the character of the huamn.
    public char getCharHuman() {
        return charHuman;
    }
     // it sets the character for palyer  that is played in the gui board.
    public void setChar(char c, int x, int y){
        StringBuilder str = new StringBuilder(board[x][y]);
        for(int i = 0; i<str.length(); i++){
            if(str.charAt(i) == '.'){
                str.replace(i,i+1,""+c);
                break;
            }
        }
        board[x][y] = str.toString();
    }

    // it remove the  character for the position.
    public void removeChar(int x, int y){
        char first = board[x][y].charAt(0);
        char second = board[x][y].charAt(1);
        if((first>='a' && first<='z') || (first>='A' && first<= 'Z')){
            String str = board[x][y].replaceFirst(""+first, ".");
            board[x][y] = str;
        }else if((second>='a' && second<='z') || (second>='A' && second<= 'Z')){
            String str = board[x][y].replaceFirst(""+second,".");
            board[x][y] = str;
        }
    }
    // it set the boolean for player.
    public void setHuman(boolean human) { isHuman = human; }
    // it return is human playing.
    public boolean isHuman() { return isHuman; }
    // it check for the horizontal position.
    public void setHorizontal(boolean horizontal) { isHorizontal = horizontal; }

    // check if the position is in center or not
    public void HvMatch(){
        String [][] tempBoard = new String[position][position];
        for(int i = 0; i< position; i++){
            for(int j = 0; j< position; j++){
                tempBoard[position -j-1][i] = board[i][j];
            }
        }
        this.board = tempBoard;
        isHvMove = true;
    }
    // get the character of the given position coordinate.
    public char getChar(int x, int y){
        if(isChar(x,y)){
            if((board[x][y].charAt(0)>='a' && board[x][y].charAt(0)<='z') || (board[x][y].charAt(0)>='A' && board[x][y].charAt(0)<='Z')){
                return board[x][y].charAt(0);
            }else {
                return board[x][y].charAt(1);
            }
        }
        return 0;
    }
    // to check for the char of  given position.
    public boolean isChar(int x, int y){
        if((x< position && x>=0 && y< position && y>=0) && ((board[x][y].charAt(0)>='a' && board[x][y].charAt(0)<='z')||
                (board[x][y].charAt(1)>='a' && board[x][y].charAt(1)<='z'))){
            return true;
        }
        return false;
    }
    // to find the  word in the horizontal position.
    public String findWord(int x, int y, char position){
        int count1 = 0;
        int count2 = 0;
        if (position == 'R') {
            count2 = 1;
        } else if (position == 'L') {
            count2 = -1;
        } else if (position == 'U') {
            count1 = -1;
        } else if (position == 'D') {
            count1 = 1;
        }
        int i = x+count1;
        int j = y+count2;
        StringBuilder str = new StringBuilder("");
        while (i< this.position && i>=0 && j< this.position && j>=0 && isChar(i,j)){
            str.append(getChar(i,j));
            i+=count1;
            j+=count2;
        }
        if(count1<0 || count2<0){
            str.reverse();
        }
        return str.toString();
    }
    // to check if the   given word  is legally valid or not.
    public boolean legalVMove(String word, int endX, int endY){
        boolean isLegal = true;
        for(int i = 0; i<word.length(); i++){
            isLegal= legalMove(endX, endY-i, word.charAt(word.length()-1-i));
            if(!isLegal){
                break;
            }
        }
        return isLegal;
    }
    //  to  return if ti is legal move.
    public boolean legalMove(int i, int j, char c){
        StringBuilder stringBuilder = new StringBuilder();
        if(i-1>=0 && i+1< position && isChar(i-1,j)&& isChar(i+1,j)){
            stringBuilder.append(findWord(i,j,'U'));
            stringBuilder.append(c);
            stringBuilder.append(findWord(i,j,'D'));
            if(isHvMove){
                stringBuilder.reverse();
            }
            if(!dictionaryReader.getTrie().Search((stringBuilder.toString().toLowerCase()))){
               return false;
            }
        }else if(i-1>=0 && isChar(i-1,j)){
            stringBuilder.append(findWord(i,j,'U'));
            stringBuilder.append(c);
            if(isHvMove){
                stringBuilder.reverse();
            }
            if(!dictionaryReader.getTrie().Search(stringBuilder.toString().toLowerCase())){
                return false;
            }
        }else if(i+1< position && isChar(i+1,j)){
            stringBuilder.append(c);
            stringBuilder.append(findWord(i,j,'D'));
            if(isHvMove){
                stringBuilder.reverse();
            }
            if(!dictionaryReader.getTrie().Search(stringBuilder.toString().toLowerCase())){
                return false;
            }
        }
        return true;
    }
     // to get the positon.
    public int getPosition() {
        return position;
    }
    // to undo the HVMove placed.
    public void undoHVMOve(){
        String[][] temp = new String[position][position];
        if(isHvMove) {
            for (int i = 0; i < position; i++) {
                for (int j = 0; j < position; j++) {
                    temp[j][position - i - 1] = board[i][j];
                }
            }
            this.board = temp;
            isHvMove = false;
        }
    }
     // get the coordintee of the word placed on the board.
    public LinkedList<int[]> getWordCoordinates(int x,int y, boolean isRotated, String word){
        LinkedList<int[]> list = new LinkedList<>();
        for(int i = 0; i<word.length(); i++){
            if(isRotated){

                int [] array= new int[]{y-i,position-x-1};
                System.out.println(Arrays.toString(array));
                list.addFirst(array);
            }else {
                list.addFirst(new int[]{x,y-i});
            }
        }
        return list;
    }
    // to update the board if every move islegal to paly  with a character.
    public void updateBoard(String word, LinkedList<int[]> coordinates){
        int counter = 0;
        for(int [] arr: coordinates){
            if(!isChar(arr[0],arr[1])){
                setChar(word.charAt(counter),arr[0],arr[1]);
                counter++;
            }else {
                counter++;
            }
        }
    }

     // to set the point
    public void setPoints(){
        for(int i = 0; i< position; i++){
            for(int j = 0; j< position; j++){
                point[i][j] = "*";
            }
        }
        for(int i = 0; i< position; i++){
            for(int j = 0; j< position; j++){
                if(isChar(i,j)){
                    if(j-1>=0 && !isChar(i,j-1)){
                        if(point[i][j-1].equals("*")){
                            point[i][j-1] = "A";
                        }
                    }
                    if(j+1< position && !isChar(i,j+1)){
                        if(point[i][j+1].equals("*")){
                            point[i][j+1] = "A";
                        }
                    }
                    if(i-1>=0 && !isChar(i-1,j)){
                        if(point[i-1][j].equals("*")){
                            point[i-1][j] = "A";
                        }
                    }
                    if(i+1< position && !isChar(i+1,j)){
                        if(point[i+1][j].equals("*")){
                            point[i+1][j] = "A";
                        }
                    }
                }
            }
        }
    }
    // to get total socore.
    public int leftTotal(int x, int y){
        int count  = 0;
        if(y-1>=0 && !isChar(x,y-1)){
            while (y-1>=0 && !isChar(x,y-1)){
                count++;
                y--;
            }
            if(y-1>=0 && isChar(x,y-1)){
                count--;
            }
        }
        return count;
    }
    // to get points
    public String[][] getPoint() {
        return point;
    }
     // to get socre .
    private int getScoreL(char c, int x, int y){
        int score = 0;
        if(!(c>='A' && c <= 'Z')){
            score = this.tray.getScore('c');
        }
        char c1 = board[x][y].charAt(1);
        if(!isChar(x,y) && c1>='0' && c1<='9'){
            score = score*(c1-'0');
        }
        return score;
    }
    // to get score of the word.
    public int getScoreWord(LinkedList<int[]> coordinates, String word){
        int score = 0;
        int wMulty = 1;
        boolean flag = false;
        if(isHvMove){
            undoHVMOve();
            flag = true;
        }
        for(int i = 0; i<word.length(); i++){
            char c = word.charAt(i);
            int x = coordinates.get(i)[0];
            int y = coordinates.get(i)[1];
            score += getScoreL(c,x,y);
            if(!isChar(x,y) && board[x][y].charAt(0)>='0' && board[x][y].charAt(0)<='9'){
                wMulty *= board[x][y].charAt(0)-'0';
            }
        }
        score *= wMulty;
        if(flag){
            HvMatch();
        }
        return score;
    }
    // to get hte total socre of the word palyed.
    public int getTotScore(LinkedList<int[]> position, String word, boolean charUsed){
        int score =0;
        LinkedList<LinkedList<int[]>> linkedListLinkedList = new LinkedList<>();
        LinkedList<String> words = new LinkedList<>();
        linkedListLinkedList.add(position);
        words.add(word);
        boolean moved = false;
        if(isHvMove){
            undoHVMOve();
            moved = true;
        }
        char positi = '*';
        if(position.getFirst()[0] == position.get(1)[0]){
            positi = 'H';
        }else {
            positi = 'V';
        }
        int count = 0;
        for(int[] array: position){
            int x = array[0];
            int y = array[1];
            char c = word.charAt(count);
            StringBuilder bString = new StringBuilder();
            if(!isChar(x,y)){
                if(positi=='H'){
                    String up = findWord(x,y,'U');
                    String down = findWord(x,y,'D');
                    int newX = x;
                    if(up.length()>0){
                        newX = newX - up.length();
                    }
                    bString.append(up);
                    bString.append(c);
                    bString.append(down);
                    String newWord = bString.toString();
                    if(newWord.length()>1){
                        LinkedList<int[]> index = new LinkedList<>();
                        for (int i = 0; i<newWord.length(); i++){
                            int xInd = newX + i;
                            index.add(new int[]{xInd,y});
                        }
                        linkedListLinkedList.add(index);
                        words.add(newWord);
                    }
                }else{
                    String left = findWord(x,y,'L');
                    String right = findWord(x,y,'R');
                    int newY = y;
                    if(left.length()>0){
                        newY = newY-left.length();
                    }
                    bString.append(left);
                    bString.append(c);
                    bString.append(right);
                    String updatedWord = bString.toString();
                    if(updatedWord.length()>1){
                        LinkedList<int[]> index = new LinkedList<>();
                        for(int i = 0; i<updatedWord.length(); i++){
                            int yInd = newY + i;
                            index.add(new int[]{x,yInd});
                        }
                        linkedListLinkedList.add(index);
                        words.add(updatedWord);
                    }
                }
            }
            count++;
        }
        for(int i = 0; i<words.size(); i++){
            score+= getScoreWord(linkedListLinkedList.get(i),words.get(i));
        }
        if(moved){
            HvMatch();
        }
        if(charUsed){
            score+=50;
        }
        return score;
    }
    // to check if is Hporizontal or vertical move or not.
    public boolean isHvMove() {
        return isHvMove;
    }
}
