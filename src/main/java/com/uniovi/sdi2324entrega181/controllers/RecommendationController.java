package com.uniovi.sdi2324entrega181.controllers;


import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.PostsService;
import com.uniovi.sdi2324entrega181.services.RecommendationService;
import com.uniovi.sdi2324entrega181.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final UsersService usersService;

    private final PostsService postsService;

    public RecommendationController(RecommendationService recommendationService, UsersService usersService,
                                    PostsService postsService){
        this.recommendationService = recommendationService;
        this.usersService = usersService;
        this.postsService = postsService;
    }

    @RequestMapping(value = "/recommendation/add", method = RequestMethod.POST)
    public String createRecommendation(@RequestParam("post_id") Long postId, @RequestParam("user_id") Long userId,
    Principal principal){
        User user = usersService.getUserByEmail(principal.getName());
        Post post = postsService.getPost(postId);
        recommendationService.addRecommendation(user,post);
        return "redirect:/user/details/"+userId;
    }


}
