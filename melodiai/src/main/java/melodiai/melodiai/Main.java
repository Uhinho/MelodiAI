package melodiai.melodiai;

import melodiai.midi.MidiParser;

public class Main {
    
    


    public static void main(String[] args) {
        MidiParser midiParser = new MidiParser();
        String[] files = {
            "Midifiles/m1.mid",
            "Midifiles/m2.mid",
            "Midifiles/m3.mid",
            "Midifiles/m4.mid"
        };
        
        midiParser.parseMidi(files);
    
        
    }
    
}
