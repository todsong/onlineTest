package com.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExamineeVerifyFilter implements Filter
{
    @Override
    public void destroy()
    {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession hs = request.getSession();
        String login = (String) hs.getAttribute("login");
        if(login==null || !login.equals("user"))
        {
            HttpServletResponse response = (HttpServletResponse)resp;
            response.sendRedirect("/onlineTest/login.jsp");
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException
    {
    }

}
