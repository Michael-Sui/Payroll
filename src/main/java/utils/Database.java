package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Michael on 2017/6/1.
 */
public class Database {
    private static final Logger LOG = LogManager.getLogger(Database.class);
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/payroll?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";
    Connection connection = null;

    String sql = null;
    Statement statement = null;
    ResultSet resultSet = null;

    private boolean connect() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            LOG.info("建立了数据库的连接");
        } catch (Exception e) {
            LOG.error("建立数据库连接失败");
            return false;
        }
        return true;
    }
    private boolean disconnect() {
        try {
            try {
                resultSet.close();
            } catch (Exception e) {
                LOG.warn("result为空，已关闭");
            }
            try {
                statement.close();
            } catch (Exception e) {
                LOG.warn("statement为空，已关闭");
            }
            sql = null;
            try {
                connection.close();
            } catch (Exception e) {
                LOG.warn("connection为空，已关闭");
            }
        } catch (Exception e) {
            LOG.error("断开数据库连接失败");
            return false;
        }
        return true;
    }
}
