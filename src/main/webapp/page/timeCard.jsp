<%@ page import="utils.TimeCardState" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上、下班打卡</title>
</head>
<body>
<% TimeCardState timeCardState = (TimeCardState) session.getAttribute("timeCardState"); %>
<% if (timeCardState.equals(TimeCardState.OFF_DUTY)) { %>
<h1>您处于非工作状态，欢迎回到工作岗位</h1>
<br>
<a href="/page/OnDutyServlet">上班打卡</a>
<% } else if (timeCardState.equals(TimeCardState.ON_DUTY)) { %>
<h1>您处于工作状态，感谢您的辛苦付出</h1>
<br>
<a href="/page/OffDutyServlet">下班打卡</a>
<% } %>
<br>
<a href="/page/main.jsp">返回普通用户主页</a>
</body>
</html>
