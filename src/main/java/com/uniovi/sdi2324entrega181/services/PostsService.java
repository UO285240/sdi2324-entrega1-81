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

    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }


    public void addPost(Post post) {
        postsRepository.save(post);
    }


    public Page<Post> getPostsByUser(Pageable pageable, User user) {
        Page<Post> posts = postsRepository.findByUser(pageable, user);
        return posts;
    }

    /**
     * Devuelve una lista de los posts de un usuario
     * @param user el usuario del que se van a sacar los posts
     * @return una lista con los posts de usuario
     */
    public List<Post> getLastPostByUser(User user) {
        return postsRepository.findLastByUser(user);
    }

    /**
     * Devuelve un post dado su id
     * @param id id del post
     * @return el post que se corresponde con el id
     */
    public Post getPost(Long id) {
        return postsRepository.findById(id).get();
    }


    public void updatePost(Post post) {
        postsRepository.save(post);
    }

    /**
     * Método para buscar publicaciones cuyo email, título, texto o estado de la publicación coinciden con el texto
     * insertado
     * @param searchText el texto a buscar
     * @param pageable objeto necesario para la paginación
     * @return las publicaciones  cuyo email, título, texto o estado de la publicación coinciden con el texto
     */
    public Page<Post> searchByPostFields(String searchText, Pageable pageable) {
        String searchTest = "%" + searchText + "%";
        Page<Post> posts = postsRepository.searchByPostFields(searchTest, pageable);
        return posts;
    }


        public Page<Post> getAllPosts (Pageable pageable){
            return postsRepository.findAll(pageable);
        }
    }

