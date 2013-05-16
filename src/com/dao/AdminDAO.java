package com.dao;

import com.pojo.Admin;

public interface AdminDAO extends GenericDAO
{
    Admin queryAdminById(String id);
    int updateAdminById(String id, Admin admin);
}
