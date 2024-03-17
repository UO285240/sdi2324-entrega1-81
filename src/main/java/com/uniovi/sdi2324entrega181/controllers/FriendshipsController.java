package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.FriendshipsService;
import com.uniovi.sdi2324entrega181.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
@Controller
public class FriendshipsController {

    private final UsersService usersService;
    private final FriendshipsService friendshipsService;

    public FriendshipsController(UsersService usersService, FriendshipsService friendshipsService){
        this.usersService = usersService;
        this.friendshipsService = friendshipsService;
    }


    /**
     * Método que envía una solicitud de amistad a otro usuario en la red social
     * @param id la id del usuario que recibe la solicitud
     * @param principal para obtener el usuario autenticado
     * @param redirectAttrs para poder enviar un mensaje a la vista
     * @return devuelve la lista con el listado de usuarios y la solicitud mandada
     */
    @RequestMapping("/friendship/send/{id}")
    public String sendFriendship(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttrs) {

        User sender = usersService.getUserByEmail(principal.getName());
        User receiver = usersService.getUser(id);

        // comprobar que no existe en la base de datos la amistad
        if(!friendshipsService.existsFriendship(sender, receiver)){
            Friendship friendship = new Friendship(sender, receiver, false, LocalDate.now());
            friendshipsService.saveFrienship(friendship);
        }
        else{
            // feeback al usuario
            redirectAttrs
                    .addFlashAttribute("mensaje", "Ya existe una petición de amistad a este usuario")
                    .addFlashAttribute("clase", "warning");
        }


        return "redirect:/user/list";
    }

    /**
     * Método que devuelve la vista con los amigos de usuario
     * @param model modelo para añadir datos a la vista
     * @param pageable objeto necesario para la paginación
     * @param principal el ususario registrado en la aplicación
     * @return devuelve la vista con el listado de amigos
     */
    @RequestMapping("/friendship/list")
    public String getList(Model model, Pageable pageable, Principal principal){
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Friendship> friendships = friendshipsService.getFriendsUser(pageable,user);
        model.addAttribute("user",user);
        model.addAttribute("friendshipList", friendships.getContent());
        model.addAttribute("page",friendships);

        return "friendship/list";
    }



}
