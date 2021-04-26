package melodiai.datastructures;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * 
 * @author juho
 * @param <T>
 * 
 * List structure that dynamically increases in size.
 */

public class DynamicList<T> implements Iterable<T>{

    private Object[] dynamicList;
    private int size;
    
    /**
     * Constructs a new dynamic list object of starting size of 10
     */
    public DynamicList() {
        this.dynamicList = new Object[10];
        this.size = 0;
    }

    private void increaseSize() {
        int increasedCapacity = dynamicList.length * 2;
        dynamicList = Arrays.copyOf(dynamicList, increasedCapacity);
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public void insert(T item) {
        if (this.size == dynamicList.length) {
            this.increaseSize();
        }
        
        this.dynamicList[this.size++] = item;
    }
    
    public void insertMany(T[] arr) {
        
        for (int i = 0; i < arr.length; i++) {
            this.insert((T) arr[i]);
        }
    }
    
    public T get(int i) {
        if (i >= this.dynamicList.length || i < 0) {
            throw new NullPointerException();
        }
        
        return (T) this.dynamicList[i];
    }
     
    public void print() {
        for (int i = 0; i < this.size(); i++) {
            System.out.println(this.dynamicList[i]);
        }
    }
    
    public void remove(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
        
        Object removed = this.dynamicList[index];
        
        for (int i = index; i < this.size - 1; i++) {
            this.dynamicList[i] = this.dynamicList[i + 1];
        }
        
        size--;
    }

    @Override
    public Iterator<T> iterator() {
        return new DynamicListIterator<T>(this);
    }

}
