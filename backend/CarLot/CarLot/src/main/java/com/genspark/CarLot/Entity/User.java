package com.genspark.CarLot.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@DynamicInsert
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @Column(name = "id")
    private String id;
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
    @Column(name = "locked")
    private boolean locked;
    @Column(name = "verified")
    private boolean verified;
}
