package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.repositories.FriendshipsRepository;
import org.springframework.data.domain.PageImpl;
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
     * Deletes a petition from the database
     * @param f friendship petition
     */
    public void deletePetition(Friendship f){
        friendshipsRepository.delete(f);
    }

    /**
     * Add new petition to the database
     * @param friendship
     */
    public void add(Friendship friendship){
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

    /**
     * Método que devuelve un Page con las amistades de un usuario
     * @param pageable objeto necesario para la paginación
     * @param user usuario del que se van a buscar las amistades
     * @return un Page con las amistades del usuario
     */
    public Page<Friendship> getFriendsUser(Pageable pageable,User user){
        return friendshipsRepository.getFriendsPageable(pageable,user.getEmail());
    }

    public boolean existsFriendship(User sender, User receiver) {
         Optional<Friendship> friendship = friendshipsRepository.findBySenderAndReceiver(sender.getEmail(), receiver.getEmail());
         return friendship.isPresent();
    }

    /**
     * Método para borrar todas las amistades de una lista de usuarios
     * @param ids lista con los ids de los usuarios
     */
    public void borrarAmistades(List<Long> ids){
        for(Long id: ids){
            friendshipsRepository.borrarAmistades(id);
        }
    }

    /**
     * Método que comprueba si 2 usuarios son amigos
     * @param user1 usuario1 a comprobar
     * @param user2 usuario 2 a comprobar
     * @return true si son amigos y false si no
     */
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

    /**
     * Devuelve una lista con todas las solicitudes pendientes de un usuario
     * @param pageable
     * @param email
     * @return pageable users
     */
    public Page<Friendship> getMyReceivedPetitions(Pageable pageable,String email){
        return friendshipsRepository.getReceivedPetitions(pageable,email);
    }

    /**
     * Returns the friend's petition between 2 users
     * @param user1
     * @param user2
     * @return  the request(s) between users
     */
    public List<Friendship> getPetitionBy2Users(String user1, String user2){
        return friendshipsRepository.getPetitionBy2Users(user1, user2);
    }
}
