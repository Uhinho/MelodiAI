package melodiai.midi;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import melodiai.datastructures.DynamicList;


public class MidiBuilder {
    

    private final byte[] GENERAL_MIDI_SOUND_SET = new byte[] {(byte) 0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
    
    
    private Track track;
    private SysexMessage sysex;
    private Sequence sequence;
    private int ticksPerBeat;
    
    
    public MidiBuilder() {
        this.track = null;
        this.sysex = new SysexMessage();
    }
    
    private void createTrack() throws InvalidMidiDataException {
        ticksPerBeat = 48000;
        
        this.sequence = new Sequence(javax.sound.midi.Sequence.PPQ, ticksPerBeat,1);
        this.track = this.sequence.createTrack();
    }
    
    private void setGeneralMidiSysEx() throws InvalidMidiDataException {
        if (this.sysex != null) {
            this.sysex.setMessage(GENERAL_MIDI_SOUND_SET, GENERAL_MIDI_SOUND_SET.length);
        }
    }
    
    private void addMidiEventToTrack(MidiMessage midiMessage, long tick) {
        
        MidiEvent midiEvent = new MidiEvent(midiMessage, tick);
        this.track.add(midiEvent);
        
    }
    
    /*  
        This message consists of six bytes of data.
        The first byte is the status byte and has a hexadecimal value of 0xFF, which means that this is a meta message.
        The second byte is the meta message type 0x51 and signifies that this is a set tempo message.
        The third byte has the value 0x03, which means that there are three bytes remaining.
        The remaining three bytes carry the number of microseconds per quarter note.
    
    
        The status byte 0xFF shows that this is a meta message. 
        The second byte is the meta type 0x51 and signifies that this is a set tempo meta message. 
        The third byte is 3, which means that there are three remaining bytes.
        These three bytes form the hexadecimal value 0x07A120 (500000 decimal), 
        which means that there are 500,000 microseconds per quarter note.
    
        Since there are 60,000,000 microseconds per minute, the message above translates to: 
        set the tempo to 60,000,000 / 500,000 = 120 quarter notes per minute (120 beats per minute).
    
        IF TEMPO IS NOT SET THE DEFAULT IS 120BPM
    */
    
    private void setTempo() throws InvalidMidiDataException {
        
        int bpm = 60;
        // byte[] bt = {(byte)0xFF, (byte)0x51, (byte)0x03, (byte)0x07, (byte)0xA1, (byte)0x20};
        
        byte[] tempoHex = new byte[3]; // big endian 3 bytes
        
        // 60 000 000 microseconds in minute. Tempo = amt of 1/4 notes per minute
        int tempo = 60000000 / bpm;
        
        
        tempoHex[0] = (byte) ((tempo >> 16) & 0xFF);
        tempoHex[1] = (byte) ((tempo >> 8) & 0xFF);
        tempoHex[2] = (byte) ((tempo & 0xFF));
   
        MetaMessage metaMsg = new MetaMessage();
        metaMsg.setMessage(0x51, tempoHex, tempoHex.length);
    }
    
    
    private void setTrackName(String name) throws InvalidMidiDataException {
        
        MetaMessage metaMsg = new MetaMessage();
        metaMsg.setMessage(0x03, name.getBytes(), name.length());
        
        this.addMidiEventToTrack(metaMsg,(long) 0);
    }
    
    private void setOmniMode() throws InvalidMidiDataException {
        //Omni mode to set receiver to take action on any data on any channel
        
        ShortMessage shortMsg = new ShortMessage();
        shortMsg.setMessage(0xB0, 0x7D, 0x00);
        this.addMidiEventToTrack(shortMsg, (long) 0);
    }
    
    private void setPolyOn() throws InvalidMidiDataException {
        
        ShortMessage shortMsg = new ShortMessage();
        shortMsg.setMessage(0xB0, 0x7F, 0x00);
        
        this.addMidiEventToTrack(shortMsg, (long) 0);
    }
    
    private void setInstrument() throws InvalidMidiDataException {
        ShortMessage shortMsg = new ShortMessage();
        
        // By default set to piano 0x00
        shortMsg.setMessage(0xC0, 0x00, 0x00);
        
        this.addMidiEventToTrack(shortMsg, (long) 0);
    }
    
    private void addNotesToTrack(int [] notesArray, DynamicList<Double> lengthList, int [] velocityArray) throws InvalidMidiDataException {
        
        // 1 tick = 8000 microseconds (8 milliseconds)
        long tickCounter = 1;
        for (int i = 0; i < lengthList.size(); i++) {
            
            // note on
            ShortMessage sm = new ShortMessage();
            sm.setMessage(0x90, notesArray[i], velocityArray[i]);
            this.addMidiEventToTrack(sm, tickCounter);
            
            tickCounter += (this.ticksPerBeat * lengthList.get(i));
            
            
            //note off
            sm = new ShortMessage();
            sm.setMessage(0x80, notesArray[i], 0x40);
            this.addMidiEventToTrack(sm, tickCounter);
        }
        
        // end track
        MetaMessage metaMsg = new MetaMessage();
        byte[] endArray = new byte[]{};
        metaMsg.setMessage(0x2F, endArray, 0);
        
        this.addMidiEventToTrack(metaMsg, tickCounter + 19);
        
    }

    private void writeMIDItoFile(String name) throws IOException {
        File file = new File("output/" + name + ".mid");
        MidiSystem.write(sequence, 1, file);
    }
    
    
    public void createMidiFile(String name, int [] notesSequence, int [] velocitySequence, DynamicList<Double> noteLengthSequence) throws InvalidMidiDataException {
        
        
        try {
            
            this.createTrack();
            this.setGeneralMidiSysEx();
            
            this.addMidiEventToTrack(sysex, 0);

            this.setTempo();
            
            this.setTrackName("Test track");

            this.setOmniMode();
            
            this.setPolyOn();
            
            this.setInstrument();
            
            this.addNotesToTrack(notesSequence, noteLengthSequence, velocitySequence);
            
            this.writeMIDItoFile(name);
            
        } catch (IOException | InvalidMidiDataException e) {
            System.out.println(e.toString());         
        }
        
        
    }
    
    public void play() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();
        
        sequencer.setSequence(this.sequence);
        sequencer.setTempoInBPM(20f);
        sequencer.setTempoFactor(1.0f);
        sequencer.start();
    }
}
