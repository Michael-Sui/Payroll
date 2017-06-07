package utils;

import bean.PersonalInformation;
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
                LOG.info("用户使用id = " + id + ", password = " + password + ",登录失败");
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

    public PersonalInformation getPersonalInformation(String id) {
        try {
            PersonalInformation personalInformation = new PersonalInformation();
            sql = "SELECT * FROM information WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                LOG.error(id + "查询个人信息失败");
                return null;
            } else {
                personalInformation.setId(id);
                personalInformation.setName(resultSet.getString("name"));
                personalInformation.setGender(resultSet.getString("gender"));
                personalInformation.setPhoneNumber(resultSet.getString("phoneNumber"));
                personalInformation.setEmail(resultSet.getString("email"));
                personalInformation.setAge(resultSet.getInt("age"));
                personalInformation.setAddress(resultSet.getString("address"));
                personalInformation.setPaymentMethod(resultSet.getInt("paymentMethod"));
                LOG.info(id + "获取个人信息成功");
                return personalInformation;
            }
        } catch (Exception e) {
            LOG.error("获取个人信息出现异常");
        }
        return null;
    }

    public void updatePersonalInformation(PersonalInformation personalInformation) {
        try {
            sql = "UPDATE information SET name = ?, gender = ?, phoneNumber = ?, email = ?, age = ?, address = ?, paymentMethod = ? where id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, personalInformation.getName());
            preparedStatement.setString(2, personalInformation.getGender());
            preparedStatement.setString(3, personalInformation.getPhoneNumber());
            preparedStatement.setString(4, personalInformation.getEmail());
            preparedStatement.setInt(5, personalInformation.getAge());
            preparedStatement.setString(6, personalInformation.getAddress());
            preparedStatement.setInt(7, personalInformation.getPaymentMethod());
            preparedStatement.setString(8, personalInformation.getId());
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                LOG.error(personalInformation.getId() + "未更新数据库");
            } else {
                LOG.info(personalInformation.getId() + "更新个人信息成功");
            }
        } catch (Exception e) {
            LOG.error(personalInformation.getId() + "更新个人信息抛出异常");
        }
    }

    public TimeCardState getTimeCardState(String id) {
        try {
            sql = "SELECT flag from timecard WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            TimeCardState timeCardState = null;
            while (resultSet.next()) {
                timeCardState = resultSet.getInt("flag") == 0 ? TimeCardState.ON_DUTY : TimeCardState.OFF_DUTY;
            }
            if (timeCardState == null) {
                timeCardState = TimeCardState.OFF_DUTY;
            }
            LOG.info(id + "获取上/下班打卡状态成功");
            return timeCardState;
        } catch (Exception e) {
            LOG.error("获取上/下班打卡状态失败出现异常");
            return null;
        }
    }

    public void onDuty(String id) {
        try {
            sql = "INSERT INTO timecard VALUES(?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(3, 0);
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                LOG.error(id + "上班打卡更新数据库失败");
            } else {
                LOG.info(id + "上班打卡更新数据库成功");
            }
        } catch (Exception e) {
            LOG.error(id + "上班打卡更新数据库抛出异常");
        }
    }

    public void offDuty(String id) {
        try {
            sql = "INSERT INTO timecard VALUES(?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(3, 1);
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                LOG.error(id + "下班打卡更新数据库失败");
            } else {
                LOG.info(id + "下班打卡更新数据库成功");
            }
        } catch (Exception e) {
            LOG.error(id + "下班打卡更新数据库抛出异常");
        }
    }
}
