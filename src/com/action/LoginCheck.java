package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.AdminDAO;
import com.dao.UserDAO;
import com.dao.impl.AdminDAOImpl;
import com.dao.impl.UserDAOImpl;
import com.pojo.Admin;
import com.pojo.User;
import com.util.MD5Util;

public class LoginCheck extends HttpServlet
{
    private static final long serialVersionUID = 4105534961881150793L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
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
            String passWd = req.getParameter("token");
            String rand = req.getParameter("rand");
            User user = ud.queryUserById(id);
            HttpSession hs = req.getSession();

            if (user != null)
            {
                String passMd5 = MD5Util.getMD5(user.getPasswd()+rand);
//                System.out.println(user.getPasswd()+","+rand);
//                System.out.println(passMd5+"="+passWd);
                
                if(passMd5.equals(passWd))
                {
                    if (user.getStatus().equals("1"))
                    {
                        hs = req.getSession();
                        hs.setAttribute("login", "inactive");
                        resp.sendRedirect("login.jsp");
                    } else
                    {
                        hs.setAttribute("id", user.getId());
                        hs.setAttribute("userName", user.getName());
                        hs.setAttribute("login", "user");
                        resp.sendRedirect("examinee/examinee.jsp");
                    }    
                }
                else
                {
                    hs.setAttribute("login", "fail");
                    resp.sendRedirect("login.jsp");
                }
            }
            else
            {
                AdminDAO ad = new AdminDAOImpl();
                Admin admin = ad.queryAdminById(id);
                if (admin != null)
                {
                    String passMd5 = MD5Util.getMD5(admin.getPasswd()+rand);
                    if(passMd5.equals(passWd))
                    {
                        hs.setAttribute("id", admin.getId());
                        hs.setAttribute("userName", admin.getId());
                        hs.setAttribute("login", "admin");
                        resp.sendRedirect("admin/admin.jsp");
                    }
                    else
                    {
                        hs.setAttribute("login", "fail");
                        resp.sendRedirect("login.jsp");    
                    }
                    
                } else
                {
                    hs.setAttribute("login", "fail");
                    resp.sendRedirect("login.jsp");
                }
         
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        doGet(req, resp);
    }

}
