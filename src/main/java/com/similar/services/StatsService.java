package com.similar.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;

@Service
public class StatsService {
    private static String URL = "src\\main\\resources\\static\\words_clean.txt";
    private int totalWords;
    private int totalRequests;
    private long avgProcTimeNs;

    @PostConstruct
    public void initStats() throws IOException {
        totalWords = calcTotalWords();
        totalRequests = 0;
        avgProcTimeNs = 0;
    }
    public void newRequest(long start,long end){
        avgProcTimeNs = (avgProcTimeNs*totalRequests + (end-start)) / (totalRequests + 1);
        totalRequests++;
    }

    private int calcTotalWords() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(URL));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
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
}
