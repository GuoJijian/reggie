package com.guojijian.reggie.mapper;

import com.guojijian.reggie.pojo.Dish;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DishMapper {
    //根据分类id查询菜品
    int selectDishByCategoryId(Long categoryId);

    //根据条件分页查询
    List<Dish> selectDishForPageByCondition(Map<String,Object> map);

    //添加菜品
    Dish insertDish(Dish dish);
}
