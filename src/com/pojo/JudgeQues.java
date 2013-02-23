package com.pojo;

import java.io.Serializable;

public class JudgeQues implements Serializable
{
    private static final long serialVersionUID = -1735917878808886298L;
    private int id;
    private String qName;
    private String qAnswer;
    private int subjectId;
    private String hash;
    private int status;
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getqName()
    {
        return qName;
    }

    public void setqName(String qName)
    {
        this.qName = qName;
    }

    public String getqAnswer()
    {
        return qAnswer;
    }

    public void setqAnswer(String qAnswer)
    {
        this.qAnswer = qAnswer;
    }

    public int getSubjectId()
    {
        return subjectId;
    }

    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }

    public String getHash()
    {
        return hash;
    }

    public void setHash(String hash)
    {
        this.hash = hash;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

}
