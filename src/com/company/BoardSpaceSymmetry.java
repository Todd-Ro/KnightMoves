package com.company;

import java.util.*;

public class BoardSpaceSymmetry {

    int boardSize; // Assumes a square board so we can find the diagonals
    int xIndex;
    int yIndex;
    int xMax;
    int yMax;

    private final Set<Pair> allTiles;
    private Set<Pair> notVisited;

    public Set<Pair> getAllTiles() {
        if (allTiles.isEmpty()) {
            generateAllTiles();
        }
        return allTiles;
    }

    public void visit(Pair p) {
        this.notVisited.remove(p);
    }

    public BoardSpaceSymmetry() {
        this.boardSize = 8;
        this.xIndex = 0;
        this.yIndex = 0;
        this.xMax = 7;
        this.yMax = 7;
        this.allTiles = generateAllTiles();
    }

    public BoardSpaceSymmetry(int boardSize, int xIndex, int yIndex) {
        this.boardSize = boardSize;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.xMax = xIndex + boardSize - 1;
        this.yMax = yIndex + boardSize - 1;
        this.allTiles = generateAllTiles();
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

    public Set<Pair> getNotVisited() {
        return notVisited;
    }

    public final Set<Pair> generateAllTiles() {
        Set<Pair> ret = new HashSet();
        int x = getxIndex();
        while (x <= xMax) {
            int y = getyIndex();
            while (y <= yMax) {
                ret.add(new Pair(x, y));
                y++;
            }
            x++;
        }
        Set<Pair> notVisitedTiles = new HashSet();
        notVisitedTiles.addAll(ret);
        this.notVisited = notVisitedTiles;
        return ret;
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

    public void refreshResetNotVisited() {
        this.notVisited = getAllTiles();
    }

    public static Pair findNearestCorner(Pair p, BoardSpaceSymmetry b) {
        int x = (Integer) p.getKey();
        int y = (Integer) p.getValue();
        int xAdj = x - b.getxIndex();
        int yAdj = y - b.getyIndex();
        boolean rightHalf = (xAdj > (b.getBoardSize()/2));
        boolean topHalf = (yAdj > (b.getBoardSize()/2));
        Integer cornerX;
        Integer cornerY;
        if (rightHalf) {
            cornerX = b.getxMax();
        }
        else {
            cornerX = b.getxIndex();
        }
        if (topHalf) {
            cornerY = b.getyMax();
        }
        else {
            cornerY = b.getyIndex();
        }

        return new Pair(cornerX, cornerY);
    }

    static int distance(Pair p, Pair comparison) {
        //Distance is basically a multiple of manhattan distance that gives slightly higher weight to x differences
        int ret = 0;
        ret += 8*Math.abs((Integer)p.getKey() - (Integer)comparison.getKey());
        ret += 7*Math.abs((Integer)p.getKey() - (Integer)comparison.getKey());
        return ret;
    }

    public static Pair pickBest(Set<Pair> candSet, Pair compCorner) {
        HashMap<Integer, Pair> distances = new HashMap();
        for (Pair p:candSet) {
            distances.put(distance(p, compCorner),p);
        }
        /*On an 8x8 board, there shouldn't be any distance ties from a corner with how distance is defined,
        but if there were, an entry in distances could get overridden.
        This is fine if we do not need a tiebreaker since we still get one of the tied entries returned.
         */
        Collection<Integer> keys = distances.keySet();
        int lowest = Collections.min(keys);
        return distances.get(lowest);
    }


}
