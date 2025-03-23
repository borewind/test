package com.zxtechai.service;

import com.zxtechai.dto.SetmealDTO;
import com.zxtechai.dto.SetmealPageQueryDTO;
import com.zxtechai.entity.Setmeal;
import com.zxtechai.result.PageResult;
import com.zxtechai.vo.DishItemVO;
import com.zxtechai.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐
     * @param setmealDTO
     */
    void add(SetmealDTO setmealDTO);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据ID回显套餐
     * @param id
     * @return
     */
    SetmealVO getById(Long id);

    /**
     * 套餐编辑
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 修改状态
     * @param status
     * @param id
     */
    void status(Long id, Integer status);

    /**
     * 批量删除套餐
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 根据分类id查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询包含的菜品列表
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
