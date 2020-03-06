package com.similar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by syanivs on 03/03/2020.
 */
@Service
public class PermutationsService {

    @Autowired
    StatsService statsService;

    public ArrayList<String> similar(String string){
        HashMap<String,ArrayList<String>> permsMap = statsService.getPermsMap();
        char[] charArray = string.toCharArray();
        Arrays.sort(charArray);
        String sorted = new String(charArray);
        ArrayList<String> perms = (ArrayList<String>) permsMap.get(sorted).clone();
        perms.remove(string);
        return perms;
    }

}
