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
}
