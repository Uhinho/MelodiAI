package melodiai.midi;

import melodiai.datastructures.DynamicList;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * 
 * @author juho
 * 
 * Object for parsing MIDI files to byte data
 */

public class MidiParser {
    
    private final int NOTE_ON = 144;
    private final int NOTE_OFF = 128;

    /**
     * Parses note keys from MIDI files
     * @param files array of paths to MIDI files
     * @return returns a list of all the note keys in given array in order of appearance
     */
    public DynamicList<Byte> parseMidi(String[] files) {
        
        DynamicList<Byte> notesList = new DynamicList<Byte>();
        
        for (String file: files) {
            try {
                Sequence sequence = MidiSystem.getSequence(new File(file));

                int trackNumber = 0;
                for (Track track :  sequence.getTracks()) {
                    trackNumber++;
                    for (int i = 0; i < track.size(); i++) {
                        MidiEvent event = track.get(i);
                        //System.out.print("@" + event.getTick() + " "); LATER USE
                        MidiMessage message = event.getMessage();
                        if (message instanceof ShortMessage) {
                            ShortMessage sm = (ShortMessage) message;
                            if (sm.getCommand() == NOTE_ON) {
                                int key = sm.getData1();
                                //System.out.println("Note on, key: " + key) ; LATER USE
                                notesList.insert((byte) key);
                            } else if (sm.getCommand() == NOTE_OFF) {
                                int key = sm.getData1();
                                //System.out.println("Note off, key: " + key); LATER USE
                            } else {
                                //System.out.println("Command: " + sm.getCommand()); LATER USE
                            }
                        }
                    }    
                }

            } catch (InvalidMidiDataException ex) {
                Logger.getLogger(MidiParser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MidiParser.class.getName()).log(Level.SEVERE, null, ex);
            }


            
        }
        
        return notesList;
    }
    
}
