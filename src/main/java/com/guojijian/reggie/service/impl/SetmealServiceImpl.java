package com.guojijian.reggie.service.impl;

import com.guojijian.reggie.mapper.SetmealMapper;
import com.guojijian.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("setmealService")
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public int querySetmealByCategoryId(Long categoryId) {
        return setmealMapper.selectSetmealCountByCategoryId(categoryId);
    }
}
