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



        User user1 = new User("user01@email.com", "Pedro", "Díaz");
        user1.setPassword("Us3r@1-PASSW");
        user1.setRole(rolesService.getRoles()[0]);

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

        User user16 = new User("user15@email.com", "Lucas", "Núñez");
        user16.setPassword("Us3r@15-PASSW");
        user16.setRole(rolesService.getRoles()[0]);



        Set<Post> posts1 = new HashSet<Post>();
        Set<Post> posts3 = new HashSet<Post>();
        Set<Post> posts4 = new HashSet<Post>();
        Set<Post> posts5 = new HashSet<Post>();
        Set<Post> posts6 = new HashSet<Post>();
        Set<Post> posts7 = new HashSet<Post>();
        Set<Post> posts8 = new HashSet<Post>();
        Set<Post> posts9 = new HashSet<Post>();
        Set<Post> posts10 = new HashSet<Post>();
        Set<Post> posts11 = new HashSet<Post>();
        Set<Post> posts12 = new HashSet<Post>();
        Set<Post> posts13 = new HashSet<Post>();
        Set<Post> posts14 = new HashSet<Post>();
        Set<Post> posts15 = new HashSet<Post>();
        Set<Post> posts16 = new HashSet<Post>();

        LocalDate date;
        String title;
        String text;
        Post post,post3,post4, post5,post6,post7, post8,post9,post10, post11,post12,post13,post14,post15,post16;


        for (int i = 1; i <= 15; i++) {
            date = LocalDate.of(2024, 2, i);
            title = "Título " + i;
            text = "Texto del post " + i;

            post = new Post(user1, title, text, date);
            post3 = new Post(user3, title, text, date);
            post4 = new Post(user4, title, text, date);
            post5 = new Post(user5, title, text, date);
            post6 = new Post(user6, title, text, date);
            post7 = new Post(user7, title, text, date);
            post8 = new Post(user8, title, text, date);
            post9 = new Post(user9, title, text, date);
            post10 = new Post(user10, title, text, date);
            post11 = new Post(user11, title, text, date);
            post12 = new Post(user12, title, text, date);
            post13 = new Post(user13, title, text, date);
            post14 = new Post(user14, title, text, date);
            post15 = new Post(user15, title, text, date);
            post16 = new Post(user16, title, text, date);

            posts1.add(post);
            posts3.add(post3);
            posts4.add(post4);
            posts5.add(post5);
            posts6.add(post6);
            posts7.add(post7);
            posts8.add(post8);
            posts9.add(post9);
            posts10.add(post10);
            posts11.add(post11);
            posts12.add(post12);
            posts13.add(post13);
            posts14.add(post14);
            posts15.add(post15);
            posts16.add(post16);

        }

        user1.setPosts(posts1);
        user3.setPosts(posts3);
        user4.setPosts(posts4);
        user5.setPosts(posts5);
        user6.setPosts(posts6);
        user7.setPosts(posts7);
        user8.setPosts(posts8);
        user9.setPosts(posts9);
        user10.setPosts(posts10);
        user11.setPosts(posts11);
        user12.setPosts(posts12);
        user13.setPosts(posts13);
        user14.setPosts(posts14);
        user15.setPosts(posts15);
        user16.setPosts(posts16);




        userService.addUser(user1);
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


        Friendship fr2 = new Friendship(user3,user1,true,LocalDate.now());

        Friendship fr3 = new Friendship(user8,user9,true,LocalDate.of(2024, 2, 3));



        friendshipsService.saveFrienship(fr2);
        friendshipsService.saveFrienship(fr3);





    }




}
