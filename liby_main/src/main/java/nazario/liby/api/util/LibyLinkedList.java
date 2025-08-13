package nazario.liby.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.BiConsumer;

public class LibyLinkedList<T> extends LinkedList<T> {

    public LibyLinkedList() {
        super();
    }

    public LibyLinkedList(Collection<? extends T> c) {
        super(c);
    }

    public void forEachIndexed(BiConsumer<? super T, Integer> action) {
        for(int index = 0;index<this.size();index++) {
            action.accept(get(index), index);
        }
    }

    public void forEachIndexedInverted(BiConsumer<Integer, ? super T> action) {
        this.forEachIndexed((type, index) -> action.accept(index, type));
    }
}
