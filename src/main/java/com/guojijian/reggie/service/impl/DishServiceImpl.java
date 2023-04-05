package com.guojijian.reggie.service.impl;

import com.guojijian.reggie.dto.DishDto;
import com.guojijian.reggie.mapper.DishMapper;
import com.guojijian.reggie.pojo.Dish;
import com.guojijian.reggie.pojo.DishFlavor;
import com.guojijian.reggie.service.DishFlavorService;
import com.guojijian.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("dishService")
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public List<Dish> queryDishByCategoryId(Long categoryId) {
        return dishMapper.selectDishByCategoryId(categoryId);
    }

    @Override
    public List<Dish> queryDishForPageByCondition(Map<String, Object> map) {
        return dishMapper.selectDishForPageByCondition(map);
    }

    @Override
    @Transactional
    public void createDish(DishDto dishDto) {
        //创建菜品
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDto,dish);
        dishMapper.insertDish(dish);
        //创建口味信息
        List<DishFlavor> dishFlavors=dishDto.getFlavors();
        List<DishFlavor> dishFlavorList=dishFlavors.stream().map((flavor)->{
            flavor.setDishId(dish.getId());
            flavor.setCreateTime(LocalDateTime.now());
            flavor.setCreateUser(dishDto.getCreateUser());
            flavor.setUpdateTime(LocalDateTime.now());
            flavor.setUpdateUser(dishDto.getUpdateUser());
            return flavor;
        }).collect(Collectors.toList());
        dishFlavorService.createDishFlavorBatch(dishFlavorList);
    }

    @Override
    public Dish queryDishById(Long id) {
        //根据id查询菜品
        Dish dish=dishMapper.selectDishById(id);
        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //根据菜品id查询口味
        dishDto.setFlavors(dishFlavorService.queryDishFlavorByDishId(id));

        return dishDto;
    }

    @Override
    @Transactional
    public void alterDish(Dish dish) {
        //修改菜品信息
        dishMapper.updateDish(dish);
        //修改口味信息
        DishDto dishDto=(DishDto) dish;
        dishFlavorService.dropDishFlavorBatchByDishId(dish.getId());
        dishFlavorService.createDishFlavorBatch(dishDto.getFlavors());
    }

    @Override
    public void alterDishStatusForBatchByIds(Map<String, Object> map) {
        dishMapper.updateDishStatusForBatchByIds(map);
    }
}
