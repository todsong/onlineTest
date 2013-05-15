package com.dao;

import java.util.List;

import com.pojo.SingleQues;

public interface SingleQuesDAO
{
    public int addSingleQues(SingleQues jq);
    public int addSingleQuesList(List jqList);
    public int deleteSingleQues(int id);
    public int updateSingleQues(int id, SingleQues jq);
    public SingleQues querySingleQuesById(int id);
    public List<SingleQues> querySingleQuesBySubject(int subjectId);
    public List<SingleQues> getAllAvailableBySubjectId(int subjectId);
    public List<SingleQues> getAllSingleQues();
    public List<SingleQues> getAllSingleQuesByStatus(int status);
    public int getCountSum();
    public int checkUnique(String hash);//-1表示没找到，其他数字为匹配到的id
}
