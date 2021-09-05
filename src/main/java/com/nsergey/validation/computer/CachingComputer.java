package com.nsergey.validation.computer;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

@Slf4j
public class CachingComputer<K, V> implements Computer<K, V> {

    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final Map<K, Future<V>> cache = new ConcurrentHashMap<>();

    @Override
    public Future<V> compute(K k, Function<K, V> f) {
        return cache.computeIfAbsent(k, key -> executor.submit(() -> f.apply(key)));
        // OR
        /*log.info("Compute key: {}", k);
        if (cache.containsKey(k)) {
            log.info("Get value from cache by key: {}", k);
            return cache.get(k);
        }
        log.info("Add new future to cache with key: {}", k);
        Future<V> future = executor.submit(() -> f.apply(k));
        cache.put(k, future);
        return future;*/
    }

}
