package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BoardSpaceSymmetry {

    int boardSize; // Assumes a square board so we can find the diagonals
    int xIndex;
    int yIndex;
    int xMax;
    int yMax;

    public BoardSpaceSymmetry() {
        this.boardSize = 8;
        this.xIndex = 0;
        this.yIndex = 0;
        this.xMax = 7;
        this.yMax = 7;
    }

    public BoardSpaceSymmetry(int boardSize, int xIndex, int yIndex) {
        this.boardSize = boardSize;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.xMax = xIndex + boardSize - 1;
        this.yMax = yIndex + boardSize - 1;
    }

    public int getBoardSize() {
        return boardSize;
    }
    public int getxIndex() {
        return xIndex;
    }
    public int getyIndex() {
        return yIndex;
    }
    public int getxMax() {
        return xMax;
    }
    public int getyMax() {
        return yMax;
    }

    public boolean isSpaceOnDiagonal (int xCoord, int yCoord) {
        if ((xCoord - getxIndex()) == (yCoord - getyIndex())) {
            return true;
        }
        if(getyMax() - yCoord == xCoord - getxIndex()) {
            return true;
        }
        return false;
    }

    public ArrayList<Integer[]> exampleSpacesOnDiagonal() {
        ArrayList<Integer[]> ret = new ArrayList<>();
        int halfSize = (boardSize+1)/2; // round up
        for (int i=0; i<halfSize; i++) {
            ret.add(new Integer[] {i+getxIndex(), i+getyIndex()});
        }
        return ret;
    }

    public ArrayList<Integer[]> exampleSpacesNotOnMainDiagonal() {
        ArrayList<Integer[]> ret = new ArrayList<>();
        int halfSize = (boardSize+1)/2; // round up
        int i = 0;
        while (i < halfSize) {
            int j = i + 1;
            while (j < halfSize) {
                ret.add(new Integer[] {j+getxIndex(), i+getyIndex()});
                j++;
            }
            i++;
        }
        return ret;
    }



    public ArrayList<Integer[]> allExampleSpaces() {
        /*
        A list of coordinates with one example of each of the ten types of tiles on the board, with their
        coordinates indexed by the ones for this BoardSpaceSymmetry
         */
        ArrayList<Integer[]> ret = exampleSpacesOnDiagonal();
        ret.addAll(exampleSpacesNotOnMainDiagonal());
        return ret;
    }

    public Set<Pair> spacesOnDiagonalHalfOfBoardNotInInputSet(Set<Pair> exclusionSet) {
        /* Returns all tiles on a particular diagonal, plus all tiles on one side of that diagonal, that are not in the
        input set.
        Uses the diagonal from xIndex, yIndex to xIndex+boardSize-1, yIndex+boardSize-1.
        The half returned is the half of the board with y coordinates greater than or equal to those on the diagonal.
         */
        Set<Pair> ret = new HashSet<>();
            int x = 0;
            while (x < boardSize) {
                int y = x;
                while (y < boardSize) {
                    Pair p = new Pair(x + xIndex, y + yIndex);
                    if (!exclusionSet.contains(p)) {
                        ret.add(p);
                    }
                    y++;
                }
                x++;
            }
        return ret;
    }

}
