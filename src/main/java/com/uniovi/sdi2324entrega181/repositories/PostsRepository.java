package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends CrudRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.user = ?1 ORDER BY p.date DESC")
    Page<Post> findByUser(Pageable pageable, User user);

    @Query("SELECT p FROM Post p WHERE p.user = ?1 ORDER BY p.date DESC")
    List<Post> findLastByUser(User user);

}
