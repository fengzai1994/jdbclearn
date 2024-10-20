package com.atguigi.senior.dao;

import com.atguigi.senior.pojo.Employee;

import java.util.List;

/**
 * EmployeeDao 这个类对应的是t_emp这张表的增删改查的操作
 */
public interface EmployeDao {

    List<Employee> selectALL();

    Employee selectByEmpId(Integer empId);

    int insert(Employee employee);

    int update(Employee employee);

    int delete(Integer empId);
}
