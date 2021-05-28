package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class BasicTests {

    public static void runBasicTests() {
        // Produces outputs that can be examined to verify how certain aspects of the program work
        testPairClass();
        testGetKnightMoves();
        testPossibleKnightMoves();
    }

    public static void testPairClass() {
        HashMap<Pair, Integer> testMap = new HashMap<>();
        Pair onThrup = new Pair(1, 3);
        System.out.println(onThrup); //Prints contents of Pair, not hash
        testMap.put(onThrup, 7);
        Pair unoTres = new Pair(1, 3);
        System.out.println(onThrup.equals(unoTres));
        System.out.println(testMap.containsKey(onThrup));
        System.out.println(unoTres.hashCode() == onThrup.hashCode());
        System.out.println(testMap.containsKey(unoTres)); // Should be true
        System.out.println();
    }

    public static void testGetKnightMoves() {
        int[] NNE = new int[]{1,2};
        int[] ENE = new int[]{2,1};
        int[] SSE = new int[]{1,-2};
        System.out.println(ENE.toString().compareTo(NNE.toString()));
        System.out.println(SSE.toString().compareTo(NNE.toString()));
        int[][] moves = KnightDestinationFinder.getKnightMoves();
        Set<int[]> moveSet = PrintAids.makeSetFromIntArrayArray(moves);
        ArrayList<String> coordShiftPrint = PrintAids.makeSortedStringsFromSetOfIntArrays(moveSet);
        PrintAids.printStringArrayList(coordShiftPrint);
        System.out.println();
    }

    public static void testPossibleKnightMoves() {
        int[][] knightMovesFromStart = KnightDestinationFinder.possibleKnightMoves(1, 0);
        Set<int[]> moveSetFromStart = PrintAids.makeSetFromIntArrayArray(knightMovesFromStart);
        ArrayList<String> printKnightMovesFromStart = PrintAids.makeSortedStringsFromSetOfIntArrays(moveSetFromStart);
        PrintAids.printStringArrayList(printKnightMovesFromStart);
        System.out.println();
    }

    public static void testValidKnightMoves() {
        ArrayList<Integer[]> validMoves = KnightDestinationFinder.validKnightMoves(1, 0, 0);
        System.out.println(validMoves.size());
        ArrayList<String> validMovesStrings = PrintAids.makeSortedStringsFromArrayListOfIntegerArrays(validMoves);
        PrintAids.printStringArrayList(validMovesStrings);
        System.out.println();
    }
}
