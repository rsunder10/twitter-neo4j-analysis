package com.thoughtworks.twitteranalysis.service;

import com.thoughtworks.twitteranalysis.nodeEntity.Tweet;
import com.thoughtworks.twitteranalysis.repositories.TwitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnalysisService {
    @Autowired
    private TwitterRepository twitterRepository;

    @Transactional(readOnly = true)
    public Tweet findById(Long id) {
        return twitterRepository.findById(id).orElse(null);
    }
}