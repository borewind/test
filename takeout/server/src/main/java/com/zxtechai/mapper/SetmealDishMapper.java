package com.zxtechai.mapper;

import com.zxtechai.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 获取关联菜品
     * @param ids
     * @return
     */
    List<Long> getDishList(List<Long> ids);

    /**
     * 新增关联菜品
     * @param dish
     */
    @Insert("insert into setmeal_dish (setmeal_id,dish_id,name,price,copies) " +
            "values (#{setmealId},#{dishId},#{name},#{price},#{copies})")
    void add(SetmealDish dish);

    /**
     * 根据套餐ID查询
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);

    /**
     * 根据套餐ID删除
     * @param id
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void delete(Long id);
}
