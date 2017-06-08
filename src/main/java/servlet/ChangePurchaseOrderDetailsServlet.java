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
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by Michael on 2017/6/8.
 */
@WebServlet(name = "ChangePurchaseOrderDetailsServlet", urlPatterns = {"/page/ChangePurchaseOrderDetailsServlet"})
public class ChangePurchaseOrderDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(ChangePurchaseOrderDetailsServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setId(request.getParameter("id"));
            purchaseOrder.setOrderId(request.getParameter("orderId"));
            purchaseOrder.setTime(Timestamp.valueOf(request.getParameter("time")));
            purchaseOrder.setMoney(Double.valueOf(request.getParameter("money")));
            purchaseOrder.setProportion(Double.valueOf(request.getParameter("proportion")));
            Database database = new Database();
            database.connect();
            database.changePurchaseOrder(purchaseOrder);
            database.disconnect();

            response.sendRedirect("/page/PurchaseOrderServlet");
        } catch (Exception e) {
            LOG.error("抛出了异常");
            response.sendRedirect("/page/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
