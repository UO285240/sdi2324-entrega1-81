package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.RolesService;
import com.uniovi.sdi2324entrega181.services.SecurityService;
import com.uniovi.sdi2324entrega181.services.SecurityService;
import com.uniovi.sdi2324entrega181.services.SecurityService;
import com.uniovi.sdi2324entrega181.services.UsersService;
import com.uniovi.sdi2324entrega181.validators.SignUpFormValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.Authentication;

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

    /**
     * Actualiza la tabla de usuarios del sistema
     */
    @RequestMapping("/user/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal) {
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);

        Page<User> users = usersService.getUsersForUser(pageable, user);

        model.addAttribute("usersList", users.getContent());
        return "user/list :: usersTable";
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