<%@ page import="utils.TimeCardState" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <% TimeCardState timeCardState = (TimeCardState) session.getAttribute("timeCardState"); %>
    <%= timeCardState.toString() %>
</body>
</html>
