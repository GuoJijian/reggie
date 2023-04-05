package com.guojijian.reggie.mapper;

import com.guojijian.reggie.pojo.Setmeal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SetmealMapper {
    //根据分类id查询套餐数量
    int selectSetmealCountByCategoryId(Long categoryId);

    //添加套餐信息
    int insertSetmeal(Setmeal setmeal);

    //分页查询套餐
    List<Setmeal> selectSetmealForPageByCondition(Map<String,Object> map);

    //根据id查询套餐信息
    Setmeal selectSetmealById(Long id);

    //根据ids查询套餐起售数量
    int selectSetmealForSoldByIds(List<Long> ids);

    //根据ids批量删除套餐信息
    int deleteSetmealBatchByIds(List<Long> ids);

    //批量修改套餐状态
    int updateSetmealStatus(Map<String,Object> map);
}
