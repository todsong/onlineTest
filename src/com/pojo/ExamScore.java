package com.pojo;

import java.io.Serializable;

public class ExamScore implements Serializable
{
    private static final long serialVersionUID = 6966263016110465370L;
    private String userId;
	private String userName;
	private int dept;
	private int score;
	
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public int getDept()
	{
		return dept;
	}
	public void setDept(int dept)
	{
		this.dept = dept;
	}
	public int getScore()
	{
		return score;
	}
	public void setScore(int score)
	{
		this.score = score;
	}
}
