
package melodiai.datastructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;



public class Trie {
    
    private TrieNode root;
    private int order;
    
    public Trie(int order) {
        this.root = new TrieNode();
        this.order = order;
    }
    
    private void insert(DynamicList<Byte> notes, int order) {
        
        if (notes.size() > order) {
            TrieNode current = root;
            if (notes.size() == 1) {
                this.addNew(root, notes.get(0));
            } else {
                for (int i = 0; i < notes.size() - order; i++) {
                    for (int y = i; y <= i + order; y++) {
                        byte note = notes.get(y);
                        this.addNew(current, note);
                        current = current.getChildren()[note];
                    }
                    
                    current = root;
                }
            }
        }
    }
    
    public void put(DynamicList<Byte> notes) {
        if (notes.size() > order) {
            this.insert(notes, order);
        } else {
            this.insert(notes, notes.size());
        }
    }
    
    private void addNew(TrieNode root, int note) {
        
        byte noteKey = (byte) note;
        
        if (root.getChildren()[note] == null) {
            TrieNode newNode = new TrieNode(noteKey);
            root.getChildren()[note] = newNode;
            root.getFollowers().insert(newNode);
        } else {
            this.updateNode(root, note);
        }
        
    }
    
    private void updateNode(TrieNode root, int note) {
        TrieNode update = root.getChildren()[note];
        update.increaseAppearance();
        root.getChildren()[note] = update;
    }
    
    
    public DynamicList<TrieNode> getFollowers(byte[] key) {
        
        TrieNode current = root;
        
        for (int i = 0; i < key.length; i++) {
            byte note = key[i];
            
            TrieNode node = current.getChildren()[note];
            
            if (node == null) {
                return null;
            }
            
            current = node;
        }
        
        return current.getFollowers();
    }
    
    public void printFollowers(byte[] key) {
        DynamicList dl = this.getFollowers(key);
        
        for (int i = 0; i < dl.size(); i++) {
            System.out.println(dl.get(i).toString());
        }
    }
    
    /**
     * Checks if sequence is included in the trie
     * @param key array of bytes to find
     * @return true / false
     */
    public boolean includes(byte[] key) {
        int level;
        int length = key.length;
        int noteKey;
        
        TrieNode pointer = root;
        
        for (level = 0; level < length; level++) {
            noteKey = key[level];        
            if (pointer.getChildren()[noteKey] == null) {
                System.out.println("empty");
                return false;
            }
            
            pointer = pointer.getChildren()[noteKey];
        }
        
        return (pointer != null &&  level == order);
    }
    
    /**
     * Helping method to test if the trie is working correctly.
     * Helps to illustrate the trie structure by printing out the structure
     * @param node Node to print
     * @param offset Offset for the recursion
     */
    private void printTrie(TrieNode node, int offset) {
        
        
        for (TrieNode child: node.getChildren()) {
            if (child != null) {
                System.out.println(child.toStringWithOffset(offset) + "(" + child.getAppearances() + ")");
                printTrie(child, offset + 2);
            }
            
        }

    }
    
    /**
     * Print trie from start to finish
     */
    public void print() {
        this.printTrie(root, 0);
    }
    
    public TrieNode getRoot() {
        return this.root;
    }
    
 
}

