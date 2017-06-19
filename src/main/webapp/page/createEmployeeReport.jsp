<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>普通用户工资单</title>
</head>
<body>
<h1>请选择工资单查询方式</h1>
<br>
<%
    int employeeType = (int) session.getAttribute("employeeType");
%>
<form method="post" action="CreateEmployeeReportServlet">
    <table>
        <tr>
            <td>查询方式：</td>
            <%
                switch (employeeType) {
                    case 0:
            %>
            <td>
                <select name="inquireMode">
                    <option value="inquireByMonth">查询某一月</option>
                    <option value="inquireAll">查询个人全部工资信息</option>
                </select>
            </td>
            <%
                    break;
                default:
            %>
            <td>
                <select name="inquireMode">
                    <option value="inquireByDay">查询某一天</option>
                    <option value="inquireByMonth">查询某一月</option>
                    <option value="inquireAll">查询个人全部工资信息</option>
                </select>
            </td>
            <%
                        break;
                }
            %>
        </tr>
        <tr>
            <td>具体信息：</td>
            <td><input type="text" name="details" maxlength="20"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="确定"></td>
        </tr>
    </table>
</form>
<br>
<a href="/page/main.jsp">返回普通用户主页</a>
</body>
</html>
