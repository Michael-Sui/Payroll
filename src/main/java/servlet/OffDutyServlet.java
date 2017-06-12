package servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Michael on 2017/6/7.
 */
@WebServlet(name = "OffDutyServlet", urlPatterns = {"/page/OffDutyServlet"})
public class OffDutyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(OffDutyServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            HttpSession httpSession = request.getSession();
            String id = httpSession.getAttribute("user").toString();
            Database database = new Database();
            database.connect();
            database.offDuty(id);
            database.disconnect();
            LOG.info("更新下班状态成功");
            response.sendRedirect("/page/TimeCardServlet");
        } catch (Exception e) {
            LOG.error("更新下班状态抛出异常");
            response.sendRedirect("/page/error.jsp");
        }
    }
}
