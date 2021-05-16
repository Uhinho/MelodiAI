/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MidiTest;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Random;
import javax.sound.midi.InvalidMidiDataException;
import melodiai.ai.ArraySequencer;
import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
import melodiai.midi.MidiBuilder;
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
public class MidiBuilderTest {
    
    MidiBuilder mb = new MidiBuilder();
    
    public MidiBuilderTest() {
        
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
    public void midiIsGenerated() throws InvalidMidiDataException {
        int[] veloSeq = new int[]{1,2};
        int[] noteSeq = new int[]{50,51};
        DynamicList<Double> lengthSeq = new DynamicList<>();
        lengthSeq.insertMany(new Double[]{0.5, 0.5});
        
        // generate name for the test MIDI
        
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String randomName = new String(array, Charset.forName("UTF-8"));
        
        mb.createMidiFile(randomName, noteSeq, veloSeq, lengthSeq);
        File generatedFile = new File("output/" + randomName + ".mid");
        boolean exists = generatedFile.exists();
        if (exists) {
            generatedFile.delete();
        }
        assertTrue(exists);
    }

    
}
