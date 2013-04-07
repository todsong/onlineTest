package com.dao;

import com.pojo.Admin;

public interface AdminDAO
{
    Admin queryAdminById(String id);
    int updateAdminById(String id, Admin admin);
}
