package com.pojo;

import java.io.Serializable;

public class UserExam implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -362157682418546871L;
    private int examId;
    private String userId;
    private int score;
    private String singleIdList;
    private String singleAnswerList;
    private String judgeIdList;
    private String judgeAnswerList;
    private String multiIdList;
    private String multiAnswerList;
    public int getExamId()
    {
        return examId;
    }
    public void setExamId(int examId)
    {
        this.examId = examId;
    }
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public int getScore()
    {
        return score;
    }
    public void setScore(int score)
    {
        this.score = score;
    }
    public String getSingleIdList()
    {
        return singleIdList;
    }
    public void setSingleIdList(String singleIdList)
    {
        this.singleIdList = singleIdList;
    }
    public String getSingleAnswerList()
    {
        return singleAnswerList;
    }
    public void setSingleAnswerList(String singleAnswerList)
    {
        this.singleAnswerList = singleAnswerList;
    }
    public String getJudgeIdList()
    {
        return judgeIdList;
    }
    public void setJudgeIdList(String judgeIdList)
    {
        this.judgeIdList = judgeIdList;
    }
    public String getJudgeAnswerList()
    {
        return judgeAnswerList;
    }
    public void setJudgeAnswerList(String judgeAnswerList)
    {
        this.judgeAnswerList = judgeAnswerList;
    }
    public String getMultiIdList()
    {
        return multiIdList;
    }
    public void setMultiIdList(String multiIdList)
    {
        this.multiIdList = multiIdList;
    }
    public String getMultiAnswerList()
    {
        return multiAnswerList;
    }
    public void setMultiAnswerList(String multiAnswerList)
    {
        this.multiAnswerList = multiAnswerList;
    }
}
