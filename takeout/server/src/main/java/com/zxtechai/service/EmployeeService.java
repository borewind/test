package com.zxtechai.service;

import com.zxtechai.dto.EmployeeDTO;
import com.zxtechai.dto.EmployeeLoginDTO;
import com.zxtechai.dto.EmployeePageQueryDTO;
import com.zxtechai.entity.Employee;
import com.zxtechai.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 添加员工
     * @param employeeDTO
     */
    void add(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据ID查询相关员工 编辑回显
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 编辑员工
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);

    /**
     * 编辑状态
     * @param status
     * @param id
     */
    void status(Integer status, Long id);
}
