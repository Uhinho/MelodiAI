package melodiai.midi;

public class Note {

    /**
     * @param start start of the note
     */
    private final int start;
    /**
     * @param end end of the note
     */
    private int end;
    /**
     * @param instrument instrument of the note
     */
    private final int instrument;
    /**
     * @param velocity velocity of the note
     */
    private final int velocity;
    /**
     * @param key key value of the note
     */
    private final int key;
    /**
     * @param noteLength length of the note
     */
    private int noteLength;

    /**
     * Constructor for Note object.
     *
     * @param noteStart noteStart tick
     * @param noteInstrument MIDI noteInstrument value
     * @param noteVelocity noteVelocity value
     * @param noteKey note noteKey
     */
    public Note(
            final int noteStart,
            final int noteInstrument,
            final int noteVelocity,
            final int noteKey) {

        this.start = noteStart;
        this.instrument = noteInstrument;
        this.velocity = noteVelocity;
        this.key = noteKey;
    }

    /**
     *
     * @return note duration
     */
    public int getNoteLength() {
        return noteLength;
    }

    /**
     * Ends the note and sets the length in ticks.
     *
     * @param noteEnd tick when note ends
     */
    public void endNote(final int noteEnd) {
        this.end = noteEnd;
        this.noteLength = noteEnd - start;
    }

    /**
     * Set note duration.
     *
     * @param noteDuration duration
     */
    public void setNoteLength(final int noteDuration) {
        this.noteLength = noteDuration;
    }

    /**
     *
     * @return tick when note starts
     */
    public int getStart() {
        return start;
    }

    /**
     *
     * @return tick when note ends
     */
    public int getEnd() {
        return end;
    }

    /**
     *
     * @return instrument value
     */
    public int getInstrument() {
        return instrument;
    }

    /**
     *
     * @return velocity value
     */
    public int getVelocity() {
        return velocity;
    }

    /**
     *
     * @return note key value
     */
    public int getKey() {
        return key;
    }

    /**
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.instrument;
        hash = 29 * hash + this.key;
        return hash;
    }

    /**
     * Match object by instrument and note key.
     *
     * @param obj object to compare
     * @return true if equals
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Note other = (Note) obj;
        if (this.instrument != other.instrument) {
            return false;
        }
        if (this.key != other.key) {
            return false;
        }
        return true;
    }

}
