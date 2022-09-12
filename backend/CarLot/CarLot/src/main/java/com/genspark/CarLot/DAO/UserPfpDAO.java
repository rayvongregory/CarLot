package com.genspark.CarLot.DAO;

import com.genspark.CarLot.Entity.UserPfp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPfpDAO extends JpaRepository<UserPfp, String> {
    @Modifying
    @Query(value =
            "DELETE " +
            "FROM user_pfp p " +
            "WHERE p.user_id=:id",
            nativeQuery = true)
    void deletePfpByUserId(@Param("id") String id);

    @Query(value =
            "SELECT * " +
            "FROM user_pfp pfp " +
            "WHERE pfp.user_id=:id",
            nativeQuery = true)
    List<UserPfp> getPfpByUserId(@Param("id") String id);
}
