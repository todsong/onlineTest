package com.pojo;

import java.io.Serializable;

public class Admin implements Serializable
{
    private static final long serialVersionUID = 184762520441194622L;
    private String id;
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getPasswd()
    {
        return passwd;
    }
    public void setPasswd(String passwd)
    {
        this.passwd = passwd;
    }
    private String passwd;
}
