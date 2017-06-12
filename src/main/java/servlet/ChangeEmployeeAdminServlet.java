package servlet;

import bean.CommonEmployeeSalary;
import bean.HourlyEmployeeSalary;
import bean.PersonalInformation;
import bean.User;
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
 * Created by Michael on 2017/6/12.
 */
@WebServlet(name = "ChangeEmployeeAdminServlet", urlPatterns = {"/page/ChangeEmployeeAdminServlet"})
public class ChangeEmployeeAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(ChangeEmployeeAdminServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String id = request.getParameter("id");
            HttpSession httpSession = request.getSession();
            CommonEmployeeSalary commonEmployeeSalary = null;
            HourlyEmployeeSalary hourlyEmployeeSalary = null;
            Database database = new Database();
            database.connect();
            boolean flag = database.isHaveUserById(id);
            if (flag) {
                User user = database.getUser(id);
                PersonalInformation personalInformation = database.getPersonalInformation(id);
                int employeeType = database.getEmployeeType(id);
                switch (employeeType) {
                    case 0:
                        commonEmployeeSalary = new CommonEmployeeSalary();
                        commonEmployeeSalary.setId(id);
                        commonEmployeeSalary.setSalary(database.getCommonEmployeeSalary(id));
                        httpSession.setAttribute("commonEmployeeSalary", commonEmployeeSalary);
                    case 1:
                        hourlyEmployeeSalary = new HourlyEmployeeSalary();
                        hourlyEmployeeSalary.setId(id);
                        hourlyEmployeeSalary.setSalary(database.getHourlyEmployeeSalary(id));
                        httpSession.setAttribute("hourlyEmployeeSalary", hourlyEmployeeSalary);
                }
                database.disconnect();
                httpSession.setAttribute("user", user);
                httpSession.setAttribute("personalInformation", personalInformation);
                httpSession.setAttribute("employeeType", employeeType);
                LOG.info("获取员工信息成功");
                response.sendRedirect("changeEmployeeDetails-admin.jsp");
            } else {
                response.sendRedirect("/page/error.jsp");
            }
        } catch (Exception e) {
            LOG.error("获取员工信息抛出异常");
            response.sendRedirect("/page/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
