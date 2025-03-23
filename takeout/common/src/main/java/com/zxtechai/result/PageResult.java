package com.zxtechai.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页查询返回的数据模型")
public class PageResult implements Serializable {
    @ApiModelProperty("数据总数量")
    private long total; //总记录数
    @ApiModelProperty("当前页数据List集合")
    private List records; //当前页数据集合

}
