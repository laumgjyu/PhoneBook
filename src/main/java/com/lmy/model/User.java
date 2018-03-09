package com.lmy.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by lmy on 2018/2/9.
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String phoneNumber;

    @ManyToOne
    private Province province;

    private String address;

    public User() {
    }

    public User(String name, String phoneNumber, Province province, String address) {

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.address = address;
    }
}
