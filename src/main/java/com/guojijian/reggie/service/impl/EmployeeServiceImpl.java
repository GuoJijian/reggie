package com.guojijian.reggie.service.impl;

import com.guojijian.reggie.mapper.EmployeeMapper;
import com.guojijian.reggie.pojo.Employee;
import com.guojijian.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(Employee employee) {
        return employeeMapper.selectEmployeeByUserName(employee);
    }

    @Override
    public void createEmployee(Employee employee) {
        employeeMapper.insertEmployee(employee);
    }

    @Override
    public List<Employee> queryEmployeeForPageByCondition(Map<String, Object> map) {
        return employeeMapper.selectEmployeeForPageByCondition(map);
    }

    @Override
    public void alterEmployee(Employee employee) {
        employeeMapper.updateEmployee(employee);
    }

    @Override
    public Employee queryEmployeeById(Long id) {
        return employeeMapper.selectEmployeeById(id);
    }
}
