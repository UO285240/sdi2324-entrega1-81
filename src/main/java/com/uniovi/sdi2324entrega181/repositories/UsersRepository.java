package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;


public interface UsersRepository extends CrudRepository<User, Long> {
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
}
