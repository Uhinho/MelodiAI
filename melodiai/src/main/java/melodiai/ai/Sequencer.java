
package melodiai.ai;

import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
import melodiai.datastructures.TrieNode;

/**
 *
 * @author juho
 */
public class Sequencer {
    
    public byte[] generateSequence(int length, Trie trie, int order, byte start) {
        
        byte[] list = new byte[length];
        
        list[0] = start;
        
        
        int index = 0;
        while (index < order - 1) {
            
        }
        
        
        return null;
        
    }
    
    private byte getNext(byte[] keyToGet, Trie trie, int from, int to) {
        
        byte[] key =  new byte[to + 1 - from];
        System.arraycopy(keyToGet, from, key, 0, (to + 1 - from));
        byte returnByte;
        
        try {
            
            DynamicList<TrieNode> followingNotes = trie.getFollowers(key);
            
            if (followingNotes.isEmpty() || followingNotes == null) {
                byte[] narrowSearch = new byte[1];
                narrowSearch[0] = key[key.length - 1];
                followingNotes = trie.getFollowers(narrowSearch);
                
                if (followingNotes == null) {
                    byte original = (byte) key[0];
                    return original;
                }
                
                if (followingNotes.size() == 1) {
                    return followingNotes.get(0).getNoteKey();
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            
        }
        
        return 0;
    }
    
    private int countTotalAppearances(DynamicList<TrieNode> nodeList) {
        
        int count = 0;
        
        for(TrieNode node: nodeList) {
            count += node.getAppearances();
        }
        
        return count;
    }
    
    private TrieNode getNextMarkov(int totalCount, DynamicList<TrieNode> nodes) {
        return null; // kesken
    }
    
}
