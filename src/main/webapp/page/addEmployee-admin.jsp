<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新建员工页面</title>
</head>
<body>
<h1>请在这里编辑新员工的信息</h1>
<br>
<form method="post" action="AddEmployeeAdminServlet">
    <table>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="id" required></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="text" name="password" required></td>
        </tr>
        <tr>
            <td>权限：</td>
            <td><input type="text" name="authority" required></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="确定"></td>
        </tr>
    </table>
</form>
<br>
<a href="/page/main-admin.jsp">返回管理员主页</a>
</body>
</html>
