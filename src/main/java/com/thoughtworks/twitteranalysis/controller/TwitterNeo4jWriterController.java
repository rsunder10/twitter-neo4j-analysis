package com.thoughtworks.twitteranalysis.controller;

import com.thoughtworks.twitteranalysis.service.TwitterNeo4jEnrichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class TwitterNeo4jWriterController {
    @Autowired
    TwitterNeo4jEnrichService twitterNeo4jEnrichService;

    @RequestMapping(value = "/enrich/tweet-neo", method = RequestMethod.GET, produces = "application/json")
    public void getTweet() throws URISyntaxException, InterruptedException {
        twitterNeo4jEnrichService.enrichTweetToNeo4j();
    }

}
