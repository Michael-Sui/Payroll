package servlet;

import utils.Database;
import utils.TimeCardState;

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
@WebServlet(name = "TimeCardServlet", urlPatterns = {"/page/TimeCardServlet"})
public class TimeCardServlet extends HttpServlet {
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
        TimeCardState timeCardState = database.getTimeCardState(id);
        database.disconnect();
        httpSession.setAttribute("timeCardState", timeCardState);

        response.sendRedirect("/page/timeCard.jsp");
    }
}
