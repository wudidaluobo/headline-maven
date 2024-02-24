package src.headline.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import src.headline.common.Result;
import src.headline.common.ResultCodeEnum;
import src.headline.util.JwtHelper;
import src.headline.util.WebUtil;

import java.io.IOException;
@WebFilter("/headline/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        String token=request.getHeader("token");
        if(token!=null&& !JwtHelper.isExpiration(token))
        {
            filterChain.doFilter(request,response);
        }else
            WebUtil.writeJson(response, Result.build(null, ResultCodeEnum.NOTLOGIN));

    }
}
