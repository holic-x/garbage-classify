package com.rc.framework.db;

import com.rc.framework.utils.MySqlHelp;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * 定义实现BaseDAO的实现类
 */
public class BaseDAOImpl<T> implements BaseDAO<T> {
    private QueryRunner qr = null;
    /**
     * 利用反射实现得到对象的类型
     * 注意导入的包是import java.lang.reflect.Type;
     */
    private Class<T> type;
    //定义一个公用的Connection-转成局部变量处理
//	public Connection conn = null;

    public BaseDAOImpl() {
        // 数据库连接不在此处控制，而是由db操作时进行创建（解决conn null问题）
//		conn = MySqlHelp.getConn();
        qr = new QueryRunner();
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        type = (Class) params[0];
    }

    @Override
    public void batch(String sql, Object[]... args)
            throws SQLException {
        Connection conn = MySqlHelp.getConn();
        qr.batch(conn, sql, args);
        conn.close();
    }

    @Override
    public <E> E getForValue(String sql, Object... args)
            throws SQLException {
        Connection conn = MySqlHelp.getConn();
        E res = (E) qr.query(conn, sql, new ScalarHandler(), args);
        conn.close();
        return res;
    }

    @Override
    public List<T> getForList(String sql, Object... args)
            throws SQLException {
        Connection conn = MySqlHelp.getConn();
        List<T> resList = qr.query(conn, sql, new BeanListHandler<>(type), args);
        conn.close();
        return resList;

    }

    @Override
    public T get(String sql, Object... args)
            throws SQLException {
        Connection conn = MySqlHelp.getConn();
        T res = qr.query(conn, sql, new BeanHandler<>(type), args);
        conn.close();
        return res;
    }

    @Override
    public void update(String sql, Object... args)
            throws SQLException {
        Connection conn = MySqlHelp.getConn();
        qr.update(conn, sql, args);
        conn.close();
    }
}
