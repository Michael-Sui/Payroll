<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加订单信息</title>
</head>
<body>
<h1>请在这里添加订单信息</h1>
<br>
<%
    String id = session.getAttribute("user").toString();
%>
<form method="post" action="AddPurchaseOrderServlet">
    <table>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="id" value=<%= id %> readonly></td>
        </tr>
        <tr>
            <td>订单编号：</td>
            <td><input type="text" name="orderId" maxlength="20" required></td>
        </tr>
        <tr>
            <td>订单时间：</td>
            <td><input type="datetime" name="time" required></td>
        </tr>
        <tr>
            <td>订单金额：</td>
            <td><input type="number" step="0.01" name="money" maxlength="20" required></td>
        </tr>
        <tr>
            <td>订单抽成：</td>
            <td><input type="number" step="0.0001" name="proportion" maxlength="20" required></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="确定提交订单"></td>
        </tr>
    </table>
</form>
<br>
<a href="/page/main.jsp">返回普通用户主页</a>
</body>
</html>
