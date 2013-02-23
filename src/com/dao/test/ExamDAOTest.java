package com.dao.test;

import java.util.Iterator;
import java.util.List;

import com.dao.ExamDAO;
import com.dao.impl.ExamDAOImpl;
import com.pojo.Exam;

public class ExamDAOTest
{
    private Exam exam;
    private ExamDAO ed = new ExamDAOImpl();

    public void addExamTest(Exam exam)
    {
        exam = new Exam();
        exam.setExamName("xx1qqqqqqqqqq");
        exam.setExamType("0");
        exam.setStartTime("20121203");
        System.out.println(ed.addExam(exam));
    }

    public void updateExamTest(int id, Exam exam)
    {
        exam = new Exam();
        exam.setExamName("xx2");
        exam.setStartTime("20121203");
        exam.setExamType("1");
        ed.updateExam(id, exam);
    }

    public void deleteExamTest(int id)
    {
        ed.deleteExam(id);
    }

    public void queryExamTestById(int id)
    {
        exam = ed.queryExamById(id);
        System.out.println(exam.getId() + " " + exam.getExamName() + " "
                + exam.getStartTime()+" "+exam.getExamType());
    }

    public void queryExamByDate(String date)
    {
        List<Exam> examList = ed.queryExamByTime(date,"1");
        for (Iterator<Exam> iter = examList.iterator(); iter.hasNext();)
        {
            Exam exam = iter.next();
            System.out.println(exam.getId() + " " + exam.getExamName() + " "
                    + exam.getStartTime()+" "+exam.getExamType());
        }
    }
    public void getRecentExam()
    {
        List<Exam> examList = ed.getRecentEaxm("1",0);
        for (Iterator<Exam> iter = examList.iterator(); iter.hasNext();)
        {
            Exam exam = iter.next();
            System.out.println(exam.getId() + " " + exam.getExamName() + " "
                    + exam.getStartTime()+" "+exam.getExamType());
        }
    }
    public void getAllExam()
    {
        List<Exam> examList = ed.getAllEaxm("0");
        for (Iterator<Exam> iter = examList.iterator(); iter.hasNext();)
        {
            Exam exam = iter.next();
            System.out.println(exam.getId() + " " + exam.getExamName() + " "
                    + exam.getStartTime()+" "+exam.getExamType());
        }
    }

    public int queryMaxByType(String type)
    {
     return    ed.getMaxQuesNumByType(type);
    }
    public static void main(String[] args)
    {
        ExamDAOTest ede = new ExamDAOTest();
        // ede.addExamTest(ede.exam);
         //ede.updateExamTest(3, ede.exam);
         //ede.deleteExamTest(3);
        // ede.queryExamTestById(13);
     //   ede.queryExamByDate("201304010000");
       //ede.getRecentExam();
        System.out.print(ede.queryMaxByType("judge"));
    }
}
