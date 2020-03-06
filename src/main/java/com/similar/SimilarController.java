package com.similar;

import com.similar.services.PermutationsService;
import org.springframework.beans.factory.annotation.Autowired;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.similar.services.StatsService;

import java.io.FileNotFoundException;
import java.util.ArrayList;

@Controller
public class SimilarController {
    @Autowired
    private StatsService statsService;
    @Autowired
    private PermutationsService permutationsService;

    @GetMapping("/api/v1/stats")
    public String stats(Model model){
        JSONObject json = new JSONObject();
        json.put("totalWords",statsService.getTotalWords());
        json.put("totalRequests",statsService.getTotalRequests());
        json.put("avgProcessingTimeNs",statsService.getAvgProcTimeNs());
        model.addAttribute("stats",json);
        return "stats";
    }

    @GetMapping("/api/v1/similar")
    public String similar(@RequestParam(name="word", required=true) String word, Model model) throws FileNotFoundException {
        long start = System.nanoTime();
        ArrayList<String> similarWords = permutationsService.similar(word);
        JSONObject json = new JSONObject();
        json.put("similar", similarWords);
        System.out.println(json.toJSONString());
        model.addAttribute("similars", json);
        long end = System.nanoTime();
        statsService.newRequest(start,end);
        //return the template - the html file name
        return "similar";
    }

}
