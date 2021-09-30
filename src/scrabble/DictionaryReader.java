
package scrabble;
import java.io.BufferedReader;
import java.io.FileReader;
 /*
  this class is for the dictionary   to read it using the trie  classs.
  */
public class DictionaryReader {
    private Trie trie;
    public DictionaryReader(String dPath){
        trie = new Trie();
        String line;
        try {  BufferedReader reader = new BufferedReader(new FileReader(dPath));
            while ((line=reader.readLine()) !=null){
                trie.add(line);
            }
        }catch (Exception e){
        }
    }

    // it  return the trie of the  class.
    public Trie getTrie() {
        return trie;
    }
}
