package com.guojijian.reggie.service;

import com.guojijian.reggie.pojo.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee login(Employee employee);

    void createEmployee(Employee employee);

    List<Employee> queryEmployeeForPageByCondition(Map<String,Object> map);

    void alterEmployee(Employee employee);

    Employee queryEmployeeById(Long id);
}
