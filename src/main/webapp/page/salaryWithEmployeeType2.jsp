<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.PurchaseOrder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>销售员工工资单</title>
</head>
<body>
    <h1>销售员工工资单</h1>
    <br>
    <%
        double salaryCount = 0;
        double money = 0;
        double moneyCount = 0;
        ArrayList<PurchaseOrder> purchaseOrders = (ArrayList<PurchaseOrder>) session.getAttribute("purchaseOrders");
        if (purchaseOrders.isEmpty()) {
    %>
    无工资信息
    <%
        } else {
            for (PurchaseOrder purchaseOrder : purchaseOrders) {
    %>
    <table>
        <tr>
            <td>用户名：</td>
            <td><%= purchaseOrder.getId() %></td>
        </tr>
        <tr>
            <td>订单编号：</td>
            <td><%= purchaseOrder.getOrderId() %></td>
        </tr>
        <tr>
            <td>订单时间：</td>
            <td><%= purchaseOrder.getTime() %></td>
        </tr>
        <tr>
            <td>订单金额：</td>
            <td><%= purchaseOrder.getMoney() %></td>
            <%
                money = purchaseOrder.getMoney();
                moneyCount += money;
            %>
        </tr>
        <tr>
            <td>订单抽成：</td>
            <td><%= purchaseOrder.getProportion() %></td>
            <%
                salaryCount += money * purchaseOrder.getProportion();
            %>
        </tr>
    </table>
    <%
            }
        }
    %>
    <br><br><br>
    您的销售总额是：<%= moneyCount %>
    <br>
    您的工资是：<%= salaryCount %>
</body>
</html>
