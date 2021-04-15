package melodiai.melodiai;

import melodiai.datastructures.Trie;
import melodiai.midi.MidiParser;

public class Main {
    
    


    public static void main(String[] args) {
        MidiParser midiParser = new MidiParser();
        String[] files = {
            "Midifiles/m1.mid",
            "Midifiles/m2.mid",
            "Midifiles/m3.mid",
            "Midifiles/m4.mid",
            "Midifiles/m5.mid",
            "Midifiles/m6.mid"
        };
        
        Trie trie = new Trie(3);
        
        trie.put(midiParser.parseMidi(files));
 
        //trie.printFollowers(new byte[]{65,69,77});
        trie.print();
        
    }
    
}
