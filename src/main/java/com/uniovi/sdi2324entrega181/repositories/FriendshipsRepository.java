package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


public interface FriendshipsRepository extends CrudRepository<Friendship, Long> {

    @Query("SELECT f.receiver.email FROM Friendship f WHERE f.sender.email LIKE ?1 AND f.isAccepted = false")
    List<String> getSentRequests(String email);

    @Query("SELECT f.sender.email FROM Friendship f WHERE f.receiver.email LIKE ?1 AND f.isAccepted = false")
    List<String> getReceivedRequests(String email);


    @Query("SELECT f FROM Friendship f " +
            "WHERE (f.sender.email LIKE ?1 OR f.receiver.email LIKE ?1 )" +
            "AND f.isAccepted = true")
    List<Friendship> getFriends(String email);


    @Query("SELECT f FROM Friendship f " +
            "WHERE (f.sender.email LIKE ?1 OR f.receiver.email LIKE ?1 )" +
            "AND f.isAccepted = true")
    Page<Friendship> getFriendsPageable(Pageable pageable,String email);

    /**
     * Si ya existe un objeto Friendship con los dos usuarios, devuleve false.
     */
    @Query("SELECT f FROM Friendship f WHERE f.sender.email = ?1 AND f.receiver.email = ?2")
    Optional<Friendship> findBySenderAndReceiver(String sender, String receiver);

    @Modifying
    @Transactional
    @Query("DELETE FROM Friendship f WHERE f.sender.id = ?1 OR f.receiver.id = ?1")
    void borrarAmistades(Long id);

    @Query("SELECT f.sender FROM Friendship f WHERE f.receiver.email LIKE (?1) AND f.isAccepted <> TRUE")
    Page<Friendship> getReceivedPetitions(Pageable pageable, String email);
}
