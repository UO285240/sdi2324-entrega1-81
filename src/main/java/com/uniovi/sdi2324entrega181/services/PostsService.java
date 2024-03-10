package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostsService {

    @Autowired
    private PostsRepository postsRepository;
    private List<Post> posts; // lista los posts de un usuario

    public PostsService(PostsRepository postsRepository){
        this.postsRepository = postsRepository;
    }


    public void addPost(Post post){
        postsRepository.save(post);
    }


    public Page<Post> getPostsByUser(Pageable pageable, User user){
        Page<Post> posts = postsRepository.findByUser(pageable, user);
        return posts;
    }

    public List<Post> getLastPostByUser(User user){
        return postsRepository.findLastByUser(user);
    }








}
