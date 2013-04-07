package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserExamDAO;
import com.dao.impl.UserExamDAOImpl;
import com.pojo.Exam;
import com.pojo.JudgeQues;
import com.pojo.MultiQues;
import com.pojo.SingleQues;
import com.pojo.UserExam;

public class HandInPrac extends HttpServlet
{
    private static final long serialVersionUID = 1761147668815021807L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        HttpSession hs = req.getSession();
        String examType = (String) hs.getAttribute("examType");

        List<JudgeQues> jqList = (ArrayList<JudgeQues>) hs
                .getAttribute("judge");
        List<SingleQues> sqList = (ArrayList<SingleQues>) hs
                .getAttribute("single");
        List<MultiQues> mqList = (ArrayList<MultiQues>) hs
                .getAttribute("multi");
        int jright = 0;
        int jwrong = 0;
        int sright = 0;
        int swrong = 0;
        int mright = 0;
        int mwrong = 0;
        int empty = 0;
        StringBuilder jResStr = new StringBuilder("");
        StringBuilder sResStr = new StringBuilder("");
        StringBuilder mResStr = new StringBuilder("");
        for (int i = 0; i < jqList.size(); i++)
        {
            String id = "judge" + i;
            String res = (String) hs.getAttribute(id);
            if (res == null || res.equals(""))
            {
                empty++;
            } else if (res.equals(jqList.get(i).getqAnswer()))
            {
                jright++;
                jResStr.append(res);
            } else
            {
                jwrong++;
                jResStr.append(res);
            }
            if (i < jqList.size() - 1)
            {
                jResStr.append("|");
            }
            hs.removeAttribute(id);
        }
        for (int i = 0; i < sqList.size(); i++)
        {
            String id = "single" + i;
            String res = (String) hs.getAttribute(id);
            if (res == null || res.equals(""))
            {
                empty++;
            } else if (res.equals(sqList.get(i).getqAnswer()))
            {
                sright++;
                sResStr.append(res);
            } else
            {
                swrong++;
                sResStr.append(res);
            }
            if (i < sqList.size() - 1)
            {
                sResStr.append("|");
            }
            hs.removeAttribute(id);
        }
        for (int i = 0; i < mqList.size(); i++)
        {
            String id = "multi" + i;
            String res = (String) hs.getAttribute(id);
            if (res == null || res.equals(""))
            {
                empty++;
            } else if (res.equals(mqList.get(i).getqAnswer()))
            {
                mright++;
                mResStr.append(res);
            } else
            {
                mwrong++;
                mResStr.append(res);
            }
            if (i < mqList.size() - 1)
            {
                mResStr.append("|");
            }
            hs.removeAttribute(id);
        }
        hs.removeAttribute("judge");
        hs.removeAttribute("judgeSum");
        hs.removeAttribute("single");
        hs.removeAttribute("singleSum");
        hs.removeAttribute("multi");
        hs.removeAttribute("multiSum");
        hs.removeAttribute("examId");
        if (examType.equals("0"))
        {
            hs.removeAttribute("count");
            UserExam ue = (UserExam) hs.getAttribute("userExam");
            Exam exam = (Exam) hs.getAttribute("exam");
            int userScore = exam.getJudgeScore() * jright
                    + exam.getSingleScore() * sright + exam.getMutliScore()
                    * mright;
            UserExamDAO ued = new UserExamDAOImpl();

            ue.setScore(userScore);
            ue.setJudgeAnswerList(jResStr.toString());
            ue.setSingleAnswerList(sResStr.toString());
            ue.setMultiAnswerList(mResStr.toString());
            ued.updateUserExamResult(ue);
            hs.setAttribute("userExam", ue);
        } else
        {
            hs.setAttribute("empty", empty);
            hs.setAttribute("right", jright + sright + mright);
            hs.setAttribute("wrong", jwrong + swrong + mwrong);
        }
        try
        {
            resp.sendRedirect("alertResult.jsp");
        } catch (IOException e)
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
