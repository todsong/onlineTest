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
import com.dao.UserExamDAO;
import com.dao.impl.ExamDAOImpl;
import com.dao.impl.UserExamDAOImpl;
import com.pojo.Exam;
import com.pojo.JudgeQues;
import com.pojo.MultiQues;
import com.pojo.PracQues;
import com.pojo.SingleQues;
import com.pojo.UserExam;
import com.resource.Cache;
import com.util.RandomUtil;

public class InExamAction extends HttpServlet
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

        HttpSession hs = req.getSession();
        String checkType = (String) hs.getAttribute("examType");
        if (checkType != null && checkType.equals("1"))
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
        String userId = (String) hs.getAttribute("id");
        String url = req.getQueryString();
        int examId = Integer.parseInt(url.split("&")[0].substring(3));

        UserExamDAO ued = new UserExamDAOImpl();
        UserExam ue = ued.queryUniqueUserExam(examId, userId);

        Exam exam = Cache.getExamMap().get(examId);

        hs.setAttribute("exam", exam);
        hs.setAttribute("examType", "0");
        if (ue == null) // 第一次进入考试，需随机生成题目
        {
            ue = new UserExam();
            ue.setExamId(examId);
            ue.setUserId(userId);
            ue.setScore(-1);

            String subjectIdStr = exam.getSubjectId();
            String judgeNumStr = exam.getJudgeNum();
            String singleNumStr = exam.getSingleNum();
            String multiNumStr = exam.getMutliNum();
            String reg="\\|";
            int[] subjectIdInt = InExamAction.Str2IntBySplit(subjectIdStr, reg);
            int[] judgeNumInt = InExamAction.Str2IntBySplit(judgeNumStr, reg);
            int[] singleNumInt = InExamAction.Str2IntBySplit(singleNumStr, reg);
            int[] multiNumInt = InExamAction.Str2IntBySplit(multiNumStr, reg);
            int subjectSum = subjectIdInt.length;
            
            int[][] jqArray = new int[subjectSum][];
            int[][] sqArray = new int[subjectSum][];
            int[][] mqArray = new int[subjectSum][];

            StringBuilder jqStr = new StringBuilder("");
            StringBuilder sqStr = new StringBuilder("");
            StringBuilder mqStr = new StringBuilder("");

            List<JudgeQues> jqAct = new ArrayList<JudgeQues>();
            List<SingleQues> sqAct = new ArrayList<SingleQues>();
            List<MultiQues> mqAct = new ArrayList<MultiQues>();
            for(int sNum=0; sNum< subjectSum; sNum++)
            {
                List<JudgeQues> jqList = Cache.getJqMap().get(subjectIdInt[sNum]);
                List<SingleQues> sqList = Cache.getSqMap().get(subjectIdInt[sNum]);
                List<MultiQues> mqList = Cache.getMqMap().get(subjectIdInt[sNum]);

                jqArray[sNum] = RandomUtil.getRandomArray(judgeNumInt[sNum],
                        jqList.size());
                sqArray[sNum] = RandomUtil.getRandomArray(singleNumInt[sNum],
                        sqList.size());
                mqArray[sNum] = RandomUtil.getRandomArray(multiNumInt[sNum],
                        mqList.size());
                for (int i = 0; i < judgeNumInt[sNum]; i++)
                {
                    if(jqStr.length()==0)
                        jqStr.append(jqList.get(jqArray[sNum][i]).getId());
                    else
                        jqStr.append('|').append(jqList.get(jqArray[sNum][i]).getId());
                    jqAct.add(jqList.get(jqArray[sNum][i]));
                    
                }
                for (int i = 0; i < singleNumInt[sNum]; i++)
                {
                    if(sqStr.length()==0)
                        sqStr.append(sqList.get(sqArray[sNum][i]).getId());
                    else
                        sqStr.append('|').append(sqList.get(sqArray[sNum][i]).getId());
                    sqAct.add(sqList.get(sqArray[sNum][i]));
                }
                for (int i = 0; i < multiNumInt[sNum]; i++)
                {
                    if(mqStr.length()==0)
                        mqStr.append(mqList.get(mqArray[sNum][i]).getId());
                    else
                        mqStr.append('|').append(mqList.get(mqArray[sNum][i]).getId());
                    mqAct.add(mqList.get(mqArray[sNum][i]));
                }
            }

            ue.setJudgeIdList(jqStr.toString());
            ue.setSingleIdList(sqStr.toString());
            ue.setMultiIdList(mqStr.toString());
            ued.addNewUserExam(ue);

            hs.setAttribute("judge", jqAct);
            hs.setAttribute("judgeSum", jqAct.size());
            hs.setAttribute("single", sqAct);
            hs.setAttribute("singleSum", sqAct.size());
            hs.setAttribute("multi", mqAct);
            hs.setAttribute("multiSum", mqAct.size());

            hs.setAttribute("userExam", ue);

        } else
        // 非首次进入考试，已存在题目
        {
            List<JudgeQues> jqAct = (ArrayList<JudgeQues>) hs
                    .getAttribute("judge");
            List<SingleQues> sqAct = (ArrayList<SingleQues>) hs
                    .getAttribute("single");
            List<MultiQues> mqAct = (ArrayList<MultiQues>) hs
                    .getAttribute("multi");

            if (jqAct == null)
            {
                String jqStr = ue.getJudgeIdList();
                String[] jqArray = jqStr.split("\\|");
                jqAct = new ArrayList<JudgeQues>();
                Map<Integer, JudgeQues> jqMap = Cache.getAllJqMap();
                for (int i = 0; i < jqArray.length; i++)
                {
                    jqAct.add(jqMap.get(Integer.parseInt(jqArray[i])));
                }

                String sqStr = ue.getSingleIdList();
                String[] sqArray = sqStr.split("\\|");
                sqAct = new ArrayList<SingleQues>();
                Map<Integer, SingleQues> sqMap = Cache.getAllSqMap();
                for (int i = 0; i < sqArray.length; i++)
                {
                    sqAct.add(sqMap.get(Integer.parseInt(sqArray[i])));
                }

                String mqStr = ue.getMultiIdList();
                String[] mqArray = mqStr.split("\\|");
                mqAct = new ArrayList<MultiQues>();
                Map<Integer, MultiQues> mqMap = Cache.getAllMqMap();
                for (int i = 0; i < mqArray.length; i++)
                {
                    mqAct.add(mqMap.get(Integer.parseInt(mqArray[i])));
                }

                hs.setAttribute("userExam", ue);
                hs.setAttribute("judge", jqAct);
                hs.setAttribute("judgeSum", jqArray.length);
                hs.setAttribute("single", sqAct);
                hs.setAttribute("singleSum", sqArray.length);
                hs.setAttribute("multi", mqAct);
                hs.setAttribute("multiSum", mqArray.length);
            }
        }

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
    public static int[] Str2IntBySplit(String src, String reg)
    {
        String[] tmp = src.split(reg);
        int[] res = new int[tmp.length];
        for(int i=0;i<res.length;i++)
        {
            res[i]=Integer.parseInt(tmp[i]);
        }
        return res;
    }
}
