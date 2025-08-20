package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface FriendshipsRepository extends CrudRepository<Friendship, Long> {

    /**
     * @param email el correo del usuario autenticado (el que envía las soliciudes)
     * @return los emails de los usuarios que han recibido solicitudes de amistad enviadas por el usuario autenticado
     */
    @Query("SELECT f.receiver.email FROM Friendship f WHERE f.sender.email LIKE ?1 AND f.isAccepted = false")
    List<String> getSentRequests(String email);

    /**
     * @param email el correo del usuario auteticado (el que recibe las solicitudes)
     * @return los emails de los usuarios que han enviado solicitudes de amistad al usuario autenticado
     */
    @Query("SELECT f.sender.email FROM Friendship f WHERE f.receiver.email LIKE ?1 AND f.isAccepted = false")
    List<String> getReceivedRequests(String email);


    /**
     * @param email el correo del usuario autenticado
     * @return la lista de amistades que tiene el usuario autenticado
     */
    @Query("SELECT f FROM Friendship f " +
            "WHERE (f.sender.email LIKE ?1 OR f.receiver.email LIKE ?1 )" +
            "AND f.isAccepted = true")
    List<Friendship> getFriends(String email);

    /**
     * Método que devuelve un Page de amistades para un usuario determinado
     * @param pageable objeto necesario para la paginación
     * @param email email del usuario del que se van a sacar las amistades
     * @return un Page con todas las amistades del usuario
     */
    @Query("SELECT f FROM Friendship f " +
            "WHERE (f.sender.email LIKE ?1 OR f.receiver.email LIKE ?1 )" +
            "AND f.isAccepted = true")
    Page<Friendship> getFriendsPageable(Pageable pageable,String email);

    /**
     * Si ya existe un objeto Friendship con los dos usuarios, devuleve false.
     */
    @Query("SELECT f FROM Friendship f WHERE f.sender.email = ?1 AND f.receiver.email = ?2")
    Optional<Friendship> findBySenderAndReceiver(String sender, String receiver);

    /**
     * Borra las amistades de un usuario
     * @param id id del usuario para borrar las amistades
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Friendship f WHERE f.sender.id = ?1 OR f.receiver.id = ?1")
    void borrarAmistades(Long id);

    @Query("SELECT f FROM Friendship f WHERE f.receiver.email LIKE (?1) AND f.isAccepted <> TRUE")
    Page<Friendship> getReceivedPetitions(Pageable pageable, String email);

    @Query("SELECT f FROM Friendship f WHERE (f.sender.email LIKE (?1) OR f.sender.email LIKE (?2)) AND " +
            "((f.receiver.email LIKE (?1) OR f.receiver.email LIKE (?2)) AND f.isAccepted <> TRUE)")
    List<Friendship> getPetitionBy2Users(String user1, String user2);
}
