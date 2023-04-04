package com.guojijian.reggie.service;

import com.guojijian.reggie.pojo.Dish;

import java.util.List;
import java.util.Map;

public interface DishService {
    int queryDishByCategoryId(Long categoryId);

    List<Dish> queryDishForPageByCondition(Map<String,Object> map);

    Dish createDish(Dish dish);
}
