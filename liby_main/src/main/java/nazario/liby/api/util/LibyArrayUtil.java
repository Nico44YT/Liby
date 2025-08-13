package nazario.liby.api.util;

import java.util.function.Consumer;

public class LibyArrayUtil {
    public static <T> void forEach(T[][] array, Consumer<T> consumer) {
        for(T[] inArray : array) {
            for(T t : inArray) {
                consumer.accept(t);
            }
        }
    }

    public static <T> void forEach(T[][][] array, Consumer<T> consumer) {
        for(T[][] _array : array) {
            for(T[] __array : _array) {
                for(T t : __array) {
                    consumer.accept(t);
                }
            }
        }
    }
}
