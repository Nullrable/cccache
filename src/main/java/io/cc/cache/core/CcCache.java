package io.cc.cache.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author nhsoft.lsd
 */
public class CcCache {

    private static final Map<String, CcCacheEntry<?>> map = new HashMap<>();

    public void set(String key, String value) {
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

    public int lpush(String key, String... values) {
        CcCacheEntry<LinkedList<String>> entry = (CcCacheEntry) map.get(key);
        if (entry == null) {
            entry = new CcCacheEntry<>(new LinkedList<>());
        }
        LinkedList list = entry.getValue();

        List.of(values).forEach(list::addFirst);

        entry.setValue(list);

        map.put(key, entry);

        return list.size();
    }

    public String lpop(String key) {
        CcCacheEntry<LinkedList<String>> entry = (CcCacheEntry) map.get(key);
        if (entry == null || entry.getValue().size() <= 0) {
            return null;
        }
        LinkedList<String> list = entry.getValue();
        return list.removeFirst();
    }

    public int llen(String key) {
        CcCacheEntry<LinkedList<String>> entry = (CcCacheEntry) map.get(key);
        if (entry == null || entry.getValue().size() <= 0) {
            return 0;
        }
        LinkedList<String> list = entry.getValue();
        return list.size();
    }

    public int rpush(String key, String... values) {
        CcCacheEntry<LinkedList<String>> entry = (CcCacheEntry) map.get(key);
        if (entry == null) {
            entry = new CcCacheEntry<>(new LinkedList<>());
        }
        LinkedList list = entry.getValue();

        List.of(values).forEach(list::addLast);

        entry.setValue(list);

        map.put(key, entry);

        return list.size();
    }

    public String rpop(String key) {
        CcCacheEntry<LinkedList<String>> entry = (CcCacheEntry) map.get(key);
        if (entry == null || entry.getValue().size() <= 0) {
            return null;
        }
        LinkedList<String> list = entry.getValue();
        return list.removeLast();
    }

    public List<String> lrange(final String key, final int start, final int end) {
        CcCacheEntry<LinkedList<String>> entry = (CcCacheEntry) map.get(key);
        if (entry == null || entry.getValue() == null || entry.getValue().size() <= 0) {
            return new ArrayList<>();
        }
        LinkedList<String> list = entry.getValue();
        int size = list.size();

        int startIndex = start;
        if (start < 0) {
            startIndex =  Math.floorMod(start, size);
            if (startIndex < 0) {
                startIndex += size;
            }
        }
        int endIndex = end;
        if (end < 0) {
            endIndex = Math.floorMod(end, size);
            if (endIndex < 0) {
                endIndex += size;
            }
        }
        endIndex = Math.min(endIndex + 1, size);

        List<String> result = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    public int append(final String key, final String value) {
       String oldValue = get(key);
       String newValue = value;
       if (oldValue != null) {
           newValue = oldValue + newValue;
       }
       set(key, newValue);
       return newValue.length();
    }
}
