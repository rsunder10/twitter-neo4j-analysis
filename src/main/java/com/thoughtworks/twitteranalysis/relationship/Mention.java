package com.thoughtworks.twitteranalysis.relationship;

import com.thoughtworks.twitteranalysis.nodeEntity.Tweet;
import com.thoughtworks.twitteranalysis.nodeEntity.User;
import lombok.*;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "MENTION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Mention {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Tweet tweet;

    @EndNode
    private User user;
}
