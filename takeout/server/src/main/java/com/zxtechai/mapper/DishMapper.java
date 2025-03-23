package com.zxtechai.mapper;

import com.github.pagehelper.Page;
import com.zxtechai.annotation.AutoFill;
import com.zxtechai.dto.DishPageQueryDTO;
import com.zxtechai.entity.Dish;
import com.zxtechai.enumeration.OperationType;
import com.zxtechai.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {
    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 编辑菜品回显查询
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    DishVO dish(Long id);

    /**
     * 编辑菜品
     * @param dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 菜品状态
     * @param status
     * @param id
     */
    @Update("update dish set status = #{status} where id = #{id}")
    void status(Integer status, Long id);

    /**
     * 新增菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)//开启自动字段填充
    void add(Dish dish);

    /**
     * 删除菜品
     * @param ids
     */
    void delete(List<Long>ids);

    /**
     * 根据ID分类查询菜品
     * @param categoryId
     * @return
     */
    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> list(Long categoryId);
    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);
    /**
     * 动态条件查询菜品
     *
     * @param dish
     * @return
     */
    List<Dish> list1(Dish dish);
    /**
     * 根据主键查询菜品
     *
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);
    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
