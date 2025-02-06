package ru.ob11to.memory.task245;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public final void put(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }

    public final V get(K key) {
        V dataCache = cache.getOrDefault(key, new SoftReference<>(null)).get();
        if (dataCache == null) {
            dataCache = load(key);
            put(key, dataCache);
        }
        return dataCache;
    }

    protected abstract V load(K key);
}