package com.asahi.demo.springSecurity.repository;

import com.asahi.demo.springSecurity.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;


public interface UserRepository extends JpaRepository<AuthUser,Integer> {
    AuthUser getByUsername(String username);

    @Modifying
    @Transactional
    @Query("update AuthUser u set u.username=?2 , u.password=?3 where u.id=?1")
    Integer updateById(Integer id, String username, String password, String updatedBy, Date updatedDate);
}
