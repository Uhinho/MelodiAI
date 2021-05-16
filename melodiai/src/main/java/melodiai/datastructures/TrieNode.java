package melodiai.datastructures;

/**
 *
 * @author juho
 *
 * Node object for trie
 */
public class TrieNode {

    /**
     * @param appears times this node has appeared in the training data
     */
    private int appears;
    /**
     * @param key key value of the node
     */
    private int key;
    /**
     * @param children children of this node
     */
    private TrieNode[] children;
    /**
     * @param followers possible followers for this node
     */
    private DynamicList<TrieNode> followers;

    /**
     * Empty constructor for node object.
     */
    public TrieNode() {
        this.key = -128;
        this.children = new TrieNode[600];
        this.followers = new DynamicList<>();
    }

    /**
     * Constructor with nodeKey value as parameter.
     *
     * @param nodeKey key value for the node
     */
    public TrieNode(final int nodeKey) {
        this.key = nodeKey;
        this.children = new TrieNode[600];
        this.followers = new DynamicList<>();
        this.appears = 1;
    }

    /**
     * Get node key value.
     *
     * @return key value
     */
    public int getNodeKey() {
        return this.key;
    }

    /**
     * Get children of the node.
     *
     * @return child nodes
     */
    public TrieNode[] getChildren() {
        return this.children;
    }

    /**
     * Get possible followers for this node.
     *
     * @return list of possible followers for this node
     */
    public DynamicList<TrieNode> getFollowers() {
        return this.followers;
    }

    /**
     *
     * @return String value for node
     */
    @Override
    public String toString() {
        return "" + key;
    }

    /**
     *
     * @return boolean true if node has children
     */
    public boolean hasChildren() {
        for (TrieNode child : children) {
            if (child != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Increase amount of times this node key has appeared in the data.
     */
    public void increaseAppearance() {
        this.appears++;
    }

    /**
     *
     * @return amount of appearances for this value.
     */
    public int getAppearances() {
        return this.appears;
    }
}
