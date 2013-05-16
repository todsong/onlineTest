package com.dao;

import java.util.List;

import com.pojo.Dept;
import com.pojo.Subject;

public interface SubjectDAO extends GenericDAO
{
    public int addSubject(Subject sj);
    public int deleteSubjectById(int id);
    public int updateSubjectById(int id, Subject sj);
    public List<Subject> getAllSubject();
    public Subject quertSubjectById(int id);
}
