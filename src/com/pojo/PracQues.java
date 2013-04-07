package com.pojo;

import java.io.Serializable;

public class PracQues implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 6553898571581610115L;
    private int pracId;
    private int caseId;
    private String singleIdList;
    private String multiIdList;
    private String judgeIdList;
    public int getPracId()
    {
        return pracId;
    }
    public void setPracId(int pracId)
    {
        this.pracId = pracId;
    }
    public int getCaseId()
    {
        return caseId;
    }
    public void setCaseId(int caseId)
    {
        this.caseId = caseId;
    }
    public String getSingleIdList()
    {
        return singleIdList;
    }
    public void setSingleIdList(String singleIdList)
    {
        this.singleIdList = singleIdList;
    }
    public String getMultiIdList()
    {
        return multiIdList;
    }
    public void setMultiIdList(String multiIdList)
    {
        this.multiIdList = multiIdList;
    }
    public String getJudgeIdList()
    {
        return judgeIdList;
    }
    public void setJudgeIdList(String judgeIdList)
    {
        this.judgeIdList = judgeIdList;
    }
}
