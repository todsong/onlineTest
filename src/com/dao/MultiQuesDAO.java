package com.dao;

import java.util.List;

import com.pojo.JudgeQues;
import com.pojo.MultiQues;

public interface MultiQuesDAO
{
    public int addMultiQues(MultiQues jq);
    public int addMultiQuesList(List jqList);
    public int deleteMultiQues(int id);
    public int updateMultiQues(int id, MultiQues jq);
    public MultiQues queryMultiQuesById(int id);
    public List<MultiQues> queryMultiQuesBySubject(int subjectId);
    public List<MultiQues> getAllMultiQues();
    public List<MultiQues> getAllAvailableBySubjectId(int subjectId);
    public int getCountSum();
    public int checkUnique(String hash);//-1表示没找到，其他数字为匹配到的id
}
