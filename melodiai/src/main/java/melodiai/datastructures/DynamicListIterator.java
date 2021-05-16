package melodiai.datastructures;

import java.util.Iterator;

/**
 *
 * @author juho
 */
public class DynamicListIterator<T> implements Iterator {

    int index = 0;
    DynamicList<T> list;

    public DynamicListIterator(DynamicList<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return (list.size() > index);
    }

    @Override
    public Object next() {
        T value = this.list.get(index);
        index++;
        return value;
    }

}
