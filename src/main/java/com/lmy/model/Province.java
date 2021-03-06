package com.lmy.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "province")
    private Set<User> users;

    public Province() {
    }

    public Province(String name) {

        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
