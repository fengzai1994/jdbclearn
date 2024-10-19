package com.atguigi.advanced.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidTest {

    @Test
    public void testHardCodeDruid() throws Exception {
        /*
            将连接池的配置信息和Java代码耦合在一起
            1. 设置DruidDataSource 连接池对象。
            2. 设置连接池配置信息
            3. 通过连接池获取链接对象
            4. 回收链接【不是释放链接，而是将链接归还给连接池，给其他线程进行敷用】
         */

        //1.设置DruidDataSource 连接池对象。
        DruidDataSource druidDataSource = new DruidDataSource();

        //2.设置连接池配置信息
        // 必须设置的
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/atguigu");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("Ff1994427");

        // 非必须的设置
        druidDataSource.setInitialSize(10);
        druidDataSource.setMaxActive(20);

        // 3. 通过连接池获取链接对象
        Connection connection = druidDataSource.getConnection();
        System.out.println(connection);

        // 可以connection进行crud

        // 4. 回收鏈接
        connection.close();

    }

    @Test
    public void testDruidSoft() throws Exception {
        Properties properties = new Properties();

        InputStream resourceAsStream = DruidTest.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(resourceAsStream);

        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        connection.close();
    }
}
