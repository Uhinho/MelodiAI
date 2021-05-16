/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AiTest;

import melodiai.ai.ArraySequencer;
import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
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
public class SequencerTest {
    
    ArraySequencer seq;
    Trie trie;
        
    public SequencerTest() {
        seq = new ArraySequencer();
        trie = new Trie(5);
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
    public void correctLengthArray() {
        
        DynamicList<Integer> list = new DynamicList<>();
        
        for(int i = 0; i < 10000; i++) {
            double randNum = Math.random() * 500;
            list.insert((int) randNum);
        }
        
        Trie t = new Trie(4);
        t.put(list);
        int seqLength = 1000;
        int[] generatedSeq = seq.generateSequence(seqLength, t, 3, t.getRandomRootChild());
        
        assertEquals(seqLength, generatedSeq.length);
    }
    
    @Test
    public void correctLengthRhytm() {
        
        int[] possibleNotes = new int[]{1,2,4,8,16};
        DynamicList<Integer> notelengthList = new DynamicList<>();
        
        for (int i = 0; i < 1000; i++) {
            notelengthList.insert(possibleNotes[(int) Math.floor(Math.random() * possibleNotes.length)]);
        }
        
        
        Trie t = new Trie(4);
        t.put(notelengthList);
        DynamicList<Double> noteLengtDoubles = seq.generateRhytmSequence(100, t, 3, t.getRandomRootChild(), 4);
        
        double totalLength = 0;
        
        for (double d: noteLengtDoubles) {
            totalLength += d;
        }
        
        assertTrue(totalLength == 100);
        
        
    }

    
}
