package com.company;

import java.util.*;

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

    public static HashMap<Pair, Integer> getWithinNKnightMoves (int xStart, int yStart, int nMoves, int offset) {
        //Offset greater than zero is mainly for recursive calls
        if (nMoves < 0) {
            return null;
        }
        Pair origin = new Pair(xStart, yStart);
        HashMap<Pair, Integer> ret = new HashMap<Pair, Integer>();
        ret.put(origin, 0+offset);
        if (nMoves > 0) {
            ArrayList<Integer[]> withinOneMoves = validKnightMoves(xStart, yStart, 0);
            //Assume index from 0
            int len = withinOneMoves.size();
            int i = 0;
            while (i < len) {
                Integer[] move = withinOneMoves.get(i);
                Pair p = new Pair(move[0], move[1]);
                ret.put(p, 1+offset);
                i++;
            }

            if (nMoves > 1) {
                int j = 0;
                while (j < len) {
                    Integer[] move = withinOneMoves.get(j);
                    Pair p = new Pair(move[0], move[1]);
                    HashMap<Pair, Integer> thisMovesBranch = getWithinNKnightMoves(
                            (Integer)p.getKey(), (Integer)p.getValue(), nMoves-1, offset+1);
                    for (Pair q: thisMovesBranch.keySet()) {
                        if (!ret.containsKey(q)) {
                            //Important that equality of Pairs and their hashes works correctly for containsKey check
                            ret.put(q, thisMovesBranch.get(q));
                        }
                        else {
                            if (thisMovesBranch.get(q) < ret.get(q)) {
                                ret.replace(q, thisMovesBranch.get(q));
                            }
                        }
                    }
                    j++;
                }
            }
        }
        return ret;
    }

    public static void checkReachOfCenterTile(int nMoves) {
        HashMap<Pair, Integer> withinNOfCenterMap = KnightDestinationFinder.getWithinNKnightMoves(
                3,3,nMoves,0);
        System.out.println(withinNOfCenterMap.size());
        Set<Pair> withinNOfCenter = withinNOfCenterMap.keySet();
        System.out.println(withinNOfCenter.size());
        Set<Pair> withinTwoKingOfOtherMap = new HashSet<Pair>();
        for (Pair p:withinNOfCenter) {
            HashSet<Pair> withinTwoOfCurrent = KingMoves.getValidKingMovesWithinN(
                    2,(Integer)p.getKey(),(Integer)p.getValue(),0);
            withinTwoKingOfOtherMap.addAll(withinTwoOfCurrent);
        }
        System.out.println(withinTwoKingOfOtherMap.size());
        System.out.println();
    }

    /*
    TODO: A method that takes the board coordinates of valid knight destinations reachable from a starting point
    to the relative vectors showing the coordinate shift from the starting point to those valid reachable tiles.
    Even if the board coordinates are indexed from zero, the vector from a start tile to a destination tile will differ
    from the coordinates of the destination tile unless the start tile is the one at (0,0).
     */
}
