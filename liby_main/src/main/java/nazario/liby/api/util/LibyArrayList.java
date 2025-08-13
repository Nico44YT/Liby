package nazario.liby.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LibyArrayList<T> extends ArrayList<T> {

    public LibyArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public LibyArrayList() {
        super();
    }

    public LibyArrayList(Collection<? extends T> c) {
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
