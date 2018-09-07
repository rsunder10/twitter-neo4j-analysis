package com.thoughtworks.twitteranalysis.nodeEntity;

import com.thoughtworks.twitteranalysis.relationship.Post;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDateTime;
import java.util.List;

@NodeEntity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    @Relationship(type = "POST")
    private List<Post> posts;
    private Long inReplyToStatusId;
    private Long inReplyToUserId;
    private String inReplyToScreenName;
    private Long quoteCount;
    private Long replyCount;
    private Long retweetCount;
    private Long favoriteCount;
    private Boolean favorited;
    private Boolean retweeted;
}
