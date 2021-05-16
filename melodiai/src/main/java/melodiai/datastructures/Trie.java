package melodiai.datastructures;

public class Trie {

    /**
     * @param root root of the trie
     */
    private final TrieNode root;
    /**
     * @param order markov order
     */
    private final int order;

    /**
     * Constructor for trie data structure.
     *
     * @param markovOrder markov markovOrder
     */
    public Trie(final int markovOrder) {
        this.root = new TrieNode();
        this.order = markovOrder;
    }

    /**
     * Insert values from DynamicList to Trie.
     *
     * @param nodes DynamicList of values
     * @param markovOrder markov markovOrder
     */
    private void insert(final DynamicList<Integer> nodes, final int markovOrder) {

        if (nodes.size() > markovOrder) {
            TrieNode current = root;
            if (nodes.size() == 1) {
                this.addNew(root, nodes.get(0));
            } else {
                for (int i = 0; i < nodes.size() - markovOrder; i++) {
                    for (int y = i; y <= i + markovOrder; y++) {
                        int nodeKey = nodes.get(y);
                        this.addNew(current, nodeKey);
                        current = current.getChildren()[nodeKey];
                    }

                    current = root;
                }
            }
        }
    }

    /**
     * Public method to insert list of nodes.
     *
     * @param nodes list of values
     */
    public void put(final DynamicList<Integer> nodes) {
        if (nodes.size() > order) {
            this.insert(nodes, order);
        } else {
            this.insert(nodes, nodes.size());
        }
    }

    /**
     * Adds new value to trie or updates if already exists.
     *
     * @param rootNode rootNode of the trie
     * @param nodeKey key to insert
     */
    private void addNew(final TrieNode rootNode, final int nodeKey) {

        int noteKey = nodeKey;
        if (rootNode.getChildren()[nodeKey] == null) {
            TrieNode newNode = new TrieNode(noteKey);
            rootNode.getChildren()[nodeKey] = newNode;
            rootNode.getFollowers().insert(newNode);
        } else {
            this.updateNode(rootNode, nodeKey);
        }
    }

    /**
     * If node key already exists, increases appearance amt.
     *
     * @param rootNode rootNode of the trie
     * @param note key value
     */
    private void updateNode(final TrieNode rootNode, final int note) {
        TrieNode update = rootNode.getChildren()[note];
        update.increaseAppearance();
        rootNode.getChildren()[note] = update;
    }

    /**
     * Get list of followers for given key sequence.
     *
     * @param key sequence to find
     * @return list of possible followers for given sequence
     */
    public DynamicList<TrieNode> getFollowers(final int[] key) {

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

    /**
     * Checks if sequence is included in the trie.
     *
     * @param key sequence to find
     * @return boolean true if sequence exists
     */
    public boolean includesNoteSequence(final int[] key) {
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
        return (pointer != null && level == order);
    }

    /**
     * Get root node.
     * @return root
     */
    public TrieNode getRoot() {
        return this.root;
    }

    /**
     * Get key of randomly picked child node of the root.
     * @return node key
     */
    public int getRandomRootChild() {
        int random = (int) (Math.random() * this.root.getChildren().length);

        while (this.root.getChildren()[random] == null) {
            random = (int) (Math.random() * this.root.getChildren().length);
        }
        return this.root.getChildren()[random].getNodeKey();
    }
}
