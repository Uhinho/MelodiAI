package melodiai.datastructures;

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
    private int max;
    
    /**
     * Constructs a new dynamic list object of starting size of 10
     */
    public DynamicList() {
        this.dynamicList = new Object[10];
        this.size = 0;
        this.max = 10;
    }

    private void increaseSize() {
        max *= 2;
        Object[] updated = new Object[max];
        System.arraycopy(this.dynamicList, 0, updated, 0, this.size);
        this.dynamicList = updated;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public void insert(T item) {
        if (this.size == this.max) {
            this.increaseSize();
        }
        
        this.dynamicList[this.size] = item;
        this.size++;
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

    @Override
    public Iterator<T> iterator() {
        return new DynamicListIterator<T>(this);
    }

}
