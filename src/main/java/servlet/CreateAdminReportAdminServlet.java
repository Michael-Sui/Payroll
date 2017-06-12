package servlet;

import bean.PurchaseOrder;
import bean.Salary;
import bean.TimeCard;
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
 * Created by Michael on 2017/6/12.
 */
@WebServlet(name = "CreateAdminReportAdminServlet", urlPatterns = {"/page/CreateAdminReportAdminServlet"})
public class CreateAdminReportAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(CreateAdminReportAdminServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String id = request.getParameter("id");
            String inquireMode = request.getParameter("inquireMode");
            String details = request.getParameter("details");
            HttpSession httpSession = request.getSession();
            Database database = new Database();
            database.connect();
            switch (inquireMode) {
                case "all":
                    ArrayList<Salary> allSalaries = database.getAllSalaries();
                    database.disconnect();
                    httpSession.setAttribute("allSalaries", allSalaries);
                    response.sendRedirect("/page/allSalaries-admin.jsp");
                    break;
                default:
                    int employeeType = database.getEmployeeType(id);
                    switch (employeeType) {
                        case 0:
                            double commonEmployeeSalary = database.getCommonEmployeeSalary(id);
                            ArrayList<Salary> salaries = database.getAllSalaryWithEmployeeType0(id, inquireMode, details);
                            database.disconnect();
                            httpSession.setAttribute("commonEmployeeSalary", commonEmployeeSalary);
                            httpSession.setAttribute("salaries", salaries);
                            response.sendRedirect("/page/salaryWithEmployeeType0-admin.jsp");
                            break;
                        case 1:
                            double hourlyEmployeeSalary = database.getHourlyEmployeeSalary(id);
                            ArrayList<TimeCard> timeCards = database.getTimeCardByDay(id, inquireMode, details);
                            database.disconnect();
                            httpSession.setAttribute("hourlyEmployeeSalary", hourlyEmployeeSalary);
                            httpSession.setAttribute("timeCards", timeCards);
                            response.sendRedirect("/page/salaryWithEmployeeType1-admin.jsp");
                            break;
                        case 2:
                            ArrayList<PurchaseOrder> purchaseOrders = database.getSaleEmployeeSalary(id, inquireMode, details);
                            database.disconnect();
                            httpSession.setAttribute("purchaseOrders", purchaseOrders);
                            response.sendRedirect("/page/salaryWithEmployeeType2-admin.jsp");
                            break;
                    }
                    break;
            }
        } catch (Exception e) {
            LOG.error("生成管理员报告抛出异常");
            response.sendRedirect("/page/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
