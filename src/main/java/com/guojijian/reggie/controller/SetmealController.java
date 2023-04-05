package com.guojijian.reggie.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.reggie.commons.R;
import com.guojijian.reggie.commons.contants.Contants;
import com.guojijian.reggie.dto.SetmealDto;
import com.guojijian.reggie.pojo.Category;
import com.guojijian.reggie.pojo.Setmeal;
import com.guojijian.reggie.service.CategoryService;
import com.guojijian.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 创建套餐
     * @return
     */
    @PostMapping
    public R createSetmeal(@RequestBody SetmealDto setmealDto, HttpSession session){
        //封装参数
        setmealDto.setCreateTime(LocalDateTime.now());
        setmealDto.setCreateUser((Long) session.getAttribute(Contants.SESSION_USER));
        setmealDto.setUpdateTime(LocalDateTime.now());
        setmealDto.setUpdateUser((Long) session.getAttribute(Contants.SESSION_USER));
        //调用service层方法，创建套餐信息
        setmealService.createSetmeal(setmealDto);

        return R.success("新建套餐成功！");
    }

    @GetMapping("/page")
    public R page(Integer pageNum,Integer pageSize,String name){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Setmeal> setmealList=setmealService.querySetmealForPageByCondition(map);
        //二次封装，便于前端显示数据
        List<SetmealDto> setmealDtos=setmealList.stream().map((setmeal)->{
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(setmeal,setmealDto);
            Category category=categoryService.queryCategoryById(setmealDto.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());

        PageInfo page=new PageInfo(setmealDtos);
        //封装查询结果，返回响应信息
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("records",setmealDtos);
        resultMap.put("total",page.getTotal());

        return R.success(resultMap);
    }

    @GetMapping("/{id}")
    public R querySetmealById(@PathVariable Long id){
        return R.success(setmealService.querySetmealById(id));
    }

    @DeleteMapping
    public R dropSetmeal(@RequestParam List<Long> ids) throws Exception {
        setmealService.dropSetmealBatchByIds(ids);
        return R.success("套餐删除成功！");
    }

    @PostMapping("/status/{status}")
    public R alterSetmealStatus(@PathVariable int status,@RequestParam List<Long> ids,HttpSession session){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("status",status);
        map.put("ids",ids);
        map.put("updateTime",LocalDateTime.now());
        map.put("updateUser",session.getAttribute(Contants.SESSION_USER));
        //批量修改套餐状态
        setmealService.alterSetmealStatus(map);

        return R.success("套餐状态修改成功！");
    }
}
