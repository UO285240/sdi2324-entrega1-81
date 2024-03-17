package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.*;
import com.uniovi.sdi2324entrega181.services.SecurityService;
import com.uniovi.sdi2324entrega181.validators.EditUserValidator;
import com.uniovi.sdi2324entrega181.validators.SignUpFormValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private LogService logService;

    private final RecommendationService recommendationService;
    private final EditUserValidator editUserValidator;




    public UsersController(UsersService usersService, SignUpFormValidator signUpFormValidator, SecurityService securityService,
                           RolesService rolesService, FriendshipsService friendshipsService, PostsService postsService,
                           RecommendationService recommendationService, EditUserValidator editUserValidator, LogService logService) {
        this.usersService = usersService;
        this.securityService = securityService;
        this.signUpFormValidator = signUpFormValidator;
        this.rolesService = rolesService;
        this.friendshipsService = friendshipsService;
        this.postsService = postsService;
        this.recommendationService = recommendationService;
        this.editUserValidator = editUserValidator;
        this.logService = logService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(name = "logout", required = false) String logout, Model model, String error) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logService.logPET("Mapping: /login Method: GET ");
        String email = auth.getName();

        if (error != null) {
            model.addAttribute("error", "Error en el usuario o la contraseña.");
        }

        if (logout != null) {
            model.addAttribute("mensaje", "s");
        }

        return "login";
    }


    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model, Pageable pageable) {
        logService.logPET("Mapping: /home Method: GET ");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        return "index";
    }

    /**
     * Método get para cargar el formulario para registrarse
     * @param model modelo para añadir datos a la vista
     * @return dirige a la vista del formulario para registrarse
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        logService.logPET("Mapping: /signup Method: GET ");
        model.addAttribute("user", new User());
        return "signup"; }

    /**
     * Método post para procesar los datos recibidos en el formulario
     * @param user usuario a registrar
     * @param result el resultado con los datos del formulario
     * @return devuelve a rellenar el formulario si hay errores y sino redirije a home
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        logService.logPET("Mapping: /signup Method: POST Params: " + user.toString());
        signUpFormValidator.validate(user,result);
        if(result.hasErrors()){
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        logService.logALTA("New User: " + user);
        return "redirect:index";
    }

    /**
     * Método que devuelve la vista con la lista de usuarios del administrador para poder manejar usuarios
     * @param model modelo para añadir datos a la vista
     * @param pageable objeto para llevar a cabo la paginación
     * @param principal usuario registrado en la aplicación
     * @param searchText texto opcional para el buscador
     * @return la vista con la lista de usuarios
     */

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
        model.addAttribute("email",email);
        if (searchText != null)
            model.addAttribute("searchText", searchText);
        else
            model.addAttribute("searchText","");

        logService.logPET("Mapping: /user/administradorList Method: GET");
        return "user/administrateUsers";
    }

    /**
     * Método que devuelve la vista con los usuarios para mandar solicitudes de amistad
     * @param model modelo para añadir datos a la vista
     * @param pageable objeto para llevar a cabo la paginación
     * @param principal usuario registrado en la aplicación
     * @param searchText texto opcional para el buscador
     * @return devuelve la vista con los usuarios para mandar solicitudes de amistad
     */
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
        model.addAttribute("email", principal.getName());
        if (searchText != null)
            model.addAttribute("searchText", searchText);
        else
            model.addAttribute("searchText","");

        // friendships
        model.addAttribute("friendRequests", friendshipsService.getFriendRequests(user));
        model.addAttribute("friends", friendshipsService.getFriends(user));
        logService.logPET("Mapping: /user/sendFriendList Method: GET");
        return "user/sendFriendshipList";
    }


    /**
     * Actualiza la tabla de usuarios del sistema
     */
    @RequestMapping("/user/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal) {
        logService.logPET("Mapping: /user/list/update Method: GET");
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);

        Page<User> users = usersService.getUsersForUser(pageable, user);

        model.addAttribute("usersList", users.getContent());
        return "/user/sendFriendshipList :: usersTable";
    }

    /**
     * Método para actualizar la tabla de la vista con la lista de ususarios para mandar solicitud de amistad
     * @param model modelo para añadir datos a la vista
     * @param pageable objeto para llevar a cabo la paginación
     * @param principal usuario registrado en la aplicación
     * @return devuelve la tabla de la vista con la lista de ususarios para mandar solicitud de amistad
     */
    @RequestMapping("/user/sendFriendshipList/update")
    public String updateSendFriendshipList(Model model, Pageable pageable, Principal principal) {
        logService.logPET("Mapping: /user/sendFriendShipList/update Method: GET");
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);

        Page<User> users = usersService.getUsersForUser(pageable, user);

        model.addAttribute("sendFriendshipList", users.getContent());
        return "user/sendFriendshipList :: sendFriendshipTable";
    }


    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        logService.logPET("Mapping: /user/edit/"+id+" Method: GET");
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("availableRoles",rolesService.getRoles());
        return "user/edit";
    }
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @ModelAttribute User user,BindingResult result) {
        User originalUser = usersService.getUser(id);
        // modificar solo Email, nombre , apellidos y role

        // TODO: RODRIGO REVISAR VALIDAR DATOS
        editUserValidator.validate(user,result);
        if(result.hasErrors()){
            return "user/edit";
        }


        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());
        originalUser.setLastName(user.getLastName());
        originalUser.setRole(user.getRole());
        usersService.saveUser(originalUser);
        return "redirect:/user/details/" + id;
    }


    /**
     * Método que recibe el formulario con los usuarios marcados para borrar en la vista de administración de usuarios
     * del administrador
     * @param usuariosABorrar lista con los ids de los ususarios a borrar
     * @return redirije a la vista de administración de usuarios
     */
    @RequestMapping(value= "/user/deleteAll",method= RequestMethod.POST)
    public String borrarTodo(@RequestParam("usuariosABorrar") List<Long> usuariosABorrar){
        if(usuariosABorrar!=null) {
            friendshipsService.borrarAmistades(usuariosABorrar);
            usersService.borrarPorId(usuariosABorrar);
        }
        return "redirect:/user/administratorList";
    }



    @RequestMapping(value= "/user/details/{id}")
    public String getDetails(@PathVariable Long id, Model model, Pageable pageable, Principal principal) throws AccessException {
        logService.logPET("Mapping: /user/details/"+id+" Method: GET");
        String email = principal.getName();
        User user1 = usersService.getUserByEmail(email);
        User user = usersService.getUser(id);
        if(friendshipsService.areFriends(user,user1) || user1.getRole().equals("ROLE_ADMIN")) {
            Page<Post> posts = postsService.getPostsByUser(pageable, user);


            // Filtrar las publicaciones que no están ni censuradas ni moderadas
            List<Post> filteredPosts = new ArrayList<>();
            posts.forEach(post -> {
                if (!post.getState().equals("CENSURADA") && !post.getState().equals("MODERADA")) {
                    filteredPosts.add(post);
                }
            });

            // Crear una nueva página con los posts filtrados
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;
            List<Post> sublist;

            if (filteredPosts.size() < startItem) {
                sublist = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, filteredPosts.size());
                sublist = filteredPosts.subList(startItem, toIndex);
            }

            Page<Post> filteredPage = new PageImpl<>(sublist, PageRequest.of(currentPage, pageSize), filteredPosts.size());



            List<Long> recommendedPosts = recommendationService.findRecommendationByUser(user1);
            Map<Post,Long> recommendationsNumber = recommendationService.getNumberOfRecommendations(posts.getContent());
            model.addAttribute("user", user);
            model.addAttribute("postsList", posts.getContent());
            model.addAttribute("page", posts);
            model.addAttribute("recommendedPosts",recommendedPosts);
            model.addAttribute("recommendationsNumber",recommendationsNumber);
            return "user/details";
        }
        else{
            return "/home";
        }
    }

}