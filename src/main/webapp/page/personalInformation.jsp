<%@ page import="bean.PersonalInformation" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人信息管理</title>
</head>
<body>
    <h1>个人信息的查询</h1>
    <br>
    <% PersonalInformation personalInformation = (PersonalInformation) session.getAttribute("personalInformation"); %>
    <table>
        <tr>
            <th>用户名</th>
            <th>姓名</th>
            <th>性别</th>
            <th>电话号码</th>
            <th>电子邮箱地址</th>
            <th>年龄</th>
            <th>家庭地址</th>
            <th>工资支付方式</th>
        </tr>
        <tr>
            <td><%= personalInformation.getId() %></td>
            <td><%= personalInformation.getName() %></td>
            <td><%= personalInformation.getGender() %></td>
            <td><%= personalInformation.getPhoneNumber() %></td>
            <td><%= personalInformation.getEmail() %></td>
            <td><%= personalInformation.getAge() %></td>
            <td><%= personalInformation.getAddress() %></td>
            <td><%= personalInformation.getPaymentMethod() %></td>
        </tr>
    </table>
</body>
</html>
