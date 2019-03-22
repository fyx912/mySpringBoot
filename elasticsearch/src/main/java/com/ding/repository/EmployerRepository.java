/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EmployeeRepository
 * Author:   THINK
 * Date:     2019/3/21 17:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ding.repository;

import com.ding.model.Employer;
import org.springframework.stereotype.Component;



/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author THINK
 * @create 2019/3/21
 * @since 1.0.0
 */
@Component
public interface EmployerRepository extends BaseElasticsearch<Employer,String> {
    /**
     * 查询雇员信息
     * @param id
     * @return
     */
    Employer  queryEmployeeById(String id);
}
