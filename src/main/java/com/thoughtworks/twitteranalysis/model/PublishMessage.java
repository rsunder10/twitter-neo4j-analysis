package com.thoughtworks.twitteranalysis.model;

import com.thoughtworks.twitteranalysis.nodeEntity.Tweet;
import com.thoughtworks.twitteranalysis.nodeEntity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PublishMessage {
    private Long userId;
    private LocalDateTime userCreatedAt;



}
