/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructureTest;

import melodiai.datastructures.DynamicList;
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
public class DynamicListTest {
    
    DynamicList<String> testList;
    
    public DynamicListTest() {
        testList = new DynamicList<String>();
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
    public void insertAndGetFromDynamicList() {
        testList.insert("test");
        
        assertEquals("test", testList.get(0));
    }
    
    @Test
    public void listIncreasesSize() {
        DynamicList<Integer> intList = new DynamicList<Integer>();
        for (int i = 0; i < 10000; i++) {
            intList.insert(i);
        }
        
        assertEquals(10000, intList.size());
    }
    
    public void listRemoveTest() {
        DynamicList<Integer> intList = new DynamicList<>();
        
        for (int i = 0; i < 10000; i++) {
            intList.insert(i);
        }
        
        for (int j = 8000; j < 9000; j++){
            intList.remove(j);
        }
        
        for (int y = 0; y < 11000; y++) {
            intList.insert(y);
        }
        
        assertEquals(20000, intList.size());
    }
}
