package com.thoughtworks.twitteranalysis.relationship;

import com.thoughtworks.twitteranalysis.nodeEntity.Tweet;
import lombok.*;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "RETWEET")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Retweet {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Tweet tweet;

    @EndNode
    private Tweet retweet;
}
