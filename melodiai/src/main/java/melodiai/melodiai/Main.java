package melodiai.melodiai;

import melodiai.ai.Sequencer;
import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
import melodiai.datastructures.TrieNode;
import melodiai.midi.MidiParser;

public class Main {
    
    


    public static void main(String[] args) {
        MidiParser midiParser = new MidiParser();
        Sequencer seq = new Sequencer();
        String[] files = {
            "Midifiles/m1.mid",
            "Midifiles/m2.mid",
            "Midifiles/m3.mid",
            "Midifiles/m4.mid",
            "Midifiles/m5.mid",
            "Midifiles/m6.mid"
        };
        
        Trie trie = new Trie(4);
 
        trie.put(midiParser.parseMidi(files));
        
        byte[] test = new byte[]{65,69,77};
 
        //trie.printFollowers(new byte[]{65,69,77});
        //trie.print();
        
        //DynamicList<TrieNode> nodes = trie.getFollowers(new byte[]{65,69});
        
        // System.out.println(seq.getWeightedRandom(nodes).getNoteKey());
        
        /*for (TrieNode node: nodes) {
            System.out.println(node.toStringWithOffset(0) + "  :" + node.getAppearances());
        } */
        
        byte[] s = seq.generateSequence(100, trie, 4, (byte) 55);
        
        for (byte b: s) {
            System.out.println(b);
        }
        
    }
    
}
