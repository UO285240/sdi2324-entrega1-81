package com.uniovi.sdi2324entrega181.entities;

import javax.persistence.*;

@Entity
@Table(name = "recommendation", uniqueConstraints  =
        {@UniqueConstraint(columnNames = {"user_id", "post_id"})})
public class Recommendation {

    public Recommendation(){}

    public Recommendation(User user, Post post){
        this.user=user;
        this.post=post;
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name= "post_id")
    private Post post;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
