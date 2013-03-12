package com.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ExamDAO;
import com.dao.JudgeQuesDAO;
import com.dao.MultiQuesDAO;
import com.dao.PracQuesDAO;
import com.dao.SingleQuesDAO;
import com.dao.impl.ExamDAOImpl;
import com.dao.impl.JudgeQuesDAOImpl;
import com.dao.impl.MultiQuesDAOImpl;
import com.dao.impl.PracQuesDAOImpl;
import com.dao.impl.SingleQuesDAOImpl;
import com.pojo.Exam;
import com.pojo.JudgeQues;
import com.pojo.MultiQues;
import com.pojo.PracQues;
import com.pojo.SingleQues;
import com.util.RandomUtil;

public class PracAction extends HttpServlet
{
    private static final long serialVersionUID = 1920148121391326440L;
    public static int caseSum = 3;

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
            String addP = req.getParameter("addP");
            String upP = req.getParameter("upP");
            if (req.getParameter("del") != null)
            {
                String id = req.getParameter("id");
                PracQuesDAO pqd = new PracQuesDAOImpl();
                pqd.deletePracQuesByPracId(Integer.parseInt(id));
                ed.deleteExam(Integer.parseInt(id));
                resp.sendRedirect("practise.jsp");
            } else if (addP != null || upP != null)
            {
                String examName = req.getParameter("examName");
                String judgeNum = req.getParameter("judgeNum");
                String singleNum = req.getParameter("singleNum");
                String multiNum = req.getParameter("multiNum");
                String shour = req.getParameter("shour");
                String ehour = req.getParameter("ehour");
                String syear = req.getParameter("syear");
                String smonth = req.getParameter("smonth");
                String sday = req.getParameter("sday");
                String eyear = req.getParameter("eyear");
                String emonth = req.getParameter("emonth");
                String eday = req.getParameter("eday");
                String subjectId = req.getParameter("subject");
                String examType = "1";
                Exam exam = new Exam();
                String startTime = syear + smonth + sday + shour + "00";
                String endTime = eyear + emonth + eday + ehour + "00";

                exam.setSubjectId(subjectId);
                exam.setExamName(examName);
                exam.setStartTime(startTime);
                exam.setEndTime(endTime);
                int subjectIdInt = Integer.parseInt(subjectId);
                int singleNumInt = Integer.parseInt(singleNum);
                int judgeNumInt = Integer.parseInt(judgeNum);
                int multiNumInt = Integer.parseInt(multiNum);
                exam.setSingleNum(singleNum);
                exam.setJudgeNum(judgeNum);
                exam.setMutliNum(multiNum);
                exam.setExamType(examType);

                if (addP != null)// 新增练习
                {
                    JudgeQuesDAO jd = new JudgeQuesDAOImpl();
                    List<JudgeQues> jqList = jd
                            .getAllAvailableBySubjectId(subjectIdInt);
                    SingleQuesDAO sd = new SingleQuesDAOImpl();
                    List<SingleQues> sqList = sd
                            .getAllAvailableBySubjectId(subjectIdInt);
                    MultiQuesDAO md = new MultiQuesDAOImpl();
                    List<MultiQues> mqList = md
                            .getAllAvailableBySubjectId(subjectIdInt);

                    if (judgeNumInt > jqList.size())
                    {
                        resp.sendRedirect("addPrac.jsp?error=jFew");
                        HttpSession hs = req.getSession();
                        hs.setAttribute("errorExam", exam);
                        return;
                    } else if (singleNumInt > sqList.size())
                    {
                        resp.sendRedirect("addPrac.jsp?error=sFew");
                        HttpSession hs = req.getSession();
                        hs.setAttribute("errorExam", exam);
                        return;
                    } else if (multiNumInt > mqList.size())
                    {
                        resp.sendRedirect("addPrac.jsp?error=mFew");
                        HttpSession hs = req.getSession();
                        hs.setAttribute("errorExam", exam);
                        return;
                    }

                    PracQues[] pq = new PracQues[caseSum];
                    for (int caseId = 0; caseId < caseSum; caseId++)
                    {
                        int[] jqArray = RandomUtil.getRandomArray(judgeNumInt,
                                jqList.size());
                        int[] sqArray = RandomUtil.getRandomArray(singleNumInt,
                                sqList.size());
                        int[] mqArray = RandomUtil.getRandomArray(multiNumInt,
                                mqList.size());

                        StringBuilder jqStr = new StringBuilder("");
                        StringBuilder sqStr = new StringBuilder("");
                        StringBuilder mqStr = new StringBuilder("");

                        for (int i = 0; i < judgeNumInt; i++)
                        {
                            jqStr.append(jqList.get(jqArray[i]).getId());
                            if (i != judgeNumInt - 1)
                            {
                                jqStr.append('|');
                            }
                        }
                        for (int i = 0; i < singleNumInt; i++)
                        {
                            sqStr.append(sqList.get(sqArray[i]).getId());
                            if (i != singleNumInt - 1)
                            {
                                sqStr.append('|');
                            }
                        }
                        for (int i = 0; i < multiNumInt; i++)
                        {
                            mqStr.append(mqList.get(mqArray[i]).getId());
                            if (i != multiNumInt - 1)
                            {
                                mqStr.append('|');
                            }
                        }

                        pq[caseId] = new PracQues();
                        pq[caseId].setCaseId(caseId);
                        pq[caseId].setJudgeIdList(jqStr.toString());
                        pq[caseId].setSingleIdList(sqStr.toString());
                        pq[caseId].setMultiIdList(mqStr.toString());
                    }
                    PracQuesDAO pqd = new PracQuesDAOImpl();
                    int pracId = ed.addExam(exam);
                    for (int i = 0; i < caseSum; i++)
                    {
                        pq[i].setPracId(pracId);
                        pqd.addPracQues(pq[i]);
                    }
                } else if (upP != null)// 更新练习
                {
                    int id = Integer.parseInt(req.getParameter("id"));
                    exam.setId(id);
                    Exam e = ed.queryExamById(id);
                    if (e.getJudgeNum() != exam.getJudgeNum()
                            || e.getSingleNum() != exam.getSingleNum()
                            || e.getMutliNum() != exam.getMutliNum()
                            || e.getSubjectId() != exam.getSubjectId())
                    {
                        JudgeQuesDAO jd = new JudgeQuesDAOImpl();
                        List<JudgeQues> jqList = jd
                                .getAllAvailableBySubjectId(subjectIdInt);
                        SingleQuesDAO sd = new SingleQuesDAOImpl();
                        List<SingleQues> sqList = sd
                                .getAllAvailableBySubjectId(subjectIdInt);
                        MultiQuesDAO md = new MultiQuesDAOImpl();
                        List<MultiQues> mqList = md
                                .getAllAvailableBySubjectId(subjectIdInt);

                        if (judgeNumInt > jqList.size())
                        {
                            resp.sendRedirect("addPrac.jsp?error=jFew&id=" + id);
                            HttpSession hs = req.getSession();
                            hs.setAttribute("errorExam", exam);
                            return;
                        } else if (singleNumInt > sqList.size())
                        {
                            resp.sendRedirect("addPrac.jsp?error=sFew&id=" + id);
                            HttpSession hs = req.getSession();
                            hs.setAttribute("errorExam", exam);
                            return;
                        } else if (multiNumInt > mqList.size())
                        {
                            resp.sendRedirect("addPrac.jsp?error=mFew&id=" + id);
                            HttpSession hs = req.getSession();
                            hs.setAttribute("errorExam", exam);
                            return;
                        }

                        PracQues[] pq = new PracQues[caseSum];
                        for (int caseId = 0; caseId < caseSum; caseId++)
                        {
                            int[] jqArray = RandomUtil.getRandomArray(
                                    judgeNumInt, jqList.size());
                            int[] sqArray = RandomUtil.getRandomArray(
                                    singleNumInt, sqList.size());
                            int[] mqArray = RandomUtil.getRandomArray(
                                    multiNumInt, mqList.size());
                            StringBuilder jqStr = new StringBuilder("");
                            StringBuilder sqStr = new StringBuilder("");
                            StringBuilder mqStr = new StringBuilder("");
                            for (int i = 0; i < judgeNumInt; i++)
                            {
                                jqStr.append(jqList.get(jqArray[i]).getId());
                                if (i != judgeNumInt - 1)
                                {
                                    jqStr.append('|');
                                }
                            }
                            for (int i = 0; i < singleNumInt; i++)
                            {
                                sqStr.append(sqList.get(sqArray[i]).getId());
                                if (i != singleNumInt - 1)
                                {
                                    sqStr.append('|');
                                }
                            }
                            for (int i = 0; i < multiNumInt; i++)
                            {
                                mqStr.append(mqList.get(mqArray[i]).getId());
                                if (i != multiNumInt - 1)
                                {
                                    mqStr.append('|');
                                }
                            }
                            pq[caseId] = new PracQues();
                            pq[caseId].setCaseId(caseId);
                            pq[caseId].setJudgeIdList(jqStr.toString());
                            pq[caseId].setSingleIdList(sqStr.toString());
                            pq[caseId].setMultiIdList(mqStr.toString());
                        }
                        PracQuesDAO pqd = new PracQuesDAOImpl();

                        for (int i = 0; i < caseSum; i++)
                        {
                            pq[i].setPracId(id);
                            pqd.updatePracQues(pq[i]);
                        }
                    }
                    exam.setId(id);

                    ed.updateExam(id, exam);
                }
                resp.sendRedirect("practise.jsp");
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