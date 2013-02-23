package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ExamDAO;
import com.dao.JudgeQuesDAO;
import com.dao.MultiQuesDAO;
import com.dao.PracQuesDAO;
import com.dao.SingleQuesDAO;
import com.dao.UserExamDAO;
import com.dao.impl.ExamDAOImpl;
import com.dao.impl.JudgeQuesDAOImpl;
import com.dao.impl.MultiQuesDAOImpl;
import com.dao.impl.PracQuesDAOImpl;
import com.dao.impl.SingleQuesDAOImpl;
import com.dao.impl.UserExamDAOImpl;
import com.pojo.Exam;
import com.pojo.JudgeQues;
import com.pojo.MultiQues;
import com.pojo.PracQues;
import com.pojo.SingleQues;
import com.pojo.UserExam;
import com.util.RandomUtil;

public class inExamAction extends HttpServlet
{
    private static final long serialVersionUID = 7116759219328286470L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            req.setCharacterEncoding("utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        HttpSession hs = req.getSession();
        String checkType = (String) hs.getAttribute("examType");
        if(checkType!=null && checkType.equals("1"))
        {
            String id = (String)hs.getAttribute("id");
            String userName = (String)hs.getAttribute("userName");
            String login = (String)hs.getAttribute("login");
            hs.invalidate();
            hs = req.getSession();

            hs.setAttribute("id", id);
            hs.setAttribute("userName", userName);
            hs.setAttribute("login", login);
        }
        String userId = (String) hs.getAttribute("id");
        String url = req.getQueryString();
        int examId = Integer.parseInt(url.split("&")[0].substring(3));
        
        UserExamDAO ued = new UserExamDAOImpl();
        UserExam ue = ued.queryUniqueUserExam(examId, userId);

        ExamDAO ed = new ExamDAOImpl();
        Exam exam = ed.queryExamById(examId);
        
        hs.setAttribute("exam", exam);
        hs.setAttribute("examType", "0");
        if(ue==null) //第一次进入考试，需随机生成题目
        {
            ue = new UserExam();
            ue.setExamId(examId);
            ue.setUserId(userId);
            ue.setScore(-1);
            JudgeQuesDAO jd = new JudgeQuesDAOImpl();
            List<JudgeQues> jqList = jd.getAllJudgeQues();
            SingleQuesDAO sd = new SingleQuesDAOImpl();
            List<SingleQues> sqList = sd.getAllSingleQues();
            MultiQuesDAO md = new MultiQuesDAOImpl();
            List<MultiQues> mqList = md.getAllMultiQues();
            
            int judgeNumInt = exam.getJudgeNum();
            int singleNumInt = exam.getSingleNum();
            int multiNumInt = exam.getMutliNum();

            int[] jqArray = RandomUtil.getRandomArray(judgeNumInt, jqList.size());
            int[] sqArray = RandomUtil.getRandomArray(singleNumInt, sqList.size());
            int[] mqArray = RandomUtil.getRandomArray(multiNumInt, mqList.size());

            StringBuilder jqStr = new StringBuilder("");
            StringBuilder sqStr = new StringBuilder("");
            StringBuilder mqStr = new StringBuilder("");
            List<JudgeQues> jqAct = new ArrayList<JudgeQues>();
            List<SingleQues> sqAct = new ArrayList<SingleQues>();
            List<MultiQues> mqAct = new ArrayList<MultiQues>();

            for(int i=0;i<judgeNumInt;i++)
            {
                jqStr.append(jqList.get(jqArray[i]).getId());
                jqAct.add(jqList.get(jqArray[i]));
                if(i!=judgeNumInt-1)
                {
                    jqStr.append('|');
                }
            }
            for(int i=0;i<singleNumInt;i++)
            {
                sqStr.append(sqList.get(sqArray[i]).getId());
                sqAct.add(sqList.get(sqArray[i]));
                if(i!=singleNumInt-1)
                {
                    sqStr.append('|');
                }
            }
            for(int i=0;i<multiNumInt;i++)
            {
                mqStr.append(mqList.get(mqArray[i]).getId());
                mqAct.add(mqList.get(mqArray[i]));
                if(i!=multiNumInt-1)
                {
                    mqStr.append('|');
                }
            }
            
            ue.setJudgeIdList(jqStr.toString());
            ue.setSingleIdList(sqStr.toString());
            ue.setMultiIdList(mqStr.toString());
            ued.addNewUserExam(ue);

            hs.setAttribute("judge", jqAct);
            hs.setAttribute("judgeSum", judgeNumInt);
            hs.setAttribute("single", sqAct);
            hs.setAttribute("singleSum", judgeNumInt);
            hs.setAttribute("multi", mqAct);
            hs.setAttribute("multiSum", multiNumInt);
            
            hs.setAttribute("userExam", ue);
            
        }
        else //非首次进入考试，已存在题目
        {
            List<JudgeQues> jqAct = (ArrayList<JudgeQues>) hs.getAttribute("judge");
            List<SingleQues> sqAct = (ArrayList<SingleQues>) hs.getAttribute("single");
            List<MultiQues> mqAct = (ArrayList<MultiQues>) hs.getAttribute("multi");
            
            if(jqAct==null)
            {
                String jqStr = ue.getJudgeIdList();
                String[] jqArray = jqStr.split("\\|");
                jqAct = new ArrayList<JudgeQues>();
                JudgeQuesDAO jd = new JudgeQuesDAOImpl();
                List<JudgeQues> jqList = jd.getAllJudgeQues();
                Map<Integer, JudgeQues> jqMap = new HashMap<Integer, JudgeQues>();
                for(int i=0;i<jqList.size();i++)
                {
                    jqMap.put(jqList.get(i).getId(),jqList.get(i));
                }
                for(int i=0;i<jqArray.length;i++)
                {
                    jqAct.add(jqMap.get(Integer.parseInt(jqArray[i])));
                }
                
                String sqStr = ue.getSingleIdList();
                String[] sqArray = sqStr.split("\\|");
                sqAct = new ArrayList<SingleQues>();
                SingleQuesDAO sd = new SingleQuesDAOImpl();
                List<SingleQues> sqList = sd.getAllSingleQues();
                Map<Integer, SingleQues> sqMap = new HashMap<Integer, SingleQues>();
                for(int i=0;i<sqList.size();i++)
                {
                    sqMap.put(sqList.get(i).getId(),sqList.get(i));
                }
                for(int i=0;i<sqArray.length;i++)
                {
                    sqAct.add(sqMap.get(Integer.parseInt(sqArray[i])));
                }
                
                String mqStr = ue.getMultiIdList();
                String[] mqArray = mqStr.split("\\|");
                mqAct = new ArrayList<MultiQues>();
                MultiQuesDAO md = new MultiQuesDAOImpl();
                List<MultiQues> mqList = md.getAllMultiQues();
                Map<Integer, MultiQues> mqMap = new HashMap<Integer, MultiQues>();
                for(int i=0;i<mqList.size();i++)
                {
                    mqMap.put(mqList.get(i).getId(),mqList.get(i));
                }
                for(int i=0;i<mqArray.length;i++)
                {
                    mqAct.add(mqMap.get(Integer.parseInt(mqArray[i])));
                }
                
                hs.setAttribute("userExam", ue);
                hs.setAttribute("judge", jqAct);
                hs.setAttribute("judgeSum", jqArray.length);
                hs.setAttribute("single", sqAct);
                hs.setAttribute("singleSum", sqArray.length);
                hs.setAttribute("multi", mqAct);
                hs.setAttribute("multiSum", mqArray.length);            }
        }
        
        try
        {
            resp.sendRedirect("/onlineTest/inTest/inTest.jsp");
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
