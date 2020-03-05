package com.similar.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by syanivs on 03/03/2020.
 */
@Service
public class PermutationsService {

    private static String URL = "src\\main\\resources\\static\\words_clean.txt";
    //I assume we use ASCII, therefore 256 possible characters
    private static int NUM_OF_CHARACTERS = 256;

    /**
     * check if str1,str2 are permutations of each other
     * complexity O(n)
     */
    private boolean arePermutations(String s1, String s2){
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        //in JAVA the arrays always initialized with zeros
        int[] histogram1 = new int[NUM_OF_CHARACTERS];
        int[] histogram2 = new int[NUM_OF_CHARACTERS];

        if(str1.length != str2.length){
            return false;
        }

        //creating histogram array to identify the chars of each string
        for(int i=0; i<str1.length; i++){
            histogram1[str1[i]]++;
            histogram2[str2[i]]++;
        }

        for(int i = 0; i< NUM_OF_CHARACTERS; i++){
            if(histogram1[i] != histogram2[i]){
                return false;
            }
        }
        //we don't want to return the same string
        return !s1.equals(s2);
    }

    public  ArrayList<String> similar(String string) throws FileNotFoundException {
        ArrayList<String> result = new ArrayList<>();
        Scanner sc = new Scanner(new File(URL));
        while(sc.hasNext()){
            String line = sc.nextLine();
            if(arePermutations(string,line)){
                result.add(line);
            }
        }
        return result;
    }
}
