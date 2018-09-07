package com.thoughtworks.twitteranalysis.writer;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.summary.ResultSummary;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

@Slf4j
public class TwitterNeo4jWriter {


    private Driver driver;

    public TwitterNeo4jWriter(String neo4jUrl, String username, String password) throws URISyntaxException {
        driver = GraphDatabase.driver(neo4jUrl, AuthTokens.basic(username, password));
    }

    private String getMergeStatement() throws IOException {
        String fileName = "statements/mergeTwitterData.cql";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }

    public void initialize() {
        try (Session session = driver.session()) {
            session.run("CREATE CONSTRAINT ON (t:Tweet) ASSERT t.id IS UNIQUE");
            session.run("CREATE CONSTRAINT ON (u:User) ASSERT u.screen_name IS UNIQUE");
            session.run("CREATE CONSTRAINT ON (t:Tag) ASSERT t.name IS UNIQUE");
            session.run("CREATE CONSTRAINT ON (l:Link) ASSERT l.url IS UNIQUE");
        }
    }

    public void close() {
        driver.close();
    }

    public int insert(List<String> tweets, int retries) {
        while (retries > 0) {
            try (Session session = driver.session()) {
                Gson gson = new Gson();
                List<Map> statuses = tweets.stream().map((s) -> gson.fromJson(s, Map.class)).collect(toList());
                long time = System.nanoTime();

                ResultSummary result = session.run(getMergeStatement(), Values.parameters("tweets", statuses)).consume();
                int created = result.counters().nodesCreated();

                log.info(created + " in " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time) + " ms");

                return created;
            } catch (Exception e) {
                log.info(e.getClass().getSimpleName() + ":" + e.getMessage() + " retries left " + retries);
                retries--;
            }
        }
        return -1;
    }
}
