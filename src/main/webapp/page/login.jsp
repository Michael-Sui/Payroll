
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <h1>请您先进行登录操作</h1>
    <br>
    <form method="post" action="LoginServlet">
        <table>
            <tr>
                <td>用户名：</td>
                <td><input type="text" name="id" required></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="登录" /></td>
            </tr>
        </table>
    </form>
</body>
</html>
