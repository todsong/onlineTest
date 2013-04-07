package com.pojo;

import java.io.Serializable;

public class Exam implements Serializable
{
    private static final long serialVersionUID = 7971198495558427799L;
    private int id;
	private String examName;
	private String startTime;
	private String endTime;
	private String singleNum;
	private int singleScore;
	private String mutliNum;
	private int mutliScore;
	private String judgeNum;
	private int judgeScore;
	private int passScore;
	private String examType;
	
	//2013年3月3日，支持一exam多subject
	private String subjectId;
	public int getId() {
		return id;
	}
	public String getSubjectId()
    {
        return subjectId;
    }
    public void setSubjectId(String subjectId)
    {
        this.subjectId = subjectId;
    }
    public String getSingleNum()
    {
        return singleNum;
    }
    public void setSingleNum(String singleNum)
    {
        this.singleNum = singleNum;
    }
    public String getMutliNum()
    {
        return mutliNum;
    }
    public void setMutliNum(String mutliNum)
    {
        this.mutliNum = mutliNum;
    }
    public String getJudgeNum()
    {
        return judgeNum;
    }
    public void setJudgeNum(String judgeNum)
    {
        this.judgeNum = judgeNum;
    }
    public void setId(int id) {
		this.id = id;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getSingleScore() {
		return singleScore;
	}
	public void setSingleScore(int singleScore) {
		this.singleScore = singleScore;
	}
	public int getMutliScore() {
		return mutliScore;
	}
	public void setMutliScore(int mutliScore) {
		this.mutliScore = mutliScore;
	}
	public int getJudgeScore() {
		return judgeScore;
	}
	public void setJudgeScore(int judgeScore) {
		this.judgeScore = judgeScore;
	}
	public int getPassScore() {
		return passScore;
	}
	public void setPassScore(int passScore) {
		this.passScore = passScore;
	}
    public String getExamType()
    {
        return examType;
    }
    public void setExamType(String examType)
    {
        this.examType = examType;
    }
}
