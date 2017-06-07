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
            <td>用户名</td>
            <td><%= personalInformation.getId() %></td>
        </tr>
        <tr>
            <td>姓名</td>
            <td><%= personalInformation.getName() %></td>
        </tr>
        <tr>
            <td>性别</td>
            <td><%= personalInformation.getGender() %></td>
        </tr>
        <tr>
            <td>电话号码</td>
            <td><%= personalInformation.getPhoneNumber() %></td>
        </tr>
        <tr>
            <td>电子邮箱地址</td>
            <td><%= personalInformation.getEmail() %></td>
        </tr>
        <tr>
            <td>年龄</td>
            <td><%= personalInformation.getAge() %></td>
        </tr>
        <tr>
            <td>家庭地址</td>
            <td><%= personalInformation.getAddress() %></td>
        </tr>
        <tr>
            <td>工资支付方式</td>
            <td><%= personalInformation.getPaymentMethod() %></td>
        </tr>
    </table>
    <a href="/page/changePersonalInformation.jsp">修改个人信息</a>
    <br>
    <a href="/page/main.jsp">返回普通用户主页</a>
</body>
</html>
