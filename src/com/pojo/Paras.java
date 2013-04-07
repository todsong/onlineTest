package com.pojo;

import java.io.Serializable;


public class Paras implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 3975728961689416571L;
    private String paraKey;
    private String paraValue;
    private String paraOptr;
    private String relation;
    public String getParaKey()
    {
        return paraKey;
    }
    public void setParaKey(String paraKey)
    {
        this.paraKey = paraKey;
    }
    public String getParaValue()
    {
        return paraValue;
    }
    public void setParaValue(String paraValue)
    {
        this.paraValue = paraValue;
    }
    public String getParaOptr()
    {
        return paraOptr;
    }
    public void setParaOptr(String paraOptr)
    {
        this.paraOptr = paraOptr;
    }
    public String getRelation()
    {
        return relation;
    }
    public void setRelation(String relation)
    {
        this.relation = relation;
    }
}