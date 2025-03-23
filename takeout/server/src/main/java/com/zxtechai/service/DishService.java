package com.zxtechai.service;

import com.zxtechai.dto.DishDTO;
import com.zxtechai.dto.DishPageQueryDTO;
import com.zxtechai.entity.Dish;
import com.zxtechai.result.PageResult;
import com.zxtechai.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 编辑菜品回显查询
     * @param id
     * @return
     */
    DishVO dish(Long id);

    /**
     * 编辑菜品
     * @param dishDTO
     */
    void update(DishDTO dishDTO);

    /**
     * 菜品状态
     * @param status
     * @param id
     */
    void status(Integer status, Long id);

    /**
     * 新增菜品
     * @param dishDTO
     */
    void add(DishDTO dishDTO);

    /**
     * 删除菜品
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 根据分类ID查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * 根据分类id查询菜品
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
