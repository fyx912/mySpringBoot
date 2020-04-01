package com.ding;

import com.ding.dao.SysRoleDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName RoleTest
 * @date 2020-03-09 13:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTest {
    @Autowired
    private SysRoleDao roleDao;

    @Test
    public void  test(){
        System.out.println(roleDao.findAll());
    }
}
