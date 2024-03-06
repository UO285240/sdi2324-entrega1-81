package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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




}
