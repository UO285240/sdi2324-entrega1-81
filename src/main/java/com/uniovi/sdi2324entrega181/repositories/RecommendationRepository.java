package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.Recommendation;
import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {

    @Query("SELECT r.post.id FROM Recommendation r WHERE r.user = ?1 ")
    List<Long> findRecommendationByUser(User user);

    @Query("SELECT COALESCE(COUNT(*),0) AS recommendations FROM Recommendation  r WHERE r.post=?1")
    Long findNumberOfRecommendationsByPost(Post post);

}
