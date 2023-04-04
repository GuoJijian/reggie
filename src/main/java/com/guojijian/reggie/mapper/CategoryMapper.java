package com.guojijian.reggie.mapper;

import com.guojijian.reggie.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    List<Category> selectCategoryForPage();

    int insertCategory(Category category);

    int updateCategory(Category category);

    int deleteCategory(Long ids);

    Category selectCategoryById(Long id);

    List<Category> selectCategoryByType(Long type);
}
