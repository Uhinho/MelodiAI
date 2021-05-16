package melodiai.midi;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import melodiai.datastructures.DynamicList;

public class MidiBuilder {

    /**
     * General MIDI sound set defaulted.
     */
    private final byte[] GENERAL_MIDI_SOUND_SET = new byte[]{
        (byte) 0xF0,
        0x7E,
        0x7F,
        0x09,
        0x01,
        (byte) 0xF7
    };

    /**
     * @param track midi trak
     */
    private Track track;
    /**
     * @param sysex sysex message
     */
    private SysexMessage sysex;
    /**
     * @param sequence sequence of MIDI events
     */
    private Sequence sequence;
    /**
     * @param ticksPerBeat amount of microsecond ticks per beat
     */
    private int ticksPerBeat;

    /**
     * Constructor for MIDI builder.
     */
    public MidiBuilder() {
        this.track = null;
        this.sysex = new SysexMessage();
    }

    private void createTrack() throws InvalidMidiDataException {
        ticksPerBeat = 48000;

        this.sequence = new Sequence(
                javax.sound.midi.Sequence.PPQ,
                ticksPerBeat,
                1
        );

        this.track = this.sequence.createTrack();
    }

    private void setGeneralMidiSysEx() throws InvalidMidiDataException {
        if (this.sysex != null) {
            this.sysex.setMessage(
                    GENERAL_MIDI_SOUND_SET,
                    GENERAL_MIDI_SOUND_SET.length
            );
        }
    }

    private void addMidiEventToTrack(
            final MidiMessage midiMessage,
            final long tick) {

        MidiEvent midiEvent = new MidiEvent(midiMessage, tick);
        this.track.add(midiEvent);

    }

    /*
        IF TEMPO IS NOT SET THE DEFAULT IS 120BPM
     */
    private void setTempo() throws InvalidMidiDataException {

        int bpm = 60;
        byte[] tempoHex = new byte[3]; // big endian 3 bytes

        // 60 000 000 microseconds in minute.
        //Tempo = amt of 1/4 notes per minute
        int tempo = 60000000 / bpm;

        tempoHex[0] = (byte) ((tempo >> 16) & 0xFF);
        tempoHex[1] = (byte) ((tempo >> 8) & 0xFF);
        tempoHex[2] = (byte) ((tempo & 0xFF));

        MetaMessage metaMsg = new MetaMessage();
        metaMsg.setMessage(0x51, tempoHex, tempoHex.length);
    }

    private void setTrackName(final String name) throws InvalidMidiDataException {

        MetaMessage metaMsg = new MetaMessage();
        metaMsg.setMessage(0x03, name.getBytes(), name.length());

        this.addMidiEventToTrack(metaMsg, (long) 0);
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

    private void addNotesToTrack(
            final int[] notesArray,
            final DynamicList<Double> lengthList,
            final int[] velocityArray)
            throws InvalidMidiDataException {

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

    private void writeMIDItoFile(final String name) throws IOException {
        File pathToOutput = new File("output/");
        if (!pathToOutput.exists()) {
            pathToOutput.mkdir();
        }
        File file = new File("output/" + name + ".mid");
        MidiSystem.write(sequence, 1, file);
    }

    /**
     * Create midi file.
     *
     * @param name name of the file
     * @param notesSequence sequence of notes
     * @param velocitySequence sequence of note velocities
     * @param noteLengthSequence sequence of note lengths
     * @throws InvalidMidiDataException
     */
    public void createMidiFile(
            final String name,
            final int[] notesSequence,
            final int[] velocitySequence,
            final DynamicList<Double> noteLengthSequence)
            throws InvalidMidiDataException {

        try {
            this.createTrack();
            this.setGeneralMidiSysEx();
            this.addMidiEventToTrack(sysex, 0);
            this.setTempo();
            this.setTrackName("Test track");
            this.setOmniMode();
            this.setPolyOn();
            this.setInstrument();
            this.addNotesToTrack(
                    notesSequence,
                    noteLengthSequence,
                    velocitySequence
            );
            this.writeMIDItoFile(name);
        } catch (IOException | InvalidMidiDataException e) {
            System.out.println(e.toString());
        }

    }
}
