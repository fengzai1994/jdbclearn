package com.atguigi.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCQuick {

    public static void main(String[] args) throws Exception {
        // 1.注册驱动
        //Class.forName("com.mysql.cj.jdbc.Driver");
        //DriverManager.registerDriver(new Driver());

        // 2.获取链接对象
        String url = "jdbc:mysql://localhost:3306/atguigu";
        String username = "root";
        String password = "Ff1994427";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 3.获取执行SQL语句的对象
        Statement statement = connection.createStatement();

        // 4.编写SQL语句，并执行，接受返回的结果集
        String sql = "SELECT emp_id, emp_name, emp_salsary, emp_age FROM t_emp;";
        ResultSet resultSet = statement.executeQuery(sql);

        // 5.处理结果：遍历resultSet结果集
        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalsary = resultSet.getDouble("emp_salsary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalsary + "\t" + empAge);
        }

        // 6.释放资源（先开后关原则）
        resultSet.close();
        statement.close();
        connection.close();
    }
}
