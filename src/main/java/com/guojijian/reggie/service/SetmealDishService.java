package com.guojijian.reggie.service;

import com.guojijian.reggie.pojo.SetmealDish;

import java.util.List;

public interface SetmealDishService {
    int createSetmealDishBatch(List<SetmealDish> setmealDishList);

    List<SetmealDish> querySetmealDishBySetmealId(String setmealId);

    int dropSetmealDishBatch(List<String> setmealId);
}
