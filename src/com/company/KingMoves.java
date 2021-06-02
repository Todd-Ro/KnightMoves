package com.company;

import java.util.HashSet;

public class KingMoves {

    public static HashSet<Pair> getValidKingMovesWithinN (int n, int xStart, int yStart, int index) {
        //As always, index is 0 if the edges of the board are handled internally as having coordinate zero
        HashSet<Pair> ret = new HashSet<>();
        int x = xStart - n;
        int y = yStart - n;
        while (x <= xStart + n) {
            if (x >= 0+index) {
                if (x <= 7+index) {
                    while (y <= yStart + n) {
                        if (y >= 0+index) {
                            if (y <= 7+index) {
                                ret.add(new Pair(x, y));
                            }
                        }
                        y++;
                    }
                    y = yStart - n;
                }
            }

            x++;
        }
        return ret;
    }

    public static void seeHashSetCloningMechanics() {
        //Simply illustrates certain properties of HashSets in Java
        HashSet h = new HashSet();
        h.add("aString");
        HashSet g = (HashSet) h.clone();
        h.add("bString");
        System.out.println(h.size());
        System.out.println(g.size());
    }




}
