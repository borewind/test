package com.zxtechai.service.impl;

import com.Contract.SetmealContract;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zxtechai.Contract.SetmealContractDTO;
import com.zxtechai.constant.MessageConstant;
import com.zxtechai.constant.StatusConstant;
import com.zxtechai.dto.SetmealDTO;
import com.zxtechai.dto.SetmealPageQueryDTO;
import com.zxtechai.entity.Setmeal;
import com.zxtechai.entity.SetmealDish;
import com.zxtechai.exception.DeletionNotAllowedException;
import com.zxtechai.exception.SetmealEnableFailedException;
import com.zxtechai.mapper.DishMapper;
import com.zxtechai.mapper.SetmealDishMapper;
import com.zxtechai.mapper.SetmealMapper;
import com.zxtechai.result.PageResult;
import com.zxtechai.service.SetmealService;
import com.zxtechai.vo.DishItemVO;
import com.zxtechai.vo.DishVO;
import com.zxtechai.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService{
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;
    /**
     * 新增菜品
     * @param setmealDTO
     */
    @Override
    @Transactional//开启事务回滚
    public void add(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        SetmealContract setmealContract = new SetmealContract();
        SetmealContractDTO setmealContractDTO = new SetmealContractDTO();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        BeanUtils.copyProperties(setmealDTO,setmealContractDTO);
        setmealMapper.add(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && setmealDishes.size() > 0){
            for (SetmealDish dish : setmealDishes) {
                dish.setSetmealId(setmeal.getId());
                setmealDishMapper.add(dish);
            }
        }
        try {
            setmealContract.addSetmeal(setmealContractDTO);
        } catch (Exception e) {
            // 区块链操作失败，事务会自动回滚
            e.printStackTrace();
        }



    }

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> pages = setmealMapper.page(setmealPageQueryDTO);
        return new PageResult(pages.getTotal(),pages.getResult());
    }

    /**
     * 根据ID回显套餐
     * @param id
     * @return
     */
    @Override
    public SetmealVO getById(Long id) {
        SetmealVO setmealVO = setmealMapper.getById(id);
        List<SetmealDish> list = setmealDishMapper.getBySetmealId(id);
        setmealVO.setSetmealDishes(list);
        return setmealVO;
    }

    /**
     * 套餐编辑
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealMapper.update(setmeal);
        if(setmealDishes != null && setmealDishes.size() > 0){
            setmealDishMapper.delete(setmealDTO.getId());
            setmealDishes.forEach(res->{
                res.setSetmealId(setmealDTO.getId());
                setmealDishMapper.add(res);
            });
        }

    }

    /**
     * 修改状态
     * @param status
     * @param id
     */
    @Override
    public void status(Long id, Integer status) {
        if(status == StatusConstant.ENABLE){
            List<SetmealDish> bySetmealId = setmealDishMapper.getBySetmealId(id);
            for (SetmealDish setmealDish : bySetmealId) {
                DishVO dish = dishMapper.dish(setmealDish.getDishId());
                if(dish.getStatus() == StatusConstant.DISABLE){
                    //套餐内包含未启售菜品，无法启售
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
        }
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        setmeal.setId(id);
        setmealMapper.update(setmeal);

    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Override
    @Transactional
    public void delete(List<Long> ids) {
        //起售中的套餐不能删除
        for (Long id : ids) {
            SetmealVO byId = setmealMapper.getById(id);
            if(byId.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        //删除套餐关联表里的套餐菜品
        for (Long id : ids) {
            setmealDishMapper.delete(id);
        }
        //删除套餐
        setmealMapper.delete(ids);

    }

    /**
     * 根据分类id查询套餐
     * @param setmeal
     * @return
     */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据套餐id查询包含的菜品列表
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
