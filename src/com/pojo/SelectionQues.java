package com.pojo;

import com.util.MD5Util;

public class SelectionQues extends AbstractQues
{
    private static final long serialVersionUID = -6949651579749859012L;
    protected String optionA;
    protected String optionB;
    protected String optionC;
    protected String optionD;
    protected String optionE;
    protected int optNum;
    public String getOptionA()
    {
        return optionA;
    }
    public void setOptionA(String optionA)
    {
        this.optionA = optionA;
    }
    public String getOptionB()
    {
        return optionB;
    }
    public void setOptionB(String optionB)
    {
        this.optionB = optionB;
    }
    public String getOptionC()
    {
        return optionC;
    }
    public void setOptionC(String optionC)
    {
        this.optionC = optionC;
    }
    public String getOptionD()
    {
        return optionD;
    }
    public void setOptionD(String optionD)
    {
        this.optionD = optionD;
    }
    public String getOptionE()
    {
        return optionE;
    }
    public void setOptionE(String optionE)
    {
        this.optionE = optionE;
    }
    public int getOptNum()
    {
        return optNum;
    }
    public void setOptNum(int optNum)
    {
        this.optNum = optNum;
    }
    
    @Override
    public void calculateHash()
    {
        StringBuilder sb = new StringBuilder(getSubjectId()
                + getqName() + getOptionA() + getOptionB()
                + getOptionC() + getOptionD());
        if (optionE != null)
        {
            sb.append(getOptionE());
        }
        hash = MD5Util.getMD5(sb.toString());
    }
}
