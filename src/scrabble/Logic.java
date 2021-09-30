

package scrabble;
import java.util.*;
public class Logic {
    // creating the board variable of Board.
    private Board board;
    //  linkedList of character  trya varaible.
    private LinkedList<Character> tray;
    // bictionary reader variable to read the dictionary.
    private DictionaryReader dictionaryReader;
    // linkedList of strring type  in a variable name called left
    // for checkign of the left string  form whic word could be formed
    private LinkedList<String> left;

    private HashMap<Integer, HashMap<String, HashMap<int[], Boolean>>> integerHashMapHashMap;
    // creating the linkedList of Node of trie
    private LinkedList<Trie.Node> node;
    // link list of the  int[] for the position.
    private LinkedList<int []> playingList;
    // string  word variable for the word formeded.
    private String word;
    // to  count the score.
    private int score;
    // constructor for the Logic class.
    public Logic(Board board, DictionaryReader dictionaryReader){
        this.score = 0;
        this.board = board;
        tray = new LinkedList<>();
        this.dictionaryReader = dictionaryReader;
        left = new LinkedList<>();
        integerHashMapHashMap = new LinkedHashMap<>();
        node = new LinkedList<>();
        this.playingList = new LinkedList<>();
        this.word = "";
    }
    // getter and setter method for score, word and tray.
    public int getScore() { return score; }
    public LinkedList<int[]> getPlayingList() { return playingList; }
    public String getWord() { return word; }
    public void setTray(LinkedList<Character> tray) { this.tray = tray; }
    //  these method is  able to  check for the word to go left or right goro the prefix of the word.
    private void left(String prefix, Trie.Node node, int limit, int x, int y, boolean move, int xInt, int yInt){
        if(limit>0){
            for (int i = 0; i<26; i++){
                Trie.Node root = node.getChildren(i);
                Character c = (char) ('a' + i);
                if(root != null && (tray.contains(c)|| tray.contains('*'))){
                    boolean usingWildTile = false;
                    Character character = c;
                    if(!(tray.contains(c))){
                        tray.remove(Character.valueOf('*'));
                        character = (char)('a'+i-32);
                        usingWildTile =true;
                    }else {
                        tray.remove(c);
                    }
                    left(prefix+character,root,limit-1, x, y,move, xInt, yInt);
                    if(usingWildTile){
                        tray.add('*');
                    }else {
                        tray.add(c);
                    }
                }
            }
        }
        left.add(prefix);
        this.node.add(node);
        if(board.legalVMove(prefix,x,y-1)){
            right(prefix,node,x,y, move,xInt, yInt);
       }
    }
    public void right(String prefix, Trie.Node node, int x, int y, boolean right, int Xpane, int Ypane){
        if((x> Xpane || y> Ypane) && !board.isChar(x,y) && node.isWord()){
            HashMap<int[],Boolean> map = new HashMap<>();
            LinkedList<int[]> linkedList = board.getWordCoordinates(x,y-1,right,prefix);
            boolean charUsed = false;
            if(tray.size() == 0){
                charUsed = true;
            }
            int score = board.getTotScore(linkedList,prefix,charUsed);
            map.put(new int[]{x,y-1,score}, right);
            HashMap<String, HashMap<int[], Boolean>> wordXYMap = new HashMap<>();
            wordXYMap.put(prefix, map);
            integerHashMapHashMap.put(score,wordXYMap);
        }
        if(y>=0 && y<board.getPosition()){
            if(!board.isChar(x,y)){
                for(int i = 0; i<26; i++){
                    Trie.Node root = node.getChildren(i);
                    Character c = (char)('a'+i);
                    if(root != null && (tray.contains(c) || tray.contains('*')) && board.legalMove(x,y,c)){
                        boolean flag = false;
                        Character character = c;
                        if(!(tray.contains(c))){
                            tray.remove(Character.valueOf('*'));
                            character = (char)('a'+i-32);
                            flag =true;
                        }else {
                            tray.remove(c);
                        }

                        right(prefix+character,root,x,y+1,right,Xpane,Ypane);
                        if(flag){
                            tray.add('*');
                        }else {
                            tray.add(c);
                        }
                    }
                }
            }else {
                char c = board.getChar(x,y);
                Trie.Node n;
                if(c<'a'){
                    n = node.getChildren(c+32-'a');
                }else {
                    n = node.getChildren(c-'a');
                }
                if(n != null){
                    right(prefix+c,n,x,y+1, right,Xpane,Ypane);
                }
            }
        }
    }
    // this mehtod is able to get tall the possible word with the  comuter tray and finding the best eord from them.
    public void allpossibleWord(){
        for (int i = 0; i<board.getPosition(); i++){
            for (int j = 0; j<board.getPosition(); j++){
                if(board.getPoint()[i][j].equals("A")){
                    int left = board.leftTotal(i,j);
                    if(left == 0){
                        String pLeft = board.findWord(i,j,'L');
                        Trie.Node node= dictionaryReader.getTrie().getRoot();
                        for(int  k = 0; k<pLeft.length(); k++){
                            node = node.getChildren(pLeft.toLowerCase().charAt(k)-'a');
                            if(node == null){
                                break;
                            }
                        }
                        if(node == null){
                            break;
                        }
                        right(pLeft,node,i,j,board.isHvMove(),i,j);
                    }else {
                        if(left>=7){
                            left("", dictionaryReader.getTrie().getRoot(),7,i,j,board.isHvMove(),i,j);
                        }else {
                            left("", dictionaryReader.getTrie().getRoot(),left,i,j,board.isHvMove(),i,j);
                        }
                    }
                }
            }
        }
    }
    public void getAllPossWords(){
        integerHashMapHashMap.clear();
        board.setPoints();
        allpossibleWord();
        board.HvMatch();
        board.setPoints();
        allpossibleWord();
        board.undoHVMOve();
        board.setPoints();
    }
    public void setPlayingList() {
        getAllPossWords();
        Comparator<Integer> compar = new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer integer1) {
                return integer.compareTo(integer1);
            }
        };
        LinkedList<Integer> list = new LinkedList<>(integerHashMapHashMap.keySet());
        list.sort(compar);
        if(list.size() == 0 || (list.size() == 1 && list.getFirst() == 0)){
            word = "";
            playingList.clear();
            return;
        }
        this.score = list.getLast();
        HashMap<String, HashMap<int[],Boolean>> hashMap1 = integerHashMapHashMap.get(list.getLast());
        String string = hashMap1.keySet().toArray()[0].toString();
        HashMap<int[],Boolean> hashMap2 = hashMap1.get(string);
        Set<int[]> set = hashMap2.keySet();
        int[][] array = set.toArray(new int[0][0]);
        int x = array[0][0];
        int y = array[0][1];
        playingList = board.getWordCoordinates(x,y, hashMap2.get(array[0]),string);
        word = string;
    }
}
