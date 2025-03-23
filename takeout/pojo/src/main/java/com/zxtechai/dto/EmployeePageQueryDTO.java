package com.zxtechai.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "页面查询传递的数据模型")
public class EmployeePageQueryDTO implements Serializable {

    //员工姓名
    @ApiModelProperty("查询用户姓名name")
    private String name;

    //页码
    @ApiModelProperty("页码第几页")
    private int page;

    //每页显示记录数
    @ApiModelProperty("每页展示数量")
    private int pageSize;

}
