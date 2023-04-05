package com.guojijian.reggie.service.impl;

import com.guojijian.reggie.commons.exception.MesException;
import com.guojijian.reggie.dto.SetmealDto;
import com.guojijian.reggie.mapper.SetmealDishMapper;
import com.guojijian.reggie.mapper.SetmealMapper;
import com.guojijian.reggie.pojo.Setmeal;
import com.guojijian.reggie.pojo.SetmealDish;
import com.guojijian.reggie.service.SetmealDishService;
import com.guojijian.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("setmealService")
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public int querySetmealByCategoryId(Long categoryId) {
        return setmealMapper.selectSetmealCountByCategoryId(categoryId);
    }

    /**
     * 创建套餐信息
     * @param setmeal
     */
    @Override
    @Transactional
    public void createSetmeal(Setmeal setmeal) {
        //创建套餐信息
        setmealMapper.insertSetmeal(setmeal);
        //创建关联的套餐-菜品信息
        SetmealDto setmealDto=(SetmealDto)setmeal;
        List<SetmealDish> setmealDishList=setmealDto.getSetmealDishes();
        setmealDishList.stream().map((setmealDish)->{
            setmealDish.setSetmealId(setmeal.getId());
            setmealDish.setCreateTime(LocalDateTime.now());
            setmealDish.setCreateUser(setmeal.getCreateUser());
            setmealDish.setUpdateTime(LocalDateTime.now());
            setmealDish.setUpdateUser(setmeal.getUpdateUser());
            return setmealDish;
        }).collect(Collectors.toList());
        setmealDishService.createSetmealDishBatch(setmealDishList);
    }

    /**
     * 分页查询套餐信息
     * @param map
     * @return
     */
    @Override
    public List<Setmeal> querySetmealForPageByCondition(Map<String, Object> map) {
        return setmealMapper.selectSetmealForPageByCondition(map);
    }

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    @Override
    public Setmeal querySetmealById(Long id) {
        Setmeal setmeal=setmealMapper.selectSetmealById(id);
        SetmealDto setmealDto=new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        //注意：此处setmeal_dish表中setmealId是VARCHAR类型，不能用Long数据进行查询
        setmealDto.setSetmealDishes(setmealDishService.querySetmealDishBySetmealId(setmealDto.getId()+""));
        return setmealDto;
    }

    /**
     * 根据ids批量删除套餐信息
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public void dropSetmealBatchByIds(List<Long> ids) throws Exception {
        //判断套餐是否停售
        if(setmealMapper.selectSetmealForSoldByIds(ids)>0){
            throw new MesException("套餐删除失败，起售套餐不可删除！");
        }
        //批量删除套餐
        setmealMapper.deleteSetmealBatchByIds(ids);
        //批量删除套餐关联的setmeal_dish
        List<String> idList=ids.stream().map((id)->{
            String i=id+"";
            return i;
        }).collect(Collectors.toList());
        setmealDishService.dropSetmealDishBatch(idList);
    }

    @Override
    public int alterSetmealStatus(Map<String, Object> map) {
        return setmealMapper.updateSetmealStatus(map);
    }
}
