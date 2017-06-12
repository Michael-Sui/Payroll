<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.TimeCard" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员工资单</title>
</head>
<body>
<h1>被查询计时员工工资单</h1>
<br>
<%
    double hourlyEmployeeSalary = (double) session.getAttribute("hourlyEmployeeSalary");
    ArrayList<TimeCard> timeCards = (ArrayList<TimeCard>) session.getAttribute("timeCards");
    double timeCount = 0;
    double startTime = 0;
    double endTime = 0;
%>
查询计时员工时薪是：<%= hourlyEmployeeSalary %>
<%
    if (timeCards.isEmpty()) {
%>
无工资信息
<br>
<%
} else {
    for (TimeCard timeCard : timeCards) {
%>
<table>
    <tr>
        <td>用户名：</td>
        <td><%= timeCard.getId() %>
        </td>
    </tr>
    <tr>
        <td>时间：</td>
        <td><%= timeCard.getTime() %>
        </td>
    </tr>
    <tr>
        <td>状态：</td>
        <td>
            <%
                if (timeCard.getFlag() == 0) {
                    startTime = timeCard.getTime().getHours();
            %>
            上班
            <%
            } else {
                endTime = timeCard.getTime().getHours();
                timeCount += endTime - startTime;
            %>
            下班
            <% } %>
        </td>
    </tr>
</table>
<%
        }
    }
%>
<br><br><br>
查询计时员工的总计工作时间为：<%= timeCount %>
<br>
总计工资为：<%= timeCount * hourlyEmployeeSalary %>
<br>
<a href="/page/main-admin.jsp">返回管理员主页</a>
</body>
</html>
