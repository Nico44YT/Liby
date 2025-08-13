package nazario.liby.api.util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LibyMultiMap<K, T> {
    private Class<? extends List> listClass;
    private Class<? extends Map> mapClass;

    private Map<K, List<T>> map;

    public LibyMultiMap() {
        this(HashMap.class, ArrayList.class);
    }

    public LibyMultiMap(Class<? extends Map> mapClass, Class<? extends List> listClass) {
        this.mapClass = mapClass;
        this.listClass = listClass;

        this.map = createNewMap(this.mapClass);
    }

    public List<T> getList(K key) {
        return map.computeIfAbsent(key, k -> this.createNewList(this.listClass));
    }

    public T get(K key, int index) {
        return this.getList(key).get(index);
    }

    public List<T> getIfPresent(K key) {
        return map.get(key);
    }

    public void put(K key, T value) {
        this.getList(key).add(value);
    }

    public void put(K key, T value, int index) {
        this.getList(key).add(index, value);
    }

    public void putAll(K key, Collection<T> values) {
        this.getList(key).addAll(values);
    }

    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    public void forEach(K key, Consumer<T> action) {
        this.getList(key).forEach(action);
    }

    public void forEachIndexed(K key, BiConsumer<T, Integer> action) {
        List<T> list = this.getList(key);
        for(int i = 0; i < list.size(); i++) {
            action.accept(list.get(i), i);
        }
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public List<T> remove(K key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

    protected Map<K, List<T>> createNewMap(Class<? extends Map> mapClass) {
        try {
            return (Map<K, List<T>>)mapClass.getDeclaredConstructor().newInstance();
        } catch (Exception ignore) {}

        return new HashMap<>();
    }

    protected List<T> createNewList(Class<? extends List> listClass) {
        try {
            return (List<T>)listClass.getDeclaredConstructor().newInstance();
        } catch (Exception ignore) {}

        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
