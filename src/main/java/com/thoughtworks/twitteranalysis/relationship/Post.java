package com.thoughtworks.twitteranalysis.relationship;

import com.thoughtworks.twitteranalysis.nodeEntity.Tweet;
import com.thoughtworks.twitteranalysis.nodeEntity.User;
import lombok.*;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "POST")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private User user;

    @EndNode
    private Tweet tweet;
}
