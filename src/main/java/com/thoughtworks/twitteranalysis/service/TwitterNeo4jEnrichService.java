package com.thoughtworks.twitteranalysis.service;

import com.thoughtworks.twitteranalysis.writer.TwitterNeo4jWriter;
import com.twitter.hbc.httpclient.BasicClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.thoughtworks.twitteranalysis.config.TwitterConfig.configureStreamClient;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Service
@Slf4j
public class TwitterNeo4jEnrichService {
    @Value("${twitter.batch}")
    private Integer BATCH;
    @Value("${twitter.max.reads}")
    private Integer maxReads;
    @Value("${twitter.keys}")
    private String twitterKeys;
    @Value("${twitter.terms}")
    private String twitterTerms;
    @Value("${twitter.location}")
    private String twitterLocations;
    @Value("${twitter.poll}")
    private Integer poll;

    @Value("${spring.data.neo4j.uri}")
    private String neo4jUri;
    @Value("${spring.data.neo4j.username}")
    private String username;
    @Value("${spring.data.neo4j.password}")
    private String password;


    public void enrichTweetToNeo4j() throws URISyntaxException, InterruptedException {
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(10000);
        List<Long> userIds = emptyList();
        List<String> terms = asList(twitterTerms.split(","));
        BasicClient client = configureStreamClient(msgQueue, twitterKeys, userIds, terms);
        TwitterNeo4jWriter writer = new TwitterNeo4jWriter(neo4jUri, username, password);
        writer.initialize();
        ExecutorService service = getExecutor();

        client.connect();

        List<String> buffer = new ArrayList<>(BATCH);
        for (int msgRead = 0; msgRead < maxReads; msgRead++) {
            if (isConnectionAvailable(client)) break;
            String msg = msgQueue.poll(poll, TimeUnit.SECONDS);
            if (msg == null) log.info("No message Received");
            else buffer.add(msg);
            buffer = pushToNeo(writer, service, buffer);
        }

        client.stop();
        writer.close();
    }

    private ExecutorService getExecutor() {
        int numProcessingThreads = Math.max(1, Runtime.getRuntime().availableProcessors() - 1);
        return Executors.newFixedThreadPool(numProcessingThreads);
    }

    private List<String> pushToNeo(TwitterNeo4jWriter writer, ExecutorService service, List<String> buffer) {
        if (buffer.size() < BATCH) return buffer;

        List<String> tweets = buffer;
        service.submit(() -> writer.insert(tweets, 5));
        buffer = new ArrayList<>(BATCH);
        return buffer;
    }

    private boolean isConnectionAvailable(BasicClient client) {
        if (client.isDone()) {
            log.info("Client connection closed : " + client.getExitEvent().getMessage());
            return true;
        }
        return false;
    }

}
