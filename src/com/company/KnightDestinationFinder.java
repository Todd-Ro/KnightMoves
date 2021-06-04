package com.company;

import java.util.*;

public abstract class KnightDestinationFinder {

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

    public static Set<Pair> validMovesWithinOneNotIncludingSelf(Pair p, BoardSpaceSymmetry b) {
        Set<Pair> ret = new HashSet<>();
        int[][] candidates = possibleKnightMoves((Integer) p.getKey(), (Integer) p.getValue());
        int i = 0;
        while (i < 8) {
            Integer[] candidate = makeIntArrayNonPrimitive(candidates[i]);
            if (candidate[0] >= b.getxIndex()) {
                if (candidate[0] <= b.getxMax()) {
                    if (candidate[1] >= b.getyIndex()) {
                        if (candidate[1] <= b.getyMax()) {
                            ret.add(new Pair(candidate[0], candidate[1]));
                        }
                    }
                }
            }
            i++;
        }
        return ret;
    }

    public static Set<Pair> validMovesNotYetTaken(Pair p, BoardSpaceSymmetry b) {
        Set<Pair> withinOne = validMovesWithinOneNotIncludingSelf(p, b);
        withinOne.retainAll(b.getNotVisited());
        return withinOne;
    }

    public static HashMap<Pair, Integer> getWithinNKnightMoves (int xStart, int yStart, int nMoves, int offset) {
        /*Offset greater than zero increases the reported number of moves to get to a tile
        and is mainly for recursive calls
         */
        if (nMoves < 0) {
            return null;
        }
        Pair origin = new Pair(xStart, yStart);
        HashMap<Pair, Integer> ret = new HashMap<>();
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

    public static HashMap<Pair, ComparablePair<Integer, ArrayList<Pair>>> getWithinNKnightMovesWithPath (
            int xStart, int yStart, int nMoves, int offset
    ) {
        /*A version of getWithinNKnightMoves that includes an example path to get to each destination
        in the shortest possible number of moves.
        A HashMap from a Pair to a ComparablePair with an Integer then an Arraylist
        of Pairs.

        Returns a HashMap with keys that are the tiles reachable from the one at (xStart, yStart)
        and values mapped to those keys that have the minimum number of moves to get there, then an example path to
        get there in that many moves.
        The example path includes the start tile (even if it is the destination) but not the destination.
         */
        /*Offset greater than zero increases the reported number of moves to get to a tile
        and is mainly for recursive calls.
        Recall that the number of spaces to get somewhere is the integer that is the first item in the ComparablePair
        that is the value in the HashMap.
        Thus, the number of moves to get somewhere is the Integer for x in a ComparablePair,
        NOT the Integer arrays from validKnightMoves.
         */
        if (nMoves < 0) {
            return null;
        }
        Pair origin = new Pair(xStart, yStart);
        HashMap<Pair, ComparablePair<Integer, ArrayList<Pair>>> ret = new HashMap<>();
        ArrayList<Pair> toBegin = new ArrayList();
        toBegin.add(origin);
        ComparablePair<Integer, ArrayList<Pair>> originPoint = new ComparablePair<>(0+offset, toBegin);
        ret.put(origin, originPoint);
        if (nMoves > 0) {
            ArrayList<Integer[]> withinOneMoves = validKnightMoves(xStart, yStart, 0);
            //Assume index from 0
            int len = withinOneMoves.size();
            int i = 0;
            while (i < len) {
                Integer[] move = withinOneMoves.get(i);
                Pair p = new Pair(move[0], move[1]);
                ComparablePair<Integer, ArrayList<Pair>> oneStep = new ComparablePair<>(1+offset, toBegin);
                ret.put(p, oneStep);
                i++;
            }

            if (nMoves > 1) {
                int j = 0;
                while (j < len) {
                    Integer[] move = withinOneMoves.get(j);
                    Pair p = new Pair(move[0], move[1]);
                    HashMap<Pair, ComparablePair<Integer, ArrayList<Pair>>> thisMovesBranch =
                            getWithinNKnightMovesWithPath(
                            (Integer)p.getKey(), (Integer)p.getValue(), nMoves-1, offset+1);
                    for (Pair q: thisMovesBranch.keySet()) {
                        if (!ret.containsKey(q)) {
                            //Important that equality of Pairs and their hashes works correctly for containsKey check
                            ComparablePair<Integer, ArrayList<Pair>> thisHashValue = thisMovesBranch.get(q);
                            Integer stepCount = (Integer) thisHashValue.getKey();
                            ArrayList<Pair> thisPathList = (ArrayList<Pair>) thisHashValue.getValue();
                            if (thisPathList.get(0).equals(origin) == false) {
                                thisPathList.add(0, origin);
                            }

                            thisHashValue = new ComparablePair<>(stepCount, thisPathList);
                            ret.put(q, thisHashValue);
                        }
                        else {
                            Integer qMovesNeeded = (Integer)thisMovesBranch.get(q).getKey();
                            if ( qMovesNeeded < ( (Integer)ret.get(q).getKey() ) ) {
                                ArrayList<Pair> a = (ArrayList<Pair>) thisMovesBranch.get(q).getValue();
                                a.add(0, origin);
                                ComparablePair<Integer, ArrayList<Pair>> qInfo = new ComparablePair<>(qMovesNeeded, a);
                                ret.replace(q, qInfo);
                            }
                        }
                    }
                    j++;
                }
            }
        }
        return ret;
    }

    public static void checkReachOfCenterTile(int nMoves, BoardSpaceSymmetry bSpace) {
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
        Set<Pair> notCovered = bSpace.spacesOnDiagonalHalfOfBoardNotInInputSet(withinTwoKingOfOtherMap);
        System.out.println(notCovered.size());
        if (notCovered.size() > 0) {
            ArrayList<String> notCoveredText = PrintAids.makeSortedStringsFromSetOfPairs(notCovered);
            PrintAids.printStringArrayList(notCoveredText);
        }
        System.out.println();
    }

    public static ArrayList<Pair> vectorsToReachCoordinates(ArrayList<Pair> destinations, int xStart, int yStart) {
        /*
        A method that takes the board coordinates of valid knight destinations reachable from a starting point
        and outputs the relative vectors showing the coordinate shift from the starting point
        to those valid reachable tiles.
        Even if the board coordinates are indexed from zero, the vector from a start tile to a destination tile will
        differ from the coordinates of the destination tile unless the start tile is the one at (0,0).
         */
        ArrayList<Pair> ret = new ArrayList();
        for (Pair p:destinations) {
            ret.add(new Pair((Integer) p.getKey() - xStart, (Integer) p.getValue()-yStart));
        }
        return ret;
    }

    public static void testOutputType() {
        HashMap<Pair, ComparablePair> testHash = new HashMap<>();
        Pair duoQuat = new Pair(2, 4);
        Pair hexdex = new Pair(6, 6);
        ArrayList<Pair> steps = new ArrayList<>();
        steps.add(new Pair(4, 5));
        Integer Two = 2;
        Integer Zero = 0;
        ComparablePair testComp = new ComparablePair<Integer, ArrayList>(Two, steps);
        ArrayList<Pair> noSteps = new ArrayList<Pair>();
        ComparablePair zeroComp = new ComparablePair<Integer, ArrayList>(Zero, noSteps);
        testHash.put(duoQuat, zeroComp);
        testHash.put(hexdex, testComp);
        for (Pair p: testHash.keySet()) {
            ComparablePair cp = testHash.get(p);
            System.out.println(p.toString() + ": " + cp.getKey().toString());
        }
        System.out.println();
        System.out.println(Two.compareTo(Zero));
        System.out.println(testComp.compareTo(zeroComp));
        //Imagine duoQuat is calling a method that traces the path to hexdex and to itself.
    }


    public static Pair nextMove(Pair p, BoardSpaceSymmetry b, Pair compCorner) {
        //Returns the next move for our pathfinding program
        b.visit(p);
        Set<Pair> finalists = new HashSet();
        Set<Pair> validMovesNotYetTaken = validMovesNotYetTaken(p, b);
        if (validMovesNotYetTaken.isEmpty()) {
            return null;
        }
        int minNeighbors = 7;
        /*Since this space is visited, the spaces k-adjacent to it wil have 7 or fewer un-visited neighbors.
        We will find the one(s) with the fewest as finalists.
        */
        for (Pair pNext:validMovesNotYetTaken) {
            int options = validMovesNotYetTaken(pNext, b).size();
            if (options < minNeighbors) {
                finalists.clear();
                finalists.add(pNext);
                minNeighbors = options;
            }
            else if (options == minNeighbors) {
                finalists.add(pNext);
            }
        }
        return BoardSpaceSymmetry.pickBest(finalists, compCorner);
    }

    public static ArrayList<Pair> thePath(int xStart, int yStart, BoardSpaceSymmetry b) {
        //Together with nextMove, this is the heart of our pathfinding determination
        b.refreshResetNotVisited();
        Pair start = new Pair(xStart, yStart);
        Pair compCorner = b.findNearestCorner(start, b);
        ArrayList<Pair> ret = new ArrayList();
        Pair thisPair = start;
        while (thisPair != null) {
            ret.add(thisPair);
            thisPair = nextMove(thisPair, b, compCorner);
        }
        return ret;
    }







}
