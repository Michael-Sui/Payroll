<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.Salary" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>普通员工工资单</title>
</head>
<body>
    <h1>普通员工工资单</h1>
    <br>
    <%
        double commonEmployeeSalary = (double) session.getAttribute("commonEmployeeSalary");
        ArrayList<Salary> salaries = (ArrayList<Salary>) session.getAttribute("salaries");
    %>
    你的基础月工资为<%= commonEmployeeSalary %>
    <br>
    <% if (salaries.isEmpty()) { %>
    无工资信息
    <%
    } else {
        for (Salary salary : salaries) {
    %>
    <table>
        <tr>
            <td>用户名：</td>
            <td><%= salary.getId() %></td>
        </tr>
        <tr>
            <td>时间：</td>
            <td><%= salary.getTime() %></td>
        </tr>
        <tr>
            <td>工资：</td>
            <td><%= salary.getSalary() %></td>
        </tr>
    </table>
    <br><br><br>
    <% }
    } %>
</body>
</html>
