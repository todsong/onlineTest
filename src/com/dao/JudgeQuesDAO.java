package com.dao;

import java.util.List;

import com.pojo.JudgeQues;
import com.pojo.SingleQues;

public interface JudgeQuesDAO extends GenericDAO
{
    public int addJudgeQues(JudgeQues jq);
    public int addJudgeQuesList(List<JudgeQues> jqList);
    public int deleteJudgeQues(int id);
    public int updateJudgeQues(int id, JudgeQues jq);
    public JudgeQues queryJudgeQuesById(int id);
    public List<JudgeQues> queryJudgeQuesBySubject(int subjectId);
    public List<JudgeQues> getAllJudgeQues();
    public List<JudgeQues> getAllAvailableBySubjectId(int subjectId);
    public int getCountSum();
    public int checkUnique(String hash);//-1表示没找到，其他数字为匹配到的id
}
