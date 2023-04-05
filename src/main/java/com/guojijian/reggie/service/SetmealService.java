package com.guojijian.reggie.service;

import com.guojijian.reggie.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    int querySetmealByCategoryId(Long categoryId);

    void createSetmeal(Setmeal setmeal);

    List<Setmeal> querySetmealForPageByCondition(Map<String,Object> map);

    Setmeal querySetmealById(Long id);

    void dropSetmealBatchByIds(List<Long> ids) throws Exception;

    int alterSetmealStatus(Map<String,Object> map);
}
