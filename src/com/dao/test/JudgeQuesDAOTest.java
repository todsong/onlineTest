package com.dao.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dao.JudgeQuesDAO;
import com.dao.impl.JudgeQuesDAOImpl;
import com.pojo.JudgeQues;

public class JudgeQuesDAOTest
{
    private JudgeQues jq;
    private List<JudgeQues> jl;
    private JudgeQuesDAO jd = new JudgeQuesDAOImpl();
    
    public void addJudgeQuesTest()
    {
        jq = new JudgeQues();
        jq.setqAnswer("tasd");
        jq.setqName("asd");
        jd.addJudgeQues(jq);
    }
    public void addJudgeQuesListTest()
    {
        jl = new ArrayList<JudgeQues>();
        JudgeQues j1 = new JudgeQues();
        JudgeQues j2 = new JudgeQues();
        j1.setqAnswer("t");
        j1.setqName("q1");
        j2.setqAnswer("f");
        j2.setqName("q2");
        jl.add(j1);
        jl.add(j2);
        jd.addJudgeQuesList(jl);
    }
    public void deleteJudegeQues(int id)
    {
        jd.deleteJudgeQues(id);
    }
    public void updateJudegeQues()
    {
        jq = new JudgeQues();
        jq.setqAnswer("q");
        jq.setqName("xxxx");
        jd.updateJudgeQues(2, jq);
    }
    public void queryJudgeQuesById(int id)
    {
        jq = jd.queryJudgeQuesById(id);
        System.out.println(jq.getqName()+" "+jq.getqAnswer());
    }
    public void getAll()
    {
        List<JudgeQues> jdList = jd.getAllJudgeQues();
        for (Iterator<JudgeQues> iter = jdList.iterator(); iter.hasNext();)
        {
            JudgeQues jq = iter.next();
            System.out.println(jq.getId() + " " + jq.getqName() + " "
                    + jq.getqAnswer());
        }
    }
    public static void main(String[] args)
    {
        JudgeQuesDAOTest jt = new JudgeQuesDAOTest();
//        jt.addJudgeQuesTest();
     //   jt.addJudgeQuesListTest();
        //jt.deleteJudegeQues(1);
        //jt.updateJudegeQues();
        //jt.queryJudgeQuesById(2);
        //jt.getAll();
    }
}
