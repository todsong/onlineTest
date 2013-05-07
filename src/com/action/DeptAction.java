package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DeptDAO;
import com.dao.UserDAO;
import com.dao.impl.DeptDAOImpl;
import com.dao.impl.UserDAOImpl;
import com.pojo.Dept;
import com.pojo.User;

public class DeptAction extends HttpServlet
{
    private static final long serialVersionUID = 2847153298264783086L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        String deptName = req.getParameter("deptName");
        String addD = req.getParameter("addD");
        String delD = req.getParameter("delD");
        String upD = req.getParameter("upD");
        DeptDAO dd = new DeptDAOImpl();

        if (addD != null)
        {
            Dept dept = new Dept();
            dept.setName(deptName);
            if(dd.quertDeptByName(deptName)==null)
                dd.addDept(dept);
            else
            {
                try
                {
                    resp.sendRedirect("dept.jsp?addError=name");
                    return;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        } else if (upD != null)
        {
            int id = Integer.parseInt(req.getParameter("deptId"));
            Dept dept = new Dept();
            dept.setName(deptName);
            dd.updateDeptById(id, dept);
        } else if (delD != null)
        {
            int id = Integer.parseInt(req.getParameter("deptId"));
            UserDAO ud = new UserDAOImpl();
            List<User> userList = ud.queryUserByDept(id);
            if (userList == null || userList.size() == 0)
            {
                dd.deleteDeptById(id);
            } else
            {
                try
                {
                    resp.sendRedirect("dept.jsp?delError=user");
                    return;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        try
        {
            resp.sendRedirect("dept.jsp");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
