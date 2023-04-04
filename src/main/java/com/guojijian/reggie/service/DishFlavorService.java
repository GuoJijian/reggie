package com.guojijian.reggie.service;

import com.guojijian.reggie.pojo.DishFlavor;

import java.util.List;

public interface DishFlavorService {
    /**
     * 批量添加口味信息
     * @param dishFlavors
     * @return
     */
    int createDishFlavorBatch(List<DishFlavor> dishFlavors);

    /**
     * 根据菜品id查询口味
     * @param dishId
     * @return
     */
    List<DishFlavor> queryDishFlavorByDishId(Long dishId);

    /**
     * 根据菜品id批量删除口味
     * @param dishId
     * @return
     */
    int dropDishFlavorBatchByDishId(Long dishId);
}
