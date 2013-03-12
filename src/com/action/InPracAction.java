package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.JudgeQuesDAO;
import com.dao.MultiQuesDAO;
import com.dao.PracQuesDAO;
import com.dao.SingleQuesDAO;
import com.dao.impl.JudgeQuesDAOImpl;
import com.dao.impl.MultiQuesDAOImpl;
import com.dao.impl.PracQuesDAOImpl;
import com.dao.impl.SingleQuesDAOImpl;
import com.pojo.JudgeQues;
import com.pojo.MultiQues;
import com.pojo.PracQues;
import com.pojo.SingleQues;

public class InPracAction extends HttpServlet
{
    private static final long serialVersionUID = 7116759219328286470L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        String url = req.getQueryString();
        int pracId = Integer.parseInt(url.split("&")[0].substring(3));
        int caseId = Integer.parseInt(url.split("&")[1].substring(2));

        PracQuesDAO qpd = new PracQuesDAOImpl();
        PracQues pq = qpd.getPracQuesById(pracId, caseId);

        List<JudgeQues> jqAct = new ArrayList<JudgeQues>();
        List<SingleQues> sqAct = new ArrayList<SingleQues>();
        List<MultiQues> mqAct = new ArrayList<MultiQues>();

        if (pq.getJudgeIdList() != null && !pq.getJudgeIdList().equals(""))
        {
            String[] jArray = pq.getJudgeIdList().split("\\|");
            JudgeQuesDAO jd = new JudgeQuesDAOImpl();
            List<JudgeQues> jqAll = jd.getAllJudgeQues();
            for (int i = 0; i < jArray.length; i++)
            {
                for (int j = 0; j < jqAll.size(); j++)
                {
                    if (Integer.parseInt(jArray[i]) == (jqAll.get(j).getId()))
                    {
                        jqAct.add(jqAll.get(j));
                        break;
                    }
                }
            }
        }
        if (pq.getSingleIdList() != null && !pq.getSingleIdList().equals(""))
        {
            String[] sArray = pq.getSingleIdList().split("\\|");
            SingleQuesDAO sd = new SingleQuesDAOImpl();
            List<SingleQues> sqAll = sd.getAllSingleQues();
            for (int i = 0; i < sArray.length; i++)
            {
                for (int j = 0; j < sqAll.size(); j++)
                {
                    if (Integer.parseInt(sArray[i]) == (sqAll.get(j).getId()))
                    {
                        sqAct.add(sqAll.get(j));
                        break;
                    }
                }
            }
        }
        if (pq.getMultiIdList() != null && !pq.getMultiIdList().equals(""))
        {
            String[] mArray = pq.getMultiIdList().split("\\|");
            MultiQuesDAO md = new MultiQuesDAOImpl();
            List<MultiQues> mqAll = md.getAllMultiQues();
            for (int i = 0; i < mArray.length; i++)
            {
                for (int j = 0; j < mqAll.size(); j++)
                {
                    if (Integer.parseInt(mArray[i]) == (mqAll.get(j).getId()))
                    {
                        mqAct.add(mqAll.get(j));
                        break;
                    }
                }
            }
        }

        HttpSession hs = req.getSession();

        String checkType = (String) hs.getAttribute("examType");

        if (checkType != null)
        {
            String id = (String) hs.getAttribute("id");
            String userName = (String) hs.getAttribute("userName");
            String login = (String) hs.getAttribute("login");
            hs.invalidate();
            hs = req.getSession();
            hs.setAttribute("id", id);
            hs.setAttribute("userName", userName);
            hs.setAttribute("login", login);
        }
        hs.setAttribute("judge", jqAct);
        hs.setAttribute("judgeSum", jqAct.size());
        hs.setAttribute("single", sqAct);
        hs.setAttribute("singleSum", sqAct.size());
        hs.setAttribute("multi", mqAct);
        hs.setAttribute("multiSum", mqAct.size());

        hs.setAttribute("examType", "1");
        try
        {
            resp.sendRedirect("/onlineTest/inTest/inTest.jsp");
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
