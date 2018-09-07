package com.thoughtworks.twitteranalysis.controller;

import com.thoughtworks.twitteranalysis.nodeEntity.Tweet;
import com.thoughtworks.twitteranalysis.repositories.TwitterRepository;
import com.thoughtworks.twitteranalysis.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalysisController {
    @Autowired
    AnalysisService analysisService;
    @Autowired
    TwitterRepository twitterRepository;

    @RequestMapping(value = "/getTweet/{id}", method = RequestMethod.GET, produces = "application/json")
    public Tweet getTweet(@PathVariable("id") Long id) {
        return analysisService.findById(id);
    }


    @RequestMapping(value = "/save/{id}", method = RequestMethod.GET, produces = "application/json")
    public Tweet saveFeatures(@PathVariable("id") Long id) {
        return twitterRepository.save(Tweet.builder().text("manoj").build());
    }

}
