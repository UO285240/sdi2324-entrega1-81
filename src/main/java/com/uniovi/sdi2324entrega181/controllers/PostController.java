package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class PostController {
    private final PostsService postsService;
    private final UsersService usersService;

    public PostController(PostsService postsService, UsersService usersService) {
        this.postsService = postsService;
        this.usersService = usersService;
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.GET)
    public String addPost() {
        return "/post/add";
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.POST)
    public String createPost(@RequestParam String title, @RequestParam String text, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        LocalDate date = LocalDate.now();
        postsService.addPost(new Post(user, title, text, date));
        return "redirect:/user/list";
    }
}
