package com.uniovi.sdi2324entrega181.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    private long id;

    private String title;
    private String text;
    private LocalDate date;

    @OneToOne
    private User user; // el propietario del post

    public Post() {}

    public Post(User user, String title, String text, LocalDate date) {
        this.user = user;
        this.title = title;
        this.text = text;
        this.date = date;
    }


}
