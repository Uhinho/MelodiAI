package DataStructureTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
        
        int markovOrder = 4;
        Trie testTrie = new Trie(markovOrder);
        DynamicList<Integer> testList = new DynamicList();
        
        for (int i = 0; i < 10000; i++) {
            double randomNum = Math.random() * 599;
            testList.insert((int) randomNum);
        }
        
        long start = System.currentTimeMillis();
        testTrie.put(testList);
        long end = System.currentTimeMillis();
        
        long duration = end - start;
        System.out.println("Put operation took " + duration + "ms");
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
