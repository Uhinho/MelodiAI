package DataStructureTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.concurrent.TimeUnit;
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
public class TrieTest {
    Trie trie;
    DynamicList<Integer> list;
    
    
    public TrieTest() {
        trie = new Trie(3);
        list = new DynamicList<>();
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
    public void trieIsCreated() {
        
        boolean exists;
        exists = trie.getRoot() != null;
        
        assertTrue(exists);
        
    }
    
    @Test
    public void notesAreInsertedToTrie() {
        list.insertMany(new Integer[]{1,2,3,4,5,6,7,8,9,10,10,9,8,7,6,5,4,3,2,1});
        
        trie.put(list);
        
        assertTrue(trie.includesNoteSequence(new int[]{3,4,5}));
    }
    
    @Test
    public void nullIfNotIncluded() {
        assertFalse(trie.includesNoteSequence(new int[]{1,100,90,20}));
    }
    
    @Test
    public void timeComplexityTest() {
        
        int markovOrder = 1;
        Trie testTrie;
        DynamicList<Integer> testList;
        int objects = 100;
        
        while (true) {
            testTrie = new Trie(markovOrder);
            testList = new DynamicList<>();
            
            for (int i = 0; i < objects; i++) {
                double randomNum = Math.random() * 128;
                testList.insert((int) randomNum);
            }
            
            long start = System.nanoTime();
            testTrie.put(testList);
            long end = System.nanoTime();
            System.out.println(objects + " objects with " + markovOrder + " order in " + this.printInMs(start, end));
            objects *= 10;
            if (markovOrder == 6 &&  objects > 1000000) {
                break;
            } else if (objects > 1000000){
                objects = 100;
                markovOrder++;
            }
        }
    }
    
    public long printInMs(long start, long end) {
        long duration = end - start;
        long inMs = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);
        
        return inMs;
    }
    
    @Test
    public void nodeHasCorrectFollowers() {
        DynamicList<Integer> list = new DynamicList<>();
        
        list.insertMany(new Integer[]{1,2,3,4,5,6,7,8,9,10,9,8,7,6,5,4,3,2,1,2,3,4,5,6,7,8,9,10});
        Trie t = new Trie(4);
        t.put(list);
        assertTrue(t.getRoot().hasChildren());
        assertEquals(3, t.getRoot().getChildren()[2].getAppearances());
        assertEquals(5, t.getFollowers(new int[]{2,3,4}).get(0).getNodeKey());
    }
    
}
