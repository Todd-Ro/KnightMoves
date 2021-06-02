package com.company;

import java.util.*;

public class PrintAids {

    public static Set<int[]> makeSetFromIntArrayArray (int[][] moves) {
        //May remove duplicate elements
        Set<int[]> moveSet = new HashSet<int[]>();
        for (int[] move:moves) {
            moveSet.add(move);
            //System.out.println(Arrays.toString(move));
        }
        return moveSet;
    }

    public static ArrayList<String> makeSortedStringsFromSetOfIntArrays(Set<int[]> moveSet) {
        ArrayList<String> coordShiftPrint = new ArrayList<>();
        int len = 0;
        for (int[] move:moveSet) {
            if (coordShiftPrint.isEmpty()) {
                coordShiftPrint.add(Arrays.toString(move));
                len++;
            }
            else {
                int i = 0;
                boolean added = false;
                while (i < len) {
                    if (Arrays.toString(move).compareTo(coordShiftPrint.get(i)) < 0 ) {
                        coordShiftPrint.add(i, Arrays.toString(move));
                        len++;
                        added = true;
                        i += len;
                    }
                    i++;
                }
                if (added == false) {
                    coordShiftPrint.add(Arrays.toString(move));
                }
            }
        }
        return coordShiftPrint;
    }

    public static ArrayList<String> makeSortedStringsFromSetOfPairs(Set<Pair> destinations) {
        ArrayList<String> destinationsPrint = new ArrayList<>();
        int len = 0;
        for (Pair move : destinations) {
            String moveString = move.toString();
            if (destinationsPrint.isEmpty()) {
                destinationsPrint.add(moveString);
                len++;
            } else {
                int i = 0;
                boolean added = false;
                while (i < len) {
                    if (moveString.compareTo(destinationsPrint.get(i)) < 0) {
                        destinationsPrint.add(i, moveString);
                        len++;
                        added = true;
                        i += len;
                    }
                    i++;
                }
                if (added == false) {
                    destinationsPrint.add(moveString);
                    len++;
                }
            }
        }
        return destinationsPrint;
    }

    public static ArrayList<String> makeSortedStringsFromArrayListOfIntegerArrays(ArrayList<Integer[]> vKMs) {
        ArrayList<String> ret = new ArrayList<>();
        for (Integer[] move:vKMs) {
            String moveString = Arrays.toString(move);
            if (ret.isEmpty()) {
                ret.add(moveString);
            }
            else {
                int len = ret.size();
                int i = 0;
                boolean added = false;
                while (i < len) {
                    if (moveString.compareTo(ret.get(i)) < 0) {
                        ret.add(i, moveString);
                        added = true;
                        i+= len;
                    }
                    i++;
                }
                if (added == false) {
                    ret.add(moveString);
                }
            }

        }
        return ret;
    }

    //May want to add method for making unsorted ArrayList to avoid n^2 insertion sort with up to 64 items

    public static void printStringArrayList(ArrayList<String> coordShiftPrint) {
        for(int i=0; i<coordShiftPrint.size(); i++) {
            System.out.println(coordShiftPrint.get(i));
            /* if (i > 0) {
                System.out.println(coordShiftPrint.get(i).compareTo(coordShiftPrint.get(i-1)));
            } */
        }
    }

    public static void printHashMap (HashMap h) {
        Set<Object> keys = h.keySet();
        for (Object key:keys) {
            System.out.println(key.toString() + ": " + h.get(key));
        }
    }

    public static String printMove(Pair destination, int xIndex, int yIndex, boolean print) {
        Integer x = (Integer) destination.getKey();
        Integer y = (Integer) destination.getValue();
        int printX = x - xIndex + 1;
        int printY = y - yIndex;
        String[] letters = new String[] {"a", "b", "c", "d", "e", "f", "g", "h"};
        // May need more letters if used for boards larger than size 8
        if (yIndex >= letters.length) {
            return null;
        }
        String ret = "N"+letters[printY]+printX;
        if (print == true) {
            System.out.println(ret);
        }
        return ret;
    }

}
