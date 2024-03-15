package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.Recommendation;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.repositories.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Map<Post, Long> getNumberOfRecommendations(List<Post> content) {
        Map<Post,Long> numbers = new HashMap<Post,Long>();
        for(Post post: content){
            Long number = recommendationRepository.findNumberOfRecommendationsByPost(post);
            numbers.put(post,number);
        }
        return numbers;
    }
}
