
package scrabble;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
/*
 this class read the board.txt file and
 it has different method to get the score of the character and how many time it nedd to be palyed in game.
 */
import static scrabble.Main.boneyard;
public class Tray {
    // value of character list.
    private ArrayList< Integer> valueList;
    // ArrayList for character.
    private ArrayList<Character> characterArrayList;
    // how many time a character is used list.
    private ArrayList< Integer> timeList;
    // a list of tray  (character)
    private LinkedList<Character> trayList;
    // 100 character list.
    private LinkedList<Character> fullHundredLinkedList;
    // constructor for Tray class.
    public Tray(String trayPath) {
        this.valueList = new ArrayList<>();
        this.timeList = new ArrayList<>();
        this.characterArrayList = new ArrayList<>();
        this.trayList = new LinkedList<>();
        this.fullHundredLinkedList = new LinkedList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(trayPath));
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split(" ");
                characterArrayList.add(strings[0].charAt(0));
                valueList.add(Integer.parseInt(strings[1]));
                timeList.add(Integer.parseInt(strings[2]));
            }
        } catch (Exception e) {
        }

    }
    // gets the score of the character  given.
    public int getScore(char c){
        for(char ch:characterArrayList){
            if(ch==c){
                return valueList.get(characterArrayList.indexOf(c));
            }
        }
        return 0;
    }
    // create 100 character LinkedList by readoing the file.
    public LinkedList<Character> createFullTray() {

        for (int i = 0; i < characterArrayList.size(); i++) {
            int counter = timeList.get(i);
            while (counter > 0) {
                fullHundredLinkedList.add(characterArrayList.get(i));
                addChar(characterArrayList.get(i));
                counter--;
            }

        }
        Collections.shuffle(fullHundredLinkedList);
        return fullHundredLinkedList;
    }
    public int getSize(){
        return trayList.size();
    }
    // to get the trayList.
    public LinkedList<Character> getTrayList() {
        return trayList;
    }
    // to get a character from the boneyard.
    public void draw(int num){
        for(int i = 0; i <num; i++){
            this.trayList.add(boneyard.get(i));
            boneyard.remove(i);
        }
    }
    // add character  to trayList.
    public void addChar(Character c){
        trayList.add(c);
    }


}