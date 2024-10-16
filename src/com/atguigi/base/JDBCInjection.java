package com.atguigi.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCInjection {

    public static void main(String[] args) throws Exception {

        // 1. 註冊驅動

        // 2. 獲取鏈接對象
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "Ff1994427");

        // 3. 獲取執行SQL語句對象
        Statement statement = connection.createStatement();

        System.out.println("please enter employee's name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        // 4. 編寫SQL語句
        String sql = "SELECT emp_id, emp_name, emp_salsary, emp_age FROM t_emp WHERE emp_name = '" + name + "'";
        ResultSet resultSet = statement.executeQuery(sql);

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
        statement.close();
        connection.close();
    }
}
