package com.guojijian.reggie.service.impl;

import com.guojijian.reggie.mapper.SetmealDishMapper;
import com.guojijian.reggie.pojo.SetmealDish;
import com.guojijian.reggie.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("setmealDishService")
public class SetmealDishServiceImpl implements SetmealDishService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public int createSetmealDishBatch(List<SetmealDish> setmealDishList) {
        return setmealDishMapper.insertSetmealDishBatch(setmealDishList);
    }

    @Override
    public List<SetmealDish> querySetmealDishBySetmealId(String setmealId) {
        return setmealDishMapper.selectSetmealDishBySetmealId(setmealId);
    }

    @Override
    public int dropSetmealDishBatch(List<String> setmealId) {
        return setmealDishMapper.deleteSetmealDishBatchBySetmealId(setmealId);
    }
}
