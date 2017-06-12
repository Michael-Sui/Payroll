<%@ page import="bean.PurchaseOrder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改订单信息</title>
</head>
<body>
<h1>请在这里修改订单信息</h1>
<br>
<%
    PurchaseOrder purchaseOrder = (PurchaseOrder) session.getAttribute("purchaseOrder");
%>
<form method="post" action="ChangePurchaseOrderDetailsServlet">
    <table>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="id" value=<%= purchaseOrder.getId() %> readonly></td>
        </tr>
        <tr>
            <td>订单编号：</td>
            <td><input type="text" name="orderId" value=<%= purchaseOrder.getOrderId() %> readonly></td>
        </tr>
        <tr>
            <td>订单时间：</td>
            <td><input type="text" name="time" value=<%= purchaseOrder.getTime() %> required></td>
        </tr>
        <tr>
            <td>订单金额</td>
            <td><input type="number" step="0.01" name="money" value=<%= purchaseOrder.getMoney() %> required></td>
        </tr>
        <tr>
            <td>订单抽成</td>
            <td><input type="number" step="0.0001" name="proportion"
                       value=<%= purchaseOrder.getProportion() %> required></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="确定提交"></td>
        </tr>
    </table>
</form>
<br>
<a href="/page/main.jsp">返回普通用户主页</a>
</body>
</html>
