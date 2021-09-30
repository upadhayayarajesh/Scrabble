
package scrabble;
/*
This class is to  read the dictionary  using trie class
 */
public class Trie {
    // root variable .
    private Node root;
    public Trie() {
        root = new Node('\0');
    }
    // add method to add a word to the root (Node)
    public void add(String word) {
        Node tempRoot = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (tempRoot.children[c - 'a'] == null) {
                tempRoot.children[c - 'a'] = new Node(c);
            }
            tempRoot = tempRoot.children[c - 'a'];
        }
        tempRoot.isWord = true;
    }
    // this class search  for the word passed into it.
    public boolean Search(String word) {
        Node node = getNode(word);
        return node != null && node.isWord;
    }
    // this class is to search for the prefix passed into it.
    public boolean startsWith(String perfix) {
        if (getNode(perfix) != null) {
            return true;
        }
        return false;
    }
    // this return the node of the word passed.
    private Node getNode(String word) {
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (current.children[c - 'a'] == null) {
                return null;
            }
            current = current.children[c - 'a'];
        }
        return current;
    }
    public Node getRoot() {
        return root;
    }
    /*
     Inner helper class Node class for the trie method.
     */
    class Node {
        public char c;
        Node[] children;
        boolean isWord;

        Node(char c) {
            isWord = false;
            children = new Node[26];
        }
        public boolean isWord() {
            return isWord;
        }
        public Node getChildren(int index) {
            return children[index];
        }
    }

}
