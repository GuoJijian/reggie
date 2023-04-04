package com.guojijian.reggie.mapper;

import org.springframework.stereotype.Repository;

@Repository
public interface SetmealMapper {
    //根据分类id查询套餐数量
    int selectSetmealCountByCategoryId(Long categoryId);
}
