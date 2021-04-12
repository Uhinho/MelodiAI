package melodiai.midi;

import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
import melodiai.datastructures.TrieNode;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
    
    public void parseMidi(String[] files) {
        
        Trie trie = new Trie(3);
        DynamicList<Byte> dl = new DynamicList<Byte>();
        
        for (String file: files) {
            try {
                Sequence sequence = MidiSystem.getSequence(new File(file));

                int trackNumber = 0;
                for (Track track :  sequence.getTracks()) {
                    trackNumber++;
                    //System.out.println("Track " + trackNumber + ": size = " + track.size());
                    //System.out.println();

                    for (int i=0; i < track.size(); i++) {
                        MidiEvent event = track.get(i);
                        //System.out.print("@" + event.getTick() + " ");
                        MidiMessage message = event.getMessage();
                        if (message instanceof ShortMessage) {
                            ShortMessage sm = (ShortMessage) message;
                            if (sm.getCommand() == NOTE_ON) {
                                int key = sm.getData1();
                                //System.out.println("Note on, key: " + key) ;
                                dl.insert((byte) key);
                            } else if (sm.getCommand() == NOTE_OFF) {
                                int key = sm.getData1();
                                //System.out.println("Note off, key: " + key);
                                //System.out.println("");
                            } else {
                                //System.out.println("Command: " + sm.getCommand());
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
        
        trie.put(dl);
        //System.out.println(trie.includes(new byte[]{52,57}));
        //System.out.println(trie.includes(new byte[]{66,46,61}));
        trie.print();
        //trie.getFollowers(new byte[]{52,57}).print();
        
    }
    
}
