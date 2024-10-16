package com.atguigi.base;

import org.junit.Test;

import java.sql.*;

public class JDBCOperation {

    @Test
    public void testQuerySingleRowAndCol() throws SQLException {
        // 1. 註冊驅動

        // 2. 獲取鏈接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");

        // 3. 预编译SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) as count FROM t_emp");

        // 4. 执行SQL语句
        ResultSet resultSet = preparedStatement.executeQuery();

        // 5. 处理结果
        while (resultSet.next()) {
            int count = resultSet.getInt("count");
            System.out.println(count);
        }

        // 6.释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testQuerySingleRow() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT emp_id, emp_name, emp_salsary, emp_age FROM t_emp WHERE emp_id = ?");

        preparedStatement.setInt(1, 5);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalsary = resultSet.getDouble("emp_salsary");
            int empAge = resultSet.getInt("emp_age");

            System.out.println(empId + " " + empName + " " + empSalsary + " " + empAge);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testQueryMoreRows() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT emp_id, emp_name, emp_salsary, emp_age FROM t_emp WHERE emp_age > ?");

        preparedStatement.setInt(1, 25);

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalsary = resultSet.getDouble("emp_salsary");
            int empAge = resultSet.getInt("emp_age");

            System.out.println(empId + " " + empName + " " + empSalsary + " " + empAge);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testInsert() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_emp(emp_name, emp_salsary, emp_age) VALUES (?, ?, ?)");

        preparedStatement.setString(1, "rose");
        preparedStatement.setDouble(2, 345.67);
        preparedStatement.setInt(3, 28);

        int result = preparedStatement.executeUpdate();

        // 根据受影响行数，做判断
        if (result > 0) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }

        preparedStatement.close();
        connection.close();


    }

    @Test
    public void testUpdate() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE t_emp SET emp_salsary = ? WHERE emp_id = ?");
        preparedStatement.setDouble(1, 888.88);
        preparedStatement.setInt(2, 6);
        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            System.out.println("success!");
        } else {
            System.out.println("fail!");
        }

        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testDelete() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM t_emp WHERE emp_id = ?");

        preparedStatement.setInt(1, 6);

        int i = preparedStatement.executeUpdate();

        if (i > 0) {
            System.out.println("success!");
        } else {
            System.out.println("fail!");
        }

        preparedStatement.close();
        connection.close();
    }
}
