package servlet;

import bean.CommonEmployeeSalary;
import bean.HourlyEmployeeSalary;
import bean.PurchaseOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Michael on 2017/6/20.
 */
@WebServlet(name = "SettlementOfSalaryServlet", urlPatterns = {"/page/SettlementOfSalaryServlet"})
public class SettlementOfSalaryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(SettlementOfSalaryServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            Database database = new Database();
            database.connect();
            ArrayList<CommonEmployeeSalary> commonEmployeeSalaryArrayList = database.getAllCommonEmployeeSalary();
            database.wagesOfCommonEmployee(commonEmployeeSalaryArrayList, month);
            ArrayList<HourlyEmployeeSalary> hourlyEmployeeSalaryArrayList = database.getAllHourlyEmployeeSalary();
            database.wagesOfHourlyEmployee(hourlyEmployeeSalaryArrayList);
            ArrayList<PurchaseOrder> purchaseOrderArrayList = database.getAllPurchaseOrder();
            database.wagesOfSaleEmployee(purchaseOrderArrayList);
            database.disconnect();
            LOG.info("工资发放成功");
            response.sendRedirect("/page/main-admin.jsp");
        } catch (Exception e) {
            LOG.error("工资发放抛出异常");
        }
    }
}
