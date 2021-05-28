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
        System.out.println();
        /*If kingTwoClone.size() equals the previous print, it means all spaces within 2 tiles can be reached within
        four knight moves.
         */

        /*
        There are ten types of spaces on a chess board - for example, corner spaces, spaces immediately orthogonally
        adjacent to corner spaces, the four middle spaces, etc. Of the 64 spaces on a chess board, there are four types
        of spaces on the main diagonals, of which there are four each, and six types of spaces not on those diagonals,
        of which there are eight each.
         */

        BoardSpaceSymmetry bSpace = new BoardSpaceSymmetry();
        ArrayList<Integer[]> oneEachOfSixNonDTypes = bSpace.exampleSpacesNotOnMainDiagonal();
        ArrayList<String> nonDgStrings = PrintAids.makeSortedStringsFromArrayListOfIntegerArrays(oneEachOfSixNonDTypes);
        PrintAids.printStringArrayList(nonDgStrings);

        /*
        TODO: Verify that all ten types of spaces have all spaces within two tiles accessible within four knight moves
         */

        /*
        TODO: Verify that from one of the four middle tiles, within two knight moves, it is possible to reach spaces
        that collectively hold every space on the board within two spaces of them.
         */
    }
}
