package com.guojijian.reggie.service.impl;

import com.guojijian.reggie.mapper.DishMapper;
import com.guojijian.reggie.pojo.Dish;
import com.guojijian.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("dishService")
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public int queryDishByCategoryId(Long categoryId) {
        return dishMapper.selectDishByCategoryId(categoryId);
    }

    @Override
    public List<Dish> queryDishForPageByCondition(Map<String, Object> map) {
        return dishMapper.selectDishForPageByCondition(map);
    }

    @Override
    public Dish createDish(Dish dish) {
        return dishMapper.insertDish(dish);
    }
}
