package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostsRepository extends CrudRepository<Post, Long> {

    /**
     * Método que devuelve un Page con todos los posts de un usuario
     * @param pageable objeto necesario para la paginación
     * @param user usuario del que se van a buscar los posts
     * @return un Page con todos los posts del usuario
     */
    @Query("SELECT p FROM Post p WHERE (p.user = ?1 AND p.state != 'CENSURADA') ORDER BY p.date DESC")
    Page<Post> findByUser(Pageable pageable, User user);

    /**
     * Método que devuelve una lista ordenada de los posts de un usuario
     * @param user usuario del que se van a buscar los posts
     * @return una lista de posts
     */
    @Query("SELECT p FROM Post p WHERE (p.user = ?1 AND p.state != 'MODERADA' AND p.state != 'CENSURADA') ORDER BY p.date DESC")
    List<Post> findLastByUser(User user);

    /**
     * Método que devueve  un Page con todas las publicaciones cuyo titulo, texto, email o estado coinciden
     * con el texto que busca el usuario
     * @param searchText el texto insertado para buscar publicaciones
     * @param pageable objeto necesario para la paginación
     * @return un Page con todas las publicaciones que coniciden con el texto insertado
     */
    @Query("SELECT p FROM Post p WHERE (LOWER(p.title) LIKE LOWER(?1) OR LOWER(p.text) LIKE LOWER(?1) OR LOWER(p.user.email) LIKE LOWER(?1) OR LOWER(p.state) LIKE LOWER(?1))")
    Page<Post> searchByPostFields(String searchText, Pageable pageable);

    /**
     * Método que devuelve un Page con todas las publicaciones del sistema
     * @param pageable objeto necesario para la paginación
     * @return un Page con todas las publicaciones del sistema
     */
    Page<Post> findAll(Pageable pageable);
}
