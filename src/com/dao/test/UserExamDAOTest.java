package com.dao.test;

import java.util.Iterator;
import java.util.List;

import com.dao.ExamDAO;
import com.dao.UserExamDAO;
import com.dao.impl.ExamDAOImpl;
import com.dao.impl.UserExamDAOImpl;
import com.pojo.Exam;
import com.pojo.UserExam;

public class UserExamDAOTest
{
    private UserExam ue;
    private UserExamDAO ued = new UserExamDAOImpl();

    public void addExamTest(UserExam ue)
    {
        ue = new UserExam();
        ue.setExamId(18);
        ue.setUserId("2");
        ue.setScore(-1);
        System.out.println(ued.addNewUserExam(ue));
    }

    public void updateExamTest()
    {
        UserExam ue = new UserExam();
        ue.setExamId(18);
        ue.setUserId("2");
        ue.setScore(10);
        ue.setJudgeAnswerList("xxxx");
        ued.updateUserExamResult(ue);
    }

    public void queryUserExamTestById(int id)
    {
        List<UserExam> ueList = ued.queryUserExamByExamId(id);
        for (Iterator<UserExam> iter = ueList.iterator(); iter.hasNext();)
        {
            UserExam ue = iter.next();
            System.out.println(ue.getUserId() + " " + ue.getScore() + " "
                    + ue.getJudgeAnswerList());
        }
    }
    public void queryUserExamTestByUser(String userId)
    {
        List<UserExam> ueList = ued.queryUserExamByUserId(userId);
        for (Iterator<UserExam> iter = ueList.iterator(); iter.hasNext();)
        {
            UserExam ue = iter.next();
            System.out.println(ue.getUserId() + " " + ue.getScore() + " "
                    + ue.getJudgeAnswerList());
        }
    }
    
    public void queryUniqueUserExamTest()
    {
        UserExam ue = ued.queryUniqueUserExam(181, "2");
        
            System.out.println(ue.getUserId() + " " + ue.getScore() + " "
                    + ue.getJudgeAnswerList());
        
    }
    public static void main(String[] args)
    {
        UserExamDAOTest ede = new UserExamDAOTest();
         //ede.addExamTest(ede.ue);
         //ede.updateExamTest();
         //ede.deleteExamTest(3);
         //ede.queryUserExamTestById(18);
      //  ede.queryUserExamTestByUser("2");
     
//        ede.queryUniqueUserExamTest();
        System.out.println(ede.ued.queryNoQuesId("judge", "45"));
    }
}