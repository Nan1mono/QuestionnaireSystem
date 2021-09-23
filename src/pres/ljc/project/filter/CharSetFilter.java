package pres.ljc.project.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 用于将所有POST下request获得的数据转换成UTF-8格式         注：GET请求默认为UTF-8格式
 */
@WebFilter("/*")
public class CharSetFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (httpServletRequest.getMethod().equalsIgnoreCase("POST")) {
            httpServletRequest.setCharacterEncoding("UTF-8");
        }
        filterChain.doFilter(httpServletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
