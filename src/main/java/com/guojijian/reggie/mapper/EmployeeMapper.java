package com.guojijian.reggie.mapper;

import com.guojijian.reggie.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeMapper {
    Employee selectEmployeeByUserName(Employee employee);

    int insertEmployee(Employee employee);

    List<Employee> selectEmployeeForPageByCondition(Map<String,Object> map);

    int updateEmployee(Employee employee);

    Employee selectEmployeeById(Long id);
}
