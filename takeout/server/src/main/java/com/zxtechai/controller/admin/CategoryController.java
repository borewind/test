package com.zxtechai.controller.admin;

import com.zxtechai.dto.CategoryDTO;
import com.zxtechai.dto.CategoryPageQueryDTO;
import com.zxtechai.entity.Category;
import com.zxtechai.result.PageResult;
import com.zxtechai.result.Result;
import com.zxtechai.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类方法
 */
@Slf4j
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类列表
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分类列表")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询:{}",categoryPageQueryDTO);
        PageResult page = categoryService.page(categoryPageQueryDTO);
        return Result.success(page);
    }

    /**
     * 编辑分类
     * @param categoryDTO
     * @return
     */
    @ApiOperation("修改分类")
    @PutMapping
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        log.info("编辑分类:{}",categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 修改状态
     * @param id
     * @param status
     * @return
     */
    @ApiOperation("修改分类状态")
    @PostMapping("/status/{status}")
    public Result<String> status(Long id,@PathVariable Integer status){
        categoryService.status(id,status);
        return Result.success();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @ApiOperation("删除分类")
    @DeleteMapping
    public Result<String> delete(Long id){
        categoryService.delete(id);
        return Result.success();
    }

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @ApiOperation("新增分类")
    @PostMapping
    public Result<String> add(@RequestBody CategoryDTO categoryDTO){
        categoryService.add(categoryDTO);
        return Result.success();
    }
}
