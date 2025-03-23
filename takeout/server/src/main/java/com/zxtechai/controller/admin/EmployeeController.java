package com.zxtechai.controller.admin;

import com.zxtechai.constant.JwtClaimsConstant;
import com.zxtechai.dto.EmployeeDTO;
import com.zxtechai.dto.EmployeeLoginDTO;
import com.zxtechai.dto.EmployeePageQueryDTO;
import com.zxtechai.entity.Employee;
import com.zxtechai.properties.JwtProperties;
import com.zxtechai.result.PageResult;
import com.zxtechai.result.Result;
import com.zxtechai.service.EmployeeService;
import com.zxtechai.utils.JwtUtil;
import com.zxtechai.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工登录退出接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO {username,password}
     * @return {id,username,name,token}
     */
    @ApiOperation("员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return Result
     */
    @ApiOperation("员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 添加员工
     * @param employeeDTO {username,name,phone,sex,idNumber}
     * @return Result
     */
    @ApiOperation("添加员工")
    @PostMapping
    public Result<String> add(@RequestBody EmployeeDTO employeeDTO){
        log.info("添加员工:{}",employeeDTO);
        employeeService.add(employeeDTO);
        return Result.success();
    }

    /**
     * 分页查询List
     * @param employeePageQueryDTO {name,page,pageSize}
     * @return 返回分页统计和List集合
     */
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询:{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.page(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询 编辑回显
     * @param id 对应id
     * @return 返回单条员工数据
     */
    @ApiOperation("编辑回显")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        log.info("编辑回显ID:{}",id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 编辑员工
     * @param employeeDTO {id,username,name,phone,sex,idNumber}
     * @return Result
     */
    @ApiOperation("编辑员工")
    @PutMapping
    public Result<String> update(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工:{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }

    /**
     * 修改状态
     * @param id 对应id
     * @param status 状态
     * @return Result
     */
    @ApiOperation("编辑状态")
    @PostMapping("/status/{status}")
    public Result<String> status(@PathVariable Integer status,Long id){
        log.info("修改状态status:{},id:{}",status,id);
        employeeService.status(status,id);
        return Result.success();
    }

}
