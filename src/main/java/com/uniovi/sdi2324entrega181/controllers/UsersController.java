package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.RolesService;
import com.uniovi.sdi2324entrega181.services.SecurityService;
import com.uniovi.sdi2324entrega181.services.UsersService;
import com.uniovi.sdi2324entrega181.validators.SignUpFormValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Controller
public class UsersController {

    private final UsersService usersService;

    private final SecurityService securityService;
    private final SignUpFormValidator signUpFormValidator;

    private final RolesService rolesService;

    public UsersController(UsersService usersService,SignUpFormValidator signUpFormValidator, SecurityService securityService,
                           RolesService rolesService) {
        this.usersService = usersService;
        this.securityService = securityService;
        this.signUpFormValidator = signUpFormValidator;
        this.rolesService = rolesService;
    }


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup"; }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user,result);
        if(result.hasErrors()){
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home() {
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
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
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

    @RequestMapping(value = "/user/{id}/borrado", method = RequestMethod.GET)
    public String setBorradoTrue(@PathVariable Long id) {
        usersService.setUserBorrado(true, id);
        return "redirect:/user/list";
    }
    @RequestMapping(value = "/user/{id}/noBorrado", method = RequestMethod.GET)
    public String setBorradoFalse(@PathVariable Long id) {
        usersService.setUserBorrado(false, id);
        return "redirect:/user/list";
    }

    @RequestMapping(value= "/user/borrarSeleccionados")
    public String borrarTodo(){
        usersService.borrarTodo();
        return "redirect:/user/list";
    }

}