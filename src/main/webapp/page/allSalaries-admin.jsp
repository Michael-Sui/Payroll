<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.Salary" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员工资单</title>
</head>
<body>
<h1>全部员工的工资单</h1>
<br>
<%
    ArrayList<Salary> allSalaries = (ArrayList<Salary>) session.getAttribute("allSalaries");
    if (allSalaries.isEmpty()) {
%>
无工资信息
<br>
<%
} else {
    for (Salary salary : allSalaries) {
%>
<table>
    <tr>
        <td>用户名：</td>
        <td><%= salary.getId() %>
        </td>
    </tr>
    <tr>
        <td></td>
        <td><%= salary.getSalary() %>
        </td>
    </tr>
</table>
<br>
<%
        }
    }
%>
<br>
<a href="/page/main-admin.jsp">返回管理员主页</a>
</body>
</html>
