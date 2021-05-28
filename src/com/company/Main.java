package com.company;

import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //BasicTests.runBasicTests();
        BasicTests.testValidKnightMoves();
        HashMap<Pair, Integer> selfDest = KnightDestinationFinder.getWithinNKnightMoves(
                1, 0, 0, 0);
        ArrayList<String> gotoSelf = PrintAids.makeSortedStringsFromSetOfPairs(selfDest.keySet());
        PrintAids.printStringArrayList(gotoSelf);
        System.out.println();

        HashMap<Pair, Integer> withinOne = KnightDestinationFinder.getWithinNKnightMoves(
                1, 0, 1, 0);
        ArrayList<String> jumpRange = PrintAids.makeSortedStringsFromSetOfPairs(withinOne.keySet());
        PrintAids.printStringArrayList(jumpRange);
        System.out.println();

        HashMap<Pair, Integer> withinTwo = KnightDestinationFinder.getWithinNKnightMoves(
                1, 0, 2, 0);
        PrintAids.printHashMap(withinTwo);
        System.out.println();

        HashSet<Pair> kingMovesWithinTwo = KingMoves.getValidKingMovesWithinN(2, 1, 0, 0);
        ArrayList<String> withinTwoTiles = PrintAids.makeSortedStringsFromSetOfPairs(kingMovesWithinTwo);
        PrintAids.printStringArrayList(withinTwoTiles);
        System.out.println();

        HashMap<Pair, Integer> withinFour = KnightDestinationFinder.getWithinNKnightMoves(
                1, 0, 4, 0);
        //PrintAids.printHashMap(withinFour);
        Set<Pair> withinFourDestinations = withinFour.keySet();
        System.out.println();

        System.out.println(kingMovesWithinTwo.size());
        HashSet<Pair> kingTwoClone = (HashSet) kingMovesWithinTwo.clone();
        kingTwoClone.retainAll(withinFourDestinations);
        System.out.println(kingTwoClone.size());
        /*If kingTwoClone.size() equals the previous print, it means all spaces within 2 tiles can be reached within
        four knight moves.
         */
    }
}
