package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.repositories.FriendshipsRepository;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipsService {

    private FriendshipsRepository friendshipsRepository;
    private UsersService usersService;
    private List<Friendship> friends; // lista con los ususarios que son amigos


    public FriendshipsService(FriendshipsRepository friendshipsRepository, UsersService usersService){
        this.friendshipsRepository = friendshipsRepository;
        this.usersService = usersService;
    }


    public void saveFrienship(Friendship friendship){
        friendshipsRepository.save(friendship);
    }



    /**
     * Devuelve una lista con los emails de todas las solicitdes de amistad que tiene el usuario
     * (solicitudes que ha mandado él y solicitudes que le han mandado otros usuarios)
     */
    public List<String> getFriendRequests(User user) {
        List<String> requests = new ArrayList<>();
        requests.addAll(friendshipsRepository.getSentRequests(user.getEmail())); // sent requests
        requests.addAll(friendshipsRepository.getReceivedRequests(user.getEmail())); // received requests

        return requests;
    }

    /**
     * Devuelve una lista con los emails de todos los amigos del usuario
     */
    public List<String> getFriends(User user) {
        List<Friendship> friendships = friendshipsRepository.getFriends(user.getEmail());

        // si el usuario es el que envia la solicitud, cojemos al que la recive, y viceversa
        List<String> friendEmails = new ArrayList<>();
        for (Friendship f : friendships)
        {
            if(f.getSender().getEmail().equals(user.getEmail()))
                friendEmails.add(f.getReceiver().getEmail());

            if(f.getReceiver().getEmail().equals(user.getEmail()))
                friendEmails.add(f.getSender().getEmail());
        }

        return friendEmails;
    }

    public Page<Friendship> getFriendsUser(Pageable pageable,User user){
        return friendshipsRepository.getFriendsPageable(pageable,user.getEmail());
    }

    public boolean existsFriendship(User sender, User receiver) {
         Optional<Friendship> friendship = friendshipsRepository.findBySenderAndReceiver(sender.getEmail(), receiver.getEmail());
         return friendship.isPresent();
    }

    public void borrarAmistades(List<Long> ids, String correo){
        for(Long id: ids){
            User principal = usersService.getUserByEmail(correo);
            if(principal.getId() != id)
                friendshipsRepository.borrarAmistades(id);
        }
    }

    public boolean areFriends(User user1, User user2){
        if(existsFriendship(user1,user2) || existsFriendship(user2,user1))
            return true;
        return false;
    }

    /**
     * Devuelve una lista con los emails de todas las solicitdes de amistad que tiene el usuario
     * (solicitudes que ha mandado él)
     */
    public List<String> getSentRequests(User user) {
        return friendshipsRepository.getSentRequests(user.getEmail());
    }

    /**
     * Devuelve una lista con lo socrreos de los usuairos que han enviado una solicitudes de amistad al usuario
     * autenticado y aún está pendiente de aceptar
     */
    public List<String> getReceivedRequests(User user) {
       return friendshipsRepository.getReceivedRequests(user.getEmail());
    }
}
