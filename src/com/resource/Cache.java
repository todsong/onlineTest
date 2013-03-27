package com.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.util.TimeUtil;

public class Cache
{
    private static Map<Integer, List<JudgeQues>> jqMap;
    private static Map<Integer, List<SingleQues>> sqMap;
    private static Map<Integer, List<MultiQues>> mqMap;
    private static Map<Integer, JudgeQues> allJqMap;
    private static Map<Integer, SingleQues> allSqMap;
    private static Map<Integer, MultiQues> allMqMap;

    private static Map<Integer, Exam> examMap;
    private static List<Exam> recentExam;
    private static List<Exam> todayExam;
    private static Date examCacheDate;

    private static List<Exam> availablePrac;
    private static Date pracCacheDate;
    
    private static List<Subject> sbList;
    private static Map<Integer, Subject> sbMap;

    public Cache()
    {
        initSubjectCache();
        initQuesCache();
        initExamCache();
        initPracCache();
    }

    public static synchronized void initSubjectCache()
    {
        SubjectDAO sd = new SubjectDAOImpl();
        sbList = sd.getAllSubject();
        sbMap = new HashMap<Integer, Subject>(); 
        for(int i=0;i<sbList.size();i++)
        {
            sbMap.put(sbList.get(i).getId(), sbList.get(i));
        }
        //System.out.println("initSubjectCache");
    }

    public static void initQuesCache()
    {
        initJudgeQuesCache();
        initSingleQuesCache();
        initMultiQuesCache();
    }

    public static synchronized void initMultiQuesCache()
    {
        mqMap = new HashMap<Integer, List<MultiQues>>();
        MultiQuesDAO md = new MultiQuesDAOImpl();
        List<MultiQues> mqList = md.getAllMultiQues();

        allMqMap = new HashMap<Integer, MultiQues>();
        for (int i = 0; i < mqList.size(); i++)
        {
            allMqMap.put(mqList.get(i).getId(), mqList.get(i));
        }

        for (int i = 0; i < sbList.size(); i++)
        {
            int subjectId = sbList.get(i).getId();
            List<MultiQues> sbMqList = new ArrayList<MultiQues>();
            for (int j = 0; j < mqList.size(); j++)
            {
                MultiQues mq = mqList.get(j);
                if (mq.getSubjectId() == subjectId && mq.getStatus() == 0)
                {
                    sbMqList.add(mq);
                }
            }
            mqMap.put(subjectId, mqList);
        }
        //System.out.println("initMultiCache");
    }

    public static synchronized void initSingleQuesCache()
    {
        sqMap = new HashMap<Integer, List<SingleQues>>();
        allSqMap = new HashMap<Integer, SingleQues>();
        SingleQuesDAO sd = new SingleQuesDAOImpl();
        List<SingleQues> sqList = sd.getAllSingleQues();
        for (int i = 0; i < sqList.size(); i++)
        {
            allSqMap.put(sqList.get(i).getId(), sqList.get(i));
        }
        for (int i = 0; i < sbList.size(); i++)
        {
            int subjectId = sbList.get(i).getId();
            List<SingleQues> sbSqList = new ArrayList<SingleQues>();
            for (int j = 0; j < sqList.size(); j++)
            {
                SingleQues sq = sqList.get(j);
                if (sq.getSubjectId() == subjectId && sq.getStatus() == 0)
                {
                    sbSqList.add(sq);
                }
            }
            sqMap.put(subjectId, sqList);
        }
        //System.out.println("initSingleCache");
    }

    public static synchronized void initJudgeQuesCache()
    {
        jqMap = new HashMap<Integer, List<JudgeQues>>();
        allJqMap = new HashMap<Integer, JudgeQues>();
        JudgeQuesDAO jd = new JudgeQuesDAOImpl();
        List<JudgeQues> jqList = jd.getAllJudgeQues();
        for (int i = 0; i < jqList.size(); i++)
        {
            allJqMap.put(jqList.get(i).getId(), jqList.get(i));
        }
        for (int i = 0; i < sbList.size(); i++)
        {
            int subjectId = sbList.get(i).getId();
            List<JudgeQues> sbJqList = new ArrayList<JudgeQues>();
            for (int j = 0; j < jqList.size(); j++)
            {
                JudgeQues jq = jqList.get(j);
                if (jq.getSubjectId() == subjectId && jq.getStatus() == 0)
                {
                    sbJqList.add(jq);
                }
            }
            jqMap.put(subjectId, sbJqList);
        }
        //System.out.println("initJudgeCache");
    }

