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
    private byte noteKey;
    private TrieNode[] children;
    private DynamicList<TrieNode> followers;
    
    public TrieNode() {
        this.noteKey = -128;
        this.children = new TrieNode[NOTES_IN_MIDI];
        this.followers = new DynamicList<>();
    }

    public TrieNode(byte noteKey) {
        this.noteKey = noteKey;
        this.children = new TrieNode[NOTES_IN_MIDI];
        this.followers = new DynamicList<>();
        this.appears = 1;
    }
    
    public byte getNoteKey() {
        return this.noteKey;
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
        int note = this.noteKey;
        char[] offs = new char[offset];
        Arrays.fill(offs, ' ');
        return new String(offs) + this.noteKey;
    }

    @Override
    public String toString() {
        return "" + noteKey;
    }
    
    public boolean hasChildren() {
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null) {
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