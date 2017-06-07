package servlet;

import bean.PersonalInformation;
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession httpSession = request.getSession();
        String id = httpSession.getAttribute("user").toString();
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        int age = Integer.valueOf(request.getParameter("age"));
        String address = request.getParameter("address");
        int paymentMethod = Integer.valueOf(request.getParameter("paymentMethod"));
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setId(id);
        personalInformation.setName(name);
        personalInformation.setGender(gender);
        personalInformation.setPhoneNumber(phoneNumber);
        personalInformation.setEmail(email);
        personalInformation.setAge(age);
        personalInformation.setAddress(address);
        personalInformation.setPaymentMethod(paymentMethod);
        Database database = new Database();
        database.connect();
        database.updatePersonalInformation(personalInformation);
        database.disconnect();

        response.sendRedirect("/page/PersonalInformationServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