    public static synchronized void initExamCache()
    {
        examCacheDate = new Date();
        ExamDAO ed = new ExamDAOImpl();
        recentExam = ed.getRecentEaxm("0", 30);
        todayExam = ed.queryExamByTime(TimeUtil.getTodayDate(), "0");
        List<Exam> examList = ed.getAllEaxm("0");
        examMap = new HashMap<Integer, Exam>();
        for (int i = 0; i < examList.size(); i++)
        {
            Exam exam = examList.get(i);
            examMap.put(exam.getId(), exam);
        }
        //System.out.println("initExamCache");
    }

    public static synchronized void initPracCache()
    {
        pracCacheDate = new Date();
        ExamDAO ed = new ExamDAOImpl();
        availablePrac = ed.getRecentEaxm("1", 0);
        //System.out.println("initPracCache");
    }

    public static Map<Integer, Exam> getExamMap()
    {
        return examMap;
    }

    public static void setExamMap(Map<Integer, Exam> examMap)
    {
        Cache.examMap = examMap;
    }

    public static List<Exam> getAvailablePrac()
    {
        return availablePrac;
    }

    public static void setAvailablePrac(List<Exam> availablePrac)
    {
        Cache.availablePrac = availablePrac;
    }

    public static Map<Integer, List<JudgeQues>> getJqMap()
    {
        return jqMap;
    }

    public static void setJqMap(Map<Integer, List<JudgeQues>> jqMap)
    {
        Cache.jqMap = jqMap;
    }

    public static Map<Integer, List<SingleQues>> getSqMap()
    {
        return sqMap;
    }

    public static void setSqMap(Map<Integer, List<SingleQues>> sqMap)
    {
        Cache.sqMap = sqMap;
    }

    public static Map<Integer, List<MultiQues>> getMqMap()
    {
        return mqMap;
    }

    public static void setMqMap(Map<Integer, List<MultiQues>> mqMap)
    {
        Cache.mqMap = mqMap;
    }

    public static List<Exam> getRecentPrac()
    {
        return availablePrac;
    }

    public static void setRecentPrac(List<Exam> recentPrac)
    {
        Cache.availablePrac = recentPrac;
    }

    public static List<Exam> getRecentExam()
    {
        return recentExam;
    }

    public static void setRecentExam(List<Exam> recentExam)
    {
        Cache.recentExam = recentExam;
    }

    public static List<Exam> getTodayExam()
    {
        return todayExam;
    }

    public static void setTodayExam(List<Exam> todayExam)
    {
        Cache.todayExam = todayExam;
    }

    public static Map<Integer, JudgeQues> getAllJqMap()
    {
        return allJqMap;
    }

    public static void setAllJqMap(Map<Integer, JudgeQues> allJqMap)
    {
        Cache.allJqMap = allJqMap;
    }

    public static Map<Integer, SingleQues> getAllSqMap()
    {
        return allSqMap;
    }

    public static void setAllSqMap(Map<Integer, SingleQues> allSqMap)
    {
        Cache.allSqMap = allSqMap;
    }

    public static Map<Integer, MultiQues> getAllMqMap()
    {
        return allMqMap;
    }

    public static void setAllMqMap(Map<Integer, MultiQues> allMqMap)
    {
        Cache.allMqMap = allMqMap;
    }

    public static List<Subject> getSbList()
    {
        return sbList;
    }

    public static void setSbList(List<Subject> sbList)
    {
        Cache.sbList = sbList;
    }

    public static Date getExamCacheDate()
    {
        return examCacheDate;
    }

    public static void setExamCacheDate(Date examCacheDate)
    {
        Cache.examCacheDate = examCacheDate;
    }

    public static Date getPracCacheDate()
    {
        return pracCacheDate;
    }

    public static void setPracCacheDate(Date pracCacheDate)
    {
        Cache.pracCacheDate = pracCacheDate;
    }
}