package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

import java.util.List;


public interface UsersRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    // todos los usuarios de la aplicación (excepto Administrador y el usuario autenticado)
    @Query("SELECT u FROM User u WHERE u.id != ?1 AND u.role = ?2 ORDER BY u.id ASC")
    Page<User> findAllByStandardUser(Pageable pageable, Long userId, String role);
    Page<User> findAll(Pageable pageable);
    User findByEmail(String email);

    Page<User> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE User SET borrado=?1 WHERE id=?2")
    void updateBorrado(Boolean borrado,Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM User WHERE borrado = true")
    void borrarTodos();

    @Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1) OR LOWER(u.email) LIKE LOWER(?1))")
    Page<User> searchByEmailNameAndSurname(String searchtext, Pageable pageable);
}
