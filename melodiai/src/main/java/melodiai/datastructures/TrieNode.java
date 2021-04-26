package melodiai.datastructures;

import java.util.Arrays;

/**
 * 
 * @author juho
 * 
 * Node object for trie
 */
public class TrieNode {

    private final int NOTES_IN_MIDI = 128;
    private int appears;
    private int key;
    private TrieNode[] children;
    private DynamicList<TrieNode> followers;
    
    public TrieNode() {
        this.key = -128;
        this.children = new TrieNode[600];
        this.followers = new DynamicList<>();
    }

    public TrieNode(int nodeKey) {
        this.key = nodeKey;
        this.children = new TrieNode[600];
        this.followers = new DynamicList<>();
        this.appears = 1;
    }
    
    public int getNodeKey() {
        return this.key;
    }
    
    public TrieNode[] getChildren() {
        return this.children;
    }
    
    public DynamicList<TrieNode> getFollowers() {
        return this.followers;
    }
    
    /**
     * toString method to print node key in recursive trie print
     * @param offset determines how many empty spaces are entered in front of the note key.
     * @return 
     */
    public String toStringWithOffset(int offset) {
        char[] offs = new char[offset];
        Arrays.fill(offs, ' ');
        return new String(offs) + this.key;
    }

    @Override
    public String toString() {
        return "" + key;
    }
    
    public boolean hasChildren() {
        for (TrieNode child : children) {
            if (child != null) {
                return true;
            } 
        }
        return false;
    }
    
    public void increaseAppearance() {
        this.appears++;
    }
    
    public int getAppearances() {
        return this.appears;
    }
    

}
