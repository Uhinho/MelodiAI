
package melodiai.midi;


public class Note {
    
    private int start;
    private int end;
    private int instrument;
    private int velocity;
    private int key;
    private int noteLength;

    public Note(int start, int instrument, int velocity, int key) {
        this.start = start;
        this.instrument = instrument;
        this.velocity = velocity;
        this.key = key;
    }

    public int getNoteLength() {
        return noteLength;
    }
    
    public void endNote(int end) {
        this.end = end;
        this.noteLength = end - start;
    }

    public void setNoteLength(int noteLength) {
        this.noteLength = noteLength;
    }

    @Override
    public String toString() {
        return "Note{" + "start=" + start + ", end=" + end + ", instrument=" + instrument + ", velocity=" + velocity + ", key=" + key + ", length=" + noteLength + '}';
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getInstrument() {
        return instrument;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.instrument;
        hash = 29 * hash + this.key;
        return hash;
    }

    

    @Override
    public boolean equals(Object obj) {
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
