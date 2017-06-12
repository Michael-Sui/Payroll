<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>生成管理员报告页面</title>
</head>
<body>
<h1>请在这里完善被查询人的信息</h1>
<br>
<form method="post" action="CreateAdminReportAdminServlet">
    <table>
        <tr>
            <td>被查询人用户名</td>
            <td><input type="text" name="id" required></td>
        </tr>
        <tr>
            <td>查询方式</td>
            <td>
                <select name="inquireMode">
                    <option value="inquireByDay">查询某一天</option>
                    <option value="inquireByMonth">查询某一月</option>
                    <option value="inquireAll">查询个人全部工资信息</option>
                    <option value="all">查询全部工资信息</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>具体信息：</td>
            <td><input type="text" name="details"></td>
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
