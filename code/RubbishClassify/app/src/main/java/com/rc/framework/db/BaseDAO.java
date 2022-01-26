package com.rc.framework.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 访问数据的DAO的接口
 * 此处定义访问数据表的各种方法
 */
public interface BaseDAO<T> {
    /**
     * 定义批量处理的方法
     *
     * @param sql
     * @param args：填充占位符，不定长参数
     * @throws SQLException
     */
    public void batch(String sql, Object[]... args) throws SQLException;

    /**
     * 定义返回一个具体的值的方法
     * 例如查询某个人的某个信息、查询总人数、查询平均工资等
     *
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public <E> E getForValue(String sql, Object... args) throws SQLException;

    /**
     * 查询一个对象的集合，返回T集合的所有对象
     *
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public List<T> getForList(String sql, Object... args) throws SQLException;

    /**
     * 返回查询的一个T类型的对象
     *
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public T get(String sql, Object... args) throws SQLException;

    /**
     * 增删改的通用方法
     *
     * @param sql
     * @param args
     * @throws SQLException
     */
    public void update(String sql, Object... args) throws SQLException;
}
