package com.dao;

import java.util.List;

import com.pojo.UserExam;

public interface UserExamDAO extends GenericDAO
{
    public int addNewUserExam(UserExam ue);
    public int updateUserExamResult(UserExam ue);
    public List<UserExam> queryUserExamByUserId(String userId);
    public List<UserExam> queryUserExamByExamId(int examId);
    public UserExam queryUniqueUserExam(int examId, String userId);
    public boolean queryNoQuesId(String type, String id);
    public void updateUserExamAnswer(UserExam ue);
}
