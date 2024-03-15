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

    /**
     * Devuelve una lista con los ids de los posts recomendados por un usuario
     * @param user usuario del que se van a buscar los posts
     * @return lista con los ids de los posts
     */
    public List<Long> findRecommendationByUser(User user){
        return recommendationRepository.findRecommendationByUser(user);
    }

    /**
     * Crea una nueva recomendación
     * @param user el usuario de la recomendación
     * @param post el post de la recomendación
     */
    public void addRecommendation(User user, Post post){
        Recommendation recommendation = new Recommendation(user,post);
        recommendationRepository.save(recommendation);
    }

    /**
     * Devuelve un map formado por posts y las veces que han sido recomendados dada una lista de posts
     * @param content lista de posts para sacar el número de recomendaciones
     * @return un map formado por posts y el número de veces que fueron recomendados
     */
    public Map<Post, Long> getNumberOfRecommendations(List<Post> content) {
        Map<Post,Long> numbers = new HashMap<Post,Long>();
        for(Post post: content){
            Long number = recommendationRepository.findNumberOfRecommendationsByPost(post);
            numbers.put(post,number);
        }
        return numbers;
    }
}
