package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.SecurityService;
import com.uniovi.sdi2324entrega181.services.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.Authentication;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final SecurityService securityService;

    public UsersController(UsersService usersService, SecurityService securityService) {
        this.usersService = usersService;
        this.securityService = securityService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        return "home";
    }



    @RequestMapping("/user/list")
    public String getList(Model model, Pageable pageable) {
        Page<User> users = usersService.getUsers(pageable);
        model.addAttribute("usersList", users.getContent());
        model.addAttribute("page", users);
        return "user/list";
    }

    @RequestMapping(value = "/user/add")
    public String getUser() {
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";

    }

    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @RequestMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        usersService.deleteUser(id);
        return "redirect:/user/list";

    }

    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @ModelAttribute User user) {
        //usersService.addUser(user);
        User originalUser = usersService.getUser(id);
        // modificar solo Email, nombre y apellidos
        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());
        originalUser.setLastName(user.getLastName());
        usersService.saveUser(originalUser);
        return "redirect:/user/details/" + id;
    }

}
