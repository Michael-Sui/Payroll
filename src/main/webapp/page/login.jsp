
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <h1>请您先进行登录操作</h1>
    <form method="post" action="LoginServlet">
        用户名：<input type="text" name="id">
        <br>
        密码：<input type="password" name="password">
        <br>
        <input type="submit" value="登录" />
    </form>
</body>
</html>
