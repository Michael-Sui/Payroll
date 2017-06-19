<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>删除员工页面</title>
</head>
<body>
<h1>请在这里输入被删除人的信息</h1>
<br>
<form method="post" action="DeleteEmployeeAdminServlet">
    <table>
        <tr>
            <td>被删除人的用户名：</td>
            <td><input type="text" name="id1" maxlength="20" required></td>
        </tr>
        <tr>
            <td>请重复输入被删除人的用户名：</td>
            <td><input type="text" name="id2" maxlength="20" required></td>
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
