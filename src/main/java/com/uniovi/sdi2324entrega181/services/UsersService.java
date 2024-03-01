package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
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

    public void setUserBorrado(boolean borrado,Long id){
        usersRepository.updateBorrado(borrado,id);
    }

    public User getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
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

    public void borrarTodo(){
        usersRepository.borrarTodos();
    }

}
