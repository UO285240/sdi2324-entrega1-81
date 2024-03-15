package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.Recommendation;
import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {

    /**
     * Método para devolver una lista con los ids de los posts de las recomendaciones de un usuario
     * @param user usuario del que se van a buscar las recomendaciones
     * @return una lista de ids de los posts de las recomendaciones
     */
    @Query("SELECT r.post.id FROM Recommendation r WHERE r.user = ?1 ")
    List<Long> findRecommendationByUser(User user);

    /**
     * Método que devuelve el número de recomendaciones en el que aparece un post determinado
     * @param post post del que se quiere buscar el número de recomendaciones
     * @return el número de recomendaciones en el que aparece el post
     */
    @Query("SELECT COALESCE(COUNT(*),0) AS recommendations FROM Recommendation  r WHERE r.post=?1")
    Long findNumberOfRecommendationsByPost(Post post);

}
