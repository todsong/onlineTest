package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ExamDAO;
import com.dao.JudgeQuesDAO;
import com.dao.MultiQuesDAO;
import com.dao.SingleQuesDAO;
import com.dao.SubjectDAO;
import com.dao.impl.ExamDAOImpl;
import com.dao.impl.JudgeQuesDAOImpl;
import com.dao.impl.MultiQuesDAOImpl;
import com.dao.impl.SingleQuesDAOImpl;
import com.dao.impl.SubjectDAOImpl;
import com.pojo.Exam;
import com.pojo.JudgeQues;
import com.pojo.MultiQues;
import com.pojo.SingleQues;
import com.pojo.Subject;

public class SubjectAction extends HttpServlet
{
    private static final long serialVersionUID = 2847153298264783086L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        String subjectName = req.getParameter("subjectName");
        String addD = req.getParameter("addD");
        String delD = req.getParameter("delD");
        String upD = req.getParameter("upD");
        SubjectDAO dd = new SubjectDAOImpl();

        if (addD != null)
        {
            Subject subject = new Subject();
            subject.setName(subjectName);
            dd.addSubject(subject);
        } else if (upD != null)
        {
            int id = Integer.parseInt(req.getParameter("subjectId"));
            Subject subject = new Subject();
            subject.setName(subjectName);
            dd.updateSubjectById(id, subject);
        } else if (delD != null)
        {
            int id = Integer.parseInt(req.getParameter("subjectId"));
            JudgeQuesDAO jd = new JudgeQuesDAOImpl();
            List<JudgeQues> jqList = jd.queryJudgeQuesBySubject(id);
            if (jqList != null && jqList.size() != 0)
            {
                try
                {
                    resp.sendRedirect("subject.jsp?delError=judge");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                return;
            }
            SingleQuesDAO sd = new SingleQuesDAOImpl();
            List<SingleQues> sqList = sd.querySingleQuesBySubject(id);
            if (sqList != null && sqList.size() != 0)
            {
                try
                {
                    resp.sendRedirect("subject.jsp?delError=single");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                return;
            }

            MultiQuesDAO md = new MultiQuesDAOImpl();
            List<MultiQues> mqList = md.queryMultiQuesBySubject(id);
            if (mqList != null && mqList.size() != 0)
            {
                try
                {
                    resp.sendRedirect("subject.jsp?delError=multi");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                return;
            }
            dd.deleteSubjectById(id);
        }
        try
        {
            resp.sendRedirect("subject.jsp");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
