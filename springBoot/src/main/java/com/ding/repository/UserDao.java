package com.ding.repository;

import com.ding.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserDao extends JpaRepository<User,Integer> {

    @Query(value = " SELECT * FROM USER  ",nativeQuery = true)
    List<User> findAll();

    @Query(value = "select * from user u where u.id=?1 ", nativeQuery = true)
    User findById( int id);

    @Modifying
    @Query(value = "update user u set u.age = :newAge where  u.id = :oldId" ,nativeQuery = true)
    void updateById(@Param("newAge") int newAge , @Param("oldId") int oldId);

}
