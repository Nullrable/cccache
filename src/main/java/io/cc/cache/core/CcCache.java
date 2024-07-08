package io.cc.cache.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nhsoft.lsd
 */
public class CcCache {

    private static Map<String, CcCacheEntry<?>> map = new HashMap<>();

    public void put(String key, String value) {
        map.put(key, new CcCacheEntry<>(value));
    }

    public String get(String key) {
        CcCacheEntry<String> entry = (CcCacheEntry<String>) map.get(key);
        return entry == null ? null : entry.getValue();
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public int incr(final String key) {
        String val = get(key);
        int value = val == null ? 0 : Integer.parseInt(val);
        value ++;
        map.put(key, new CcCacheEntry<>(String.valueOf(value)));
        return value;
    }

    public int incrby(final String key, final String param) {
        String val = get(key);
        int value = val == null ? 0 : Integer.parseInt(val);
        value = value + Integer.parseInt(param);
        map.put(key, new CcCacheEntry<>(String.valueOf(value)));
        return value;
    }

    public int decr(final String key) {
        String val = get(key);
        int value = val == null ? 0 : Integer.parseInt(val);
        value --;
        map.put(key, new CcCacheEntry<>(String.valueOf(value)));
        return value;
    }

    public int decrby(final String key, final String param) {
        String val = get(key);
        int value = val == null ? 0 : Integer.parseInt(val);
        value = value - Integer.parseInt(param);
        map.put(key, new CcCacheEntry<>(String.valueOf(value)));
        return value;
    }

    public int del(final String key) {
        CcCacheEntry<?> val = map.remove(key);
        if (val == null) {
            return 0;
        }
        return 1;
    }
}
