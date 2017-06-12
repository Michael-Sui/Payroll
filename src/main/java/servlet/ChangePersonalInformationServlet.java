package servlet;

import bean.PersonalInformation;
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
@WebServlet(name = "ChangePersonalInformationServlet", urlPatterns = {"/page/ChangePersonalInformationServlet"})
public class ChangePersonalInformationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(ChangePersonalInformationServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            HttpSession httpSession = request.getSession();
            PersonalInformation personalInformation = new PersonalInformation();
            personalInformation.setId(httpSession.getAttribute("user").toString());
            personalInformation.setName(request.getParameter("name"));
            personalInformation.setGender(request.getParameter("gender"));
            personalInformation.setPhoneNumber(request.getParameter("phoneNumber"));
            personalInformation.setEmail(request.getParameter("email"));
            personalInformation.setAge(Integer.valueOf(request.getParameter("age")));
            personalInformation.setAddress(request.getParameter("address"));
            personalInformation.setPaymentMethod(Integer.valueOf(request.getParameter("paymentMethod")));
            personalInformation.setEmployeeType(Integer.valueOf(request.getParameter("employeeType")));
            Database database = new Database();
            database.connect();
            database.updatePersonalInformation(personalInformation);
            database.disconnect();
            response.sendRedirect("/page/PersonalInformationServlet");
        } catch (Exception e) {
            LOG.error("抛出了异常");
            response.sendRedirect("/page/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
