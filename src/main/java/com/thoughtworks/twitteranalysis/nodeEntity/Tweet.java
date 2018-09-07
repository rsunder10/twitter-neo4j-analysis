package com.thoughtworks.twitteranalysis.nodeEntity;

import com.thoughtworks.twitteranalysis.relationship.Mention;
import com.thoughtworks.twitteranalysis.relationship.Retweet;
import com.thoughtworks.twitteranalysis.relationship.Tag;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tweet {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    @Relationship(type = "MENTION")
    private List<Mention> mentions;
    @Relationship(type = "TAG")
    private List<Tag> tags;
    @Relationship(type = "RETWEET")
    private List<Retweet> retweets;
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
