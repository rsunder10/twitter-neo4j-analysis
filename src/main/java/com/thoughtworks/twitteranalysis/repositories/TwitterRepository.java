package com.thoughtworks.twitteranalysis.repositories;

import com.thoughtworks.twitteranalysis.nodeEntity.Tweet;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface TwitterRepository extends Neo4jRepository<Tweet, Long> {
    Optional<Tweet> findById(Long id);
}
