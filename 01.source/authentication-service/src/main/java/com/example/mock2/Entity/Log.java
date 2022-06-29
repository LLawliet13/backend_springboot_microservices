package com.example.mock2.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String typeOfAction;
    String dateCreated;
    String username;
    String action;

    public Log(String typeOfAction, String dateCreated, String username, String action) {
        this.typeOfAction = typeOfAction;
        this.dateCreated = dateCreated;
        this.username = username;
        this.action = action;
    }
}
