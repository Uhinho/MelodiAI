package Midi;

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



public class MidiParser {
    
    private final int NOTE_ON;
    private final int NOTE_OFF;

    public MidiParser() {
        this.NOTE_ON = 144;
        this.NOTE_OFF = 128;
    }
    
    public void parseMidi(String file) {
        
        try {
            Sequence sequence = MidiSystem.getSequence(new File(file));
            
            int trackNumber = 0;
            for (Track track :  sequence.getTracks()) {
                trackNumber++;
                System.out.println("Track " + trackNumber + ": size = " + track.size());
                System.out.println();
                
                for (int i=0; i < track.size(); i++) {
                    MidiEvent event = track.get(i);
                    System.out.print("@" + event.getTick() + " ");
                    MidiMessage message = event.getMessage();
                    if (message instanceof ShortMessage) {
                        ShortMessage sm = (ShortMessage) message;
                        System.out.print("Channel: " + sm.getChannel() + " ");
                        if (sm.getCommand() == NOTE_ON) {
                            int key = sm.getData1();
                            System.out.println("Note on, key: " + key) ;
                        } else if (sm.getCommand() == NOTE_OFF) {
                            int key = sm.getData1();
                            System.out.println("Note off, key: " + key);
                        } else {
                            System.out.println("Command: " + sm.getCommand());
                        }
                    }
                }
                
                System.out.println();
            }
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(MidiParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MidiParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
