package com.company;

import java.util.*;

public class Pair {
    // Return a map entry (key-value pair) from the specified values
    public static <T, U> Map.Entry<T, U> of(T first, U second) {
        return new AbstractMap.SimpleEntry<>(first, second);
    }

    private Map.Entry entry;

    public Pair (Object x, Object y) {
        Set<Map.Entry> entries = new HashSet<>();
        entries.add(Pair.of(x, y));
        Object[] setArray = entries.toArray();
        this.entry = (Map.Entry) setArray[0];
    }

    public Object getKey() {
        return entry.getKey();
    }
    public Object getValue() {
        return entry.getValue();
    }


    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Pair) {
            Pair pair = (Pair) o;
            return ( (entry.getKey().equals(pair.getKey())) && (entry.getValue().equals(pair.getValue())) );
        }
        return false;
    }

    @Override
    public int hashCode() {
            return Objects.hash(entry.getKey(), entry.getValue());
    }

    @Override
    public String toString() {
            return String.format( "(" +entry.getKey().toString() + ", " + entry.getValue().toString() + ")" );
    }
}
