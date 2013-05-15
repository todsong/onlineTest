package com.pojo;

import java.io.Serializable;

public abstract class AbstractQues implements Serializable
{
    private static final long serialVersionUID = 8828243115787517673L;
    protected int id;
    protected String qName;
    protected String qAnswer;
    protected int subjectId;
    protected String hash;
    protected int status;
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
    
    public abstract void calculateHash();
}
