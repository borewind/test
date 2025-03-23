package com.zxtechai.service.impl;

import com.Contract.DishContract;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zxtechai.Contract.DishContractDTO;
import com.zxtechai.constant.MessageConstant;
import com.zxtechai.constant.StatusConstant;
import com.zxtechai.dto.DishDTO;
import com.zxtechai.dto.DishPageQueryDTO;
import com.zxtechai.entity.Dish;
import com.zxtechai.entity.DishFlavor;
import com.zxtechai.exception.DeletionNotAllowedException;
import com.zxtechai.mapper.DishFlavorMapper;
import com.zxtechai.mapper.DishMapper;
import com.zxtechai.mapper.SetmealDishMapper;
import com.zxtechai.result.PageResult;
import com.zxtechai.service.DishService;
import com.zxtechai.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.page(dishPageQueryDTO);
        //拼接口味数据
        page.stream().forEach(dish->{
            List<DishFlavor> list = dishFlavorMapper.list(dish.getId());
            dish.setFlavors(list);
        });
        //拼接口味数据
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 编辑菜品回显查询
     * @param id
     * @return
     */
    @Override
    public DishVO dish(Long id) {
        DishVO dishVO = dishMapper.dish(id);
        List<DishFlavor> list = dishFlavorMapper.list(dishVO.getId());
        dishVO.setFlavors(list);
        return dishVO;
    }

    /**
     * 编辑菜品
     * @param dishDTO
     */
    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        dishFlavorMapper.delete(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors.size() > 0 && flavors != null){
            flavors.stream().forEach(res->res.setDishId(dishDTO.getId()));
            dishFlavorMapper.add(flavors);
        }
    }

    /**
     * 菜品状态
     * @param status
     * @param id
     */
    @Override
    public void status(Integer status, Long id) {
        dishMapper.status(status,id);
    }

    /**
     * 新增菜品
     *
     * 此方法用于添加新的菜品信息到系统中，包括菜品的基本信息和口味信息
     * 如果菜品有口味信息且不为空，则会将口味信息也添加到系统中
     * 此外，还会尝试通过区块链服务将菜品信息添加到区块链上，以保证菜品信息的透明性和可追溯性
     *
     * @param dishDTO 要添加的菜品信息，包括菜品的基本信息和口味信息
     */
    @Override
    @Transactional//事务未成功回滚
    public void add(DishDTO dishDTO) {
        // 创建菜品合约对象，用于与区块链交互
        DishContract dishContract = new DishContract();
        // 创建菜品合约DTO对象，用于与区块链交互的数据传输
        DishContractDTO dishContractDTO = new DishContractDTO();
        // 创建菜品对象，用于存储菜品的基本信息
        Dish dish = new Dish();
        // 将传入的菜品信息复制到菜品对象中
        BeanUtils.copyProperties(dishDTO,dish);
        BeanUtils.copyProperties(dishDTO,dishContractDTO);
        // 调用Mapper方法将菜品信息添加到数据库中
        dishMapper.add(dish);

        // 获取菜品的口味信息列表
        List<DishFlavor> flavors = dishDTO.getFlavors();
        // 如果口味信息列表不为空且长度大于0，则将每个口味信息的菜品ID设置为当前添加的菜品ID，并添加到数据库中
        if(flavors != null && flavors.size() > 0){
            flavors.stream().forEach(res->res.setDishId(dish.getId()));
            dishFlavorMapper.add(flavors);
        }
        try {
            // 通过区块链服务将菜品信息添加到区块链
            dishContract.add(dishContractDTO);
        } catch (Exception e) {
            // 区块链操作失败，事务会自动回滚
            e.printStackTrace();
        }
    }

    /**
     * 删除菜品
     * @param ids
     */
    @Override
    @Transactional
    public void delete(List<Long> ids) {
        //"起售中的菜品不能删除
        for (Long id : ids) {
            DishVO dish = dishMapper.dish(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //当前菜品关联了套餐,不能删除
        List<Long> dishList = setmealDishMapper.getDishList(ids);
        if(dishList != null && dishList.size() > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        ids.stream().forEach(res->dishFlavorMapper.delete(res));//删除所有菜品口味
        dishMapper.delete(ids);//单个或批量删除菜品
    }

    @Override
    public List<Dish> list(Long categoryId) {
        List<Dish> list = dishMapper.list(categoryId);
        return list;
    }

    /**
     * 根据分类id查询菜品
     * @param dish
     * @return
     */
    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list1(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

}
