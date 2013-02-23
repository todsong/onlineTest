package com.dao;

import com.pojo.Admin;

public interface AdminDAO
{
    public Admin queryAdminByPwd(String id, String passwd);
    public int updateAdminById(String id, Admin admin);
}
