package io.cc.cache.core;

import io.cc.cache.exception.SyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author nhsoft.lsd
 */
public class Cache {

    private static final Map<String, CacheEntry<?>> map = new LinkedHashMap<>();

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CacheEntry<V>{

        private V value;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(of = "member")
    public static class ZsetEntry {

        private double score;

        private String member;

        public void addScore(final double increment) {
            score += increment;
        }
    }



    public void set(String key, String value) {
        map.put(key, new CacheEntry<>(value));
    }

    public String get(String key) {
        CacheEntry<String> entry = (CacheEntry<String>) map.get(key);
        return entry == null ? null : entry.getValue();
    }

    public boolean exists(String key) {
        return map.containsKey(key);
    }

    public int incr(final String key) {
        String val = get(key);
        int value = val == null ? 0 : Integer.parseInt(val);
        value ++;
        map.put(key, new CacheEntry<>(String.valueOf(value)));
        return value;
    }

    public int incrby(final String key, final String param) {
        String val = get(key);
        int value = val == null ? 0 : Integer.parseInt(val);
        value = value + Integer.parseInt(param);
        map.put(key, new CacheEntry<>(String.valueOf(value)));
        return value;
    }

    public int decr(final String key) {
        String val = get(key);
        int value = val == null ? 0 : Integer.parseInt(val);
        value --;
        map.put(key, new CacheEntry<>(String.valueOf(value)));
        return value;
    }

    public int decrby(final String key, final String param) {
        String val = get(key);
        int value = val == null ? 0 : Integer.parseInt(val);
        value = value - Integer.parseInt(param);
        map.put(key, new CacheEntry<>(String.valueOf(value)));
        return value;
    }

    public int del(final String key) {
        CacheEntry<?> val = map.remove(key);
        if (val == null) {
            return 0;
        }
        return 1;
    }

    public int lpush(String key, String... values) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry) map.get(key);
        if (entry == null) {
            entry = new CacheEntry<>(new LinkedList<>());
        }
        LinkedList list = entry.getValue();

        List.of(values).forEach(list::addFirst);

        entry.setValue(list);

        map.put(key, entry);

