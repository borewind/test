package com.zxtechai.controller.admin;

import com.zxtechai.dto.DishDTO;
import com.zxtechai.dto.DishPageQueryDTO;
import com.zxtechai.entity.Dish;
import com.zxtechai.result.PageResult;
import com.zxtechai.result.Result;
import com.zxtechai.service.DishService;
import com.zxtechai.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

//@SuppressWarnings("all")//取消所有警告
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询:{}",dishPageQueryDTO);
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 编辑菜品回显查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("编辑菜品回显查询")
    public Result<DishVO> dish(@PathVariable Long id){
        log.info("编辑回显id:{}",id);
        DishVO dishVO = dishService.dish(id);
        return Result.success(dishVO);
    }

    /**
     * 编辑菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑菜品")
    public Result<String> update(@RequestBody DishDTO dishDTO){
        log.info("编辑菜品:{}",dishDTO);
        dishService.update(dishDTO);
        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 菜品状态
     * @param status
     * @param id
     * @return
     */

    @PostMapping("/status/{status}")
    @ApiOperation("菜品状态")
    public Result<String> status(@PathVariable Integer status,Long id){
        log.info("编辑状态:{} {}",status,id);
        dishService.status(status,id);
        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result<String> add(@RequestBody DishDTO dishDTO){
        log.info("新增菜品:{}",dishDTO);
        dishService.add(dishDTO);
        //清理缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);
        return Result.success();
    }

    /**
     * 删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除菜品")
    public Result<String> delete(@RequestParam List<Long> ids){
        log.info("删除菜品:{}",ids);
        dishService.delete(ids);
        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 根据分类ID查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类ID查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 清理缓存数据
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
