package DataStructures;


public class TrieNode {
    
    private byte noteKey;
    private TrieNode[] children;
    
    public TrieNode() {
        this.noteKey = -128;
        this.children = new TrieNode[256];
    }

    public TrieNode(byte noteKey) {
        this.noteKey = noteKey;
        this.children = new TrieNode[128];
    }
    
    public TrieNode next(byte noteKey) {
        return children[noteKey];
    }
}
