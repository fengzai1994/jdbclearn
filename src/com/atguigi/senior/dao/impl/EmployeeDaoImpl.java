package com.atguigi.senior.dao.impl;

import com.atguigi.senior.dao.BaseDAO;
import com.atguigi.senior.dao.EmployeDao;
import com.atguigi.senior.pojo.Employee;

import java.sql.SQLException;
import java.util.List;


public class EmployeeDaoImpl extends BaseDAO implements EmployeDao {
    @Override
    public List<Employee> selectALL() {
        try {
            String sql = "SELECT emp_id empId, emp_name empName, emp_salary empSalary, emp_age empAge FROM t_emp";
            return executeQuery(Employee.class, sql, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee selectByEmpId(Integer empId) {
        try {
            String sql = "SELECT emp_id empId, emp_name empName, emp_salary empSalary, emp_age empAge FROM t_emp WHERE emp_id = ?";
            return executeQueryBean(Employee.class, sql, 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Employee employee) {
        try {
            String sql = "insert into t_emp(emp_name, emp_salary, emp_age) values(?,?,?)";
            return executeUpdate(sql, employee.getEmpName(), employee.getEmpSalary(), employee.getEmpAge());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Employee employee) {
        try {
            String sql = "update t_emp set emp_salary = ? where emp_id = ?";
            return executeUpdate(sql, employee.getEmpSalary(), employee.getEmpId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Integer empId) {
        try {
            String sql = "delete from t_emp where emp_id = ?";
            return executeUpdate(sql, empId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
