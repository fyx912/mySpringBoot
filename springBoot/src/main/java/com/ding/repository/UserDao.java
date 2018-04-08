package com.ding.repository;

import com.ding.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserDao extends Repository<User,Integer> {

    @Query(value = "select * from user",nativeQuery = true)
    List<User> findAll();

    @Query(value = "select * from user  where id= ? " ,nativeQuery = true)
    User findById(int id);
}
