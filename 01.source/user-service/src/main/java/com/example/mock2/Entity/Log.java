package com.example.mock2.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Log {
    @Id
    @GeneratedValue
    String date;

}
