package com.atguigi.senior.dao.impl;

import com.atguigi.senior.dao.BankDao;
import com.atguigi.senior.dao.BaseDAO;

import java.sql.SQLException;

public class BankDaoImpl extends BaseDAO implements BankDao {
    @Override
    public int addMoney(Integer id, Integer money) {
        try {
            String sql = "update t_bank set money = money + ? where id = ?;";
            return executeUpdate(sql, money, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int subMoney(Integer id, Integer money) {
        try {
            String sql = "update t_bank set money = money - ? where id = ?;";
            return executeUpdate(sql, money, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
