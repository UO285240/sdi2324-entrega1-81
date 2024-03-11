package com.uniovi.sdi2324entrega181.services;
import javax.annotation.PostConstruct;

import com.uniovi.sdi2324entrega181.entities.Friendship;
import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class InsertSampleDataService {
    private final UsersService userService;
    private final RolesService rolesService;
    private final PostsService postsService;

    private final FriendshipsService friendshipsService;

    public InsertSampleDataService(UsersService usersService, RolesService rolesService, PostsService postsService,
     FriendshipsService friendshipsService) {
        this.userService = usersService;
        this.rolesService = rolesService;
        this.postsService = postsService;
        this.friendshipsService = friendshipsService;
    }

    @PostConstruct
    public void init() {
        User admin = new User("admin@email.com", "Admin", "Istrador");
        admin.setPassword("@Dm1n1str@D0r");
        admin.setRole(rolesService.getRoles()[1]);
        userService.addUser(admin);



        User user1 = new User("pedro@example.com", "Pedro", "Díaz");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);

        User user2 = new User("lucas@example.com", "Lucas", "Núñez");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[0]);

        User user3 = new User("user02@email.com", "María", "Rodríguez");
        user3.setPassword("Us3r@2-PASSW");
        user3.setRole(rolesService.getRoles()[0]);

        User user4 = new User("user03@email.com", "Marta", "Almonte");
        user4.setPassword("Us3r@3-PASSW");
        user4.setRole(rolesService.getRoles()[0]);

        User user5 = new User("user04@email.com", "Pelayo", "Valdes");
        user5.setPassword("Us3r@4-PASSW");
        user5.setRole(rolesService.getRoles()[0]);

        User user6 = new User("user05@email.com", "John", "Doe");
        user6.setPassword("Us3r@5-PASSW");
        user6.setRole(rolesService.getRoles()[0]);

        User user7 = new User("user06@email.com", "Jane", "Smith");
        user7.setPassword("Us3r@6-PASSW");
        user7.setRole(rolesService.getRoles()[0]);

        User user8 = new User("user07@email.com", "Michael", "Jones");
        user8.setPassword("Us3r@7-PASSW");
        user8.setRole(rolesService.getRoles()[0]);

        User user9 = new User("user08@email.com", "Susan", "White");
        user9.setPassword("Us3r@8-PASSW");
        user9.setRole(rolesService.getRoles()[0]);

        User user10 = new User("user09@email.com", "David", "Wilson");
        user10.setPassword("Us3r@9-PASSW");
        user10.setRole(rolesService.getRoles()[0]);

        User user11 = new User("user10@email.com", "Emily", "Brown");
        user11.setPassword("Us3r@10-PASSW");
        user11.setRole(rolesService.getRoles()[0]);

        User user12 = new User("user11@email.com", "Olivia", "Johnson");
        user12.setPassword("Us3r@11-PASSW");
        user12.setRole(rolesService.getRoles()[0]);

        User user13 = new User("user12@email.com", "Ethan", "Moore");
        user13.setPassword("Us3r@12-PASSW");
        user13.setRole(rolesService.getRoles()[0]);

        User user14 = new User("user13@email.com", "Ava", "Taylor");
        user14.setPassword("Us3r@13-PASSW");
        user14.setRole(rolesService.getRoles()[0]);

        User user15 = new User("user14@email.com", "William", "Anderson");
        user15.setPassword("Us3r@14-PASSW");
        user15.setRole(rolesService.getRoles()[0]);

        User user16 = new User("user15@email.com", "William", "Anderson");
        user16.setPassword("Us3r@15-PASSW");
        user16.setRole(rolesService.getRoles()[0]);



        Set<Post> posts = new HashSet<Post>();

        // 15 publicaciones para el usuario pedro@example.com
        LocalDate date;
        String title;
        String text;
        Post post;
        for (int i = 1; i <= 15; i++) {
            date = LocalDate.of(2024, 2, i);
            title = "Título " + i;
            text = "Texto del post " + i;

            post = new Post(user1, title, text, date);
            posts.add(post);
            //postsService.addPost(post);
        }

        user1.setPosts(posts);




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
        userService.addUser(user16);


        Friendship fr1 = new Friendship(user1,user2,true,LocalDate.now());
        Friendship fr2 = new Friendship(user3,user1,true,LocalDate.now());

        friendshipsService.saveFrienship(fr1);
        friendshipsService.saveFrienship(fr2);



    }




}
