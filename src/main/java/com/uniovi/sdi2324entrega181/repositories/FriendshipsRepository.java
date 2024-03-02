package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface FriendshipsRepository extends CrudRepository<Friendship, Long> {

    @Query("SELECT f.receiver.email FROM Friendship f WHERE f.sender.email LIKE ?1 AND f.isAccepted = false")
    List<String> getSentRequests(String email);

    @Query("SELECT f.sender.email FROM Friendship f WHERE f.receiver.email LIKE ?1 AND f.isAccepted = false")
    List<String> getReceivedRequests(String email);


    @Query("SELECT f FROM Friendship f " +
            "WHERE (f.sender.email LIKE ?1 OR f.receiver.email LIKE ?1 )" +
            "AND f.isAccepted = true")
    List<Friendship> getFriends(String email);
}
