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
@WebServlet(name = "FindExistPurchaseOrderServlet", urlPatterns = {"/page/FindExistPurchaseOrderServlet"})
public class FindExistPurchaseOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(FindExistPurchaseOrderServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            HttpSession httpSession = request.getSession();
            String id = httpSession.getAttribute("user").toString();
            ArrayList<PurchaseOrder> purchaseOrders = new ArrayList<>();
            Database database = new Database();
            database.connect();
            purchaseOrders = database.getPurchaseOrder(id);
            database.disconnect();
            httpSession.setAttribute("purchaseOrders", purchaseOrders);

            response.sendRedirect("/page/changePurchaseOrder.jsp");
        } catch (Exception e) {
            LOG.error("抛出了异常");
            response.sendRedirect("/page/error.jsp");
        }
    }
}
