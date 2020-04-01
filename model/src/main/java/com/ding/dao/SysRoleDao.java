package com.ding.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleDao {

    List<SysRoleDao> findAll();
}
