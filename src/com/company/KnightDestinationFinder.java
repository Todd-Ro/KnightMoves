package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class KnightDestinationFinder {

    public static int[][] getKnightMoves() {
        int[][] ret = new int[8][2];
        ret[0] = new int[]{1,2};
        ret[1] = new int[]{2,1};
        ret[2] = new int[]{2,-1};
        ret[3] = new int[]{1,-2};
        ret[4] = new int[]{-1,-2};
        ret[5] = new int[]{-2,-1};
        ret[6] = new int[]{-2,1};
        ret[7] = new int[]{-1,2};
        return ret;
    }

    public static int[][] possibleKnightMoves(int xStart, int yStart) {
        int[][] ret = new int[8][2];
        int[][] kMoves = getKnightMoves();
        int i = 0;
        while (i < 8) {
            int[] thisMove = kMoves[i];
            ret[i] = new int[]{thisMove[0] + xStart, thisMove[1] + yStart};
            i++;
        }
        return ret;
    }

    public static Integer[] makeIntArrayNonPrimitive(int[] candidates) {
        Integer[] boxedArray;
        /*boxedArray = new Integer[candidates.length];
        for (int i=0; i < candidates.length; i++) {
            boxedArray[i] = Integer.valueOf(candidates[i]);
        } */
        boxedArray = Arrays.stream(candidates).boxed().toArray(Integer[]::new); //Java 8 solution
        return boxedArray;
    }

    public static ArrayList<Integer[]> validKnightMoves(int xStart, int yStart, int indexFromOne) {
        // indexFromOne should be 0 when indexing coordinate grid (board edge spaces) from 0 and 1 to index from 1
        ArrayList<Integer[]> ret = new ArrayList<Integer[]>();
        int[][] candidates = possibleKnightMoves(xStart, yStart);
        int i = 0;
        while (i < 8) {
            Integer[] candidate = makeIntArrayNonPrimitive(candidates[i]);
            if (candidate[0] >= 0 + indexFromOne) {
                if (candidate[0] <= 7 + indexFromOne) {
                    if (candidate[1] >= 0 + indexFromOne) {
                        if (candidate[1] <= 7 + indexFromOne) {
                            ret.add(candidate);
                        }
                    }
                }
            }
            i++;
        }
        return ret;
    }
}
