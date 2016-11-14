package com.luos.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户请求过滤器
 * <p>
 * Created by luos on 2016/11/1.
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (shouldFilt(request)) {
            //如果过滤掉，回跳到login.jsp页面
            response.sendRedirect("login.jsp");
        } else {
            //否则继续做下一次过滤
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 是否应该过滤掉
     *
     * @param request
     * @return
     */
    private boolean shouldFilt(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object object = session.getAttribute("current");
        String path = request.getServletPath();
        return object == null
                && path.indexOf("login") < 0
                && path.indexOf("bootstrap-3.3.7-dist") < 0
                && path.indexOf("images") < 0;
    }

}
