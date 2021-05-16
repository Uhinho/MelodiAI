package melodiai.datastructures;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Dynamic list object with start length of 10.
 *
 * @author juho
 * @param <T>
 */
public class DynamicList<T> implements Iterable<T> {

    /**
     * @param dynamicList array of objects in the list
     */
    private Object[] dynamicList;
    /**
     * @param size current size of the list
     */
    private int size;

    /**
     * Constructor for DynamicList object.
     */
    public DynamicList() {
        this.dynamicList = new Object[10];
        this.size = 0;
    }

    /**
     * Increases the size of the list when max size is reached.
     */
    private void increaseSize() {
        int increasedCapacity = dynamicList.length * 2;
        dynamicList = Arrays.copyOf(dynamicList, increasedCapacity);
    }

    /**
     *
     * @return current size of the list
     */
    public int size() {
        return this.size;
    }

    /**
     *
     * @return boolean value true if list has no items
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Insert item to the list.
     *
     * @param item item to insert
     */
    public void insert(final T item) {
        if (this.size == dynamicList.length) {
            this.increaseSize();
        }

        this.dynamicList[this.size++] = item;
    }

    /**
     * Insert an array of items all at once.
     *
     * @param arr array of items
     */
    public void insertMany(final T[] arr) {

        for (T arr1 : arr) {
            this.insert((T) arr1);
        }
    }

    /**
     * Get value of index i.
     *
     * @param i index
     * @return value of index i
     */
    public T get(final int i) {
        if (i >= this.dynamicList.length || i < 0) {
            throw new NullPointerException();
        }

        return (T) this.dynamicList[i];
    }

    /**
     * Print the values of the list to console.
     */
    public void print() {
        for (int i = 0; i < this.size(); i++) {
            System.out.println(this.dynamicList[i]);
        }
    }

    /**
     * Remove value of index.
     *
     * @param index index of the item to remove from the list
     */
    public void remove(final int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(
                            "Index: "
                            + index
                            + ", Size: "
                            + this.size);
        }

        Object removed = this.dynamicList[index];

        for (int i = index; i < this.size - 1; i++) {
            this.dynamicList[i] = this.dynamicList[i + 1];
        }

        size--;
    }

    /**
     * Custom iterator for DynamicList.
     *
     * @return Iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new DynamicListIterator<T>(this);
    }

}
