package com.ding.repository;

import com.ding.domain.User;
import org.springframework.data.repository.CrudRepository;

// 该接口会自动被实现，springdata已经帮我们实现了基本的增删改查
// CRUD --> Create（增）, Read（查）, Update（改）, Delete（删）
public interface UserRepository extends CrudRepository<User, Long> {
}
