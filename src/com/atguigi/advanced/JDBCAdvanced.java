package com.atguigi.advanced;

import com.atguigi.advanced.pojo.Employee;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAdvanced {

    @Test
    public void testORM() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT emp_id, emp_name, emp_salary, emp_age FROM t_emp WHERE emp_id = ?");

        preparedStatement.setInt(1, 1);

        ResultSet resultSet = preparedStatement.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            employee = new Employee();
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            Double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");

            // 为对象的属性赋值
            employee.setEmpId(empId);
            employee.setEmpName(empName);
            employee.setEmpSalary(empSalary);
            employee.setEmpAge(empAge);
        }

        System.out.println(employee);

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testORMList() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT emp_id, emp_name, emp_salary, emp_age FROM t_emp");
        ResultSet resultSet = preparedStatement.executeQuery();
        Employee employee = null;
        List<Employee> employeeList = new ArrayList<>();

        while (resultSet.next()) {
            employee = new Employee();

            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            Double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");

            // 为对象的属性赋值
            employee.setEmpId(empId);
            employee.setEmpName(empName);
            employee.setEmpSalary(empSalary);
            employee.setEmpAge(empAge);

            employeeList.add(employee);
        }

        employeeList.forEach(System.out::println);

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testReturnPK() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");

        String sql = "insert into t_emp (emp_name, emp_salary, emp_age) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        Employee employee = new Employee(null, "jack", 123.45, 29);
        preparedStatement.setString(1, employee.getEmpName());
        preparedStatement.setDouble(2, employee.getEmpSalary());
        preparedStatement.setInt(3, employee.getEmpAge());

        int result = preparedStatement.executeUpdate();

        ResultSet generatedKeys = null;

        if (result > 0) {
            System.out.println("success");
            // 获取当前新增数据的PK，回显到Java中employee对象的empId属性上
            // 返回的PK是单行单列的结果
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int empId = generatedKeys.getInt(1);
                employee.setEmpId(empId);
            }

            System.out.println(employee);
        } else {
            System.out.println("fail");
        }

        if (generatedKeys != null) {
            generatedKeys.close();
        }
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testMoreInsert() throws SQLException {
        // 1. 注册驱动
        //Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. 获取链接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");

        // 3. 编写SQL语句
        String sql = "insert into t_emp (emp_name, emp_salary, emp_age) VALUES (?,?,?)";

        // 4. 创建预编译的preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 获取当前行代码执行的时间，毫秒值
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            // 5. 直接為佔位符赋值
            preparedStatement.setString(1, "marry" + i);
            preparedStatement.setDouble(2, 100.0 + i);
            preparedStatement.setInt(3, 20 + i);

            preparedStatement.executeUpdate();
        }
        long end = System.currentTimeMillis();

        System.out.println("used time: " + (end - start));

        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testBatch() throws SQLException {
        // 1. 注册驱动
        //Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. 获取链接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?rewriteBatchedStatements=true", "root", "Ff1994427");

        // 3. 编写SQL语句
        /*
            注意：1.必须在链接数据库的URL后面追加？rewriteBatchedStatement=true, 允许批量操作
                2.新增SQL必须用valus。且语句最后不要追加；结束
                3.调用addBatch()方法，将SQL语句进行批量添加操作
                4.统一执行批量操作，调用preparedStatement.executeBatch()
         */
        String sql = "insert into t_emp (emp_name, emp_salary, emp_age) VALUES (?,?,?)";

        // 4. 创建预编译的preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 获取当前行代码执行的时间，毫秒值
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            // 5. 直接為佔位符赋值
            preparedStatement.setString(1, "marry" + i);
            preparedStatement.setDouble(2, 100.0 + i);
            preparedStatement.setInt(3, 20 + i);

            preparedStatement.addBatch();
        }

        // 执行批量操作
        preparedStatement.executeBatch();

        long end = System.currentTimeMillis();

        System.out.println("used time: " + (end - start));

        preparedStatement.close();
        connection.close();
    }
}
