package com.dao;

import java.util.List;

import com.pojo.Dept;

public interface DeptDAO
{
    public int addDept(Dept sj);
    public int deleteDeptById(int id);
    public int updateDeptById(int id, Dept sj);
    public List<Dept> getAllDept();
    public Dept quertDeptById(int id);
    public Dept quertDeptByName(String name);
}
