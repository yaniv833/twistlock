package com.similar;

import com.similar.services.PermutationsService;
import org.springframework.beans.factory.annotation.Autowired;
import net.minidev.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.similar.services.DataService;

@Controller
public class SimilarController {
    @Autowired
    private DataService dataService;
    @Autowired
    private PermutationsService permutationsService;

    @GetMapping("/api/v1/stats")
    public ResponseEntity stats(){
        JSONObject stats = dataService.getJsonStats();
        System.out.println(stats);
        return ResponseEntity.ok(stats);
    }

    @GetMapping(value = "/api/v1/similar", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity similar(@RequestParam(name="word", required=true, defaultValue = "") String word){
        long start = System.nanoTime();
        JSONObject similars = permutationsService.getJsonSimilars(word);
        long end = System.nanoTime();
        dataService.newRequest(start,end);
        System.out.println(similars);
        return ResponseEntity.ok(similars);
    }

}
