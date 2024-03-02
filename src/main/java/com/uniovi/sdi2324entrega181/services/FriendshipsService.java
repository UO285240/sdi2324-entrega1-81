package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.repositories.FriendshipsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendshipsService {

    private FriendshipsRepository friendshipsRepository;
    private List<Friendship> friends; // lista con los ususarios que son amigos


    public FriendshipsService(FriendshipsRepository friendshipsRepository){
        this.friendshipsRepository = friendshipsRepository;
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
}
