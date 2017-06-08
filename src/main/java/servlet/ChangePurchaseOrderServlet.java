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

/**
 * Created by Michael on 2017/6/8.
 */
@WebServlet(name = "ChangePurchaseOrderServlet", urlPatterns = {"/page/ChangePurchaseOrderServlet"})
public class ChangePurchaseOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(ChangePurchaseOrderServlet.class);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String changePurchaseOrderId = request.getParameter("changePurchaseOrderId");
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            Database database = new Database();
            database.connect();
            purchaseOrder = database.getPurchaseOrderByOrderId(changePurchaseOrderId);
            database.disconnect();
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("purchaseOrder", purchaseOrder);

            response.sendRedirect("/page/changePurchaseOrderDetails.jsp");
        } catch (Exception e) {
            LOG.error("抛出了异常");
            response.sendRedirect("/page/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
