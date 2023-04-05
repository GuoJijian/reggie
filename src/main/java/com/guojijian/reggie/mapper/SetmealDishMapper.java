package com.guojijian.reggie.mapper;

import com.guojijian.reggie.pojo.SetmealDish;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetmealDishMapper {
    int insertSetmealDishBatch(List<SetmealDish> setmealDishes);

    List<SetmealDish> selectSetmealDishBySetmealId(String setmealId);

    int deleteSetmealDishBatchBySetmealId(List<String> setmealId);
}
