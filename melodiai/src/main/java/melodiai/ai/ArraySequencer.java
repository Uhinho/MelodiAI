package melodiai.ai;

import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
import melodiai.datastructures.TrieNode;

public class ArraySequencer {

    /**
     * Generate sequence based on markov chain.
     *
     * @param length length of the sequence
     * @param trie
     * @param order order of the markov chain
     * @param start key of the start node
     * @return sequence of integers generated based on markov chain rules
     */
    public int[] generateSequence(
            final int length,
            final Trie trie,
            final int order,
            final int start) {

        int[] list = new int[length];
        list[0] = start;
        int index = 0;

        while (index < order - 1) {
            int next = this.getNext(list, trie, 0, index);
            list[index + 1] = next;
            index++;
        }

        for (int i = 0; i < length - order; i++) {
            int next = getNext(list, trie, i, i + order - 1);
            list[i + order] = next;
        }
        return list;
    }

    /**
     * Generate list of note durations.
     *
     * @param lengthInBars length of note sequence in bars
     * @param trie
     * @param order markov order
     * @param start key of the start node
     * @param timeSigNumerator time signature numerator
     * @return list of note durations in double
     */
    public DynamicList<Double> generateRhytmSequence(
            final int lengthInBars,
            final Trie trie,
            final int order,
            final int start,
            final double timeSigNumerator) {

        // Base for the note lengths
        int[] rhytmSequence = this.generateSequence(lengthInBars * 10,
                                                    trie,
                                                    order,
                                                    start);
        DynamicList<Double> noteLengthsList = new DynamicList<>();
        int notes = 0;
        int bars = 0;
        double fullBar = timeSigNumerator / 4;
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
                double nextLength = this.findFittingNote(
                        noteLengthsList.get(notes - 1),
                        availableSpaceInBar,
                        trie, timeSigNumerator);
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
     * Get note duration in double.
     *
     * @param noteSig note signature (1 = whole note, 2 = half note, 4 = quarter
     * note, ...)
     * @return converted value from signature to double length
     */
    private double getNoteLength(final int noteSig) {
        double noteSigDouble = (double) noteSig;
        return (1.0 / noteSigDouble);
    }

    /**
     * Finds a next note for sequence of note durations.
     *
     *
     * @param previous duration of the previous note
     * @param maxLength max duration of the next note
     * @param trie
     * @param timeSigNumerator time signature numerator for the bar
     * @return double value of next note duration
     */
    private double findFittingNote(
            final double previous,
            final double maxLength,
            final Trie trie,
            final double timeSigNumerator) {

        int[] search = new int[1];

        // Note length conversion from int to double
        double previousValueInInteger = timeSigNumerator / previous;
        search[0] = (int) previousValueInInteger;
        DynamicList<TrieNode> nodes = trie.getFollowers(search);

        if (nodes == null || nodes.isEmpty()) {
            return maxLength;
        }

        for (int i = 0; i < nodes.size(); i++) {
            double possibleDur = this.getNoteLength(nodes.get(i).getNodeKey());

            // If returned note would be too long to fit the bar,
            // max length fitting to the bar is returned as default.
            if (possibleDur <= maxLength) {
                return possibleDur;
            }
        }
        return maxLength;
    }

    /**
     * Get next note to the sequence.
     *
     * @param currentSequence current sequence
     * @param trie trie structure that includes the key values
     * @param from start index for the new search key
     * @param to end index for the new search key
     * @return next note
     */
    private int getNext(
            final int[] currentSequence,
            final Trie trie,
            final int from,
            final int to) {

        int[] key = new int[to + 1 - from];
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
            int rootNote = key[0];
            return rootNote;
        }
        // lone follower is returned directly
        if (followingNotes.size() == 1) {
            return followingNotes.get(0).getNodeKey();
        }
        // Next note is calculated as weighted random
        return this.getWeightedRandom(followingNotes).getNodeKey();
    }

    private int countTotalAppearances(final DynamicList<TrieNode> nodeList) {

        int count = 0;
        for (TrieNode node : nodeList) {
            count += node.getAppearances();
        }
        return count;
    }

    /**
     * Returns weighted random node from given list.
     *
     * @param nodeList list of nodes to pick from
     * @return trie node selected by weighted random pick
     */
    private TrieNode getWeightedRandom(final DynamicList<TrieNode> nodeList) {

        int totalCount = this.countTotalAppearances(nodeList);
        double randomNum = Math.random() * totalCount;
        double countAppearances = 0.0;

        for (TrieNode node : nodeList) {
            countAppearances += (double) node.getAppearances();
            if (countAppearances > randomNum) {
                return node;
            }
        }
        return null;
    }
}
