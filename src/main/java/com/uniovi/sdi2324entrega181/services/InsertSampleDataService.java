package com.uniovi.sdi2324entrega181.services;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;

import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.stereotype.Service;

@Service
public class InsertSampleDataService {
    private final UsersService userService;
    private final RolesService rolesService;
    public InsertSampleDataService(UsersService usersService, RolesService rolesService) {
        this.userService = usersService;
        this.rolesService = rolesService;
    }
    @PostConstruct
    public void init() {
        User user1 = new User("99999990A", "Pedro", "Díaz");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);
        User user2 = new User("99999991B", "Lucas", "Núñez");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[0]);
        User user3 = new User("99999992C", "María", "Rodríguez");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);
        User user4 = new User("99999993D", "Marta", "Almonte");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[1]);
        User user5 = new User("99999977E", "Pelayo", "Valdes");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[1]);



        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);
        userService.addUser(user5);
    }
}
