package filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Michael on 2017/6/2.
 */
@javax.servlet.annotation.WebFilter(filterName = "AuthorityFilter", urlPatterns = "/*")
public class AuthorityFilter implements javax.servlet.Filter {
    public void destroy() {}

    public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response,
                         javax.servlet.FilterChain chain) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession();
        String requestPath = httpServletRequest.getServletPath();
        if (httpSession.getAttribute("user") == null &&
                !requestPath.endsWith("/index.jsp") &&
                !requestPath.endsWith("/page/login.jsp") &&
                !requestPath.endsWith("jpg")) {
            httpServletResponse.sendRedirect("/index.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(javax.servlet.FilterConfig config) throws javax.servlet.ServletException {}
}
