
package melodiai.datastructures;

public class Trie {
    
    private TrieNode root;
    private int order;
    
    public Trie(int order) {
        this.root = new TrieNode();
        this.order = order;
    }
    
    private void insert(DynamicList<Integer> nodes, int order) {
        
        if (nodes.size() > order) {
            TrieNode current = root;
            if (nodes.size() == 1) {
                this.addNew(root, nodes.get(0));
            } else {
                for (int i = 0; i < nodes.size() - order; i++) {
                    for (int y = i; y <= i + order; y++) {
                        int nodeKey = nodes.get(y);
                        this.addNew(current, nodeKey);
                        current = current.getChildren()[nodeKey];
                    }
                    
                    current = root;
                }
            }
        }
    }
    
    public void put(DynamicList<Integer> nodes) {
        if (nodes.size() > order) {
            this.insert(nodes, order);
        } else {
            this.insert(nodes, nodes.size());
        }
    }
    
    private void addNew(TrieNode root, int nodeKey) {
        
        int noteKey = nodeKey;   
        if (root.getChildren()[nodeKey] == null) {
            TrieNode newNode = new TrieNode(noteKey);
            root.getChildren()[nodeKey] = newNode;
            root.getFollowers().insert(newNode);
        } else {
            this.updateNode(root, nodeKey);
        }   
    }
    
    private void updateNode(TrieNode root, int note) {
        TrieNode update = root.getChildren()[note];
        update.increaseAppearance();
        root.getChildren()[note] = update;
    }
    
    
    public DynamicList<TrieNode> getFollowers(int[] key) {
        
        TrieNode current = root;
        
        for (int i = 0; i < key.length; i++) {
            int note = key[i];          
            TrieNode node = current.getChildren()[note];  
            if (node == null) {
                return null;
            }
            current = node;
        } 
        return current.getFollowers();
    }
    
    public void printFollowers(int[] key) {
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
    public boolean includesNoteSequence(int[] key) {
        int level;
        int length = key.length;
        int noteKey;   
        TrieNode pointer = root;
        
        for (level = 0; level < length; level++) {
            noteKey = key[level];        
            if (pointer.getChildren()[noteKey] == null) {
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
    
    public int getRandomRootChild() {
        int random = (int) (Math.random() * this.root.getChildren().length);
        
        while (this.root.getChildren()[random] == null){
            random = (int) (Math.random() * this.root.getChildren().length);
        } 
        return this.root.getChildren()[random].getNodeKey();
    }
}

