package com.guojijian.reggie.mapper;

import com.guojijian.reggie.pojo.Dish;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DishMapper {
    //根据分类id查询菜品
    List<Dish> selectDishByCategoryId(Long categoryId);

    //根据条件分页查询
    List<Dish> selectDishForPageByCondition(Map<String,Object> map);

    //添加菜品
    int insertDish(Dish dish);

    //根据id查询菜品
    Dish selectDishById(Long id);

    //根据id修改菜品信息
    int updateDish(Dish dish);

    //根据ids批量修改菜品状态
    int updateDishStatusForBatchByIds(Map<String,Object> map);

}
