
package melodiai.datastructures;



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
                for (int i = 0; i < notes.size() - order; i++){
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
    
    public boolean includes(byte[] key) {
        int level;
        int length = key.length;
        int noteKey;
        
        TrieNode pointer = root;
        
        for (level = 0; level < length; level++) {
            noteKey = key[level];
            System.out.println(noteKey);
            
            if (pointer.getChildren()[noteKey] == null) {
                System.out.println("empty");
                return false;
            }
            
            pointer = pointer.getChildren()[noteKey];
            System.out.println(pointer.getChildren()[noteKey]);
        }
        
        return (pointer != null &&  level == order);
    }
    
    /**
     * Helping method to test if the trie is working correctly.
     * Helps to illustrate the trie structure.
     * 
     */
    public void print() {
        TrieNode curr = root;
        
        if (!curr.hasChildren()) {
            System.out.println("end");
        } else {
            for (int i = 0; i < curr.getChildren().length; i++) {
                if (curr.getChildren()[i] != null){
                    System.out.println("");
                    System.out.println("");
                    System.out.println(i + "PARENT: " + curr.getChildren()[i]);
                    System.out.println("APPEARS: " + curr.getChildren()[i].getAppearances());
                        for (int j = 0; j < curr.getChildren()[i].getChildren().length; j++) {
                            if (curr.getChildren()[i].getChildren()[j] != null) {
                                System.out.println(j + "CHILD" + curr.getChildren()[i].getChildren()[j]);
                                System.out.println("APPEARS: " + curr.getChildren()[i].getChildren()[j].getAppearances());
                                
                                for (int y = 0; y < curr.getChildren()[i].getChildren()[j].getChildren().length; y++) {
                                    if (curr.getChildren()[i].getChildren()[j].getChildren()[y] != null) {
                                        System.out.println(y + "SECOND" + curr.getChildren()[i].getChildren()[j].getChildren()[y]);
                                        System.out.println("APPEARS: " + curr.getChildren()[i].getChildren()[j].getChildren()[y].getAppearances());
                                        }
                                    }
                    
                            }   
                        }
                }
            }

        }
    }
}
