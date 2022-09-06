package com.genspark.CarLot.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@DynamicInsert
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "fname", nullable = false)
    private String fname;
    @Column(name = "lname", nullable = false)
    private String lname;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", columnDefinition = "varchar(255) default 'user'")
    private String role;
}
