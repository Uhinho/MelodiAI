/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MidiTest;

import java.io.File;
import melodiai.midi.MidiParser;
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
public class MidiParserTest {
    
    MidiParser mp = new MidiParser();
    
    public MidiParserTest() {
        mp.parseMidi(new File("Midifiles/mozart/").listFiles());
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
    public void dataIsCollectedFromMidiFiles() {
        assertTrue(!mp.getNoteKeys().isEmpty()
            && !mp.getNoteLengths().isEmpty()
            && !mp.getNoteObjects().isEmpty()
            && !mp.getVelocities().isEmpty());
    }

    
}
