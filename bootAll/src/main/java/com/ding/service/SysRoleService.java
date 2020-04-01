package com.ding.service;

import com.ding.common.utils.JsonResultUtils;
import com.ding.dao.SysRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName SysRoleService
 * @date 2020-03-09 12:25
 */
@Service
public class SysRoleService {
    @Autowired
    private SysRoleDao roleDao;

    public String findAll(){
       return   JsonResultUtils.success(roleDao.findAll());
    }
}
