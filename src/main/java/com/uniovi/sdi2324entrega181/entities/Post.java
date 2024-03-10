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

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;



    public Post() {}

    public Post(User user, String title, String text, LocalDate date) {
        this.user = user;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
