package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.*;
import com.uniovi.sdi2324entrega181.services.SecurityService;
import com.uniovi.sdi2324entrega181.validators.SignUpFormValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.expression.AccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final SecurityService securityService;

    private final SignUpFormValidator signUpFormValidator;
    private final RolesService rolesService;
    private final FriendshipsService friendshipsService;
    private final PostsService postsService;


    public UsersController(UsersService usersService, SignUpFormValidator signUpFormValidator, SecurityService securityService,
                           RolesService rolesService, FriendshipsService friendshipsService, PostsService postsService) {
        this.usersService = usersService;
        this.securityService = securityService;
        this.signUpFormValidator = signUpFormValidator;
        this.rolesService = rolesService;
        this.friendshipsService = friendshipsService;
        this.postsService = postsService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(name = "logout", required = false) String logout, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        // En el caso de que el usuario ya esté logeado, ir a user/list
        if (email != "anonymousUser")
            return "redirect:user/list";

        if (logout != null) {
            model.addAttribute("mensaje", "s");
        }

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
    public String getList(Model model, Pageable pageable, Principal principal, @RequestParam(value="", required=false) String searchText){
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);

        // devuelve la lista de usuarios en función del rol del usuario autentificado
        Page<User> users = usersService.getUsersForUser(pageable, user);

        if (searchText != null && !searchText.isEmpty()){
            users = usersService.searchByEmailNameAndSurname(searchText, pageable);
        }

        model.addAttribute("usersList", users.getContent());
        model.addAttribute("page", users);
        if (searchText != null)
            model.addAttribute("searchText", searchText);
        else
            model.addAttribute("searchText","");

        // friendships
        model.addAttribute("friendRequests", friendshipsService.getFriendRequests(user));
        model.addAttribute("friends", friendshipsService.getFriends(user));

        return "user/list";
    }

    @RequestMapping("/user/administratorList")
    public String getAdministratorList(Model model, Pageable pageable, Principal principal, @RequestParam(value="", required=false) String searchText){
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);

        // devuelve la lista de usuarios en función del rol del usuario autentificado
        Page<User> users = usersService.getUsersForUser(pageable, user);

        if (searchText != null && !searchText.isEmpty()){
            users = usersService.searchByEmailNameAndSurname(searchText, pageable);
        }

        model.addAttribute("administratorList", users.getContent());
        model.addAttribute("page", users);
        if (searchText != null)
            model.addAttribute("searchText", searchText);
        else
            model.addAttribute("searchText","");


        return "user/administrateUsers";
    }

    @RequestMapping("/user/sendFriendshipList")
    public String getSendFriendshipList(Model model, Pageable pageable, Principal principal, @RequestParam(value="", required=false) String searchText){
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);

        // devuelve la lista de usuarios en función del rol del usuario autentificado
        Page<User> users = usersService.getUsersForUser(pageable, user);

        if (searchText != null && !searchText.isEmpty()){
            users = usersService.searchByEmailNameAndSurname(searchText, pageable);
        }

        model.addAttribute("sendFriendshipList", users.getContent());
        model.addAttribute("page", users);
        if (searchText != null)
            model.addAttribute("searchText", searchText);
        else
            model.addAttribute("searchText","");

        // friendships
        model.addAttribute("friendRequests", friendshipsService.getFriendRequests(user));
        model.addAttribute("friends", friendshipsService.getFriends(user));

        return "user/sendFriendshipList";
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

    @RequestMapping("/user/sendFriendshipList/update")
    public String updateSendFriendshipList(Model model, Pageable pageable, Principal principal) {
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);

        Page<User> users = usersService.getUsersForUser(pageable, user);

        model.addAttribute("sendFriendshipList", users.getContent());
        return "user/sendFriendshipList :: sendFriendshipTable";
    }


/*
    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }
    */


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



    @RequestMapping(value= "/user/borrarTodos",method= RequestMethod.POST)
    public String borrarTodo(@RequestParam("usuariosABorrar") List<Long> usuariosABorrar, Principal principal){
        if(usuariosABorrar!=null) {
            String correo = principal.getName();
            friendshipsService.borrarAmistades(usuariosABorrar,correo);
            usersService.borrarPorId(usuariosABorrar,correo);
        }
        return "redirect:/user/administratorList";
    }

    @RequestMapping(value= "/user/details/{id}")
    public String getDetails(@PathVariable Long id, Model model, Pageable pageable, Principal principal) throws AccessException {
        String email = principal.getName();
        User user1 = usersService.getUserByEmail(email);
        User user = usersService.getUser(id);
        if(friendshipsService.areFriends(user,user1)) {
            Page<Post> posts = postsService.getPostsByUser(pageable, user);
            model.addAttribute("user", user);
            model.addAttribute("postsList", posts.getContent());
            model.addAttribute("page", posts);
            return "user/details";
        }
        else{
            throw new AccessException("Authentication error") {};
        }
    }

}