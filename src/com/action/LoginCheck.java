package com.action;

import java.io.IOException;
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

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            UserDAO ud = new UserDAOImpl();
            String id = req.getParameter("id");
            String passWd = req.getParameter("token");
            String rand = req.getParameter("rand");
            User user = ud.queryUserById(id);
            HttpSession hs = req.getSession();

            String loginUser = null;
            
            if (user == null)
            {
                AdminDAO ad = new AdminDAOImpl();
                Admin admin = ad.queryAdminById(id);
                if (admin == null)
                {
                    loginUser = "fail";  
                }
                else
                {
                    String passMd5 = MD5Util.getMD5(admin.getPasswd() + rand);
                    if (passMd5.equals(passWd))
                    {
                        hs.setAttribute("id", admin.getId());
                        hs.setAttribute("userName", admin.getId());
                        loginUser = "admin";
                    } else
                    {
                        loginUser = "fail";
                    }

                } 
            }
            else
            {
                String passMd5 = MD5Util.getMD5(user.getPasswd() + rand);

                if (passMd5.equals(passWd))
                {
                    if (user.getStatus().equals("1"))
                    {
                        loginUser = "inactive";
                    } else
                    {
                        hs.setAttribute("id", user.getId());
                        hs.setAttribute("userName", user.getName());
                        loginUser = "user";
                    }
                } else
                {
                    loginUser = "fail";
                }
            }

            if("fail".equals(loginUser) || "inactive".equals(loginUser))
            {
                hs.setAttribute("login", "fail");
                resp.sendRedirect("login.jsp");
            }
            else
            {
                hs.setAttribute("login", loginUser);
                if("admin".equals(loginUser))
                {
                    resp.sendRedirect("admin/admin.jsp");
                }
                else
                {
                    resp.sendRedirect("examinee/examinee.jsp");
                }
            }
            

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        doPost(req, resp);
    }
}
