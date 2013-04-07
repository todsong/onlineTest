package com.pojo;

import java.io.Serializable;

public class UserScore implements Serializable
{
	/**
     * 
     */
    private static final long serialVersionUID = 8947173573915167113L;
    private String examName;
	private String examDate;
	private int passScore;
	private int actScore;
	public String getExamName()
	{
		return examName;
	}
	public void setExamName(String examName)
	{
		this.examName = examName;
	}
	public String getExamDate()
	{
		return examDate;
	}
	public void setExamDate(String examDate)
	{
		this.examDate = examDate;
	}
	public int getPassScore()
	{
		return passScore;
	}
	public void setPassScore(int passScore)
	{
		this.passScore = passScore;
	}
	public int getActScore()
	{
		return actScore;
	}
	public void setActScore(int actScore)
	{
		this.actScore = actScore;
	}
}
