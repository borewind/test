package com.zxtechai.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zxtechai.constant.MessageConstant;
import com.zxtechai.constant.StatusConstant;
import com.zxtechai.context.BaseContext;
import com.zxtechai.dto.CategoryDTO;
import com.zxtechai.dto.CategoryPageQueryDTO;
import com.zxtechai.entity.Category;
import com.zxtechai.exception.DeletionNotAllowedException;
import com.zxtechai.mapper.CategoryMapper;
import com.zxtechai.mapper.DishMapper;
import com.zxtechai.mapper.SetmealMapper;
import com.zxtechai.result.PageResult;
import com.zxtechai.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> categories = categoryMapper.page(categoryPageQueryDTO);
        return new PageResult(categories.getTotal(),categories.getResult());
    }

    /**
     * 编辑分类
     * @param categoryDTO
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    /**
     * 修改状态
     * @param id
     * @param status
     */
    @Override
    public void status(Long id, Integer status) {
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
        categoryMapper.update(category);
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    public void delete(Long id) {
        Integer dishCount = dishMapper.countByCategoryId(id);
        Integer setmealCount = setmealMapper.countByCategoryId(id);
        if(dishCount > 0){
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        if(setmealCount > 0){
            //当前分类下有套餐，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.delete(id);
    }

    /**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void add(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());
        category.setStatus(StatusConstant.DISABLE);
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.add(category);
    }

    /**
     * 分类列表
     * @param type
     * @return
     */
    @Override
    public List<Category> list(Integer type) {
        List<Category> list = categoryMapper.list(type);
        return list;
    }
}
