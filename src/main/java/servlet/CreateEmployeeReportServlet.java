package servlet;

import bean.Salary;
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
import java.util.ArrayList;

/**
 * Created by Michael on 2017/6/9.
 */
@WebServlet(name = "CreateEmployeeReportServlet", urlPatterns = {"/page/CreateEmployeeReportServlet"})
public class CreateEmployeeReportServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(CreateEmployeeReportServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String requireMode = request.getParameter("inquireMode");
            String details = request.getParameter("details");
            HttpSession httpSession = request.getSession();
            String id = httpSession.getAttribute("user").toString();
            int employeeType = Integer.valueOf(httpSession.getAttribute("employeeType").toString());
            Database database = new Database();
            database.connect();
            switch (employeeType) {
                case 0:
                    double commonEmployeeSalary = database.getCommonEmployeeSalary(id);
                    ArrayList<Salary> salaries = database.getAllSalaryWithEmployeeType0(id, requireMode, details);
                    httpSession.setAttribute("commonEmployeeSalary", commonEmployeeSalary);
                    httpSession.setAttribute("salaries", salaries);
                    response.sendRedirect("/page/salaryWithEmployeeType0.jsp");
                    break;
                case 1:
                    switch (requireMode) {
                        case "requireByDay":

                        case "requireByMonth":

                        case "requireAll":

                        default:

                    }
                case 2:
                    switch (requireMode) {
                        case "requireByDay":

                        case "requireByMonth":

                        case "requireAll":

                        default:

                    }
            }
            database.disconnect();
        } catch (Exception e) {
            LOG.error("抛出了异常");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
