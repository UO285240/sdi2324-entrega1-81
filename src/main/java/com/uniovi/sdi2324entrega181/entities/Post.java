package com.uniovi.sdi2324entrega181.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class Post {

    @Id
    @GeneratedValue
    private long id;

    private String title;
    private String text;

    @OneToOne
    private User user; // el propietario del post

    public Post(User user, String title, String text) {
        this.user = user;
        this.title = title;
        this.text = text;
    }
}
