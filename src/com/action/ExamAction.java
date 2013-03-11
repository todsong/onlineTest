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
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        try
        {
            ExamDAO ed = new ExamDAOImpl();
            String addE = req.getParameter("addE");
            String upE = req.getParameter("upE");
            if (req.getParameter("del") != null)
            {
                String id = req.getParameter("id");
                ed.deleteExam(Integer.parseInt(id));
                resp.sendRedirect("exam.jsp");
            } else if (addE != null || upE != null)
            {
                String idStr = req.getParameter("id");
                String examName = req.getParameter("examName");
                String judgeScore = req.getParameter("judgeScore");
                String singleScore = req.getParameter("singleScore");
                String multiScore = req.getParameter("multiScore");
                String passScore = req.getParameter("passScore");
                String startHour = req.getParameter("startHour");
                String endHour = req.getParameter("endHour");
                String startMinute = req.getParameter("startMinute");
                String endMinute = req.getParameter("endMinute");
                String year = req.getParameter("year");
                String month = req.getParameter("month");
                String day = req.getParameter("day");
                // System.out.println(req.getParameter("subject"));
                String examType = "0";

                int subjectNum = Integer.parseInt(req.getParameter("subjectNum"));
                int[] judges = new int[subjectNum];
                int[] singles = new int[subjectNum];
                int[] multis = new int[subjectNum];
                int[] subjects = new int[subjectNum];
                String judgeNum="";
                String singleNum="";
                String multiNum="";
                String subjectId = "";
                int judgeSum = 0;
                int singleSum = 0;
                int multiSum = 0;
                for(int i=0;i<subjectNum;i++)
                {
                    judges[i] = Integer.parseInt(req.getParameter("judgeNum"+i));
                    singles[i] = Integer.parseInt(req.getParameter("singleNum"+i));
                    multis[i] = Integer.parseInt(req.getParameter("multiNum"+i));
                    subjects[i] = Integer.parseInt(req.getParameter("subject"+i));

                    if(subjectId.equals(""))
                    {
                        subjectId += subjects[i];
                        judgeNum += judges[i];
                        singleNum += singles[i];
                        multiNum += multis[i];
                    }
                    else
                    {
                        subjectId += "|"+subjects[i];
                        judgeNum += "|"+judges[i];
                        singleNum += "|"+singles[i];
                        multiNum += "|"+multis[i];
                    }
                    judgeSum += judges[i];
                    singleSum += singles[i];
                    multiSum += multis[i];
                }
                if (day.length() == 1)
                {
                    day = "0" + day;
                }
                if (month.length() == 1)
                {
                    month = "0" + month;
                }
                if (startHour.length() == 1)
                {
                    startHour = "0" + startHour;
                }
                if (endHour.length() == 1)
                {
                    endHour = "0" + endHour;
                }
                Exam exam = new Exam();
                String startTime = year + month + day + startHour + startMinute;
                String endTime = year + month + day + endHour + endMinute;
                exam.setExamName(examName);
                exam.setStartTime(startTime);
                exam.setEndTime(endTime);
                
                exam.setSingleNum(singleNum);
                exam.setSingleScore(Integer.parseInt(singleScore));
                exam.setJudgeNum(judgeNum);
                exam.setJudgeScore(Integer.parseInt(judgeScore));
                exam.setMutliNum(multiNum);
                exam.setMutliScore(Integer.parseInt(multiScore));
                exam.setPassScore(Integer.parseInt(passScore));
                exam.setExamType(examType);
                exam.setSubjectId(subjectId);
                int id = 0;
                if (idStr != null && !idStr.isEmpty())
                {
                    id = Integer.parseInt(idStr);
                    exam.setId(id);
                }

                JudgeQuesDAO jd = new JudgeQuesDAOImpl();
                
                //逻辑待优化
                String subfix = "";
                if (upE != null)
                {
                    subfix = "&id=" + idStr;
                }
                for(int i=0;i<subjectNum;i++)
                {
                    List<JudgeQues> jqList = jd
                            .getAllAvailableBySubjectId(subjects[i]);
                    SingleQuesDAO sd = new SingleQuesDAOImpl();
                    List<SingleQues> sqList = sd
                            .getAllAvailableBySubjectId(subjects[i]);
                    MultiQuesDAO md = new MultiQuesDAOImpl();
                    List<MultiQues> mqList = md
                            .getAllAvailableBySubjectId(subjects[i]);
                    if (judges[i] > jqList.size())
                    {
                        resp.sendRedirect("addExam.jsp?error=jFew" + subfix + "&subject="+(i+1));
                        HttpSession hs = req.getSession();
                        hs.setAttribute("errorExam", exam);
                        return;
                    } else if (singles[i] > sqList.size())
                    {
                        resp.sendRedirect("addExam.jsp?error=sFew" + subfix+ "&subject="+(i+1));
                        HttpSession hs = req.getSession();
                        hs.setAttribute("errorExam", exam);
                        return;
                    } else if (multis[i] > mqList.size())
                    {
                        resp.sendRedirect("addExam.jsp?error=mFew" + subfix+ "&subject="+(i+1));
                        HttpSession hs = req.getSession();
                        hs.setAttribute("errorExam", exam);
                        return;
                    }
                }
                if (addE != null)
                {
                    ed.addExam(exam);
                    resp.sendRedirect("exam.jsp");
                    return;
                } else if (upE != null)
                {
                    ed.updateExam(id, exam);
                    resp.sendRedirect("exam.jsp");
                    return;
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
