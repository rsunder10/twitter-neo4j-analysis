package com.thoughtworks.twitteranalysis.nodeEntity;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@NodeEntity
public class HashTag {
    @Id
    @GeneratedValue
    private Long id;
    private String tags;

}
