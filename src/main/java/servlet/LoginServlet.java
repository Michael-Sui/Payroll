package servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Database;
import utils.UserAuthority;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Michael on 2017/6/6.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/page/LoginServlet"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(LoginServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            HttpSession httpSession = request.getSession();
            String id = request.getParameter("id");
            String password = request.getParameter("password");
            Database database = new Database();
            database.connect();
            UserAuthority userAuthority = database.isLogin(id, password);
            int employeeType = database.getEmployeeType(id);
            database.disconnect();
            httpSession.setAttribute("user", id);
            httpSession.setAttribute("authority", userAuthority.toString());
            httpSession.setAttribute("employeeType", employeeType);
            switch (userAuthority) {
                case ADMIN:
                    LOG.info("ADMIN:" + id + "登陆");
                    response.sendRedirect("/page/main-admin.jsp");
                    break;
                case GUEST:
                    LOG.info("GUEST:" + id + "登陆");
                    response.sendRedirect("/page/main.jsp");
                    break;
                case ERROR:
                    LOG.info("ERROR:" + id + "登陆");
                    response.sendRedirect("/page/error.jsp");
                    break;
                default:
                    LOG.error("未知用户身份:" + id + "登陆");
                    response.sendRedirect("/page/error.jsp");
                    break;
            }
        } catch (Exception e) {
            LOG.error("抛出了异常");
            response.sendRedirect("/page/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
