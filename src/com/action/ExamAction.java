package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ExamDAO;
import com.dao.JudgeQuesDAO;
import com.dao.MultiQuesDAO;
import com.dao.SingleQuesDAO;
import com.dao.impl.ExamDAOImpl;
import com.dao.impl.JudgeQuesDAOImpl;
import com.dao.impl.MultiQuesDAOImpl;
import com.dao.impl.SingleQuesDAOImpl;
import com.pojo.Exam;
import com.pojo.JudgeQues;
import com.pojo.MultiQues;
import com.pojo.SingleQues;

public class ExamAction extends HttpServlet
{
    private static final long serialVersionUID = 1763547668815021807L;
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
            ExamDAO ed = new ExamDAOImpl();
            String addE = req.getParameter("addE");
            String upE = req.getParameter("upE");
            if(req.getParameter("del")!=null)
            {
                String id = req.getParameter("id");
                ed.deleteExam(Integer.parseInt(id));
                resp.sendRedirect("exam.jsp");
            }
            else if(addE!=null || upE!=null )
            {
                String idStr = req.getParameter("id");
                String examName = req.getParameter("examName");
                String judgeNum = req.getParameter("judgeNum");
                String judgeScore = req.getParameter("judgeScore");
                String singleNum = req.getParameter("singleNum");
                String singleScore = req.getParameter("singleScore");
                String multiNum = req.getParameter("multiNum");
                String multiScore = req.getParameter("multiScore");
                String passScore = req.getParameter("passScore");
                String startHour = req.getParameter("startHour");
                String endHour = req.getParameter("endHour");
                String startMinute = req.getParameter("startMinute");
                String endMinute = req.getParameter("endMinute");
                String year = req.getParameter("year");
                String month = req.getParameter("month");
                String day = req.getParameter("day");
//                System.out.println(req.getParameter("subject"));
                int subjectId = Integer.parseInt(req.getParameter("subject"));
                String examType = "0";
                if(day.length()==1)
                {
                    day = "0" + day;
                }
                if(month.length()==1)
                {
                    month = "0" + month;
                }
                if(startHour.length()==1)
                {
                    startHour = "0" + startHour;
                }
                if(endHour.length()==1)
                {
                    endHour = "0" + endHour;
                }
                Exam exam = new Exam();
                String startTime = year + month + day + startHour + startMinute;
                String endTime = year + month + day + endHour + endMinute;
                exam.setExamName(examName);
                exam.setStartTime(startTime);
                exam.setEndTime(endTime);
                exam.setSingleNum(Integer.parseInt(singleNum));
                exam.setSingleScore(Integer.parseInt(singleScore));
                exam.setJudgeNum(Integer.parseInt(judgeNum));
                exam.setJudgeScore(Integer.parseInt(judgeScore));
                exam.setMutliNum(Integer.parseInt(multiNum));
                exam.setMutliScore(Integer.parseInt(multiScore));
                exam.setPassScore(Integer.parseInt(passScore));
                exam.setExamType(examType);
                exam.setSubjectId(subjectId);
                int id=0;
                if(idStr!=null&&!idStr.isEmpty())
                {
                    id = Integer.parseInt(idStr);
                    exam.setId(id);
                }
                
                JudgeQuesDAO jd = new JudgeQuesDAOImpl();
                List<JudgeQues> jqList = jd.getAllAvailableBySubjectId(subjectId);
                SingleQuesDAO sd = new SingleQuesDAOImpl();
                List<SingleQues> sqList = sd.getAllAvailableBySubjectId(subjectId);
                MultiQuesDAO md = new MultiQuesDAOImpl();
                List<MultiQues> mqList = md.getAllAvailableBySubjectId(subjectId);
                String subfix = "";
                if(upE!=null)
                {
                    subfix = "&id="+idStr;
                }
                if(exam.getJudgeNum() > jqList.size())
                {
                    resp.sendRedirect("addExam.jsp?error=jFew"+subfix);
                    HttpSession hs = req.getSession();
                    hs.setAttribute("errorExam", exam);
                    return;
                }
                else if(exam.getSingleNum()> sqList.size())
                {
                    resp.sendRedirect("addExam.jsp?error=sFew"+subfix);
                    HttpSession hs = req.getSession();
                    hs.setAttribute("errorExam", exam);
                    return;
                }
                else if(exam.getMutliNum()> mqList.size())
                {
                    resp.sendRedirect("addExam.jsp?error=mFew"+subfix);
                    HttpSession hs = req.getSession();
                    hs.setAttribute("errorExam", exam);
                    return;
                }
                
                if(addE!=null)
                {
                    ed.addExam(exam);
                    resp.sendRedirect("exam.jsp");
                    return;
                }
                else if(upE!=null)
                {
                    ed.updateExam(id, exam);
                    resp.sendRedirect("exam.jsp");
                    return;
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
