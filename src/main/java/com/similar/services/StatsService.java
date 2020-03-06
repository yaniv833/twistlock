package com.similar.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Service
public class StatsService {
    private static String URL = "src\\main\\resources\\static\\words_clean.txt";
    HashMap<String,ArrayList<String>> permsMap;
    private int totalWords;
    private int totalRequests;
    private long avgProcTimeNs;

    @PostConstruct
    public void initStats() throws IOException {
        totalWords = initData();
        totalRequests = 0;
        avgProcTimeNs = 0;
    }

    /**
     * Arranging the data as map, such as the key is sorted word, and values are all the permutations of that word.
     * @throws FileNotFoundException
     */
    private int initData() throws FileNotFoundException {
        permsMap = new HashMap<>();
        int count = 0;
        Scanner sc = new Scanner(new File(URL));
        while(sc.hasNext()){
            String orig = sc.nextLine();
            char[] charArray = orig.toCharArray();
            Arrays.sort(charArray);
            String sorted = new String(charArray);
            if(permsMap.containsKey(sorted)){
                permsMap.get(sorted).add(orig);
            }
            else {
                ArrayList<String> perms = new ArrayList<>();
                perms.add(orig);
                permsMap.put(sorted,perms);
            }
            count++;
        }
        return count;
    }

    public void newRequest(long start,long end){
        avgProcTimeNs = (avgProcTimeNs*totalRequests + (end-start)) / (totalRequests + 1);
        totalRequests++;
    }

    @Bean
    public int getTotalWords() {
        return totalWords;
    }
    @Bean
    public int getTotalRequests() {
        return totalRequests;
    }
    @Bean
    public long getAvgProcTimeNs() {
        return avgProcTimeNs;
    }
    @Bean
    public HashMap<String, ArrayList<String>> getPermsMap() {
        return permsMap;
    }
}
