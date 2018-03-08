package com.mm.model;

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

    private Integer phoneNumber;

    @ManyToOne
    private Province province;

    private String detail;
}
