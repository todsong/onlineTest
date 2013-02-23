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

public class PassWdCheck extends HttpServlet
{
    private static final long serialVersionUID = 1616264730326316034L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            
            HttpSession hs = req.getSession();
            String login = (String) hs.getAttribute("login");
            if(login==null || login.equals(""))
            {
                return;
            }
            String id = (String) hs.getAttribute("id");
            String passWdOld = req.getParameter("passWdOld");
            String passMd5 = MD5Util.getMD5(id + passWdOld);
            String passWdNew = req.getParameter("passWdNew");
            String passwd = MD5Util.getMD5(id+passWdNew);
            
            
            if (login.equals("user"))
            {
                UserDAO ud = new UserDAOImpl();
                User user = ud.queryUserByPwd(id, passMd5);
                if(user!=null)
                {
                    user.setPasswd(passwd);
                    ud.updateUserById(id, user);
                    resp.sendRedirect("examinee/examMain.jsp");
                }
                else
                {
                    hs.setAttribute("passUp", "fail");
                    resp.sendRedirect("examinee/passwd.jsp");
                }
            }
            else if(login.equals("admin"))
            {
                AdminDAO ad = new AdminDAOImpl();
                Admin admin = ad.queryAdminByPwd(id, passMd5);
                if(admin!=null)
                {
                    admin.setPasswd(passwd);
                    ad.updateAdminById(id, admin);
                    resp.sendRedirect("admin/main.jsp");
                }
                else
                {
 //                   System.out.println("xx");
                    hs.setAttribute("passUp", "fail");
                    resp.sendRedirect("admin/passwd.jsp");
                }
            }
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
