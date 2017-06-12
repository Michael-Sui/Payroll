<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.PurchaseOrder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人订单信息</title>
</head>
<body>
<h1>您的订单信息如下</h1>
<br>
<%
    ArrayList<PurchaseOrder> purchaseOrders = (ArrayList<PurchaseOrder>) session.getAttribute("purchaseOrders");
    if (purchaseOrders.isEmpty()) {
%>
无订单信息
<br>
<%
} else {
    for (PurchaseOrder purchaseOrder : purchaseOrders) {
%>
<table>
    <tr>
        <td>订单编号</td>
        <td><%= purchaseOrder.getOrderId() %>
        </td>
    </tr>
    <tr>
        <td>订单时间</td>
        <td><%= purchaseOrder.getTime() %>
        </td>
    </tr>
    <tr>
        <td>订单金额</td>
        <td><%= purchaseOrder.getMoney() %>
        </td>
    </tr>
    <tr>
        <td>订单抽成</td>
        <td><%= purchaseOrder.getProportion() %>
        </td>
    </tr>
</table>
<br><br><br>
<%
        }
    }
%>
<a href="/page/addPurchaseOrder.jsp">添加订单信息</a>
<br>
<a href="/page/FindExistPurchaseOrderServlet">修改订单信息</a>
<br>
<a href="/page/main.jsp">返回普通用户主页</a>
</body>
</html>