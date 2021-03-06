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
        System.out.println();

        ArrayList<Integer[]> oneEachOfTenTypes = bSpace.allExampleSpaces();
        for (Integer[] space: oneEachOfTenTypes) {
            /*
            Verify that all ten types of spaces have all spaces within two tiles accessible within four knight moves
             */
            HashMap<Pair, Integer> withinFourOfSpace = KnightDestinationFinder.getWithinNKnightMoves(
                    space[0], space[1], 4, 0);
            Set<Pair> withinFourMoveSpots = withinFourOfSpace.keySet();
            HashSet<Pair> withinTwoKingMoveSpots = KingMoves.getValidKingMovesWithinN(
                    2, space[0], space[1], 0);
            int nWithinTwo = withinTwoKingMoveSpots.size();
            withinTwoKingMoveSpots.retainAll(withinFourMoveSpots);
            int nReachableWithinTwo = withinTwoKingMoveSpots.size();
            //nReachableWithinTwo should equal nWithinTwo if all valid spaces within two are reachable in four moves
            System.out.println(Arrays.toString(space) + ": " + nWithinTwo + ", " + nReachableWithinTwo);
        }
        System.out.println();

        KnightDestinationFinder.checkReachOfCenterTile(1, bSpace);
        KnightDestinationFinder.checkReachOfCenterTile(2, bSpace);
        /*
            Verifies that from one of the four middle tiles, within two knight moves, it is possible to reach spaces
            that collectively hold every space on the board within two spaces (two king moves) of them.
        */

        HashMap<Pair, Integer> withinThreeOfCenterMap = KnightDestinationFinder.getWithinNKnightMoves(
                3,3,3,0);
        System.out.println(withinThreeOfCenterMap.size());
        Set<Pair> notWithinThreeOnUpperHalfOfBoard =
                bSpace.spacesOnDiagonalHalfOfBoardNotInInputSet(withinThreeOfCenterMap.keySet());
        ArrayList<String> notCoveredText = PrintAids.makeSortedStringsFromSetOfPairs(notWithinThreeOnUpperHalfOfBoard);
        PrintAids.printStringArrayList(notCoveredText);
        //notCoveredText has three spots on main diagonal, plus one other space that is mirrored on the other side
        HashMap<Pair, Integer> withinFourOfCenterMap = KnightDestinationFinder.getWithinNKnightMoves(
                3,3,4,0);
        System.out.println(withinFourOfCenterMap.size());
        /*Since this outputs 64, we have proven that all tiles on the board can be reached from a given center tile
        within four moves. Since the number of moves from tile A to tile B equals the number of moves from B to A,
        this proves that the maximum number of moves from any tile on the board to any other tile on the board
        cannot exceed 8.
         */
        System.out.println();

        BasicTests.testPairClassMore();
        System.out.println();

        KnightDestinationFinder.testOutputType();
        System.out.println();
        System.out.println();

        HashMap<Pair, ComparablePair<Integer, ArrayList<Pair>>> threeThreePath =
                KnightDestinationFinder.getWithinNKnightMovesWithPath(3, 3, 3, 0);
        for (Pair p: threeThreePath.keySet()) {
            System.out.println(p.toString() + ": " + threeThreePath.get(p).getKey().toString() + " move(s) required");
            for (Pair q: (ArrayList<Pair>) threeThreePath.get(p).getValue()) {
                System.out.println(q.toString());
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();

        Pair onZro = new Pair(1+1, 0+2);
        System.out.println(PrintAids.printMove(onZro, 0, 0, false)); // Should print "Nc3"
        System.out.println();

        for (Integer[] space: oneEachOfTenTypes) {
            /*
            Verify how many spaces each of the ten types of space has within one move
             */
            HashMap<Pair, Integer> withinOneOfSpace = KnightDestinationFinder.getWithinNKnightMoves(
                    space[0], space[1], 1, 0);
            Set<Pair> withinOneMoveSpots = withinOneOfSpace.keySet();
            System.out.println(Arrays.toString(space) + ": " + (Integer) (withinOneMoveSpots.size()-1)
                    + " k-adjacent");
            //Subtract one because each set includes the space itself
        }
        System.out.println();

        /*bSpace.visit(new Pair(1, 0));
        System.out.println(bSpace.getNotVisited().size());
        System.out.println(bSpace.getAllTiles().size());*/
        // Test visit method

        System.out.println("And now, the moment you've all been waiting for...");
        System.out.println();
        ArrayList<Pair> theKnightPath = KnightDestinationFinder.thePath(1, 0, bSpace);
        System.out.println(theKnightPath.size());
        System.out.println();
        for (Pair p:theKnightPath) {
            System.out.println(p.toString());
        }
        System.out.println();
        Set<Pair> allTilesInPath = new HashSet(theKnightPath);
        System.out.println(allTilesInPath.size());


    }
}
