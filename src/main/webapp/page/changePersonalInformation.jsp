<%@ page import="bean.PersonalInformation" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人信息管理</title>
</head>
<body>
<h1>个人信息的修改</h1>
<br>
<%
    PersonalInformation personalInformation = (PersonalInformation) session.getAttribute("personalInformation");
%>
<form method="post" action="ChangePersonalInformationServlet">
    <table>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="id" value=<%= personalInformation.getId() %> readonly></td>
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
            <td><input type="number" name="employeeType" value=<%= personalInformation.getEmployeeType() %> readonly>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="提交修改"/></td>
        </tr>
    </table>
</form>
<br>
<a href="/page/main.jsp">返回普通用户主页</a>
</body>
</html>
