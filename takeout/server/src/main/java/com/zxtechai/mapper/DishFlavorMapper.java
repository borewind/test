package com.zxtechai.mapper;

import com.zxtechai.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 根据菜品id获取口味
     * @param dish_id
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{dish_id}")
    List<DishFlavor> list(Long dish_id);

    /**
     * 根据ID删除口味
     * @param dish_id
     */
    @Delete("delete from dish_flavor where dish_id = #{dish_id}")
    void delete(Long dish_id);

    /**
     * 批量添加口味
     * @param flavors
     */
    void add(List<DishFlavor> flavors);

    /**
     * 根据菜品id查询对应的口味数据
     * @param dishId
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
