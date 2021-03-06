package filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.UserAuthority;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Michael on 2017/6/10.
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = "/*")
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Logger LOG = LogManager.getLogger(AdminFilter.class);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpSession httpSession = httpServletRequest.getSession();
            String userAuthority = (String) httpSession.getAttribute("authority");
            String requestPath = httpServletRequest.getServletPath();
            if (requestPath.endsWith("-admin.jsp") && !userAuthority.equals("ADMIN")) {
                LOG.warn("拦截了一个请求");
                httpServletResponse.sendRedirect("/index.jsp");
            } else {
                LOG.info("通过了一个请求");
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            LOG.warn("抛出了一个异常");
            httpServletResponse.sendRedirect("/index.jsp");
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
