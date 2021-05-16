package melodiai.datastructures;

import java.util.Iterator;

/**
 * Custom list iterator for DynamicList object.
 *
 * @author juho
 * @param <T> object type of DynamicList
 */
public class DynamicListIterator<T> implements Iterator {

    /**
     * @param index current index
     */
    private int index = 0;
    /**
     * @param list DynamicList object
     */
    private final DynamicList<T> list;

    /**
     * Constructor for iterator.
     *
     * @param listObject DynamicList object
     */
    public DynamicListIterator(final DynamicList<T> listObject) {
        this.list = listObject;
    }

    /**
     *
     * @return boolean true if list object has next value
     */
    @Override
    public boolean hasNext() {
        return (list.size() > index);
    }

    /**
     *
     * @return next value in the list
     */
    @Override
    public Object next() {
        T value = this.list.get(index);
        index++;
        return value;
    }

}
