package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.SecurityService;
import com.uniovi.sdi2324entrega181.services.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final SecurityService securityService;

    public UsersController(UsersService usersService, SecurityService securityService) {
        this.usersService = usersService;
        this.securityService = securityService;
    }



    @RequestMapping("/user/list")
    public String getList(Model model, Pageable pageable, @RequestParam(value="", required=false) String searchText) {
        Page<User> users = usersService.getUsers(pageable);

        if (searchText != null && !searchText.isEmpty()){
            users = usersService.searchByEmailNameAndSurname(searchText, pageable);
        }

        model.addAttribute("usersList", users.getContent());
        model.addAttribute("page", users);
        model.addAttribute("searchText", searchText);

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
        model.addAttribute("user", usersService.getUser(id));
        return "user/edit";
    }

    @RequestMapping(value="/user/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute User user, @PathVariable Long id){
        user.setId(id);
        usersService.addUser(user);
        return "redirect:/user/details/"+id;
    }

}
