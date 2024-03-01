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
        User user1 = new User("pedro@example.com", "Pedro", "Díaz");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);

        User user2 = new User("lucas@example.com", "Lucas", "Núñez");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[0]);

        User user3 = new User("maria@example.com", "María", "Rodríguez");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);

        User user4 = new User("marta@example.com", "Marta", "Almonte");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[1]);

        User user5 = new User("pelayo@example.com", "Pelayo", "Valdes");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[1]);

        User user6 = new User("john.doe@example.com", "John", "Doe");
        user6.setPassword("123456");
        user6.setRole(rolesService.getRoles()[0]);

        User user7 = new User("jane.smith@example.com", "Jane", "Smith");
        user7.setPassword("123456");
        user7.setRole(rolesService.getRoles()[1]);

        User user8 = new User("michael.jones@example.com", "Michael", "Jones");
        user8.setPassword("123456");
        user8.setRole(rolesService.getRoles()[0]);

        User user9 = new User("susan.white@example.com", "Susan", "White");
        user9.setPassword("123456");
        user9.setRole(rolesService.getRoles()[1]);

        User user10 = new User("david.wilson@example.com", "David", "Wilson");
        user10.setPassword("123456");
        user10.setRole(rolesService.getRoles()[0]);

        User user11 = new User("emily.brown@example.com", "Emily", "Brown");
        user11.setPassword("123456");
        user11.setRole(rolesService.getRoles()[1]);

        User user12 = new User("olivia.johnson@example.com", "Olivia", "Johnson");
        user12.setPassword("123456");
        user12.setRole(rolesService.getRoles()[0]);

        User user13 = new User("ethan.moore@example.com", "Ethan", "Moore");
        user13.setPassword("123456");
        user13.setRole(rolesService.getRoles()[1]);

        User user14 = new User("ava.taylor@example.com", "Ava", "Taylor");
        user14.setPassword("123456");
        user14.setRole(rolesService.getRoles()[0]);

        User user15 = new User("william.anderson@example.com", "William", "Anderson");
        user15.setPassword("123456");
        user15.setRole(rolesService.getRoles()[1]);



        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);
        userService.addUser(user5);
        userService.addUser(user6);
        userService.addUser(user7);
        userService.addUser(user8);
        userService.addUser(user9);
        userService.addUser(user10);
        userService.addUser(user11);
        userService.addUser(user12);
        userService.addUser(user13);
        userService.addUser(user14);
        userService.addUser(user15);
    }
}
