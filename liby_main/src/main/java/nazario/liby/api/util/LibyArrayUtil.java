package nazario.liby.api.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LibyArrayUtil {
    public static <T> void forEach(T[] array, Consumer<T> consumer) {
        if (array == null) return;
        for(T value : array) {
            consumer.accept(value);
        }
    }

    public static <T> void forEach(T[][] array, Consumer<T> consumer) {
        if (array == null) return;
        for(T[] inArray : array) {
            forEach(inArray, consumer);
        }
    }

    public static <T> void forEach(T[][][] array, Consumer<T> consumer) {
        if (array == null) return;
        for(T[][] inArray : array) {
            forEach(inArray, consumer);
        }
    }

    public static <T> void forEach(T[][][][] array, Consumer<T> consumer) {
        if (array == null) return;
        for(T[][][] inArray : array) {
            forEach(inArray, consumer);
        }
    }

    public static <T> void forEachIndexed(T[] array, BiConsumer<T, Integer> consumer) {
        if (array == null) return;

        for(int x = 0;x < array.length; x++) {
            consumer.accept(array[x], x);
        }
    }

    public static <T> void forEachIndexed(T[][] array, TriConsumer<T, Integer, Integer> consumer) {
        if (array == null) return;

        for(int x = 0;x < array.length; x++) {
            for(int y = 0;y < array[x].length; y++) {
                consumer.accept(array[x][y], x, y);
            }
        }
    }

    public static <T> void forEachIndexed(T[][][] array, QuadConsumer<T, Integer, Integer, Integer> consumer) {
        if (array == null) return;

        for(int x = 0;x < array.length; x++) {
            for(int y = 0;y < array[x].length; y++) {
               for(int z = 0;z < array[x][y].length; z++) {
                   consumer.accept(array[x][y][z], x, y, z);
               }
            }
        }
    }
}
