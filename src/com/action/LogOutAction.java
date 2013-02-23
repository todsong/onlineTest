package com.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDAO;
import com.dao.impl.UserDAOImpl;
import com.pojo.User;
import com.util.MD5Util;

public class LogOutAction extends HttpServlet
{
    private static final long serialVersionUID = 4105535261881150793L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            HttpSession hs = req.getSession();
            hs.invalidate();
            PrintWriter out = resp.getWriter();
            out.write("<script>window.parent.location.href='/onlineTest/login.jsp';</script>");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        doGet(req, resp);
    }

}
