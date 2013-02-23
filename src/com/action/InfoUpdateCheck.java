package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDAO;
import com.dao.UserExamDAO;
import com.dao.impl.UserDAOImpl;
import com.dao.impl.UserExamDAOImpl;
import com.pojo.User;
import com.util.MD5Util;

public class InfoUpdateCheck extends HttpServlet
{
    private static final long serialVersionUID = 1616264730326316034L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            req.setCharacterEncoding("utf-8");
        }
        catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        try
        {
            UserDAO ud = new UserDAOImpl();
            HttpSession hs = req.getSession();
            String login = (String) hs.getAttribute("login");
            if(login==null||login.equals(""))
            {
                return;
            }
            String id = req.getParameter("userId");
            User user = ud.queryUserById(id);
            
            String delete = req.getParameter("delete");
            String active = req.getParameter("active");
            String inactive = req.getParameter("inactive");
            String update = req.getParameter("update");
            String repwd = req.getParameter("repwd");
            
            
            if (user != null)
            {
                if(delete!=null)
                {
                    ud.deleteUserById(id);
                    resp.sendRedirect("admin/userAdmin.jsp");
                }
                else if(active!=null)
                {
                    user.setStatus("0");
                    ud.updateUserById(id, user);
                    resp.sendRedirect("admin/userAdmin.jsp");
                }
                else if(inactive!=null)
                {
                    user.setStatus("1");
                    ud.updateUserById(id, user);
                    resp.sendRedirect("admin/userAdmin.jsp");
                }
                else if(repwd!=null)
                {
                    String defaultPwd = "000000";
                    String pwd = MD5Util.getMD5(id+defaultPwd);
                    user.setPasswd(pwd);

                    ud.updateUserById(id, user);
                    resp.sendRedirect("admin/userAdmin.jsp");
                }
                else
                {
                    user.setName(req.getParameter("userName"));
                    user.setDept(Integer.parseInt(req.getParameter("dept")));
                    user.setTelephone(req.getParameter("telNo"));
                    ud.updateUserById(id, user);
                    if (login.equals("user"))
                    {
                        hs.removeAttribute("userName");
                        hs.setAttribute("userName", req.getParameter("userName"));
                        resp.sendRedirect("examinee/examMain.jsp");
                    }
                    else
                    {
                        resp.sendRedirect("admin/userAdmin.jsp");
                    }
                }
            }
            else
            {
                return;
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
