package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ExamDAO;
import com.dao.JudgeQuesDAO;
import com.dao.PracQuesDAO;
import com.dao.SingleQuesDAO;
import com.dao.UserDAO;
import com.dao.UserExamDAO;
import com.dao.impl.ExamDAOImpl;
import com.dao.impl.JudgeQuesDAOImpl;
import com.dao.impl.PracQuesDAOImpl;
import com.dao.impl.SingleQuesDAOImpl;
import com.dao.impl.UserDAOImpl;
import com.dao.impl.UserExamDAOImpl;
import com.pojo.JudgeQues;
import com.pojo.User;
import com.util.MD5Util;

public class JudgeQuesAction extends HttpServlet
{
    private static final long serialVersionUID = -15809939970928793L;

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
            JudgeQuesDAO jqd = new JudgeQuesDAOImpl();
            JudgeQues jq = new JudgeQues();
            String del = req.getParameter("del");
            String up = req.getParameter("up");
            String addOne = req.getParameter("addOne");
            String addBatch = req.getParameter("addBatch");
            String id = req.getParameter("id");
            String addJ = req.getParameter("addJ");
            String subjectId = req.getParameter("subject");
            String status = req.getParameter("status");
            if(addOne !=null)
            {
                resp.sendRedirect("addOneJudgeQues.jsp");
            }
            else if(addBatch!=null)
            {
                resp.sendRedirect("addBatchJudgeQues.jsp");
            }
            else if(del!=null) //删除操作
            {
                ExamDAO ed = new ExamDAOImpl();
                int maxNeedInExam = ed.getMaxQuesNumByType("judge");
                int maxNumInRepo = jqd.getCountSum();
                
                UserExamDAO ued = new UserExamDAOImpl();
                PracQuesDAO qpd = new PracQuesDAOImpl();
                
                if(maxNumInRepo<=maxNeedInExam)
                {
                    resp.sendRedirect("judge.jsp?delTooFew");
                }
                else if(!ued.queryNoQuesId("judge", id) || !qpd.queryNoQuesId("judge", id))
                {
                    resp.sendRedirect("judge.jsp?delUsed");
                }
                else
                {
                    jqd.deleteJudgeQues(Integer.parseInt(id));
                    resp.sendRedirect("judge.jsp");
                }
            }
            else if(addJ!=null)
            {
                String ques = req.getParameter("ques");
                String answer = req.getParameter("answer");
                jq.setqAnswer(answer);
                jq.setqName(ques);
                jq.setSubjectId(Integer.parseInt(subjectId));
                jq.setStatus(0);
                String hash = MD5Util.getMD5(0+ques);
                jq.setHash(hash);
                int unique = jqd.checkUnique(jq.getHash());
                if(unique!=-1 && unique!=Integer.parseInt(id))
                {
                    resp.sendRedirect("judge.jsp?unique");
                    return;
                }

                jqd.addJudgeQues(jq);
                resp.sendRedirect("judge.jsp");
            }
            else //update
            {
                String ques = req.getParameter("ques");
                String answer = req.getParameter("answer");
                if (ques == null || ques.equals(""))
                {
                    resp.sendRedirect("judgeItem.jsp");
                }
                else if (answer == null || answer.equals(""))
                {
                    resp.sendRedirect("judgeItem.jsp");
                }
                jq.setqAnswer(answer);
                jq.setqName(ques);
                jq.setSubjectId(Integer.parseInt(subjectId));
                int st = Integer.parseInt(status);
                jq.setStatus(st);
                String hash = MD5Util.getMD5(st+ques);
                jq.setHash(hash);
                int unique = jqd.checkUnique(jq.getHash());
                if(unique!=-1 && unique!=Integer.parseInt(id))
                {
                    resp.sendRedirect("judge.jsp?unique");
                    return;
                }

                jqd.updateJudgeQues(Integer.parseInt(id), jq);
                resp.sendRedirect("judge.jsp");
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        doGet(req, resp);
    }

}