        return list.size();
    }

    public String lpop(String key) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry) map.get(key);
        if (entry == null || entry.getValue().size() <= 0) {
            return null;
        }
        LinkedList<String> list = entry.getValue();
        return list.removeFirst();
    }

    public int llen(String key) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry) map.get(key);
        if (entry == null || entry.getValue().size() <= 0) {
            return 0;
        }
        LinkedList<String> list = entry.getValue();
        return list.size();
    }

    public int rpush(String key, String... values) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry) map.get(key);
        if (entry == null) {
            entry = new CacheEntry<>(new LinkedList<>());
        }
        LinkedList list = entry.getValue();

        List.of(values).forEach(list::addLast);

        entry.setValue(list);

        map.put(key, entry);

        return list.size();
    }

    public String rpop(String key) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry) map.get(key);
        if (entry == null || entry.getValue().size() <= 0) {
            return null;
        }
        LinkedList<String> list = entry.getValue();
        return list.removeLast();
    }

    public List<String> lrange(final String key, final int start, final int end) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry) map.get(key);
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

        CacheEntry<LinkedHashMap<String, String>> cacheEntry = (CacheEntry<LinkedHashMap<String, String>>) map.get(key);

        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Map<String, String> hashMap = cacheEntry.getValue();

        String val = hashMap.remove(field);

        return val == null ? 0 : 1;
    }

    public int hset(final String key, final String field, final String value) {
        CacheEntry<LinkedHashMap<String, String>> cacheEntry = (CacheEntry<LinkedHashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            cacheEntry = new CacheEntry<>(new LinkedHashMap<>());
            map.put(key, cacheEntry);
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        String val = hashMap.put(field, value);
        return val == null ? 0 : 1;
    }

    public String hget(final String key, final String field) {
        CacheEntry<LinkedHashMap<String, String>> cacheEntry = (CacheEntry<LinkedHashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return null;
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        return hashMap.get(field);
    }

    public int hexists(final String key, final String field) {
        CacheEntry<LinkedHashMap<String, String>> cacheEntry = (CacheEntry<LinkedHashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        return hashMap.containsKey(field) ? 1 : 0;
    }

    public List<String> hgetall(final String key) {
        CacheEntry<LinkedHashMap<String, String>> cacheEntry = (CacheEntry<LinkedHashMap<String, String>>) map.get(key);
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
        CacheEntry<LinkedHashMap<String, String>> cacheEntry = (CacheEntry<LinkedHashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            cacheEntry = new CacheEntry<>(new LinkedHashMap<>());
            map.put(key, cacheEntry);
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        int val = Integer.parseInt(hashMap.get(field) == null ? "0" : hashMap.get(field));
        val += value;
        hashMap.put(field, String.valueOf(val));
        return val;
    }

    public List<String> hkeys(final String key) {
        CacheEntry<LinkedHashMap<String, String>> cacheEntry = (CacheEntry<LinkedHashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new ArrayList<>();
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        List<String> list = new ArrayList<>(hashMap.keySet());
        return list;
    }

    public int hlen(final String key) {
        CacheEntry<LinkedHashMap<String, String>> cacheEntry = (CacheEntry<LinkedHashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        return hashMap.size();
    }

    public int hsetnx(final String key, final String field, final String value) {
        CacheEntry<LinkedHashMap<String, String>> cacheEntry = (CacheEntry<LinkedHashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            cacheEntry = new CacheEntry<>(new LinkedHashMap<>());
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
        CacheEntry<LinkedHashMap<String, String>> cacheEntry = (CacheEntry<LinkedHashMap<String, String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new ArrayList<>();
        }
        Map<String, String> hashMap = cacheEntry.getValue();
        List<String> list = new ArrayList<>(hashMap.values());
        return list;
    }

    public int sadd(final String key, final String... members) {
        CacheEntry<Set<String>> cacheEntry = (CacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            cacheEntry = new CacheEntry<>(new LinkedHashSet<>());
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
        CacheEntry<Set<String>> cacheEntry = (CacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Set<String> set = cacheEntry.getValue();
        return set.size();
    }

    public Set<String> sdiff(final String... keys) {
        CacheEntry<Set<String>> cacheEntry = (CacheEntry<Set<String>>) map.get(keys[0]);
        Set<String> originSet;
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            originSet = new LinkedHashSet<>();
        } else {
            originSet = new LinkedHashSet<>(cacheEntry.getValue());
        }

        for (int i = 1; i < keys.length; i++) {
            CacheEntry<Set<String>> cacheEntryOther = (CacheEntry<Set<String>>) map.get(keys[i]);
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
        map.put(destination, new CacheEntry<>(diffSet));
        return diffSet;
    }

    public Set<String> sinter(final String... keys) {
        CacheEntry<Set<String>> cacheEntry = (CacheEntry<Set<String>>) map.get(keys[0]);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new LinkedHashSet<>();
        }
        Set<String> originSet = new LinkedHashSet<>(cacheEntry.getValue());
        for (int i = 1; i < keys.length; i++) {
            CacheEntry<Set<String>> cacheEntryOther = (CacheEntry<Set<String>>) map.get(keys[i]);
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
        map.put(destination, new CacheEntry<>(interSet));
        return interSet;
    }

    public int sismember(final String key, final String member) {
        CacheEntry<Set<String>> cacheEntry = (CacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Set<String> set = cacheEntry.getValue();
        return set.contains(member) ? 1 : 0;
    }

    public Set<String> smembers(final String key) {
        CacheEntry<Set<String>> cacheEntry = (CacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new LinkedHashSet<>();
        }
        return cacheEntry.getValue();
    }

    public int smove(final String source, final String destination, final String member) {
        CacheEntry<Set<String>> sourceEntry = (CacheEntry<Set<String>>) map.get(source);
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

    public List<String> spop(final String key, final int count) {
        CacheEntry<Set<String>> cacheEntry = (CacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return null;
        }
        Set<String> set = cacheEntry.getValue();

        List<String> list = new ArrayList<>(set);
        Collections.shuffle(list);

        int size = list.size();
        size =  Math.min(count, size);

        List<String> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String member = list.get(i);
            if (set.remove(member)) {
                result.add(member);
            }
        }

        return result;
    }

    public Set<String> srandmember(final String key, final int count) {
        CacheEntry<Set<String>> cacheEntry = (CacheEntry<Set<String>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return new LinkedHashSet<>();
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
        return new LinkedHashSet<>(list.subList(0, size));
    }

    public int srem(final String key, final String... members) {
        CacheEntry<Set<String>> cacheEntry = (CacheEntry<Set<String>>) map.get(key);
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
        Set<String> set = new LinkedHashSet<>();
        for (String key : keys) {
            CacheEntry<Set<String>> cacheEntry = (CacheEntry<Set<String>>) map.get(key);
            if (cacheEntry == null || cacheEntry.getValue() == null) {
                continue;
            }
            set.addAll(cacheEntry.getValue());
        }
        return set;
    }

    public int sunionstore(final String destination, final String... keys) {
        Set<String> sets = sunion(keys);
        map.put(destination, new CacheEntry<>(sets));
        return sets.size();
    }
    //=============================set end========================================

    //=============================zset start========================================
    public int zadd(final String key, final ZsetEntry ... entries) {
        CacheEntry<LinkedHashSet<ZsetEntry>> cacheEntry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            cacheEntry = new CacheEntry<>(new LinkedHashSet<>());
            map.put(key, cacheEntry);
        }
        Set<ZsetEntry> set = cacheEntry.getValue();

        int count = 0;
        for (ZsetEntry entry : entries) {
            if (set.add(entry)) {
                count ++;
            }
        }
        return count;
    }

    public int zcard(final String key) {
        CacheEntry<LinkedHashSet<ZsetEntry>> cacheEntry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Set<ZsetEntry> set = cacheEntry.getValue();
        return set.size();
    }

    public int zcount(final String key, final double min, final double max) {
        CacheEntry<LinkedHashSet<ZsetEntry>> cacheEntry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Set<ZsetEntry> set = cacheEntry.getValue();
        int count = 0;
        for (ZsetEntry entry : set) {
            double score = entry.getScore();
            if (score >= min && score <= max) {
                count++;
            }
        }
        return count;
    }

    public double zincrby(final String key, final double increment, final String member) {
        CacheEntry<LinkedHashSet<ZsetEntry>> cacheEntry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            cacheEntry = new CacheEntry<>(new LinkedHashSet<>());
            map.put(key, cacheEntry);
        }
        Set<ZsetEntry> sets = cacheEntry.getValue();

        ZsetEntry zsetEntry = sets.stream().filter(entry -> entry.getMember().equals(member)).findFirst().orElse(null);
        if (zsetEntry == null) {
            zsetEntry = new ZsetEntry(increment, member);
            cacheEntry.getValue().add(zsetEntry);
        } else {
            zsetEntry.addScore(increment);
        }

        return zsetEntry.getScore();
    }

    public int zinterstore(final String destination, final int numKeys, final String... keys) {

        if (keys.length != numKeys) {
            throw new SyntaxException();
        }

        CacheEntry<LinkedHashSet<ZsetEntry>> cacheEntry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(keys[0]);
        if (cacheEntry == null || cacheEntry.getValue() == null) {
            return 0;
        }
        Set<ZsetEntry> originSet = new LinkedHashSet<>(cacheEntry.getValue());

        Set<ZsetEntry> resultSet = new LinkedHashSet<>();
        for (ZsetEntry origin : originSet) {
            for (int i = 1; i < keys.length; i++) {
                CacheEntry<LinkedHashSet<ZsetEntry>> cacheEntryOther = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(keys[i]);
                if (cacheEntry.getValue() == null) {
                    continue;
                }
                Set<ZsetEntry> otherSet = cacheEntryOther.getValue();
                for (ZsetEntry other : otherSet) {
                    if (origin.equals(other)) {
                        origin.addScore(other.getScore());
                        resultSet.add(origin);
                    }
                }
            }
        }

        map.put(destination, new CacheEntry<>(resultSet));
        return resultSet.size();
    }

    public Set<ZsetEntry> zrange(final String key, final int start, final int end) {
        CacheEntry<LinkedHashSet<ZsetEntry>> entry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        if (entry == null || entry.getValue() == null) {
            return new LinkedHashSet<>();
        }

        LinkedHashSet<ZsetEntry> zsetEntries = entry.getValue();
        int size = zsetEntries.size();

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

        List<ZsetEntry> list = new ArrayList<>(zsetEntries);
        Set<ZsetEntry> result = new LinkedHashSet<>();
        for (int i = startIndex; i < endIndex; i++) {
            result.add(list.get(i));
        }
        return result;
    }
}
