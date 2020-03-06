package com.similar.services;

import net.minidev.json.JSONObject;
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
    DataService dataService;

    public ArrayList<String> similar(String string){
        HashMap<String,ArrayList<String>> permsMap = dataService.getPermsMap();
        char[] charArray = string.toCharArray();
        Arrays.sort(charArray);
        String sorted = new String(charArray);
        ArrayList<String> perms = new ArrayList<>();
        if(permsMap.containsKey(sorted)){
            perms = (ArrayList<String>) permsMap.get(sorted).clone();
            perms.remove(string);
        }
        return perms;
    }

    public JSONObject getJsonSimilars(String word){
        ArrayList<String> similarWords = similar(word);
        JSONObject json = new JSONObject();
        json.put("similar", similarWords);
        return json;
    }

}
