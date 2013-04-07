package com.pojo;

import java.io.Serializable;

public class Subject implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 8563863354215129843L;
    private int id;
    private String name;
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
}
