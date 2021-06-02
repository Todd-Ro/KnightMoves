package com.company;

import java.util.*;

public class ComparablePair <T extends Comparable, U> implements Comparable<ComparablePair> {

    public static <T extends Comparable, U> Map.Entry<T, U> of(T first, U second) {
        return new AbstractMap.SimpleEntry<>(first, second);
    }

    Map.Entry<Comparable, Object> entry;

    public ComparablePair (T x, U y) {
        Set<Map.Entry<T, U>> entries = new HashSet<>();
        entries.add(ComparablePair.of(x, y));
        Object[] setArray = entries.toArray();
        this.entry = (Map.Entry<Comparable, Object>) setArray[0];
    }

    ComparablePair() {}


    public Comparable getKey() {
        return entry.getKey();
    }


    public Object getValue() {return entry.getValue();}

    @Override
    public int compareTo(ComparablePair p) {
        /* Returns comparison of keys if not zero, otherwise comparison of values if comparable,
        otherwise difference of hashes.
        May produce errors if the values are Comparable but not to each other, or the keys are of differing types
        such that each key is comparable to members of its own class but not the other key's class.
        Transitivity may occasionally be violated if three values have equal keys, two have values that are comparable,
        and the third has a value that does not implement comparable and so goes to hash comparison.
         */
        int keyComp = getKey().compareTo(p.getKey());
        if (keyComp == 0) {
            boolean bothYsComparable = ((getValue() instanceof Comparable) && (p.getValue() instanceof Comparable));
            if (bothYsComparable) {
                Comparable thisValue = (Comparable) getValue();
                Comparable thatValue = (Comparable) p.getValue();
                return thisValue.compareTo(thatValue);
            }
            else {
                return getValue().hashCode() - p.getValue().hashCode();
            }
        }
        else {
            return keyComp;
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof ComparablePair) {
            ComparablePair pair = (ComparablePair) o;
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
