package com.zxtechai.service.impl;

import com.Contract.EmployeeContract;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zxtechai.Contract.EmployeeContractDTO;
import com.zxtechai.constant.MessageConstant;
import com.zxtechai.constant.PasswordConstant;
import com.zxtechai.constant.StatusConstant;
import com.zxtechai.context.BaseContext;
import com.zxtechai.dto.EmployeeDTO;
import com.zxtechai.dto.EmployeeLoginDTO;
import com.zxtechai.dto.EmployeePageQueryDTO;
import com.zxtechai.entity.Employee;
import com.zxtechai.exception.AccountLockedException;
import com.zxtechai.exception.AccountNotFoundException;
import com.zxtechai.exception.PasswordErrorException;
import com.zxtechai.mapper.EmployeeMapper;
import com.zxtechai.result.PageResult;
import com.zxtechai.service.EmployeeService;
import com.zxtechai.utils.Md5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //MD5比对
        String md5Str = Md5Util.md5(password);
        if (!md5Str.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 添加员工
     * @param employeeDTO 员工数据传输对象，包含员工的基本信息和合同信息
     */
    @Override
    public void add(EmployeeDTO employeeDTO){
        // 创建员工实体对象
        Employee employee = new Employee();
        // 创建员工合同实体对象
        EmployeeContract employeeContract = new EmployeeContract();
        // 创建员工合同数据传输对象
        EmployeeContractDTO employeeContractDTO = new EmployeeContractDTO();

        // 将员工数据传输对象的属性复制到员工实体对象
        BeanUtils.copyProperties(employeeDTO,employee);
        // 将员工数据传输对象的属性复制到员工合同数据传输对象
        BeanUtils.copyProperties(employeeDTO,employeeContractDTO);

        // 为新员工设置默认密码，并进行MD5加密
        employee.setPassword(Md5Util.md5(PasswordConstant.DEFAULT_PASSWORD));

        // 设置员工状态为启用
        employee.setStatus(StatusConstant.ENABLE);

        // 调用员工映射器的添加方法，将员工信息添加到数据库
        employeeMapper.add(employee);

        try {
            // 通过区块链服务将员工信息添加到区块链
            employeeContract.addEmployee(employeeContractDTO);
        } catch (Exception e) {
            // 区块链操作失败，事务会自动回滚
            e.printStackTrace();
        }
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> employees = employeeMapper.page(employeePageQueryDTO);

        PageResult pageResult = new PageResult(employees.getTotal(),employees.getResult());
        return pageResult;
    }

    /**
     * 编辑回显
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("******");
        return employee;
    }

    /**
     * 编辑员工
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

    /**
     * 编辑状态
     * @param status
     * @param id
     */
    @Override
    public void status(Integer status, Long id) {
        Employee employee = new Employee();
        employee.setStatus(status);
        employee.setId(id);
        employeeMapper.update(employee);
    }

}
