package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostsRepository extends CrudRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE (p.user = ?1 AND p.state != 'CENSURADA') ORDER BY p.date DESC")
    Page<Post> findByUser(Pageable pageable, User user);

    @Query("SELECT p FROM Post p WHERE (p.user = ?1 AND p.state != 'MODERADA' AND p.state != 'CENSURADA') ORDER BY p.date DESC")
    List<Post> findLastByUser(User user);

    @Query("SELECT p FROM Post p WHERE (LOWER(p.title) LIKE LOWER(?1) OR LOWER(p.text) LIKE LOWER(?1) OR LOWER(p.user.email) LIKE LOWER(?1) OR LOWER(p.state) LIKE LOWER(?1))")
    Page<Post> searchByPostFields(String searchText, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
}
