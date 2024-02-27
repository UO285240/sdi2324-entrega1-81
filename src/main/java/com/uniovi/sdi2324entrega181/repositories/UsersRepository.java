package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UsersRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    Page<User> findAll(Pageable pageable);
}
