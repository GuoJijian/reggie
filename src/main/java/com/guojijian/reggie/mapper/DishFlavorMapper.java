package com.guojijian.reggie.mapper;

import com.guojijian.reggie.pojo.DishFlavor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishFlavorMapper {
    //批量添加口味信息
    int insertDishFlavorBatch(List<DishFlavor> dishFlavors);

    //根据菜品id查询口味信息
    List<DishFlavor> selectDishFlavorByDishId(Long dishId);

    //根据菜品id批量删除口味信息
    int deleteDishFlavorBatchByDishId(Long dishId);
}
