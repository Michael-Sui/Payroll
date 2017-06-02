package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Created by Michael on 2017/6/1.
 */
public class Database {
    private final Logger LOG = LogManager.getLogger(Database.class);
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/payroll?useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "mysql";
    private Connection connection = null;

    private String sql = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public boolean connect() {
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

    public boolean disconnect() {
        try {
            try {
                resultSet.close();
            } catch (Exception e) {
                LOG.info("result为空，已关闭");
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                LOG.info("preparedStatement为空，已关闭");
            }
            sql = null;
            try {
                connection.close();
            } catch (Exception e) {
                LOG.info("connection为空，已关闭");
            }
        } catch (Exception e) {
            LOG.error("断开数据库连接失败");
            return false;
        }
        LOG.info("断开了数据库的连接");
        return true;
    }

    public UserAuthority isLogin(String id, String password) {
        try {
            sql = "SELECT authority FROM user WHERE id = ? AND password = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                LOG.warn("用户使用id = " + id + ", password = " + password + ",登录失败");
                return UserAuthority.ERROR;
            } else {
                String authority = resultSet.getString("authority");
                switch (authority) {
                    case "guest":
                        LOG.info("用户使用id = " + id + ", password = " + password + ",GUEST登录成功");
                        return UserAuthority.GUEST;
                    case "admin":
                        LOG.info("用户使用id = " + id + ", password = " + password + ",ADMIN登录成功");
                        return UserAuthority.ADMIN;
                    default:
                        LOG.error("用户使用id = " + id + ", password = " + password + ",登录失败");
                        return UserAuthority.ERROR;
                }
            }
        } catch (SQLException e) {
            LOG.error("登录过程出现异常");
        }
        LOG.error("用户使用id = " + id + ", password = " + password + ",登录失败");
        return UserAuthority.ERROR;
    }
}
