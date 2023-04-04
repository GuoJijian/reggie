package com.guojijian.reggie.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.reggie.commons.R;
import com.guojijian.reggie.commons.contants.Contants;
import com.guojijian.reggie.dto.DishDto;
import com.guojijian.reggie.pojo.Category;
import com.guojijian.reggie.pojo.Dish;
import com.guojijian.reggie.pojo.DishFlavor;
import com.guojijian.reggie.service.CategoryService;
import com.guojijian.reggie.service.DishService;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
    public R createDish(@RequestBody DishDto dishDto, HttpSession session){
        //封装参数
        dishDto.setCreateTime(LocalDateTime.now());
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setCreateUser((Long) session.getAttribute(Contants.SESSION_USER));
        dishDto.setUpdateUser((Long) session.getAttribute(Contants.SESSION_USER));

        dishService.createDish(dishDto);
        return R.success("创建成功！");
    }

    @GetMapping("/{id}")
    public R queryDish(@PathVariable Long id){

        return R.success(dishService.queryDishById(id));
    }

    @PutMapping
    public R alterDish(@RequestBody DishDto dishDto,HttpSession session){
        //封装参数
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setUpdateUser((Long) session.getAttribute(Contants.SESSION_USER));
        dishDto.getFlavors().stream().map((dishFlavor)->{
            dishFlavor.setDishId(dishDto.getId());
            dishFlavor.setCreateTime(LocalDateTime.now());
            dishFlavor.setCreateUser((Long) session.getAttribute(Contants.SESSION_USER));
            dishFlavor.setUpdateTime(LocalDateTime.now());
            dishFlavor.setUpdateUser((Long) session.getAttribute(Contants.SESSION_USER));
            return dishFlavor;
        }).collect(Collectors.toList());
        //修改菜品信息
        dishService.alterDish(dishDto);
        return R.success("修改成功！");
    }

    @PostMapping("/status/{status}")
    public R removed(@PathVariable int status,Long[] ids,HttpSession session){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("status",status);
        map.put("ids",ids);
        map.put("updateTime",LocalDateTime.now());
        map.put("updateUser",(Long)session.getAttribute(Contants.SESSION_USER));
        //修改菜品信息
        dishService.alterDishStatusForBatchByIds(map);

        return R.success("菜品停售成功！");
    }
}
