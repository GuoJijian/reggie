package com.guojijian.reggie.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.reggie.commons.R;
import com.guojijian.reggie.commons.contants.Contants;
import com.guojijian.reggie.commons.exception.AddException;
import com.guojijian.reggie.commons.exception.DeleteException;
import com.guojijian.reggie.commons.exception.UpdateException;
import com.guojijian.reggie.pojo.Category;
import com.guojijian.reggie.service.CategoryService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R page(Integer pageNum,Integer pageSize){
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Category> categoryList=categoryService.queryCategoryForPage();
        PageInfo<Category> page=new PageInfo<>(categoryList);
        //封装查询结果，返回响应信息
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("records",categoryList);
        resultMap.put("total",(int)page.getTotal());

        return R.success(resultMap);
    }

    @PostMapping
    public R saveCategory(@RequestBody Category category,HttpSession session) throws AddException {
        //封装参数
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser((Long) session.getAttribute(Contants.SESSION_USER));
        category.setUpdateUser((Long) session.getAttribute(Contants.SESSION_USER));
        //添加分类信息
        categoryService.saveCategory(category);

        return R.success("添加成功！");
    }

    @PutMapping
    public R alterCategory(@RequestBody Category category,HttpSession session) throws UpdateException {
        //封装参数
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser((Long) session.getAttribute(Contants.SESSION_USER));
        //修改分类信息
        categoryService.alterCategory(category);

        return R.success("修改成功！");
    }

    @DeleteMapping
    public R dropCategory(Long ids) throws DeleteException {

        categoryService.dropCategory(ids);

        return R.success("删除成功！");
    }

    @GetMapping("/list")
    public R queryCategoryByType(Long type){

        List<Category> categoryList = categoryService.queryCategoryByType(type);

        return R.success(categoryList);
    }
}
