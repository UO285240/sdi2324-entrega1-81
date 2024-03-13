package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public Page<Post> getPostsByUser(Pageable pageable, User user){
        Page<Post> posts = postsRepository.findByUser(pageable, user);
        return posts;
    }

    public List<Post> getLastPostByUser(User user){
        return postsRepository.findLastByUser(user);
    }

    public Post getPost(Long id) {
        Post post = postsRepository.findById(id).isPresent() ? postsRepository.findById(id).get() : new Post();
        return post;
    }

    public void updatePost(Post post) {
        postsRepository.save(post);
    }


    /**
     * Buscar por email, título, texto o estado de la publicación
     */
    public Page<Post> searchByPostFields(String searchText, Pageable pageable) {
        String searchTest = "%"+searchText+"%";
        Page<Post> posts = postsRepository.searchByPostFields(searchTest, pageable);

        return posts;
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postsRepository.findAll(pageable);
    }
}
