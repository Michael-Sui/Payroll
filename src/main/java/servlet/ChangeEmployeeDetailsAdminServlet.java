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
@WebServlet(name = "ChangeEmployeeDetailsAdminServlet", urlPatterns = {"/page/ChangeEmployeeDetailsAdminServlet"})
public class ChangeEmployeeDetailsAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(ChangeEmployeeDetailsAdminServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            HttpSession httpSession = request.getSession();
            User user = new User();
            String id = request.getParameter("id");
            user.setId(id);
            user.setPassword(request.getParameter("password"));
            user.setAuthority(request.getParameter("authority"));
            PersonalInformation personalInformation = new PersonalInformation();
            personalInformation.setId(id);
            personalInformation.setName(request.getParameter("name"));
            personalInformation.setGender(request.getParameter("gender"));
            personalInformation.setPhoneNumber(request.getParameter("phoneNumber"));
            personalInformation.setEmail(request.getParameter("email"));
            personalInformation.setAge(Integer.valueOf(request.getParameter("age")));
            personalInformation.setAddress(request.getParameter("address"));
            personalInformation.setPaymentMethod(Integer.valueOf(request.getParameter("paymentMethod")));
            personalInformation.setEmployeeType(Integer.valueOf(request.getParameter("employeeType")));
            int employeeType = (int) httpSession.getAttribute("employeeType");
            CommonEmployeeSalary commonEmployeeSalary = null;
            HourlyEmployeeSalary hourlyEmployeeSalary = null;
            switch (employeeType) {
                case 0:
                    commonEmployeeSalary = new CommonEmployeeSalary();
                    commonEmployeeSalary.setId(id);
                    commonEmployeeSalary.setSalary(Double.valueOf(request.getParameter("commonSalary")));
                    break;
                case 1:
                    hourlyEmployeeSalary = new HourlyEmployeeSalary();
                    hourlyEmployeeSalary.setId(id);
                    hourlyEmployeeSalary.setSalary(Double.valueOf(request.getParameter("hourlySalary")));
                    break;
            }
            Database database = new Database();
            database.connect();
            database.updateUser(user);
            database.updatePersonalInformation(personalInformation);
            switch (employeeType) {
                case 0:
                    database.updateCommonEmployeeSalary(commonEmployeeSalary);
                    break;
                case 1:
                    database.updateHourlyEmployeeSalary(hourlyEmployeeSalary);
                    break;
            }
            database.disconnect();
            LOG.info(id + "更新信息成功");
            response.sendRedirect("/page/Information-admin.jsp");
        } catch (Exception e) {
            LOG.error("更新信息失败");
            response.sendRedirect("/page/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
