package com.guojijian.reggie.service;

import com.guojijian.reggie.dto.DishDto;
import com.guojijian.reggie.pojo.Dish;

import java.util.List;
import java.util.Map;

public interface DishService {
    List<Dish> queryDishByCategoryId(Long categoryId);

    List<Dish> queryDishForPageByCondition(Map<String,Object> map);

    void createDish(DishDto dishDto);

    Dish queryDishById(Long id);

    void alterDish(Dish dish);

    void alterDishStatusForBatchByIds(Map<String,Object> map);
}
