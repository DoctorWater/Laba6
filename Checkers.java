package mainClient.java;

import common.Product;

import java.io.Serializable;
import java.util.Hashtable;

public class Checkers implements Serializable {
    public static class CheckPartNumber {
        public static boolean check (Hashtable<String, Product> productHashtable, String partNumber){
            final int[] checker = {1};
            if (partNumber==null) {
                checker[0] = 0;
            }
            if (partNumber.length()>86)
                checker[0]=0;
            productHashtable.forEach((k, v) -> {
                if (v.getPartNumber().equals(partNumber))
                    checker[0] = 0;
            });
            return checker[0] != 0;
            }
        }

    }

