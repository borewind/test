package com.zxtechai.controller.admin;

import com.zxtechai.dto.SetmealDTO;
import com.zxtechai.dto.SetmealPageQueryDTO;
import com.zxtechai.result.PageResult;
import com.zxtechai.result.Result;
import com.zxtechai.service.SetmealService;
import com.zxtechai.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "套餐相关接口")
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")//key: setmealCache::100
    public Result add(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐:{}",setmealDTO);
        setmealService.add(setmealDTO);
        return Result.success();
    }

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询:{}",setmealPageQueryDTO);
        PageResult pageResult = setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据ID回显套餐
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据ID回显套餐")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("根据ID查询套餐:{}",id);
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    /**
     * 套餐编辑
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("套餐编辑")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result<String> update(@RequestBody SetmealDTO setmealDTO){
        log.info("套餐编辑:{}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * 修改状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改状态 ")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result<String> status(Long id, @PathVariable Integer status){
        log.info("修改状态,{} {}",id,status);
        setmealService.status(id,status);
        return Result.success();
    }

    /**
     * 批量 删除套餐
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result<String> delete(@RequestParam List<Long> ids){
        log.info("批量删除套餐:{}",ids);
        setmealService.delete(ids);
        return Result.success();
    }
}
