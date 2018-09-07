package com.thoughtworks.twitteranalysis.config;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class TwitterConfig {

    public static BasicClient configureStreamClient(BlockingQueue<String> msgQueue, String twitterKeys, List<Long> userIds, List<String> terms) {
        Hosts hosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint()
                .followings(userIds)
                .trackTerms(terms);
        endpoint.stallWarnings(false);

        String[] keys = twitterKeys.split(":");

        Authentication auth = new OAuth1(keys[0], keys[1], keys[2], keys[3]);

        ClientBuilder builder = new ClientBuilder()
                .name("Neo4j-Twitter-Stream")
                .hosts(hosts)
                .authentication(auth)
                .endpoint(endpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        return builder.build();
    }
}
