package com.dao;

import com.pojo.Admin;

public interface AdminDAO
{
    Admin queryAdminByPwd(String id, String passwd);
    int updateAdminById(String id, Admin admin);
}
