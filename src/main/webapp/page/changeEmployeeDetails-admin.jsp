<%@ page import="bean.User" %>
<%@ page import="bean.PersonalInformation" %>
<%@ page import="bean.CommonEmployeeSalary" %>
<%@ page import="bean.HourlyEmployeeSalary" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改员工信息页面</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    PersonalInformation personalInformation = (PersonalInformation) session.getAttribute("personalInformation");
    int employeeType = (int) session.getAttribute("employeeType");
    CommonEmployeeSalary commonEmployeeSalary = null;
    HourlyEmployeeSalary hourlyEmployeeSalary = null;
    switch (employeeType) {
        case 0:
            commonEmployeeSalary = (CommonEmployeeSalary) session.getAttribute("commonEmployeeSalary");
            break;
        case 1:
            hourlyEmployeeSalary = (HourlyEmployeeSalary) session.getAttribute("hourlyEmployeeSalary");
            break;
    }
%>
<h1>员工<%= user.getId() %>的信息如下</h1>
<br>
<form method="post" action="ChangeEmployeeDetailsAdminServlet">
    <table>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="id" value=<%= user.getId() %> readonly></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="text" name="password" value=<%= user.getPassword() %> maxlength="20" required></td>
        </tr>
        <tr>
            <td>权限：</td>
            <td><input type="text" name="authority" value=<%= user.getAuthority() %> maxlength="20" required></td>
        </tr>
        <tr>
            <td>姓名：</td>
            <td><input type="text" name="name" value=<%= personalInformation.getName() %> maxlength="20" required></td>
        </tr>
        <tr>
            <td>性别：</td>
            <td><input type="text" name="gender" value=<%= personalInformation.getGender() %> maxlength="20" required></td>
        </tr>
        <tr>
            <td>电话号码：</td>
            <td><input type="text" name="phoneNumber" value=<%= personalInformation.getPhoneNumber() %> maxlength="20" required></td>
        </tr>
        <tr>
            <td>电子邮箱地址：</td>
            <td><input type="email" name="email" value=<%= personalInformation.getEmail() %> maxlength="30" required></td>
        </tr>
        <tr>
            <td>年龄：</td>
            <td><input type="number" name="age" value=<%= personalInformation.getAge() %> maxlength="11" required></td>
        </tr>
        <tr>
            <td>家庭地址：</td>
            <td><input type="text" name="address" value=<%= personalInformation.getAddress() %> maxlength="30" required></td>
        </tr>
        <tr>
            <td>工资支付方式：</td>
            <td><input type="number" name="paymentMethod" value=<%= personalInformation.getPaymentMethod() %> maxlength="11" required>
            </td>
        </tr>
        <tr>
            <td>员工类型：</td>
            <td><input type="number" name="employeeType" value=<%= personalInformation.getEmployeeType() %> maxlength="11" required>
            </td>
        </tr>
        <%
            switch (employeeType) {
                case 0:
        %>
        <tr>
            <td>基础月工资是：</td>
            <td><input type="number" name="commonSalary" value=<%= commonEmployeeSalary.getSalary() %> required></td>
        </tr>
        <%
            break;
            case 1:
        %>
        <tr>
            <td>时薪是：</td>
            <td><input type="number" name="hourlySalary" value=<%= hourlyEmployeeSalary.getSalary() %> required></td>
        </tr>
        <%
            break;
            }
        %>
        <tr>
            <td></td>
            <td><input type="submit" value="提交修改"/></td>
        </tr>
    </table>
</form>
<br>
<a href="/page/main-admin.jsp">返回管理员主页</a>
</body>
</html>
