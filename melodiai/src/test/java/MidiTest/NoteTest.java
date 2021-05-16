/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MidiTest;

import melodiai.midi.Note;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author juho
 */
public class NoteTest {
    
    public NoteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void noteIsCreated() {
        Note note = new Note(0, 1, 1, 50);
        
        assertEquals(50, note.getKey());
    }
    
    @Test
    public void correctDuration() {
        Note note = new Note(0, 1, 1, 50);
        note.endNote(100);
        
        assertEquals(100, note.getNoteLength());
        
        note.setNoteLength(16);
        
        assertEquals(16, note.getNoteLength());
    }
    
    @Test
    public void noteEqualsCorrect() {
        Note note = new Note(0, 1, 1, 50);
        Note note2 = new Note(0, 2, 1, 50);
        Note note3 = new Note(0, 1, 1, 50);
        Note note4 = new Note(0, 1, 1, 51);
        
        assertTrue(note.equals(note3) && !note.equals(note2) && !note.equals(note4) && !note.equals(new Integer[]{1,2,3}));
    }
    
    @Test
    public void gettersCorrectData() {
        Note note = new Note(0, 1, 1, 50);
        note.endNote(100);
        
        int checkSum = note.getStart()
                + note.getEnd()
                + note.getInstrument()
                + note.getKey()
                + note.getVelocity()
                + note.getNoteLength();
        
        assertEquals(252, checkSum);
        
    }

}
