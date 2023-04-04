package com.guojijian.reggie.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.reggie.commons.R;
import com.guojijian.reggie.dto.DishDto;
import com.guojijian.reggie.pojo.Category;
import com.guojijian.reggie.pojo.Dish;
import com.guojijian.reggie.service.CategoryService;
import com.guojijian.reggie.service.DishService;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R page(Integer pageNum,Integer pageSize,String name){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);

        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Dish> dishList=dishService.queryDishForPageByCondition(map);
        //遍历查询结果，封装属性
        List<DishDto> dishDtoList=dishList.stream().map((item)->{
            //将dish对象复制给dishDto对象
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(item,dishDto,"categoryId");
            //根据id查询分类名称
            Category category=categoryService.queryCategoryById(item.getCategoryId());
            dishDto.setCategoryName(category.getName());

            return dishDto;
        }).collect(Collectors.toList());

        PageInfo page=new PageInfo(dishList);

        //封装查询结果，返回响应信息
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("records",dishDtoList);
        resultMap.put("total",(int)page.getTotal());

        return R.success(resultMap);
    }

    @PostMapping
    public R createDish(@RequestBody DishDto dishDto){
        return R.success(null);
    }
}
