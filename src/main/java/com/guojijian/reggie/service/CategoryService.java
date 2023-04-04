package com.guojijian.reggie.service;

import com.guojijian.reggie.commons.exception.AddException;
import com.guojijian.reggie.commons.exception.DeleteException;
import com.guojijian.reggie.commons.exception.UpdateException;
import com.guojijian.reggie.pojo.Category;

import java.util.List;

public interface CategoryService {
    //分页查询
    List<Category> queryCategoryForPage();

    //添加分类信息
    void saveCategory(Category category) throws AddException;

    //修改分类信息
    void alterCategory(Category category) throws UpdateException;

    //根据ids删除分类信息
    void dropCategory(Long ids) throws DeleteException;

    //根据id查询分类信息
    Category queryCategoryById(Long id);

    //根据类型查询分类信息
    List<Category> queryCategoryByType(Long type);
}
