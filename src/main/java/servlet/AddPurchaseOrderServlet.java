package servlet;

import bean.PurchaseOrder;
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
@WebServlet(name = "AddPurchaseOrderServlet", urlPatterns = {"/page/AddPurchaseOrderServlet"})
public class AddPurchaseOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        database.addPurchaseOrder(purchaseOrder);
        database.disconnect();

        response.sendRedirect("/page/PurchaseOrderServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
