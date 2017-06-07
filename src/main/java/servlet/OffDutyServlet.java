package servlet;

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession httpSession = request.getSession();
        String id = httpSession.getAttribute("user").toString();
        Database database = new Database();
        database.connect();
        database.offDuty(id);
        database.disconnect();

        response.sendRedirect("/page/TimeCardServlet");
    }
}
