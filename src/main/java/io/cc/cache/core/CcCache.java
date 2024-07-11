package io.cc.cache.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public boolean exists(String key) {
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

    public String getrange(final String key, int start, int end) {
        String value = get(key);
        if (value == null || value.length() <= 0) {
            return null;
        }
        char[] exist = value.toCharArray();

        int size = exist.length;

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

        int len = endIndex - startIndex;
        char[] ret = new char[len];
        for(int i= 0; i < len; i++) {
            ret[i] = exist[i + startIndex];
        }
        return new String(ret);
    }

    public String getset(final String key, final String value) {

        String oldValue = get(key);

        set(key, value);

        return oldValue;
    }

    public int setrange(final String key, final String value, final int offset) {

        String oldValue = get(key);
        if (oldValue == null) {
            oldValue = "";
        }
        int oldLen = oldValue.length();

        if (offset > oldLen) {
            int len = offset - oldLen;
            String[] blanks = new String[len];
            Arrays.fill(blanks, 0, len, "\u0000");
            for (String blank : blanks) {
                oldValue = oldValue + blank;
            }
            oldValue = oldValue +value;
        } else {
            oldValue = oldValue.substring(0, offset);
            oldValue = oldValue + value;
        }
        set(key, oldValue);

        return oldValue.length();
    }

    public Set<String> getKeys() {

       return map.keySet();
    }

    public int hdel(final String key, final String field) {

        CcCacheEntry<HashMap<String, String>> cacheEntry = (CcCacheEntry<HashMap<String, String>>) map.get(key);

        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Map<String, String> hashMap = cacheEntry.getValue();

        String val = hashMap.remove(field);

        return val == null ? 0 : 1;
    }

    public int hset(final String key, final String field, final String value) {
        CcCacheEntry<HashMap<String, String>> cacheEntry = (CcCacheEntry<HashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            cacheEntry = new CcCacheEntry<>(new HashMap<>());
            map.put(key, cacheEntry);
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        String val = hashMap.put(field, value);
        return val == null ? 0 : 1;
    }

    public String hget(final String key, final String field) {
        CcCacheEntry<HashMap<String, String>> cacheEntry = (CcCacheEntry<HashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return null;
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        return hashMap.get(field);
    }

    public int hexists(final String key, final String field) {
        CcCacheEntry<HashMap<String, String>> cacheEntry = (CcCacheEntry<HashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        return hashMap.containsKey(field) ? 1 : 0;
    }

    public List<String> hgetall(final String key) {
        CcCacheEntry<HashMap<String, String>> cacheEntry = (CcCacheEntry<HashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new ArrayList<>();
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        List<String> list = new ArrayList();
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            list.add(entry.getKey());
            list.add(entry.getValue());
        }
        return list;
    }

    public int hincrby(final String key, final String field, final int value) {
        CcCacheEntry<HashMap<String, String>> cacheEntry = (CcCacheEntry<HashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            cacheEntry = new CcCacheEntry<>(new HashMap<>());
            map.put(key, cacheEntry);
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        int val = Integer.parseInt(hashMap.get(field) == null ? "0" : hashMap.get(field));
        val += value;
        hashMap.put(field, String.valueOf(val));
        return val;
    }

    public List<String> hkeys(final String key) {
        CcCacheEntry<HashMap<String, String>> cacheEntry = (CcCacheEntry<HashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new ArrayList<>();
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        List<String> list = new ArrayList<>(hashMap.keySet());
        return list;
    }

    public int hlen(final String key) {
        CcCacheEntry<HashMap<String, String>> cacheEntry = (CcCacheEntry<HashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        return hashMap.size();
    }

    public int hsetnx(final String key, final String field, final String value) {
        CcCacheEntry<HashMap<String, String>> cacheEntry = (CcCacheEntry<HashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            cacheEntry = new CcCacheEntry<>(new HashMap<>());
            map.put(key, cacheEntry);
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        if (hashMap.containsKey(field)) {
            return 0;
        }
        hashMap.putIfAbsent(field, value);
        return 1;
    }

    public List<String> hvals(final String key) {
        CcCacheEntry<HashMap<String, String>> cacheEntry = (CcCacheEntry<HashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new ArrayList<>();
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        List<String> list = new ArrayList<>(hashMap.values());
        return list;
    }
}
