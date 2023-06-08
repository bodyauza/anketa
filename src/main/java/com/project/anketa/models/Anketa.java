package com.project.anketa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Anketa {

    public Anketa() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name, email;
    private String number;
    private Date date;

    public Anketa(String name, String email, String number, Date date) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.date = date;
    }
}


