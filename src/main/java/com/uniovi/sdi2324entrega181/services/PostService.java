package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.repositories.FriendshipsRepository;
import com.uniovi.sdi2324entrega181.repositories.PostsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostsRepository postsRepository;
    private List<Post> posts; // lista los posts de un usuario

    public PostService(PostsRepository postsRepository){
        this.postsRepository = postsRepository;
    }


    public void savePost(Post post){
        postsRepository.save(post);
    }




}
