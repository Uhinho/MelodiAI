package melodiai.melodiai;

import melodiai.midi.MidiParser;

public class Main {
    
    


    public static void main(String[] args) {
        MidiParser midiParser = new MidiParser();
        String[] files = {
            "Midifiles/m1.mid",
        };
        
        midiParser.parseMidi(files);
        
        
    }
    
}
