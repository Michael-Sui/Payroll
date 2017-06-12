<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.Salary" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员工资单</title>
</head>
<body>
<h1>被查询员工工资单</h1>
<br>
<%
    double commonEmployeeSalary = (double) session.getAttribute("commonEmployeeSalary");
    ArrayList<Salary> salaries = (ArrayList<Salary>) session.getAttribute("salaries");
%>
被查询员工基础月工资为<%= commonEmployeeSalary %>
<br>
<%
    if (salaries.isEmpty()) {
%>
无工资信息
<br>
<%
} else {
    for (Salary salary : salaries) {
%>
<table>
    <tr>
        <td>用户名：</td>
        <td><%= salary.getId() %>
        </td>
    </tr>
    <tr>
        <td>时间：</td>
        <td><%= salary.getTime() %>
        </td>
    </tr>
    <tr>
        <td>工资：</td>
        <td><%= salary.getSalary() %>
        </td>
    </tr>
</table>
<br><br><br>
<%
        }
    }
%>
<br>
<a href="/page/main-admin.jsp">返回管理员主页</a>
</body>
</html>
