<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.PurchaseOrder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改订单信息</title>
</head>
<body>
    <h1>请在这里选择订单信息</h1>
    <br>
    <% ArrayList<PurchaseOrder> purchaseOrders = (ArrayList<PurchaseOrder>) session.getAttribute("purchaseOrders"); %>
    <form method="post" action="ChangePurchaseOrderServlet">
        <table>
            <tr>
                <td>想要修改的订单编号：</td>
                <td>
                    <select name="changePurchaseOrderId">
                        <% for (PurchaseOrder purchaseOrder : purchaseOrders) { %>
                        <option value=<%= purchaseOrder.getOrderId() %>><%= purchaseOrder.getOrderId() %></option>
                        <% } %>
                    </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="确定"></td>
            </tr>
        </table>
    </form>
    <br>
    <a href="/page/main.jsp">返回普通用户主页</a>
</body>
</html>
