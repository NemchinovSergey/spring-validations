package com.nsergey.validation.computer;

import java.util.concurrent.Future;
import java.util.function.Function;

public interface Computer<K, V> {
    Future<V> compute(K k, Function<K, V> f);
}
