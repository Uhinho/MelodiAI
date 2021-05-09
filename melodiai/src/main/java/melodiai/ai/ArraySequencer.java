
package melodiai.ai;

import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
import melodiai.datastructures.TrieNode;

public class ArraySequencer {
    
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
    
    public DynamicList<Double> generateRhytmSequence(int lengthInBars, Trie trie, int order, int start, double timeSigNumerator) {
        double timeSigDenominator = 4;
        
        // Base for the note lengths
        int[] rhytmSequence = this.generateSequence(lengthInBars * 10, trie, order, start);
        DynamicList<Double> noteLengthsList = new DynamicList<>();
        
        int notes = 0;
        int bars = 0;  
        double fullBar = timeSigNumerator / timeSigDenominator;
        double availableSpaceInBar = fullBar;
        
        for (int i = 0; i < rhytmSequence.length; i++) {
            if (bars == lengthInBars) {
                break;
            }    
            double nextNoteLength = this.getNoteLength(rhytmSequence[i]);
            
            // check available space  
            if (nextNoteLength <= availableSpaceInBar) {
                noteLengthsList.insert(nextNoteLength);
                notes++;
                availableSpaceInBar -= nextNoteLength;
                
                if (availableSpaceInBar == 0) {
                    bars++;
                    availableSpaceInBar = fullBar;
                }
            } else {
                double nextLength = this.findFittingNote(noteLengthsList.get(notes - 1), availableSpaceInBar, trie, timeSigNumerator);        
                noteLengthsList.insert(nextLength);
                availableSpaceInBar -= nextLength;
                notes++;
                
                if (availableSpaceInBar == 0) {
                    bars++;
                    availableSpaceInBar = fullBar;
                }
            }
        }        
        return noteLengthsList;
    }
    
    /**
     * 
     * @param noteSig note signature (1 = whole note, 2 = half note, 4 = quarter note, ...)
     * @return converted value from signature to double length
     */
    private double getNoteLength(int noteSig) {
        double noteSigDouble = (double) noteSig;     
        return (1.0 / noteSigDouble);
    }
    
    private double findFittingNote(double previous, double maxLength, Trie trie, double timeSigNumerator) {
        
        int[] search = new int[1];
        
        // Note lengths are relative to quarter notes and stored as integers. Conversion is made here.
        double previousValueInInteger = timeSigNumerator / previous;       
        search[0] = (int) previousValueInInteger;
        DynamicList<TrieNode> nodes = trie.getFollowers(search);
        
        if (nodes == null || nodes.isEmpty()) {
            return maxLength;
        }
        
        for (int i = 0; i < nodes.size(); i++) {
            double durationOfPossibleNote = this.getNoteLength(nodes.get(i).getNodeKey());
            
            // If returned note would be too long to fit the bar, max length fitting to the bar is returned as default.
            if (durationOfPossibleNote <= maxLength) {
                return durationOfPossibleNote;
            }       
        }
        return maxLength;
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
