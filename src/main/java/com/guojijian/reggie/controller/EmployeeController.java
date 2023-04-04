package com.guojijian.reggie.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.reggie.commons.R;
import com.guojijian.reggie.commons.contants.Contants;
import com.guojijian.reggie.pojo.Employee;
import com.guojijian.reggie.service.EmployeeService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.Addressing;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R login(@RequestBody Employee employee,HttpSession session){
        Employee e = employeeService.login(employee);
        if(e==null){
            return R.error("账号错误！");
        }

        //将页面提交的密码password进行md5加密处理
        String password=DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        if(!e.getPassword().equals(password)){
            return R.error("密码错误！");
        }
        if(e.getStatus()==0){
            return R.error("账号已锁定！");
        }

        //将用户id保存至session域对象中
        session.setAttribute(Contants.SESSION_USER,e.getId());

        return R.success(e);
    }

    @PostMapping("/logout")
    public R logout(HttpSession session){

        session.removeAttribute(Contants.SESSION_USER);

        return R.success("退出成功！");
    }

    @PostMapping
    public R createEmployee(@RequestBody Employee employee, HttpSession session){
        //封装参数
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser((Long) session.getAttribute(Contants.SESSION_USER));
        employee.setUpdateUser((Long) session.getAttribute(Contants.SESSION_USER));

        //创建员工信息
        employeeService.createEmployee(employee);

        return R.success("添加员工成功！");
    }

    @GetMapping("/page")
    public R page(String name,int pageNum,int pageSize){
        //封装参数
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);

        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Employee> employeeList=employeeService.queryEmployeeForPageByCondition(map);
        PageInfo<Employee> page=new PageInfo<>(employeeList,5);

        //封装返回结果
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("records",employeeList);
        resultMap.put("total",(int)page.getTotal());

        return R.success(resultMap);
    }

    @PutMapping
    public R alertEmployee(@RequestBody Employee employee,HttpSession session){
        //封装参数
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) session.getAttribute(Contants.SESSION_USER));
        //更改员工信息
        employeeService.alterEmployee(employee);

        return R.success("修改成功！");
    }

    @GetMapping("/{id}")
    public R queryEmployeeById(@PathVariable Long id){
        //根据id查询员工信息
        Employee employee=employeeService.queryEmployeeById(id);
        if(employee==null){
            return R.error("查询失败！");
        }

        return R.success(employee);
    }
}
