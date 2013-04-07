package com.pojo;

import java.io.Serializable;

public class User implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 3245323721295236594L;
    private String id;
    private String passwd;
    private String name;
    private String telephone;
    private int dept;
    private String status;//0激活，1未激活
    
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getDept() {
        return dept;
    }
    public void setDept(int dept) {
        this.dept = dept;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    
}
