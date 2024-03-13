package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.Recommendation;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.repositories.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private RecommendationRepository recommendationRepository;

    public RecommendationService(RecommendationRepository recommendationRepository){
        this.recommendationRepository=recommendationRepository;
    }

    public List<Long> findRecommendationByUser(User user){
        return recommendationRepository.findRecommendationByUser(user);
    }

    public void addRecommendation(User user, Post post){
        Recommendation recommendation = new Recommendation(user,post);
        recommendationRepository.save(recommendation);
    }


}
