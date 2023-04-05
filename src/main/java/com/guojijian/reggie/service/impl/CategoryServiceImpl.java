package com.guojijian.reggie.service.impl;

import com.guojijian.reggie.commons.R;
import com.guojijian.reggie.commons.exception.AddException;
import com.guojijian.reggie.commons.exception.DeleteException;
import com.guojijian.reggie.commons.exception.UpdateException;
import com.guojijian.reggie.mapper.CategoryMapper;
import com.guojijian.reggie.pojo.Category;
import com.guojijian.reggie.pojo.Dish;
import com.guojijian.reggie.service.CategoryService;
import com.guojijian.reggie.service.DishService;
import com.guojijian.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public List<Category> queryCategoryForPage() {
        return categoryMapper.selectCategoryForPage();
    }

    @Override
    public void saveCategory(Category category) throws AddException {
        int result=categoryMapper.insertCategory(category);
        if(result!=1){
            throw new AddException("添加分类信息失败！");
        }
    }

    @Override
    public void alterCategory(Category category) throws UpdateException {
        int result=categoryMapper.updateCategory(category);
        if(result!=1){
            throw new UpdateException("修改分类信息失败！");
        }
    }

    @Override
    public void dropCategory(Long ids) throws DeleteException {
        List<Dish> dishList = dishService.queryDishByCategoryId(ids);

        if(dishList.size()>0){
            throw new DeleteException("根据ids删除分类信息失败，存在关联的菜品");
        }

        if(setmealService.querySetmealByCategoryId(ids)>0){
            throw new DeleteException("根据ids删除分类信息失败，存在关联的套餐");
        }


        int result=categoryMapper.deleteCategory(ids);
        if (result!=1){
            throw new DeleteException("根据ids删除分类信息失败，未匹配到信息");
        }
    }

    @Override
    public Category queryCategoryById(Long id) {
        return categoryMapper.selectCategoryById(id);
    }

    @Override
    public List<Category> queryCategoryByType(Long type) {
        return categoryMapper.selectCategoryByType(type);
    }
}
