package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDAO;
import com.dao.impl.UserDAOImpl;
import com.pojo.User;
import com.util.MD5Util;

public class RegCheck extends HttpServlet
{
    private static final long serialVersionUID = 1179683565716593757L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        UserDAO ud = new UserDAOImpl();
        String id = req.getParameter("id");
        if (ud.queryUserById(id) == null)
        {
            String userName = req.getParameter("userName");
            String passwd = req.getParameter("token");
            String dept = req.getParameter("dept");
            String telNo = req.getParameter("telNo");
            User user = new User();
            user.setId(id);
            user.setName(userName);
            user.setPasswd(passwd);
            user.setDept(Integer.parseInt(dept));
            user.setTelephone(telNo);
            user.setStatus("1");
            ud.addUser(user);
            try
            {
                HttpSession hs = req.getSession();
                hs.setAttribute("reg", "succ");
                resp.sendRedirect("login.jsp");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } else
        {
            try
            {
                HttpSession hs = req.getSession();
                hs.setAttribute("reg", "fail");
                resp.sendRedirect("register.jsp");
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        doGet(req, resp);
    }
}
