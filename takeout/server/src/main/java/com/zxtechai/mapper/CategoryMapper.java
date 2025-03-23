package com.zxtechai.mapper;

import com.github.pagehelper.Page;
import com.zxtechai.annotation.AutoFill;
import com.zxtechai.dto.CategoryPageQueryDTO;
import com.zxtechai.entity.Category;
import com.zxtechai.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 编辑分类
     * @param category
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    /**
     * 删除分类
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void delete(Long id);

    /**
     * 新增分类
     * @param category
     */
    @Insert("insert into category (type,name,sort,status,create_time,update_time,create_user,update_user) " +
            "values (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void add(Category category);

    /**
     * 根据ID查询分类
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
