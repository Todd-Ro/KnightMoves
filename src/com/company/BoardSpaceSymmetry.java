package com.company;

import java.util.ArrayList;

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

}
