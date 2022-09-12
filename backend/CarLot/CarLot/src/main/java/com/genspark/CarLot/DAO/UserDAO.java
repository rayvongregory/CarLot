package com.genspark.CarLot.DAO;

import com.genspark.CarLot.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDAO extends JpaRepository<User, String> {
    @Query(value =
            "SELECT u " +
            "FROM User u " +
            "WHERE u.email=:email")
    List<User> getUserByEmail(@Param("email") String email);
}
