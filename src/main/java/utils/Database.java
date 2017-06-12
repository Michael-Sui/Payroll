package utils;

import bean.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.dc.pr.PRError;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
                personalInformation.setEmployeeType(resultSet.getInt("employeeType"));
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
            sql = "UPDATE information SET name = ?, gender = ?, phoneNumber = ?, email = ?, age = ?, address = ?, paymentMethod = ?, employeeType = ? where id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, personalInformation.getName());
            preparedStatement.setString(2, personalInformation.getGender());
            preparedStatement.setString(3, personalInformation.getPhoneNumber());
            preparedStatement.setString(4, personalInformation.getEmail());
            preparedStatement.setInt(5, personalInformation.getAge());
            preparedStatement.setString(6, personalInformation.getAddress());
            preparedStatement.setInt(7, personalInformation.getPaymentMethod());
            preparedStatement.setInt(8, personalInformation.getEmployeeType());
            preparedStatement.setString(9, personalInformation.getId());
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

    public ArrayList<PurchaseOrder> getPurchaseOrder(String id) {
        try {
            sql = "SELECT * FROM purchaseorder WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            ArrayList<PurchaseOrder> purchaseOrders = new ArrayList<>();
            while (resultSet.next()) {
                PurchaseOrder purchaseOrder = new PurchaseOrder();
                purchaseOrder.setId(resultSet.getString("id"));
                purchaseOrder.setOrderId(resultSet.getString("orderId"));
                purchaseOrder.setTime(resultSet.getTimestamp("time"));
                purchaseOrder.setMoney(resultSet.getDouble("money"));
                purchaseOrder.setProportion(resultSet.getDouble("proportion"));
                purchaseOrders.add(purchaseOrder);
            }
            LOG.info(id + "获取订单信息成功");
            return purchaseOrders;
        } catch (Exception e) {
            LOG.error(id + "获取订单信息抛出异常");
        }
        return null;
    }

    public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
        try {
            sql = "INSERT INTO purchaseorder VALUES(?, ?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, purchaseOrder.getId());
            preparedStatement.setString(2, purchaseOrder.getOrderId());
            preparedStatement.setTimestamp(3, purchaseOrder.getTime());
            preparedStatement.setDouble(4, purchaseOrder.getMoney());
            preparedStatement.setDouble(5, purchaseOrder.getProportion());
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                LOG.error(purchaseOrder.getId() + "添加订单" + purchaseOrder.getOrderId() + "失败");
            } else {
                LOG.info(purchaseOrder.getId() + "添加订单" + purchaseOrder.getOrderId() + "成功");
            }
        } catch (Exception e) {
            LOG.error(purchaseOrder.getId() + "添加订单" + purchaseOrder.getOrderId() + "抛出了异常");
        }
    }

    public PurchaseOrder getPurchaseOrderByOrderId(String changePurchaseOrderId) {
        try {
            sql = "SELECT * FROM purchaseorder WHERE orderId = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, changePurchaseOrderId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                LOG.error("查询订单" + changePurchaseOrderId + "失败");
                return null;
            } else {
                PurchaseOrder purchaseOrder = new PurchaseOrder();
                purchaseOrder.setId(resultSet.getString("id"));
                purchaseOrder.setOrderId(resultSet.getString("orderId"));
                purchaseOrder.setTime(resultSet.getTimestamp("time"));
                purchaseOrder.setMoney(resultSet.getDouble("money"));
                purchaseOrder.setProportion(resultSet.getDouble("proportion"));
                LOG.info("查询订单" + changePurchaseOrderId + "成功");
                return purchaseOrder;
            }
        } catch (Exception e) {
            LOG.error("查询订单" + changePurchaseOrderId + "抛出了异常");
        }
        return null;
    }

    public void changePurchaseOrder(PurchaseOrder purchaseOrder) {
        try {
            sql = "UPDATE purchaseorder SET time = ?, money = ?, proportion = ? WHERE id = ? AND orderId = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, purchaseOrder.getTime());
            preparedStatement.setDouble(2, purchaseOrder.getMoney());
            preparedStatement.setDouble(3, purchaseOrder.getProportion());
            preparedStatement.setString(4, purchaseOrder.getId());
            preparedStatement.setString(5, purchaseOrder.getOrderId());
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                LOG.error(purchaseOrder.getId() + "的订单" + purchaseOrder.getOrderId() + "未更新成功");
            } else {
                LOG.info(purchaseOrder.getId() + "的订单" + purchaseOrder.getOrderId() + "更新成功");
            }
        } catch (Exception e) {
            LOG.error(purchaseOrder.getId() + "的订单" + purchaseOrder.getOrderId() + "更新时抛出异常");
        }
    }

    public int getEmployeeType(String id) {
        try {
            sql = "SELECT employeeType FROM information WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LOG.info(id + "查询员工类型成功");
                return resultSet.getInt("employeeType");
            } else {
                LOG.error(id + "查询员工类型失败");
                return -1;
            }
        } catch (Exception e) {
            LOG.error(id + "查询员工类型抛出了异常");
            return -1;
        }
    }

    public double getCommonEmployeeSalary(String id) {
        try {
            sql = "SELECT salary FROM commonemployeesalary WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LOG.info(id + "查询每月基本工资成功");
                return resultSet.getDouble("salary");
            } else {
                LOG.error(id + "查询每月基本工资失败");
                return -1;
            }
        } catch (Exception e) {
            LOG.error(id + "查询每月基本工资抛出了异常");
            return -1;
        }
    }

    public ArrayList<Salary> getAllSalaryWithEmployeeType0(String id, String inquireMode, String details) {
        try {
            switch (inquireMode) {
                case "inquireByMonth":
                    sql = "SELECT * FROM salary WHERE id = ? AND time >= ? AND time < ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Calendar now = Calendar.getInstance();
                    java.util.Date date1 = simpleDateFormat.parse("" + now.get(Calendar.YEAR) + "-" + details + "-01 00:00:00");
                    java.util.Date date2 = simpleDateFormat.parse("" + now.get(Calendar.YEAR) + "-" + String.valueOf(Integer.valueOf(details) + 1) + "-01 00:00:00");
                    String time1 = simpleDateFormat.format(date1);
                    String time2 = simpleDateFormat.format(date2);
                    Timestamp timestamp1 = Timestamp.valueOf(time1);
                    Timestamp timestamp2 = Timestamp.valueOf(time2);
                    preparedStatement.setTimestamp(2, timestamp1);
                    preparedStatement.setTimestamp(3, timestamp2);
                    break;
                case "inquireAll":
                    sql = "SELECT * FROM salary WHERE id = ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    break;
            }
            resultSet = preparedStatement.executeQuery();
            ArrayList<Salary> salaries = new ArrayList<>();
            while (resultSet.next()) {
                Salary salary = new Salary();
                salary.setId(resultSet.getString("id"));
                salary.setTime(resultSet.getTimestamp("time"));
                salary.setSalary(resultSet.getDouble("salary"));
                salaries.add(salary);
            }
            LOG.info(id + "使用查询方式:" + inquireMode + ",查询参数：" + details + "查询基本工资成功");
            return salaries;
        } catch (Exception e) {
            LOG.error(id + "使用查询方式:" + inquireMode + ",查询参数：" + details + "查询基本工资抛出异常");
            return null;
        }
    }

    public ArrayList<TimeCard> getTimeCardByDay(String id, String inquireMode, String details) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar now = Calendar.getInstance();
            java.util.Date date1 = null;
            java.util.Date date2 = null;
            String time1 = null;
            String time2 = null;
            Timestamp timestamp1 = null;
            Timestamp timestamp2 = null;
            switch (inquireMode) {
                case "inquireByDay":
                    sql = "SELECT * FROM timecard where id = ? AND time >= ? AND time < ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    date1 = simpleDateFormat.parse("" + now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + details + " 00:00:00");
                    date2 = simpleDateFormat.parse("" + now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + String.valueOf(Integer.valueOf(details) + 1) + " 00:00:00");
                    time1 = simpleDateFormat.format(date1);
                    time2 = simpleDateFormat.format(date2);
                    timestamp1 = Timestamp.valueOf(time1);
                    timestamp2 = Timestamp.valueOf(time2);
                    preparedStatement.setTimestamp(2, timestamp1);
                    preparedStatement.setTimestamp(3, timestamp2);
                    break;
                case "inquireByMonth":
                    sql = "SELECT * FROM timecard WHERE id = ? AND time >= ? AND time < ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    date1 = simpleDateFormat.parse("" + now.get(Calendar.YEAR) + "-" + details + "-01 00:00:00");
                    date2 = simpleDateFormat.parse("" + now.get(Calendar.YEAR) + "-" + String.valueOf(Integer.valueOf(details) + 1) + "-01 00:00:00");
                    time1 = simpleDateFormat.format(date1);
                    time2 = simpleDateFormat.format(date2);
                    timestamp1 = Timestamp.valueOf(time1);
                    timestamp2 = Timestamp.valueOf(time2);
                    preparedStatement.setTimestamp(2, timestamp1);
                    preparedStatement.setTimestamp(3, timestamp2);
                    break;
                case "inquireAll":
                    sql = "SELECT * FROM timecard WHERE id = ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    break;
            }
            resultSet = preparedStatement.executeQuery();
            ArrayList<TimeCard> timeCards = new ArrayList<>();
            while (resultSet.next()) {
                TimeCard timeCard = new TimeCard();
                timeCard.setId(resultSet.getString("id"));
                timeCard.setTime(resultSet.getTimestamp("time"));
                timeCard.setFlag(resultSet.getInt("flag"));
                timeCards.add(timeCard);
            }
            LOG.info(id + "使用查询方式:" + inquireMode + ",查询参数：" + details + "查询计时工资成功");
            return timeCards;
        } catch (Exception e) {
            LOG.error(id + "使用查询方式:" + inquireMode + ",查询参数：" + details + "查询计时工资抛出异常");
            return null;
        }
    }

    public double getHourlyEmployeeSalary(String id) {
        try {
            sql = "SELECT salary FROM hourlyemployeesalary WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LOG.info(id + "查询每小时工资成功");
                return resultSet.getDouble("salary");
            } else {
                LOG.error(id + "查询每小时工资失败");
                return -1;
            }
        } catch (Exception e) {
            LOG.error(id + "查询每小时工资抛出了异常");
            return -1;
        }
    }

    public ArrayList<PurchaseOrder> getSaleEmployeeSalary(String id, String inquireMode, String details) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar now = Calendar.getInstance();
            java.util.Date date1 = null;
            java.util.Date date2 = null;
            String time1 = null;
            String time2 = null;
            Timestamp timestamp1 = null;
            Timestamp timestamp2 = null;
            switch (inquireMode) {
                case "inquireByDay":
                    sql = "SELECT * FROM purchaseorder WHERE id = ? AND time >= ? AND time < ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    date1 = simpleDateFormat.parse("" + now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + details + " 00:00:00");
                    date2 = simpleDateFormat.parse("" + now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + String.valueOf(Integer.valueOf(details) + 1) + " 00:00:00");
                    time1 = simpleDateFormat.format(date1);
                    time2 = simpleDateFormat.format(date2);
                    timestamp1 = Timestamp.valueOf(time1);
                    timestamp2 = Timestamp.valueOf(time2);
                    preparedStatement.setTimestamp(2, timestamp1);
                    preparedStatement.setTimestamp(3, timestamp2);
                    break;
                case "inquireByMonth":
                    sql = "SELECT * FROM purchaseorder WHERE id = ? AND time >= ? AND time < ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    date1 = simpleDateFormat.parse("" + now.get(Calendar.YEAR) + "-" + details + "-01 00:00:00");
                    date2 = simpleDateFormat.parse("" + now.get(Calendar.YEAR) + "-" + String.valueOf(Integer.valueOf(details) + 1) + "-01 00:00:00");
                    time1 = simpleDateFormat.format(date1);
                    time2 = simpleDateFormat.format(date2);
                    timestamp1 = Timestamp.valueOf(time1);
                    timestamp2 = Timestamp.valueOf(time2);
                    preparedStatement.setTimestamp(2, timestamp1);
                    preparedStatement.setTimestamp(3, timestamp2);
                    break;
                case "inquireAll":
                    sql = "SELECT * FROM purchaseorder WHERE id = ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    break;
            }
            resultSet = preparedStatement.executeQuery();
            ArrayList<PurchaseOrder> purchaseOrders = new ArrayList<>();
            while (resultSet.next()) {
                PurchaseOrder purchaseOrder = new PurchaseOrder();
                purchaseOrder.setId(resultSet.getString("id"));
                purchaseOrder.setOrderId(resultSet.getString("orderId"));
                purchaseOrder.setTime(resultSet.getTimestamp("time"));
                purchaseOrder.setMoney(resultSet.getDouble("money"));
                purchaseOrder.setProportion(resultSet.getDouble("proportion"));
                purchaseOrders.add(purchaseOrder);
            }
            LOG.info(id + "使用查询方式:" + inquireMode + ",查询参数：" + details + "查询销售工资成功");
            return purchaseOrders;
        } catch (Exception e) {
            LOG.error(id + "使用查询方式:" + inquireMode + ",查询参数：" + details + "查询销售人员工资抛出了异常");
            return null;
        }
    }

    public boolean addEmployee(String id, String password, String authority) {
        try {
            sql = "INSERT INTO user VALUES(?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, authority.toLowerCase());
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                LOG.error("添加了重复的用户" + id);
                return false;
            }
            sql = "INSERT INTO information VALUES (?, '0', '0', '0', '0@a.com', 0, '0', 0, 0);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            int result2 = preparedStatement.executeUpdate();
            if (result2 == 0) {
                LOG.error("添加用户信息错误" + id);
                return false;
            }
            sql = "INSERT INTO commonemployeesalary VALUES (?, 0);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            int result3 = preparedStatement.executeUpdate();
            if (result3 == 0) {
                LOG.error("添加用户信息错误" + id);
                return false;
            }
            sql = "INSERT INTO hourlyemployeesalary VALUES (?, 0);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            int result4 = preparedStatement.executeUpdate();
            if (result4 == 0) {
                LOG.error("添加用户信息错误" + id);
                return false;
            }
            LOG.info("添加用户" + id + ", " + password + ", " + authority + "成功");
            return true;
        } catch (Exception e) {
            LOG.info("添加用户" + id + ", " + password + ", " + authority + "抛出了异常");
            return false;
        }
    }

    public boolean isHaveUserById(String id) {
        try {
            sql = "SELECT * FROM user WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LOG.info("查询到用户" + id);
                return true;
            } else {
                LOG.warn("未查询到用户" + id);
                return false;
            }
        } catch (Exception e) {
            LOG.error("查询用户" + id + "是否存在时抛出异常");
            return false;
        }
    }

    public User getUser(String id) {
        try {
            sql = "SELECT * FROM user WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setPassword(resultSet.getString("password"));
                user.setAuthority(resultSet.getString("authority"));
                LOG.info("查询用户" + id + "信息成功");
                return user;
            } else {
                LOG.warn("查询用户" + id + "信息失败");
                return null;
            }
        } catch (Exception e) {
            LOG.error("查询用户" + id + "信息抛出异常");
            return null;
        }
    }

    public void updateUser(User user) {
        try {
            sql = "UPDATE user SET password = ?, authority = ? WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getAuthority());
            preparedStatement.setString(3, user.getId());
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                LOG.warn("更新登录信息" + user.getId() + ", " + user.getPassword() + ", " + user.getAuthority() + "失败");
            } else {
                LOG.info("更新登录信息" + user.getId() + ", " + user.getPassword() + ", " + user.getAuthority() + "成功");
            }
        } catch (Exception e) {
            LOG.error("更新登录信息" + user.getId() + ", " + user.getPassword() + ", " + user.getAuthority() + "抛出异常");
        }
    }

    public void deleteEmployee(String id1) {
        try {
            sql = "DELETE FROM user WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id1);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM timecard WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id1);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM salary WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id1);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM purchaseorder WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id1);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM information WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id1);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM hourlyemployeesalary WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id1);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM commonemployeesalary WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id1);
            preparedStatement.executeUpdate();
            LOG.info("删除员工" + id1 + "成功");
        } catch (Exception e) {
            LOG.error("删除员工失败");
        }
    }

    public void updateCommonEmployeeSalary(CommonEmployeeSalary commonEmployeeSalary) {
        try {
            sql = "UPDATE commonemployeesalary SET salary = ? WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, commonEmployeeSalary.getSalary());
            preparedStatement.setString(2, commonEmployeeSalary.getId());
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                LOG.error(commonEmployeeSalary.getId() + "更新工资信息" + commonEmployeeSalary.getSalary() + "失败");
            } else {
                LOG.info(commonEmployeeSalary.getId() + "更新工资信息" + commonEmployeeSalary.getSalary() + "成功");
            }
        } catch (Exception e) {
            LOG.error(commonEmployeeSalary.getId() + "更新工资信息" + commonEmployeeSalary.getSalary() + "抛出异常");
        }
    }

    public void updateHourlyEmployeeSalary(HourlyEmployeeSalary hourlyEmployeeSalary) {
        try {
            sql = "UPDATE hourlyemployeesalary SET salary = ? WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, hourlyEmployeeSalary.getSalary());
            preparedStatement.setString(2, hourlyEmployeeSalary.getId());
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                LOG.error(hourlyEmployeeSalary.getId() + "更新工资信息" + hourlyEmployeeSalary.getSalary() + "失败");
            } else {
                LOG.info(hourlyEmployeeSalary.getId() + "更新工资信息" + hourlyEmployeeSalary.getSalary() + "成功");
            }
        } catch (Exception e) {
            LOG.error(hourlyEmployeeSalary.getId() + "更新工资信息" + hourlyEmployeeSalary.getSalary() + "抛出异常");
        }
    }

    public ArrayList<Salary> getAllSalaries() {
        try {
            sql = "SELECT * from salary;";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ArrayList<Salary> salaries = new ArrayList<>();
            while (resultSet.next()) {
                Salary salary = new Salary();
                salary.setId(resultSet.getString("id"));
                salary.setTime(resultSet.getTimestamp("time"));
                salary.setSalary(resultSet.getDouble("salary"));
                salaries.add(salary);
            }
            LOG.info("获取全部工资信息成功");
            return salaries;
        } catch (Exception e) {
            LOG.error("获取全部工资信息失败");
            return null;
        }
    }
}
