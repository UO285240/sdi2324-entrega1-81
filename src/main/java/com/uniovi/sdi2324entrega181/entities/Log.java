package com.uniovi.sdi2324entrega181.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue
    private long id;
    private String type;
    private Timestamp date;
    private String description;

    @Transient
    private Logger logger = LoggerFactory.getLogger(Log.class);

    public Log(String type, String description) {
        this.type = type;
        this.description = description;
        this.date = new Timestamp(new Date().getTime());
        logger.debug(type + ": Timestamp: " + date + " Desc: " + description);
    }

    public Log() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }
}
