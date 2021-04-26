
package melodiai.ai;

import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
import melodiai.datastructures.TrieNode;

/**
 *
 * @author juho
 */
public class Sequencer {
    
    public int[] generateSequence(int length, Trie trie, int order, int start) {
        
        int[] list = new int[length];
        
        list[0] = start;
        
        int index = 0;
        while (index < order - 1) {
            int next = this.getNext(list, trie, 0, index);
            list[index + 1] = next;
            index++;
        }
        
        for(int i = 0; i < length - order; i++) {
            int next = getNext(list, trie, i, i + order - 1);
            list[i + order] = next;
        }
        
        return list;
        
    }
    
    /**
     * Get next note to the sequence
     * @param currentSequence current sequence
     * @param from start index for the new search key
     * @param to end index for the new search key
     * @return next note
     */
    private int getNext(int[] currentSequence, Trie trie, int from, int to) {
        
        int[] key =  new int[to + 1 - from];
        System.arraycopy(currentSequence, from, key, 0, (to + 1 - from));
            
        DynamicList<TrieNode> followingNotes = trie.getFollowers(key);
        
        // If key doesn't exist in trie, find followers of shorter key
        if (followingNotes == null || followingNotes.isEmpty()) {
            int[] narrowSearch = new int[1];
            
            //use only the last note of the sequence
            narrowSearch[0] = key[key.length - 1];
            followingNotes = trie.getFollowers(narrowSearch);
        }
        
        // If still no followers, return the root note
        if (followingNotes == null) {
            int rootNote =  key[0];
            return rootNote;
        }
        
        // lone follower is returned directly
        if (followingNotes.size() == 1) {
            return followingNotes.get(0).getNodeKey();
        }
        
        // Next note is calculated as weighted random key by appearance amounts as weights.
        return this.getWeightedRandom(followingNotes).getNodeKey();
    }
    
    private int countTotalAppearances(DynamicList<TrieNode> nodeList) {
        
        int count = 0;
        
        for(TrieNode node: nodeList) {
            count += node.getAppearances();
        }
        
        return count;
    }
    
    /**
     * Returns weighted random node from given list
     * @param nodeList
     * @return 
     */
    private TrieNode getWeightedRandom(DynamicList<TrieNode> nodeList) {
        
        int totalCount = this.countTotalAppearances(nodeList);
        double randomNum = Math.random() * totalCount;
        
        double countAppearances = 0.0;
        
        for (TrieNode node: nodeList) {
            countAppearances += (double) node.getAppearances();
            if (countAppearances > randomNum) {
                return node;
            }
        }
        return null; 
    }
    
}
