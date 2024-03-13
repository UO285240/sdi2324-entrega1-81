package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    private final PostsService postsService;
    private final UsersService usersService;



    public PostController(PostsService postsService, UsersService usersService) {
        this.postsService = postsService;
        this.usersService = usersService;
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.GET)
    public String addPost(Model model) {
        return "/post/add";
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.POST)
    public String createPost(@RequestParam String title, @RequestParam String text, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        LocalDate date = LocalDate.now();
        postsService.addPost(new Post(user, title, text, date));
        return "redirect:/post/list";
    }


    @RequestMapping("/post/list")
    public String getList(Model model, Pageable pageable, Principal principal){
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);


        Page<Post> posts = postsService.getPostsByUser(pageable, user);

        model.addAttribute("postsList", posts.getContent());
        model.addAttribute("page", posts);


        return "post/list";
    }



    @RequestMapping("/post/adminList")
    public String getAdminList(Model model, Pageable pageable, Principal principal, @RequestParam(value="", required=false) String searchText){
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);

        Page<Post> posts = postsService.getAllPosts(pageable);
        if (searchText != null && !searchText.isEmpty()){
            posts = postsService.searchByPostFields(searchText, pageable);
        }

        model.addAttribute("postsList", posts.getContent());
        model.addAttribute("page", posts);
        if (searchText != null)
            model.addAttribute("searchText", searchText);
        else
            model.addAttribute("searchText","");

        // valores de estado de una publicación
        List<Post.PostState> postStates = Arrays.asList(Post.PostState.values());
        model.addAttribute("postStates", postStates);


        return "post/adminList";
    }



    @RequestMapping("/post/details/{id}")
    public String getDetails(Model model, @PathVariable Long id){
        User user = usersService.getUser(id);
        List<Post> posts = postsService.getLastPostByUser(user);
        model.addAttribute("post", posts.get(0));
        return "/post/details";
    }


    @RequestMapping(value = "/post/updateState/{id}", method = RequestMethod.POST)
    public String updatePostState(@PathVariable Long id, @RequestParam("state") Post.PostState newState) {
        Post post = postsService.getPost(id);
        post.setState(newState);
        postsService.updatePost(post);
        return "redirect:/post/adminList";
    }


}
