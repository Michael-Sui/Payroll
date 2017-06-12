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
 * Created by Michael on 2017/6/12.
 */
@WebServlet(name = "DeleteEmployeeAdminServlet", urlPatterns = {"/page/DeleteEmployeeAdminServlet"})
public class DeleteEmployeeAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(DeleteEmployeeAdminServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String id1 = request.getParameter("id1");
            String id2 = request.getParameter("id2");
            if (!id1.equals(id2)) {
                LOG.warn("两次输入删除员工的用户名不一致");
                response.sendRedirect("/page/error.jsp");
                return;
            }
            Database database = new Database();
            database.connect();
            database.deleteEmployee(id1);
            database.disconnect();
            LOG.info("删除员工" + id1 + "成功");
            response.sendRedirect("/page/Information-admin.jsp");
        } catch (Exception e) {
            LOG.error("删除员工失败");
            response.sendRedirect("/page/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
