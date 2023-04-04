package com.guojijian.reggie.service.impl;

import com.guojijian.reggie.mapper.DishFlavorMapper;
import com.guojijian.reggie.pojo.DishFlavor;
import com.guojijian.reggie.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dishFlavorService")
public class DishFlavorServiceImpl implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public int createDishFlavorBatch(List<DishFlavor> dishFlavors) {
        return dishFlavorMapper.insertDishFlavorBatch(dishFlavors);
    }

    @Override
    public List<DishFlavor> queryDishFlavorByDishId(Long dishId) {
        return dishFlavorMapper.selectDishFlavorByDishId(dishId);
    }

    @Override
    public int dropDishFlavorBatchByDishId(Long dishId) {
        return dishFlavorMapper.deleteDishFlavorBatchByDishId(dishId);
    }
}
