package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.FriendshipsService;
import com.uniovi.sdi2324entrega181.services.LogService;
import com.uniovi.sdi2324entrega181.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
@Controller
public class FriendshipsController {

    private final UsersService usersService;
    private final FriendshipsService friendshipsService;

    LogService logService;

    public FriendshipsController(UsersService usersService, FriendshipsService friendshipsService){
        this.usersService = usersService;
        this.friendshipsService = friendshipsService;
    }


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


        return "redirect:/user/sendFriendshipList";
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
        logService.logPET("Mapping: /friendship/list Method: GET Page: " + pageable.getPageNumber() );
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Friendship> friendships = friendshipsService.getFriendsUser(pageable,user);
        model.addAttribute("user",user);
        model.addAttribute("friendshipList", friendships.getContent());
        model.addAttribute("page",friendships);

        return "friendship/list";
    }

    /**
     * Devuelve la lista con las solicitudes de amistad pendientes
     * @param model
     * @param pageable
     * @param principal user en sesión
     * @return String url
     */
    @RequestMapping(value="/friendship/requestlist")
    public String getReceivedPetitions(Model model, Pageable pageable, Principal principal){
        logService.logPET("Mapping: /friendship/requestlist Method: GET Page: " + pageable.getPageNumber() );
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Friendship> friendships = friendshipsService.getMyReceivedPetitions(pageable, email);

        model.addAttribute("user", user);
        model.addAttribute("friendships", friendships.getContent());
        model.addAttribute("page", friendships);

        return "friendship/requestlist";
    }

    /**
     * Accept a friend request
     * @param principal user in session
     * @param id identifier of the user who accepts the petition
     * @return String view url
     */
    @RequestMapping(value="/friendship/accept/{id}")
    public String acceptPetition(Principal principal, @PathVariable Long id) {
        User receiver = usersService.getUser(id);
        List<Friendship> friendships = friendshipsService.getPetitionBy2Users(principal.getName(), receiver.getEmail());

        for (Friendship f : friendships){
            friendshipsService.deletePetition(f);
            f.setIsAccepted(true);
            friendshipsService.add(f);
        }

        return "redirect:/friendship/requestlist";
    }



}
