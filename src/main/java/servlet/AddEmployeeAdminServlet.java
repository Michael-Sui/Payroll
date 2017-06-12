package servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Michael on 2017/6/10.
 */
@WebServlet(name = "AddEmployeeAdminServlet", urlPatterns = {"/page/AddEmployeeAdminServlet"})
public class AddEmployeeAdminServlet extends HttpServlet {
    private String id;
    private String password;
    private String authority;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(AddEmployeeAdminServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            id = request.getParameter("id");
            password = request.getParameter("password");
            authority = request.getParameter("authority");
            Database database = new Database();
            database.connect();
            boolean flag = database.addEmployee(id, password, authority);
            database.disconnect();
            if (flag) {
                LOG.info("创建员工" + id + "," + password + "," + authority + "成功");
                response.sendRedirect("/page/Information-admin.jsp");
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            LOG.error("创建员工" + id + "," + password + "," + authority + "失败");
            response.sendRedirect("/page/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
