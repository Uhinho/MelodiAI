package melodiai.midi;

import melodiai.datastructures.DynamicList;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
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
    private final int SET_TEMPO = 0x51;
    private final int TIME_SIGNATURE = 0x58;
    private final DynamicList<Note> notesList = new DynamicList<>();
    private int numerator = 4; // numerator of time signature
    private int denominator = 4; // denominator of time signature
    private int resolution;
    private int tempo;
    /**
     * Parses note keys from MIDI files
     * @param files array of paths to MIDI files
     */
    public void parseMidi(File[] files) {
        
        
        
    
        for (File file: files) {
            
            try {  
                Sequence sequence = MidiSystem.getSequence(file);
                
                //System.out.println(sequence.getResolution() + "   &    " + sequence.getTickLength());

                int trackNumber = 0;
                for (Track track :  sequence.getTracks()) {
                    trackNumber++;
                    DynamicList<Note> stillPlaying = new DynamicList<>();
                    this.resolution = sequence.getResolution();
                    for (int i = 0; i < track.size(); i++) {
                        MidiEvent event = track.get(i);
                        MidiMessage message = event.getMessage();  
                        if (message instanceof MetaMessage) {
                            this.checkTimeSignature((MetaMessage) message);
                            if (((MetaMessage) message).getType() == SET_TEMPO) {
                                //System.out.println("TEMPO: " + this.getTempoInBpm((MetaMessage) message));
                            }
                        }
                        
                        if (message instanceof ShortMessage) {
                            ShortMessage sm = (ShortMessage) message;
                            
                            int key = sm.getData1();
                            int velocity = sm.getData2();
                            int channel = sm.getChannel();
                            int beat = (int) Math.floor(this.denominator * event.getTick() / (sequence.getResolution() / this.numerator));
                            if(velocity != 0  && sm.getCommand() != NOTE_OFF) {
                               Note note = new Note((int) event.getTick(), channel, velocity, key);
                               stillPlaying.insert(note); 
                            } else {
                                Note temporary = new Note(0, channel, velocity, key);
                                for (int j = 0; j < stillPlaying.size(); j++) {
                                    Note unfinishedNote = stillPlaying.get(j);
                                    if (unfinishedNote.equals(temporary)) {
                                        stillPlaying.remove(j);
                                        unfinishedNote.endNote((int) event.getTick());
                                        
                                        unfinishedNote.setNoteLength(this.checkNoteType(unfinishedNote.getNoteLength()));
                                        notesList.insert(unfinishedNote);
                                        
                                    }
                                }
                            } 
                        }
                    }    
                }

            } catch (InvalidMidiDataException | IOException ex) {
                Logger.getLogger(MidiParser.class.getName()).log(Level.SEVERE, null, ex);
            }


            
        }
        
    }
    
    public int getTempoInBpm(MetaMessage metaMessage) {
        byte[] data = metaMessage.getData();
        
        if (metaMessage.getType() != SET_TEMPO || data.length != 3) {
            throw new IllegalArgumentException("metamessage: " + metaMessage);
        }
        
        int mspq = ((data[0] & 0xFF) << 16) | ((data[1] & 0xFF << 8) | (data[2] & 0xFF));
        int temp = Math.round(60000001f / mspq);
        
        return temp;
    }
    
  
    private void checkTimeSignature(MetaMessage metaMsg) {
        if (metaMsg.getType() == TIME_SIGNATURE) {
            switch(metaMsg.getData()[0]) {
                case 4:
                    break;
                case 2:
                    break;
                case 3:
                    this.numerator = 4;
                    break;
                case 6:
                    this.numerator = 6;
                    this.denominator = 8;
                    break;
                case 7:
                    this.numerator = 7;
                    this.denominator = 8;
                
            }
        }     
    }
    
    /**
     * 
     * @param noteLength length of note in MIDI ticks
     * @return int value of note length multiplied by 1000. Length / 1000 is the note length relative to quarter note. 
     */
    public int checkNoteType(int noteLength) {
        double notelen = (double) 1.0 * noteLength;
        double res = (double) 1.0 * this.resolution;
        
        double result = (double) notelen / res;
        
        // Limit the possible note length to a whole note
        if (result >= 4.0) {
            return 1;
        }
        
        if (result >= 2.0) {
            return 2;
        }
        
        if (result >= 1.0) {
            return 4;
        }
        
        if (result >= 0.5) {
            return 8;
        }
        
        if (result >= 0.25) {
            return 16;
        }
        
        if (result <= 0.125) {
            return 32;
        }
          
        
        return 16;
    }
    
    public DynamicList<Integer> getNoteKeys() {
        
        DynamicList<Integer> listOfNoteKeys = new DynamicList<>();
        
        for (Note note: this.notesList) {
            listOfNoteKeys.insert(note.getKey());
        }
        
        return listOfNoteKeys;
    }
    
    public DynamicList<Integer> getNoteLengths() {
        
        DynamicList<Integer> listOfNoteLengths = new DynamicList<>();
        
        for (Note note: this.notesList) {
            listOfNoteLengths.insert(note.getNoteLength());
        }
        
        return listOfNoteLengths;
    }
    
    public DynamicList<Integer> getVelocities() {
        DynamicList<Integer> listOfVelocities = new DynamicList<>();
        
        for (Note note: this.notesList) {
            listOfVelocities.insert(note.getVelocity());
        }
        
        return listOfVelocities;
    }
    
    public DynamicList<Note> getNoteObjects() {
        return this.notesList;
    }
    
    
    
}
