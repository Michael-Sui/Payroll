package servlet;

import bean.PurchaseOrder;
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
 * Created by Michael on 2017/6/8.
 */
@WebServlet(name = "PurchaseOrderServlet", urlPatterns = {"/page/PurchaseOrderServlet"})
public class PurchaseOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(PersonalInformationServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            HttpSession httpSession = request.getSession();
            String id = httpSession.getAttribute("user").toString();
            Database database = new Database();
            database.connect();
            ArrayList<PurchaseOrder> purchaseOrders = new ArrayList<>();
            purchaseOrders = database.getPurchaseOrder(id);
            database.disconnect();
            httpSession.setAttribute("purchaseOrders", purchaseOrders);
            LOG.info(id + "获取订单成功");
            response.sendRedirect("/page/purchaseOrder.jsp");
        } catch (Exception e) {
            LOG.error("抛出了异常");
            response.sendRedirect("/page/error.jsp");
        }
    }
}
