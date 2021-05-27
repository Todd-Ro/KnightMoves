package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    public static ArrayList<String> makeSortedStringsFromArrayListOfIntegerArrays(ArrayList<Integer[]> vKMs) {
        ArrayList<String> ret = new ArrayList<String>();
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

}