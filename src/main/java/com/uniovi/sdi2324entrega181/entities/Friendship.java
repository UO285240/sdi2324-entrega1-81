package com.uniovi.sdi2324entrega181.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "friendship")
public class Friendship {


    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private User sender; // el que envía la solicitud de amistad

    @OneToOne
    private User receiver; // el que recive la solicitud de amistad

    private boolean isAccepted; // true -> amigos

    private LocalDate sendDate; // fecha de envío de la solicitud


    public Friendship(){}

    public Friendship(User sender, User receiver, boolean isAccepted, LocalDate sendDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.isAccepted = isAccepted;
        this.sendDate = sendDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}
