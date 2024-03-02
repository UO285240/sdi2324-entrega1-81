package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.FriendshipsService;
import com.uniovi.sdi2324entrega181.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class FriendshipsController {

    private UsersService usersService;
    private FriendshipsService friendshipsService;

    public FriendshipsController(UsersService usersService, FriendshipsService friendshipsService){
        this.usersService = usersService;
        this.friendshipsService = friendshipsService;
    }


    @RequestMapping("/friendship/send/{id}")
    public String sendFriendship(@PathVariable Long id, Principal principal) {

        User sender = usersService.getUserByEmail(principal.getName());
        User receiver = usersService.getUser(id);

        Friendship friendship = new Friendship(sender, receiver, false);
        friendshipsService.saveFrienship(friendship);
        return "redirect:/user/list";
    }



}
