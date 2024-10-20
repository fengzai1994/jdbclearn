package com.atguigi.senior;

import com.atguigi.senior.dao.BankDao;
import com.atguigi.senior.dao.EmployeDao;
import com.atguigi.senior.dao.impl.BankDaoImpl;
import com.atguigi.senior.dao.impl.EmployeeDaoImpl;
import com.atguigi.senior.util.JDBCUtil;
import com.atguigi.senior.util.JDBCUtilV2;
import org.junit.Test;

import java.sql.Connection;

public class JDBCUtilTest {

    @Test
    public void testGetConnection() {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);

        JDBCUtil.release(connection);
    }

    @Test
    public void testJDBCV2() {
        //Connection connection1 = JDBCUtil.getConnection();
        //Connection connection2 = JDBCUtil.getConnection();
        //Connection connection3 = JDBCUtil.getConnection();

        //System.out.println(connection1);
        //System.out.println(connection2);
        //System.out.println(connection3);

        Connection connection1 = JDBCUtilV2.getConnection();
        Connection connection2 = JDBCUtilV2.getConnection();
        Connection connection3 = JDBCUtilV2.getConnection();

        System.out.println(connection1);
        System.out.println(connection2);
        System.out.println(connection3);

    }

    @Test
    public void testEmployeeDAO() {
        // 1.创建DAO实现类对象
        EmployeDao employeeDao = new EmployeeDaoImpl();

        //// 2.调用查询所有方法
        //List<Employee> employeeList = employeeDao.selectALL();
        //
        //// 3.处理结果
        //for (Employee employee : employeeList) {
        //    System.out.println("employee = " + employee);
        //}

        // 调用根据id查询单个员工方法
        //Employee employee = employeeDao.selectByEmpId(1);
        //System.out.println(employee);

        // 调用添加员工的方法
        //Employee employee = new Employee(null, "tom", 300.65, 38);
        //int insert = employeeDao.insert(employee);
        //System.out.println(insert);

        //Employee employee = new Employee(20008, "tom", 600.65, 38);
        //int update = employeeDao.update(employee);
        //System.out.println(update);

        int delete = employeeDao.delete(20008);
        System.out.println(delete);

    }

    @Test
    public void testTransaction() {
        BankDao bankDao = new BankDaoImpl();
        Connection connection = null;
        try {
            // 1.获取链接，将连接的事务提交改为手动提交
            connection = JDBCUtilV2.getConnection();
            connection.setAutoCommit(false); // 开启事务，当前自动链接关闭，改为手动提交

            // 2.操作扣钱
            bankDao.subMoney(1, 100);

            // 3.操作加钱
            bankDao.addMoney(2, 100);

            // 4.前置的多次dao操作没有问题，没有异常，提交事务
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            JDBCUtilV2.release();
        }
    }
}
