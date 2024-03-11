package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
    }

    public Page<User> getUsers(Pageable pageable) {
        Page<User> users = usersRepository.findAll(pageable);
        return users;
    }


    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    public User getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }


    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user); }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public void saveUser(User user){
        usersRepository.save(user);
    }

    public void updateUser(User user, Long id) {

        User originalUser = getUser(id);
        if(!originalUser.equals(null)) {
            originalUser.setEmail(user.getEmail());
            originalUser.setName(user.getName());
            originalUser.setLastName(user.getLastName());
            usersRepository.save(originalUser);
        }

    }




    public Page<User> searchByEmailNameAndSurname(String text, Pageable pageable){

        String searchTest = "%"+text+"%";
        Page<User> users = usersRepository.searchByEmailNameAndSurname(searchTest, pageable);

        return users;
    }

    public Page<User> getUsersForUser(Pageable pageable, User user) {
        Page<User> users = new PageImpl<>(new LinkedList<>());

        if (user.getRole().equals("USUARIO_ESTANDAR")) {
            users = usersRepository.findAllByStandardUser(pageable, user.getId(), "USUARIO_ESTANDAR");}

        if (user.getRole().equals("USUARIO_ADMIN")) {
            users = getUsers(pageable); }

        return users;
    }

    public void borrarPorId(List<Long> usuariosABorrar, String correo) {

        for(Long id: usuariosABorrar){
            User principal = getUserByEmail(correo);
            if(principal.getId() != id)
                usersRepository.deleteById(id);
        }
    }
}
