package io.cc.cache.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

    public int sadd(final String key, final String... members) {
        CcCacheEntry<Set<String>> cacheEntry = (CcCacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            cacheEntry = new CcCacheEntry<>(new HashSet<>());
            map.put(key, cacheEntry);
        }
        Set<String> set = cacheEntry.getValue();
        int count = 0;
        for (String member : members) {
            if (!set.contains(member)) {
                count++;
                set.add(member);
            }
        }
        return count;
    }

    public int scard(final String key) {
        CcCacheEntry<Set<String>> cacheEntry = (CcCacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Set<String> set = cacheEntry.getValue();
        return set.size();
    }

    public Set<String> sdiff(final String... keys) {
        CcCacheEntry<Set<String>> cacheEntry = (CcCacheEntry<Set<String>>) map.get(keys[0]);
        Set<String> originSet;
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            originSet = new HashSet<>();
        } else {
            originSet = new HashSet<>(cacheEntry.getValue());
        }

        for (int i = 1; i < keys.length; i++) {
            CcCacheEntry<Set<String>> cacheEntryOther = (CcCacheEntry<Set<String>>) map.get(keys[i]);
            if (cacheEntry == null || cacheEntry.getValue() == null) {
                continue;
            }
            Set<String> otherSet = cacheEntryOther.getValue();
            originSet.removeAll(otherSet);
        }
        return originSet;
    }

    public Set<String> sdiffstore(final String destination, final String... keys) {
        Set<String> diffSet = sdiff(keys);
        map.put(destination, new CcCacheEntry<>(diffSet));
        return diffSet;
    }

    public Set<String> sinter(final String... keys) {
        CcCacheEntry<Set<String>> cacheEntry = (CcCacheEntry<Set<String>>) map.get(keys[0]);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new HashSet<>();
        }
        Set<String> originSet = new HashSet<>(cacheEntry.getValue());
        for (int i = 1; i < keys.length; i++) {
            CcCacheEntry<Set<String>> cacheEntryOther = (CcCacheEntry<Set<String>>) map.get(keys[i]);
            if (cacheEntry == null || cacheEntry.getValue() == null) {
                continue;
            }
            Set<String> otherSet = cacheEntryOther.getValue();
            originSet.retainAll(otherSet);
        }
        return originSet;
    }

    public Set<String> sinterstore(final String destination, final String... keys) {
        Set<String> interSet = sinter(keys);
        map.put(destination, new CcCacheEntry<>(interSet));
        return interSet;
    }

    public int sismember(final String key, final String member) {
        CcCacheEntry<Set<String>> cacheEntry = (CcCacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Set<String> set = cacheEntry.getValue();
        return set.contains(member) ? 1 : 0;
    }

    public Set<String> smembers(final String key) {
        CcCacheEntry<Set<String>> cacheEntry = (CcCacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new HashSet<>();
        }
        return cacheEntry.getValue();
    }

    public int smove(final String source, final String destination, final String member) {
        CcCacheEntry<Set<String>> sourceEntry = (CcCacheEntry<Set<String>>) map.get(source);
        if (sourceEntry == null || sourceEntry.getValue() == null) {
            return 0;
        }
        Set<String> sourceSet = sourceEntry.getValue();
        boolean contained = sourceSet.remove(member);
        if (!contained) {
            return 0;
        }
        sadd(destination, member);
        return 1;
    }

    public String spop(final String key) {
        CcCacheEntry<Set<String>> cacheEntry = (CcCacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return null;
        }
        Set<String> set = cacheEntry.getValue();

        List<String> list = new ArrayList<>(set);
        Collections.shuffle(list);

        for (String member : list) {
            if (set.remove(member)) {
                return member;
            }
        }
        return null;
    }

    public Set<String> srandmember(final String key, final int count) {
        CcCacheEntry<Set<String>> cacheEntry = (CcCacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new HashSet<>();
        }
        Set<String> set = cacheEntry.getValue();
        List<String> list = new ArrayList<>(set);
        Collections.shuffle(list);
        int size = list.size();

        if (count > 0 && count < size) {
            size = count;
        } else if (count <= 0 && Math.abs(count) >= size) {
            size = 0;
        }
        return new HashSet<>(list.subList(0, size));
    }

    public int srem(final String key, final String... members) {
        CcCacheEntry<Set<String>> cacheEntry = (CcCacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Set<String> set = cacheEntry.getValue();
        int count = 0;
        for (String member : members) {
            count += set.remove(member) ? 1 : 0;
        }
        return count;
    }

    public Set<String> sunion(final String... keys) {
        Set<String> set = new HashSet<>();
        for (String key : keys) {
            CcCacheEntry<Set<String>> cacheEntry = (CcCacheEntry<Set<String>>) map.get(key);
            if (cacheEntry == null || cacheEntry.getValue() == null) {
                continue;
            }
            set.addAll(cacheEntry.getValue());
        }
        return set;
    }

    public int sunionstore(final String destination, final String... keys) {
        Set<String> sets = sunion(keys);
        map.put(destination, new CcCacheEntry<>(sets));
        return sets.size();
    }
}
