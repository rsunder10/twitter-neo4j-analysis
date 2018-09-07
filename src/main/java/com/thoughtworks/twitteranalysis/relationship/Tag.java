package com.thoughtworks.twitteranalysis.relationship;

import com.thoughtworks.twitteranalysis.nodeEntity.HashTag;
import com.thoughtworks.twitteranalysis.nodeEntity.Tweet;
import lombok.*;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "TAG")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Tweet tweet;

    @EndNode
    private HashTag tag;
}
