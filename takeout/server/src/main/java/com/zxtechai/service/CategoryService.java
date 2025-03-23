package com.zxtechai.service;

import com.zxtechai.dto.CategoryDTO;
import com.zxtechai.dto.CategoryPageQueryDTO;
import com.zxtechai.entity.Category;
import com.zxtechai.result.PageResult;

import java.util.List;


public interface CategoryService {
    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 编辑分类
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 修改状态
     * @param id
     * @param status
     */
    void status(Long id, Integer status);

    /**
     * 删除分类
     * @param id
     */
    void delete(Long id);

    /**
     * 新增分类
     * @param categoryDTO
     */
    void add(CategoryDTO categoryDTO);

    /**
     * 分类列表
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
