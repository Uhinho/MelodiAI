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
 * Object for parsing MIDI files to byte data.
 *
 * @author juho
 *
 */
public class MidiParser {

    /**
     * @param NOTE_OFF MIDI event value for note off event
     */
    private final int NOTE_OFF = 0x80;
    /**
     * @param notesList list for unfinished notes
     */
    private final DynamicList<Note> notesList = new DynamicList<>();
    /**
     * @param resolution time resolution for the midi file (amt of ticks per beat)
     */
    private int resolution;

    /**
     * Parses note keys from MIDI files.
     *
     * @param files array of paths to MIDI files
     */
    public void parseMidi(File[] files) {

        for (File file : files) {
            try {
                Sequence sequence = MidiSystem.getSequence(file);
                for (Track track : sequence.getTracks()) {
                    DynamicList<Note> stillPlaying = new DynamicList<>();
                    this.resolution = sequence.getResolution();
                    for (int i = 0; i < track.size(); i++) {
                        MidiEvent event = track.get(i);
                        MidiMessage message = event.getMessage();

                        if (message instanceof ShortMessage) {
                            ShortMessage sm = (ShortMessage) message;
                            int key = sm.getData1();
                            int velocity = sm.getData2();
                            int channel = sm.getChannel();
                            if (velocity != 0 && sm.getCommand() != NOTE_OFF) {
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

    /**
     * Converts note duration to integer value.
     *
     * @param noteLength length of note in MIDI ticks
     * @return note length 1/x note. (e.g. 1/1 note is whole note, 1/2 is half
     * note etc.)
     */
    public int checkNoteType(final int noteLength) {
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

    /**
     *
     * @return list of note keys
     */
    public DynamicList<Integer> getNoteKeys() {

        DynamicList<Integer> listOfNoteKeys = new DynamicList<>();

        for (Note note : this.notesList) {
            listOfNoteKeys.insert(note.getKey());
        }

        return listOfNoteKeys;
    }

    /**
     *
     * @return list of note lengths
     */
    public DynamicList<Integer> getNoteLengths() {

        DynamicList<Integer> listOfNoteLengths = new DynamicList<>();

        for (Note note : this.notesList) {
            listOfNoteLengths.insert(note.getNoteLength());
        }

        return listOfNoteLengths;
    }

    /**
     *
     * @return list of velocity values
     */
    public DynamicList<Integer> getVelocities() {
        DynamicList<Integer> listOfVelocities = new DynamicList<>();

        for (Note note : this.notesList) {
            listOfVelocities.insert(note.getVelocity());
        }

        return listOfVelocities;
    }

    /**
     *
     * @return list of note objects
     */
    public DynamicList<Note> getNoteObjects() {
        return this.notesList;
    }

}
