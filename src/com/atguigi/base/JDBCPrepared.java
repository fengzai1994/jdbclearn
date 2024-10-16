package com.atguigi.base;

import java.sql.*;
import java.util.Scanner;

public class JDBCPrepared {

    public static void main(String[] args) throws Exception {

        // 1. 註冊驅動

        // 2. 獲取鏈接對象
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");

        // 3. 獲取執行SQL語句對象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT emp_id, emp_name, emp_salsary, emp_age FROM t_emp WHERE emp_name = ? ");

        System.out.println("please enter employee's name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        // 4. 为？赋值，并执行SQL，接受返回的结果
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        // 5. 遍歷ResultSet
        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalsary = resultSet.getDouble("emp_salsary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalsary + "\t" + empAge);
        }

        // 6. 釋放資源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
