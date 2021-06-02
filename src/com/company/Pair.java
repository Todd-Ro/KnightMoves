package com.company;

import java.util.*;

public class Pair extends ComparablePair {
    public static Map.Entry<Integer, Integer> intOf(Integer first, Integer second) {
        return new AbstractMap.SimpleEntry<>(first, second);
    }

    public Pair (Integer x, Integer y) {
        Set<Map.Entry> entries = new HashSet<>();
        entries.add(Pair.intOf(x, y));
        Object[] setArray = entries.toArray();
        this.entry = (Map.Entry<Integer, Integer>) setArray[0];
    }

    public Pair (int x, int y) {
        Set<Map.Entry> entries = new HashSet<>();
        Integer xInte = Integer.valueOf(x);
        Integer yInte = Integer.valueOf(y);
        entries.add(Pair.intOf(xInte, yInte));
        Object[] setArray = entries.toArray();
        this.entry = (Map.Entry<Integer, Integer>) setArray[0];
    }

    /* public int compareTo(Pair p) {
        // Returns comparison of keys if not zero, otherwise comparison of values.
        int keyComp = getKey().compareTo(p.getKey());
        if (keyComp != 0) {
            return keyComp;
        }
        else {
            Integer value = (Integer) getValue();
            Integer pValue = (Integer) p.getValue();
            return value.compareTo(pValue);
        }
    }
    */


}